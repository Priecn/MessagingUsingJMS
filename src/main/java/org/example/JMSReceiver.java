package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSReceiver {
    public static void main(String[] args) {

        Connection connection = null;

        try {
            ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(Common.MQ_HOST_PORT);
            System.out.println("Creating connection and session");
            connection = cf.createConnection();
            connection.start();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            System.out.println("Connection established !! Session created.");

            System.out.println("creating or using queue");
            Queue queue = session.createQueue(Common.QUEUE_NAME);
            System.out.println("Got access to queue");

            System.out.println("Creating receiver with destination as queue");
            MessageConsumer receiver = session.createConsumer(queue);
            System.out.println("receiver created");

            System.out.println("Reading text message from queue");
            // Blocking call. Will continue to wait for message
            TextMessage textMessage = (TextMessage) receiver.receive();
            // And it finishes as soon as it receives a massage. After that even if connection is open it will not read new messages.
            System.out.println("message received. Message is " + textMessage.getText());

        } catch (JMSException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
