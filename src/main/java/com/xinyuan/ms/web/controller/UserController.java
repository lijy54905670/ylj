package com.xinyuan.ms.web.controller;

import com.xinyuan.ms.common.util.MD5Util;
import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.common.web.PageBody;
import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.service.UserService;
import com.xinyuan.ms.web.request.BoundWXRequest;
import com.xinyuan.ms.web.request.SaveUserRequest;
import com.xinyuan.ms.web.vo.UserShowVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: hwz
 * @Date: Created in 15:15 2018/4/10.
 */
@Api(description = "用户管理")
@RestController
@RequestMapping("/backstage/user")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "微信绑定接口", notes = "绑定微信")
    @RequestMapping(value = "boundWX", method = RequestMethod.POST)
    public ResponseEntity<Message> boundWX(@RequestBody BoundWXRequest boundWXRequest) {
        Message message = null;
        try {
            message = userService.boundWX(boundWXRequest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(message);
    }



    @ApiOperation(value = "重置密码", notes = "重置密码")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "List")
    @RequestMapping(value = "resetPassword", method = RequestMethod.POST)
    public ResponseEntity<Message> resetPassword(@RequestBody List<Long> id) {
        Message message = ResultUtil.success("重置密码成功");
        try {
            for (long i : id) {
                User user = userService.findById(i);
                user.setPassword(MD5Util.MD5("password").toLowerCase());
                userService.updateUser(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = ResultUtil.error(2003, "重置密码失败");
        }
        return ResponseEntity.ok(message);
    }
}
