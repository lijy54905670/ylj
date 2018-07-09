package com.xinyuan.ms.config.security;

import com.xinyuan.ms.entity.User;
import com.xinyuan.ms.service.UserRoleService;
import com.xinyuan.ms.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

/**
 * @author liang
 */
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * 根据用户名获取登录用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户名：" + username + "不存在！");
        }

        user.setLoginCount(user.getLoginCount() + 1);
        user.setLoginTime(new Date());
        userService.updateUser(user);

        Collection<SimpleGrantedAuthority> collection = new HashSet<>();

        Iterator<String> iterator = userRoleService.findRoles(user.getId()).iterator();
        while (iterator.hasNext()) {
            collection.add(new SimpleGrantedAuthority(iterator.next()));
        }

        return new org.springframework.security.core.userdetails.User(username, user.getPassword(), collection);
    }
}
