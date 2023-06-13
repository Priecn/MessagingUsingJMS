package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSSender {
    public static void main(String[] args) {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(Common.MQ_HOST_PORT);
        System.out.println("Creating connection and session");
        try (Connection connection = cf.createConnection()) {
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("Connection established !! Session created.");

            System.out.println("creating or using queue");
            Queue queue = session.createQueue(Common.QUEUE_NAME);
            System.out.println("Got access to queue");

            System.out.println("Creating sender with destination as queue");
            MessageProducer sender = session.createProducer(queue);
            System.out.println("Sender created");

            System.out.println("Creating text message on session to be sent");
            TextMessage textMessage = session.createTextMessage("BUY APL 1000 SHARES");
            System.out.println("text message created");

            System.out.println("Sending message");
            sender.send(textMessage);
            System.out.println("Message sent.");

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}