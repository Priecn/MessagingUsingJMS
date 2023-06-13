package org.example;



import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Queue;

public class JMSReceiver {
    public static void main(String[] args) {
        ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        try (JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue(Common.QUEUE_NAME);
            // String message = jmsContext.createConsumer(queue).receiveBody(String.class);
            Message message = jmsContext.createConsumer(queue).receive();
            String body = message.getBody(String.class);
            String treader = message.getStringProperty("trader_name");
            System.out.println("Message received! Message { body : " + body + ", trader_name: " + treader + " }");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
