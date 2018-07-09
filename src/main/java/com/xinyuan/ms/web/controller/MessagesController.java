package com.xinyuan.ms.web.controller;


import com.xinyuan.ms.common.account.MessagesAccount;
import com.xinyuan.ms.common.util.MD5Util;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.entity.UserRole;
import com.xinyuan.ms.mapper.UserRepository;
import com.xinyuan.ms.service.UserRoleService;
import com.xinyuan.ms.service.UserService;
import com.xinyuan.ms.web.request.MessagesRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Author: hwz
 * @Date: Created in 15:15 2018/4/10.
 */
@Api(description = "短信验证码")
@RestController
@RequestMapping("/messages")
public class MessagesController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserRepository userRepository;


    @ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
    @ApiImplicitParam(name = "messagesRequest", value = "messagesRequest", required = true, dataType = "MessagesRequest")
    @RequestMapping(value = "sendMessage", method = RequestMethod.POST)
    public ResponseEntity<String> sendSms(@RequestBody MessagesRequest messagesRequest) throws UnsupportedEncodingException {
        Integer x_ac = 10;
        HttpURLConnection httpconn = null;
        String result = "Error";
        StringBuilder sb = new StringBuilder();
        String mobile = messagesRequest.getMobile();
        String password = code();
        String content = "【易健在线】验证码：" + password + "。欢迎使用易健在线，验证码有效时间为3分钟，如非本人操作请忽略本条短信。";
        sb.append("http://service.winic.org:8009/sys_port/gateway/index.asp?");

        //存到数据库
        User user = userService.findByUsername(mobile);
        if (user != null) {
            user.setPassword(MD5Util.MD5(password).toLowerCase());
            userService.updateUser(user);
        } else {
            user = new User();
            user.setUsername(mobile);
            user.setPassword(MD5Util.MD5(password).toLowerCase());
            user.setPhone(mobile);
            user.setNickname(mobile);
            user = userService.saveUser(user);
            UserRole userRole = new UserRole();

            userRole.setUid(user.getId());

            userRole.setRid((long) 3);

            userRoleService.save(userRole);
        }

        //1分钟过期
        timerOverdue(user.getId());

        try {
            sb.append("id=").append(URLEncoder.encode(MessagesAccount.X_ID, "gb2312"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        sb.append("&pwd=").append(MessagesAccount.X_PWD);
        sb.append("&to=").append(mobile);
        sb.append("&content=").append(URLEncoder.encode(content, "gb2312"));
        sb.append("&time=");
        try {
            URL url = new URL(sb.toString());
            httpconn = (HttpURLConnection) url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(httpconn.getInputStream()));
            result = rd.readLine();
            rd.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpconn != null) {
                httpconn.disconnect();
            }

        }
        return ResponseEntity.ok(result);
    }

    public static String code() {
        String code = "";
        for (int i = 1; i <= 6; i++) {
            Random r = new Random();
            code += r.nextInt(9) + 1 + "";
        }
        return code;
    }

    public void timerOverdue(Long userId) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("save").build());
        executorService.schedule(() -> {

            User user = userService.get(userId);

            user.setPassword(MD5Util.MD5(code()).toLowerCase());

            userRepository.saveAndFlush(user);


        }, 3, TimeUnit.MINUTES);
        executorService.shutdown();
    }

}
