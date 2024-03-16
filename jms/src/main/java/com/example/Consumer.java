package com.example;

import com.rabbitmq.client.*;

class Consumer implements IFRabiitMQ {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USER);
        factory.setPassword(PASS);

        System.out.println("Create connection");
        Connection connection = factory.newConnection();

        System.out.println("Create channel");
        Channel channel = connection.createChannel();
        channel.queueDeclare(GETTING_QUEUE, false, false, false, null);

        System.out.println("Waiting for messages...");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Received message: '" + message + "'");
        };

        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("Consumer was cancelled by the server");
        };
        channel.basicConsume(GETTING_QUEUE, true, deliverCallback, cancelCallback);
    }
}