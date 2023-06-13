package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class AsyncJMSReceiver implements MessageListener {

    public AsyncJMSReceiver() {
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

            System.out.println("RRegistering receiver");
            receiver.setMessageListener(this);
            System.out.println("waiting for messages...");

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("message arrived at queue, reading it.!!");
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("message received. Message is " + textMessage.getText());
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Thread threadToRunReceiver = new Thread(AsyncJMSReceiver::new);
        threadToRunReceiver.start();
    }
}
