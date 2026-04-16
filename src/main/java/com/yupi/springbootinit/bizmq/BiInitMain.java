package com.yupi.springbootinit.bizmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import static com.yupi.springbootinit.bizmq.BiMqConstant.*;
public class BiInitMain {
    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            String exchangeName = BI_EXCHANGE_NAME;
            channel.exchangeDeclare(exchangeName, "direct");
            //创建队列
            String queueName = BI_QUEUE_NAME;
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, exchangeName, BI_ROUTING_KEY);
        } catch (Exception e) {
        }

    }
}
