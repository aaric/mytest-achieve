package com.github.aaric.achieve.springjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * JdbcTemplate测试类
 *
 * @author Aaric, created on 2017-05-25T17:39.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcTemplateTest {

    @Autowired
    protected JdbcTemplate jdbcTemplate;

/*
    CREATE TABLE `t_user`(
      `id` INT UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'ID',
      `name` VARCHAR(50) COMMENT 'Name',
      `email` VARCHAR(100) COMMENT 'Email',
      `password` VARCHAR(50) COMMENT 'Password',
      PRIMARY KEY (`id`)
    ) COMMENT='User';
*/
    @Test
    public void testInsert() throws Exception {
        String sql = "insert into t_user (name, email, password) values ('springjdbc', 'springjdbc@qq.com', 'springjdbc')";
        jdbcTemplate.execute(sql);
    }
}
