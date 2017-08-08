package com.github.aaric.achieve.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * MyRealm2
 *
 * @author Aaric, created on 2017-08-08T14:15.
 * @since 1.0-SNAPSHOT
 */
public class MyRealm2 implements Realm {

    @Override
    public String getName() {
        return "myRealm1";
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        // 仅支持UsernamePasswordToken类型的Token
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 用户名
        String username = (String) token.getPrincipal();
        if(!"admin".equals(username)){
            // 账号不存在
            throw new UnknownAccountException();
        }

        // 密码
        String password = new String((char[]) token.getCredentials());
        if(!"admin".equals(password)) {
            // 密码错误
            throw new IncorrectCredentialsException();
        }

        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
