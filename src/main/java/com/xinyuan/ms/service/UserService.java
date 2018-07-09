package com.xinyuan.ms.service;

import com.alibaba.druid.support.json.JSONUtils;
import com.xinyuan.ms.common.service.PageBean;
import com.xinyuan.ms.common.service.SelectParam;
import com.xinyuan.ms.common.util.HttpUtils;
import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.common.web.PageBody;
import com.xinyuan.ms.config.api.wx.impl.WXPayConfigImpl;
import com.xinyuan.ms.entity.Role;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.entity.UserRole;
import com.xinyuan.ms.exception.UserException;
import com.xinyuan.ms.mapper.UserRepository;
import com.xinyuan.ms.web.request.BoundWXRequest;
import com.xinyuan.ms.web.request.SaveUserRequest;
import com.xinyuan.ms.web.vo.UserShowVo;
import com.xinyuan.ms.web.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import java.io.InputStream;
import java.util.*;

/**
 * @author liang
 */
@Service
public class UserService extends BaseService<UserRepository, User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;



    public User findByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    public User findById(Long id) {
        return userRepository.findByIdAndDeleted(id, 0);
    }


    /**
     * 新增异常处理
     *
     * @param saveUserRequest
     */
    @Transactional
    public Message saveUserInfor(SaveUserRequest saveUserRequest) {
        if (userRepository.findByUsername(saveUserRequest.getUser().getUsername()) != null) {
            throw UserException.USER_NAME_IS_REPEAT;
        }
        User user = save(saveUserRequest.getUser());

        Role role = roleService.findById(saveUserRequest.getRole());

        if (role != null) {
            UserRole userRole = new UserRole();
            userRole.setUid(user.getId());
            userRole.setRid(role.getId());

            userRoleService.save(userRole);
        }

        return ResultUtil.success("新增用户成功");
    }

    /**
     * 新增异常处理
     *
     * @param user
     */
    @Transactional
    public User saveUser(User user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw UserException.USER_NAME_IS_REPEAT;
        }


        return save(user);
    }

    public void remove(User user) {
        userRepository.delete(user);
    }


    /**
     * 更新异常处理
     * @param saveUserRequest
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfor(SaveUserRequest saveUserRequest) {
        if (!StringUtils.isEmpty(saveUserRequest.getUser().getUsername())) {
            User username = userRepository.findByUsername(saveUserRequest.getUser().getUsername());
            if (username != null) {
                if (!saveUserRequest.getUser().getId().equals(username.getId())) {
                    throw UserException.USER_NAME_IS_REPEAT;
                }
            }
        }

        update(saveUserRequest.getUser());

        if (saveUserRequest.getRole() != null) {
            UserRole userRole = userRoleService.findByUid(saveUserRequest.getUser().getId());
            userRole.setRid(saveUserRequest.getRole());

            userRoleService.update(userRole);
        }
    }

    /**
     * 更新异常处理
     *
     * @param user
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        User username = userRepository.findByUsername(user.getUsername());
        if (username != null) {
            if (!user.getId().equals(username.getId())) {
                throw UserException.USER_NAME_IS_REPEAT;
            }
        }
        update(user);
    }

    public void removeUser(List<Long> ids) {
        for (Long i : ids) {
            super.remove(i);
        }
    }



    public Page findUser(PageBody pageBody) {
        Page page;

        Sort sort = sort(pageBody.getOrder());
        if (pageBody.getPageBean() != null) {
            page = findByPage(new PageBean(pageBody.getPageBean().getPageNumber(), pageBody.getPageBean().getPageSize(), sort), getSelectParamList(pageBody.getConditions()), false);
        } else {
            page = findByPage(new PageBean(1, Integer.MAX_VALUE, sort), getSelectParamList(pageBody.getConditions()), false);
        }

        return page;
    }

    public Page findByPage(Pageable pageable, List<SelectParam> selectParams, Boolean hasRole) {

        Page returnValue;
        Page page = findByCondition(pageable.getPageNumber(), pageable.getPageSize(), pageable.getSort(), selectParams);
        List<User> userList = page.getContent();
        returnValue = new PageImpl(userShowTransfer(userList, hasRole), pageable, page.getTotalElements());

        return returnValue;
    }

    public List<UserShowVo> userShowTransfer(List<User> userList, Boolean hasRole) {
        List<UserShowVo> returnValue = new ArrayList<>();

        if (!CollectionUtils.isEmpty(userList)) {
            for (User user : userList) {
                returnValue.add(userShowTransfer(user, hasRole));
            }
        }
        return returnValue;
    }

    public UserShowVo userShowTransfer(User user, Boolean hasRole) {
        UserShowVo userShowVo = new UserShowVo();
        UserVo userVo = new UserVo();
        if (user != null) {
            BeanUtils.copyProperties(user, userVo);
            userShowVo.setUserVo(userVo);
        }
        return userShowVo;

    }



    @Transactional(rollbackFor = Exception.class)
    public Message boundWX(BoundWXRequest boundWXRequest) {

        WXPayConfigImpl wxPayConfig;
        try {
            User user = get(boundWXRequest.getUserId());

            if (!StringUtils.isEmpty(user.getOpenId())) {
                return ResultUtil.success();
            }

            wxPayConfig = WXPayConfigImpl.getInstance();

            String secret = "7fb539abb9a464835260d0e4037be905";

            String OPEN_ID = "openid";
            String openId = null;

            Map<String, String> headers = new HashMap<>(5);

            Map<String, String> querys = new HashMap<>(5);
            querys.put("appid", wxPayConfig.getAppID());
            querys.put("secret", secret);
            querys.put("code", boundWXRequest.getCode());
            querys.put("grant_type", "authorization_code");

            String getopenidUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
            HttpResponse response = HttpUtils.doGet(getopenidUrl, "", "GET", headers, querys);
            InputStream content = response.getEntity().getContent();
            String authString = HttpUtils.inputSteam2String(content);

            Map<String, String> authMap = (Map<String, String>) JSONUtils.parse(authString);

            if (authMap.containsKey(OPEN_ID)) {
                openId = authMap.get(OPEN_ID);
            }

            System.out.println("=========================" + openId);


            user.setOpenId(openId);
            updateUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
}
