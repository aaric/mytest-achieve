package com.github.aaric.achieve.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.common.http.ClientConfiguration;
import com.aliyun.mns.model.Message;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.*;

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
    private String accessId;

    @Value("${aliyun.accessKeySecret}")
    private String accessKey;

    @Value("${aliyun.MNS.endpoint}")
    private String accountEndpoint;

    @Value("${aliyun.MNS.maxConnections}")
    private int maxConnections;

    @Value("${aliyun.MNS.maxConnectionsPerRoute}")
    private int maxConnectionsPerRoute;

    @Value("${aliyun.MNS.queueName}")
    private String queueName;

    @Value("${aliyun.MNS.topicName}")
    private String topicName;

    protected MNSClient client;

    @Before
    public void begin() throws Exception {
        ClientConfiguration config = new ClientConfiguration();
        config.setMaxConnections(maxConnections);
        config.setMaxConnectionsPerRoute(maxConnectionsPerRoute);
        CloudAccount account = new CloudAccount(accessId, accessKey, accountEndpoint, config);
        client = account.getMNSClient();
    }

    @After
    public void end() throws Exception {
        client.close();
    }

    @Test
    @Ignore
    public void testProducer() throws Exception {
        CloudQueue cloudQueue = client.getQueueRef(queueName);
        Message message = new Message();
        message.setMessageBody("test msg");
        message = cloudQueue.putMessage(message);
        message.setPriority(8);
        System.out.printf("messageId: %s\n", message.getMessageId());
    }

    @Test
    @Ignore
    public void testConsumer() throws Exception {
        CloudQueue cloudQueue = client.getQueueRef(queueName);

        // Get
        Message message = cloudQueue.popMessage();
        System.out.println(message.getMessageBodyAsString());

        // Delete
        cloudQueue.deleteMessage(message.getReceiptHandle());
    }

    @Test
    @Ignore
    public void testDelete() throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 100; i++) {
            service.execute(new Runnable() {

                @Override
                public void run() {
                    ClientConfiguration config = new ClientConfiguration();
                    config.setMaxConnections(maxConnections);
                    config.setMaxConnectionsPerRoute(maxConnectionsPerRoute);
                    CloudAccount account = new CloudAccount(accessId, accessKey, accountEndpoint, config);
                    CloudQueue cloudQueue = account.getMNSClient().getQueueRef(queueName);
                    Message message = cloudQueue.popMessage();
                    System.out.println(message.getMessageBodyAsString());
                    cloudQueue.deleteMessage(message.getReceiptHandle());
                }
            });
        }
        service.shutdown();
    }

    @Test
    @Ignore
    public void testDelete2() throws Exception {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,
                10,
                3,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(10),
                new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 100; i++) {
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    ClientConfiguration config = new ClientConfiguration();
                    config.setMaxConnections(maxConnections);
                    config.setMaxConnectionsPerRoute(maxConnectionsPerRoute);
                    CloudAccount account = new CloudAccount(accessId, accessKey, accountEndpoint, config);
                    CloudQueue cloudQueue = account.getMNSClient().getQueueRef(queueName);
                    Message message = cloudQueue.popMessage();
                    System.out.println(message.getMessageBodyAsString());
                    cloudQueue.deleteMessage(message.getReceiptHandle());
                }
            });
        }
    }
}
