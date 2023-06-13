package org.example;



import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSContext;
import javax.jms.Queue;

public class JMSSender {
    public static void main(String[] args) {
        ConnectionFactory cf = new com.sun.messaging.ConnectionFactory();
        try (JMSContext jmsContext = cf.createContext()) {
            Queue queue = jmsContext.createQueue(Common.QUEUE_NAME);
            jmsContext
                    .createProducer()
                    .setProperty("trader_name", "Mark")
                    .setDeliveryMode(DeliveryMode.NON_PERSISTENT)
                    .send(queue, "BUY AAPL 1500 SHARES");
            System.out.println("Message sent!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}