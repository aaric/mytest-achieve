package com.incarcloud.rooster.msg;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;

/**
 * RocketMQ消息发布
 *
 * @author Aaric, created on 2017-05-27T10:45.
 * @since 1.0-SNAPSHOT
 */
@Component
@ConfigurationProperties(prefix = "rocketmq")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class RocketMQProducer {

    /**
     * Name server name
     */
    private String namesrvAddr;
    /**
     * Producer group name
     */
    private String producerGroup;
    /**
     * Producer
     */
    private DefaultMQProducer producer;

    /**
     * 初始化方法的注解方式，等同与init-method=init
     */
    @PostConstruct
    private void init() throws MQClientException {
        /**
         * 一个应用创建一个Producer，由应用来维护此对象，可以设置为全局对象或者单例<br>
         * 注意：ProducerGroupName需要由应用来保证唯一<br>
         * ProducerGroup这个概念发送普通的消息时，作用不大，但是发送分布式事务消息时，比较关键，
         * 因为服务器会回查这个Group下的任意一个Producer
         */
        producer = new DefaultMQProducer(producerGroup);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setInstanceName("Producer");

        /**
         * Producer对象在使用之前必须要调用start初始化，初始化一次即可<br>
         * 注意：切记不可以在每次发送消息时，都调用start方法
         */
        producer.start();
    }

    /**
     * 发送消息
     *
     * @param topic 消息主题
     * @param tags 消息分组
     * @param keys 消息键值
     * @param content 消息内容
     * @return 结果
     * @throws InterruptedException
     * @throws RemotingException
     * @throws MQClientException
     * @throws MQBrokerException
     */
    public SendResult send(String topic, String tags, String keys, String content) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        System.out.printf("[Producer] %s - %s - %s: %s\n", topic, tags, keys, content);
        return producer.send(new Message(topic, tags, keys, content.getBytes(Charset.forName(RemotingHelper.DEFAULT_CHARSET))));
    }

    /**
     * 销毁方法的注解方式，等同于destory-method=destory
     */
    @PreDestroy
    private void destroy() {
        /**
         * 应用退出时，要调用shutdown来清理资源，关闭网络连接，从MetaQ服务器上注销自己
         * 注意：我们建议应用在JBOSS、Tomcat等容器的退出钩子里调用shutdown方法
         */
        producer.shutdown();
    }

    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getProducerGroup() {
        return producerGroup;
    }

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }
}
