package com.github.aaric.achieve.mns;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * MNSTest
 *
 * @author Aaric, created on 2017-06-12T13:55.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MNSTest {


    @Value("${aliyun.accessKeyId}")
    private String accessKeySecret;

    @Test
    public void test() throws Exception {
        System.err.println(accessKeySecret);
    }
}
