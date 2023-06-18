package org.example;



import javax.jms.*;

public class JMSReceiver implements MessageListener {

    public JMSReceiver() {
        ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        try {
            JMSContext jmsContext = cf.createContext();
            Queue queue = jmsContext.createQueue(Common.QUEUE_NAME);
            jmsContext.createConsumer(queue).setMessageListener(this);
            System.out.println("Waiting on message...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new Thread(JMSReceiver::new).start();
    }

    @Override
    public void onMessage(Message message) {
        try {
            String body = message.getBody(String.class);
            System.out.println("Message received! " + body);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
