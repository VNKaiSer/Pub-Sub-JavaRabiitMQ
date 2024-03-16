package com.example;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.ConnectionFactory;

public class Producer implements IFRabiitMQ {

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USER);
        factory.setPassword(PASS);

        System.out.println("Create connection");

        try (com.rabbitmq.client.Connection connection = factory.newConnection();
                com.rabbitmq.client.Channel channel = connection.createChannel()) {
            System.out.println("Create channel");
            channel.queueDeclare(GETTING_QUEUE, false, false, false, null);
            String message;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                do {
                    System.out.println("Enter message: ");
                    message = reader.readLine().trim();
                    channel.basicPublish("", GETTING_QUEUE, null, message.getBytes());
                    System.out.println("Sent message: " + message);
                } while (!message.isEmpty());
            }

            channel.basicPublish("", GETTING_QUEUE, null, message.getBytes());
            System.out.println("Sent message: " + message);

        }

    }
}
