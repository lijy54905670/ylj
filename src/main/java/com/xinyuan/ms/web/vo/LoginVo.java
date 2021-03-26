package com.xinyuan.ms.web.vo;

import lombok.Data;

import java.util.Set;

@Data
public class LoginVo {
    protected Set<String> roles;
    protected Set<String> stringPermissions;
}
