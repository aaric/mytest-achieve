package com.github.aaric.achieve.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * RabbitMQ Config.
 *
 * @author Aaric, Created on 2017-05-17T14:35.
 * @since 1.0-SNAPSHOT
 */
//@Configuration
//@ConfigurationProperties(prefix = "rabbitmq")
public class AmqpConfiguration {

    /**
     * Exchange.
     */
    public static final String EXCHANGE   = "spring-boot-exchange";

    /**
     * Routing key.
     */
    public static final String ROUTINGKEY = "spring-boot-routingKey";

    /**
     * RabbitMQ地址
     */
    private String address;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 虚拟主机
     */
    private String virtualHost;

    /**
     * 获得RabbitMQ连接工厂
     *
     * @return RabbitMQ连接工厂
     */
//    @Bean
    public ConnectionFactory connectionFactory ()  {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(address);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setPublisherConfirms(true);
        return connectionFactory;
    }

    /**
     * 获得RabbitMQ模板
     *
     * @return RabbitMQ模板
     */
//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVirtualHost() {
        return virtualHost;
    }

    public void setVirtualHost(String virtualHost) {
        this.virtualHost = virtualHost;
    }
}
