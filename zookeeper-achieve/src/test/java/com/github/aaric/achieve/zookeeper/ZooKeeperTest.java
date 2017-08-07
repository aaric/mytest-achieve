package com.github.aaric.achieve.zookeeper;

import org.apache.zookeeper.ZooKeeper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * ZooKeeperTest
 *
 * @author Aaric, created on 2017-08-07T10:50.
 * @since 1.0-SNAPSHOT
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ZooKeeperTest {

    @Value("${achieve.zookeeper.connectString}")
    protected String connectString;

    @Value("${achieve.zookeeper.sessionTimeout}")
    protected Integer sessionTimeout;

    @Test
    public void testConn() throws IOException {
        System.out.println(connectString);
        ZooKeeper zooKeeper = new ZooKeeper(connectString, sessionTimeout, null);
    }
}
