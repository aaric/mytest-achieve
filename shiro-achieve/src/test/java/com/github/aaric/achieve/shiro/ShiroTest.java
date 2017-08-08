package com.github.aaric.achieve.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

/**
 * ShiroTest
 *
 * @author Aaric, created on 2017-08-08T11:44.
 * @since 1.0-SNAPSHOT
 */
public class ShiroTest {

    @Test
    public void testLogin() {
        // 1、获取SecurityManager工厂，此处使用Ini配置文件初始化SecurityManager
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");

        // 2、得到SecurityManager实例 并绑定给SecurityUtils
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        // 3、得到Subject及创建用户名/密码身份验证Token（即用户身份/凭证）
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        token.setRememberMe(true);

        try {
            // 4、登录，即身份验证
            subject.login(token);
        } catch (AuthenticationException e) {
            /**
             * 常见的如：
             *   DisabledAccountException（禁用的帐号）
             *   LockedAccountException（锁定的帐号）
             *   UnknownAccountException（错误的帐号）
             *   ExcessiveAttemptsException（登录失败次数过多）
             *   IncorrectCredentialsException （错误的凭证）
             *   ExpiredCredentialsException（过期的凭证）
             */
            // 5、身份验证失败
            //e.printStackTrace();
            System.out.println("logout");
        }

        // 判断用户是否登陆
        Assert.assertEquals(true, subject.isAuthenticated());

        // 6、退出
        subject.logout();
    }

    @Test
    public void testLogin2() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        token.setRememberMe(true);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("logout");
        }

        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }

    @Test
    public void testLogin3() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-multi-realm.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
        token.setRememberMe(true);

        try {
            subject.login(token);
        } catch (AuthenticationException e) {
            System.out.println("logout");
        }

        Assert.assertEquals(true, subject.isAuthenticated());
        subject.logout();
    }
}
