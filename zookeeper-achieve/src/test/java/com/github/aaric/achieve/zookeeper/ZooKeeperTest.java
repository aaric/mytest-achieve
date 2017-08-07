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
import java.util.List;

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
        zooKeeper = new ZooKeeper(connectString, sessionTimeout, new Watcher() {

            @Override
            public void process(WatchedEvent event) {
                System.err.println(event.getType());
            }
        });
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
        /**
         * CreateMode 标识有四种形式的目录节点:
         *   1.PERSISTENT：持久化目录节点，这个目录节点存储的数据不会丢失
         *   2.PERSISTENT_SEQUENTIAL：顺序自动编号的目录节点，这种目录节点会根据当前已近存在的节点数自动加 1，然后返回给客户端已经成功创建的目录节点名
         *   3.EPHEMERAL：临时目录节点，一旦创建这个节点的客户端与服务器端口也就是 session 超时，这种节点会被自动删除
         *   4.EPHEMERAL_SEQUENTIAL：临时自动编号节点
         */
        zooKeeper.create(PATH, "Hello World".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(PATH + "/one", "Hello One".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(PATH + "/two", "Hello Two".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        zooKeeper.create(PATH + "/three", "Hello Three".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    @Test
    public void testGetChildren() throws KeeperException, InterruptedException {
        List<String> dataList =  zooKeeper.getChildren(PATH, false);
        if(null != dataList && 0 < dataList.size()) {
            for (String string: dataList) {
                System.out.println(string);
            }
        }
    }

    @Test
    public void testGetData() throws KeeperException, InterruptedException {
        System.out.println(new String(zooKeeper.getData(PATH, false, null)));
        System.out.println(new String(zooKeeper.getData(PATH + "/one", false, null)));
        System.out.println(new String(zooKeeper.getData(PATH + "/two", false, null)));
        System.out.println(new String(zooKeeper.getData(PATH + "/three", false, null)));
    }

    @Test
    public void testSetDate() throws KeeperException, InterruptedException {
        zooKeeper.setData(PATH, "Hello Zookeeper".getBytes(), -1);
    }

    @Test
    public void testDelete() throws KeeperException, InterruptedException {
        zooKeeper.delete(PATH + "/one", -1);
        zooKeeper.delete(PATH + "/two", -1);
        zooKeeper.delete(PATH + "/three", -1);
        zooKeeper.delete(PATH, -1);
    }
}
