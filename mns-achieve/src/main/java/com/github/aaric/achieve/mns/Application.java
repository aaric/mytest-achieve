package com.github.aaric.achieve.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.common.http.ClientConfiguration;
import com.aliyun.mns.model.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.MessageFormat;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Spring Boot launcher.
 *
 * @author Aaric, created on 2017-06-12T14:02.
 * @since 1.0-SNAPSHOT
 */
@SpringBootApplication
public class Application /*implements CommandLineRunner*/ {

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

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /*private static int counter = 0;

    @Override
    public void run(String... args) throws Exception {
        ExecutorService service = Executors.newFixedThreadPool(20);
        for (int i = 0; i < 1000; i++) {
            service.execute(new Runnable() {

                @Override
                public void run() {
                    ClientConfiguration config = new ClientConfiguration();
                    config.setMaxConnections(maxConnections);
                    config.setMaxConnectionsPerRoute(maxConnectionsPerRoute);
                    CloudAccount account = new CloudAccount(accessId, accessKey, accountEndpoint, config);
                    CloudQueue cloudQueue = account.getMNSClient().getQueueRef(queueName);
                    Message message = cloudQueue.popMessage();
                    if(null != message) {
                        System.out.printf("--%s: [%s]%s\n", MessageFormat.format("{0,number,000000}", ++counter), queueName, message.getMessageBodyAsString());
                        cloudQueue.deleteMessage(message.getReceiptHandle());{}
                    }
                }
            });
        }
        service.shutdown();
    }*/
}
