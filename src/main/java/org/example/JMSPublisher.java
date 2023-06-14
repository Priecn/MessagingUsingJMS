package org.example;

import javax.jms.*;
import java.text.DecimalFormat;

public class JMSPublisher {

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
        try (JMSContext context = connectionFactory.createContext()) {
            Topic topic = context.createTopic(Common.TOPIC_NAME);

            JMSProducer jmsProducer = context.createProducer();
            DecimalFormat df = new DecimalFormat("##.00");
            String price = df.format(98.0 + Math.random());
            jmsProducer.send(topic, price);
            System.out.println("Message est!");
        }
    }
}
