package com.github.aaric.achieve.hbase;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.MessageFormat;
import java.util.List;

/**
 * UserRepository测试类
 *
 * @author Aaric, created on 2017-05-25T10:07.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
public class UserRepositoryTest {

    @Autowired
    protected UserUtils userUtils;

    @Autowired
    protected UserRepository userRepository;

    @Test
    public void testCreateTable() throws Exception {
        userUtils.initialize();
    }

    @Test
    public void testFindAll() throws Exception {
        List<User> userList = userRepository.findAll();
        for(User user: userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testGet() throws Exception {
        System.out.println(userRepository.get(1));
    }

    @Test
    public void testSave() throws Exception {
        String email;
        String password;
        for (int i = 1; i <= 100000; i++) {
            email = "6688" + MessageFormat.format("{0,number,000000}@qq.com", new Object[]{i});
            password = MessageFormat.format("{0,number,000000}", new Object[]{i});
            System.out.println(userRepository.save(i, "user" + i, email, DigestUtils.md5Hex(password)));
        }
    }

    @Test
    public void testDelete() throws Exception {
        userRepository.delete(10);
    }

    @Test
    public void testCount() throws Exception {
        // 100000(9708ms)
        System.out.println(userRepository.count());
    }

    @Test
    public void testCount2() throws Exception {
        // 100000(646ms)
        System.out.println(userRepository.count2());
    }
}
