package com.github.aaric.achieve.springjdbc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * UserService测试类
 *
 * @author Aaric, created on 2017-05-26T10:26.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class UserRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void testAdd() throws Exception {
        User user = new User("root", "root@qq.com", "root");
        System.out.println(userRepository.add(user));
    }

    @Test
    public void testRemove() throws Exception {
        System.out.println(userRepository.remove(3));
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User("testmq", "testmq@qq.com", "testmq");
        user.setId(1);
        System.out.println(userRepository.update(user));
    }

    @Test
    public void testGetOne() throws Exception {
        System.out.println(userRepository.getOne(1));
    }

    @Test
    public void testQueryList() throws Exception {
        System.out.println(userRepository.queryList());
    }
}
