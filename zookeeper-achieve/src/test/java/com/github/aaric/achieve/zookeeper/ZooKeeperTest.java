package com.github.aaric.achieve.zookeeper;

import org.apache.zookeeper.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
    private String connectString;

    @Value("${achieve.zookeeper.sessionTimeout}")
    private Integer sessionTimeout;

    protected ZooKeeper zooKeeper;

    @Before
    public void begin() throws IOException {
        System.out.println(connectString);
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new MyWatcher());
    }

    @After
    public void end() throws InterruptedException {
        zooKeeper.close();
    }

    @Test
    public void testConnect() {
        Assert.assertNotNull(zooKeeper);
    }

    public static final String PATH = "/testZK";

    @Test
    public void testExists() throws KeeperException, InterruptedException {
        System.out.println(zooKeeper.exists(PATH, true));
    }

    @Test
    public void testCreate() throws KeeperException, InterruptedException {
        zooKeeper.create(PATH, "Hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void testGetData() throws KeeperException, InterruptedException {
        System.out.println(new String(zooKeeper.getData(PATH, true, null)));
    }

    @Test
    public void testSetDate() throws KeeperException, InterruptedException {
        zooKeeper.setData(PATH, "Hello Zookeeper".getBytes(), -1);
    }

    @Test
    public void testDelete() throws KeeperException, InterruptedException {
        zooKeeper.delete(PATH, -1);
    }

    /**
     * MyWatcher
     */
    public static class MyWatcher implements Watcher {

        @Override
        public void process(WatchedEvent event) {
            System.err.println(event.getState());
        }
    }
}
