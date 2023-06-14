package org.example;

import javax.jms.*;

public class JMSSubscriber implements MessageListener {

    public JMSSubscriber() {
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        JMSContext context = connectionFactory.createContext();
        Topic topic = context.createTopic(Common.TOPIC_NAME);

        JMSConsumer consumer = context.createSharedConsumer(topic, "sub:3d");
        consumer.setMessageListener(this);
        System.out.println("Ready to receive message.");
    }
    public static void main(String[] args) {
        Thread thread = new Thread(JMSSubscriber::new);
        thread.start();
    }
    @Override
    public void onMessage(Message message) {
        try {
            System.out.println(message.getBody(String.class));
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
