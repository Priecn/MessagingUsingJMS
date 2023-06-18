package org.example;

import javax.jms.*;

public class JMSAsyncCountReceiver implements MessageListener {

    // This property is mandatory from JMS 2.0. Vendors needs to be sent this even if broker does not support this.
    public static final String JMSX_DELIVERY_COUNT = "JMSXDeliveryCount";
    private JMSContext jmsContext;
    public JMSAsyncCountReceiver() {
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        jmsContext = connectionFactory.createContext(Session.SESSION_TRANSACTED);
        Queue queue = jmsContext.createQueue(Common.QUEUE_NAME);

        jmsContext.createConsumer(queue).setMessageListener(this);

        System.out.println("Waiting on message...");
    }
    @Override
    public void onMessage(Message message) {

        try {
            // This is the number of times, a message was sent to receiver. We can use this to handle poison messages.
            // poison messages - message that receiver is not able process and because of which it's being resent from broker to receiver.
            int retries =  message.getIntProperty(JMSX_DELIVERY_COUNT);
            if (retries > 2) {
                System.out.println("Can not process message - sending to DLQ.");
                jmsContext.commit();
                return;
            }
            System.out.println("Processing the message... with " + retries + " retries. Body: " + message.getBody(String.class));
            Thread.sleep(5000);
            jmsContext.rollback();
            System.out.println("Ops!! ERROR");
        } catch (InterruptedException | JMSException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        new Thread(JMSAsyncCountReceiver::new).start();
    }
}
