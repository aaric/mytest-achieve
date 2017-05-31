package com.github.aaric.achieve.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.MessageFormat;
import java.util.List;

/**
 * RocketMQT测试类
 *
 * @author Aaric, created on 2017-05-23T09:48.
 * @since 1.0-SNAPSHOT
 */
public class RocketMQTest {

    /**
     * Name server name
     */
    public static final String NAME_SRV_ADDR = "linux6-1:9876";

    /**
     * Producer group name
     */
    public static final String PRODUCER_GROUP_NAME = "ProducerGroupName";

    /**
     * Consumer group name
     */
    public static final String CONSUMER_GROUP_NAME = "ConsumerGroupName";

    @Before
    public void begin() throws Exception {

    }

    @After
    public void end() throws Exception {

    }

    @Test
    public void testProducer() throws Exception {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP_NAME);
        producer.setNamesrvAddr(NAME_SRV_ADDR);
        producer.setInstanceName("Producer");

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();

        /**
         * 下面这段代码表明一个Producer对象可以发送多个topic，多个tag的消息。
         * 注意：send方法是同步调用，只要不抛异常就标识成功。但是发送成功也可会有多种状态，<br>
         * 例如消息写入Master成功，但是Slave不成功，这种情况消息属于成功，但是对于个别应用如果对消息可靠性要求极高，<br>
         * 需要对这种情况做处理。另外，消息可能会存在发送失败的情况，失败重试由应用来处理。
         */
        Message msg = null;
        SendResult result;
        for (int i = 0; i < 100; i++) {
            switch (i%3) {
                case 0:
                    msg = new Message("TopicTest1", //topic
                            "TagA", //tag
                            "OrderID" + MessageFormat.format("{0,number,000}", new Object[]{i+1}), //key
                            "Hello MetaQ".getBytes()); //body
                    break;
                case 1:
                    msg = new Message("TopicTest2", //topic
                            "TagB", //tag
                            "OrderID" + MessageFormat.format("{0,number,000}", new Object[]{i+1}), //key
                            "Hello MetaQ".getBytes()); //body
                    break;
                case 2:
                    msg = new Message("TopicTest3", //topic
                            "TagC", //tag
                            "OrderID" + MessageFormat.format("{0,number,000}", new Object[]{i+1}), //key
                            "Hello MetaQ".getBytes()); //body
                    break;
            }
            if(null != msg) {
                result = producer.send(msg);
                System.out.println(result);
            }
        }

        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }

    @Test
    public void testConsumer() throws Exception {
        /**
         * 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ConsumerGroupName需要由应用来保证唯一
         */
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP_NAME);
        consumer.setNamesrvAddr(NAME_SRV_ADDR);
        consumer.setInstanceName("Consumer");

        /**
         * 订阅指定topic下tags分别等于TagA或TagC或TagD
         */
        consumer.subscribe("TopicTest1", "TagA || TagC || TagD");
        /**
         * 订阅指定topic下所有消息<br>
         * 注意：一个consumer对象可以订阅多个topic
         */
        consumer.subscribe("TopicTest2", "*");

        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        /**
         * 设置为集群消费(区别于广播消费)
         */
        consumer.setMessageModel(MessageModel.CLUSTERING);

        /**
         * 注册消息监听器
         */
        consumer.registerMessageListener(new MessageListenerConcurrently() {

            /**
             * 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
             */
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(Thread.currentThread().getName() + " Receive New Message: " + msgs.size());
                MessageExt msg = msgs.get(0);
                if("TopicTest1".equals(msg.getTopic())) {
                    if(null != msg.getTags()) {
                        if("TagA".equals(msg.getTags())) {
                            // 执行TagA的消费
                            System.out.println("TagA: " + new String(msg.getBody()));
                        } else if("TagB".equals(msg.getTags())) {
                            // 执行TagB的消费
                            System.out.println("TagB: " + new String(msg.getBody()));
                        } else if("TagC".equals(msg.getTags())) {
                            // 执行TagC的消费
                            System.out.println("TagC: " + new String(msg.getBody()));
                        }
                    }
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        /**
         * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         */
        consumer.start();
        System.out.println("Consumer started.");

        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        consumer.shutdown();
    }
}
