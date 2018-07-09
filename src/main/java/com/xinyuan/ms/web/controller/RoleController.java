package com.xinyuan.ms.web.controller;
import com.xinyuan.ms.common.util.ResultUtil;
import com.xinyuan.ms.common.web.Message;
import com.xinyuan.ms.common.web.PageBody;
import com.xinyuan.ms.entity.Role;
import com.xinyuan.ms.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: hwz
 * @Date: Created in 15:15 2018/4/10.
 */
@Api(description = "角色管理")
@RestController
@RequestMapping("/backstage/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @ApiOperation(value = "新增", notes = "新增")
    @ApiImplicitParam(name = "role", value = "role", required = true, dataType = "Role")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public ResponseEntity<Message> saveUser(@RequestBody Role role) {
        Message message = roleService.saveRole(role);

        return ResponseEntity.ok(message);
    }

    @ApiOperation(value = "删除角色", notes = "删除角色")
    @ApiImplicitParam(name = "ids", value = "角色ID", required = true, dataType = "List")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity<Message> delete(@RequestBody List<Long> ids) {
        try {
            roleService.removeRole(ids);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(ResultUtil.success());
    }


    @ApiOperation(value = "更新角色", notes = "更新角色")
    @ApiImplicitParam(name = "role", value = "role", required = true, dataType = "Role")
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<Message> update(@RequestBody Role role) {
        roleService.update(role);
        return ResponseEntity.ok(ResultUtil.success());
    }

    /**
     * 条件查询
     *
     * @param pageBody
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "条件查询")
    @ApiImplicitParam(name = "pageBody", value = "pageBody", required = false, dataType = "PageBody")
    @RequestMapping(value = "query", method = RequestMethod.POST)
    public ResponseEntity<Page<Role>> query(@RequestBody PageBody pageBody) {
        Page<Role> page = null;
        try {
            page = roleService.query(pageBody);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(page);
    }

}
