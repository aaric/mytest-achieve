package com.github.aaric.achieve.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * UserRepositoryTest
 *
 * @author Aaric, created on 2017-05-26T11:46.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void testInsert() throws Exception {
        User user = new User("mybatis", "mybatis@qq.com", "mybatis");
        System.out.println(userRepository.insert(user));
    }

    @Test
    public void testQueryList() throws Exception {
        System.out.println(userRepository.queryList());
    }

    @Test
    public void testGetOne() throws Exception {
        System.out.println(userRepository.getOne(1));
    }

    @Test
    public void testAdd() throws Exception {
        User user = new User("mybatis", "mybatis@qq.com", "mybatis");
        System.out.println(userRepository.add(user));
    }

    @Test
    public void testUpdate() throws Exception {
        System.out.println(userRepository.update("test", 1));
    }

    @Test
    public void testDelete() throws Exception {
        System.out.println(userRepository.delete(5));
    }
}
