package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSSender {
    public static void main(String[] args) {
        try {

            Context context = new InitialContext();
            Connection connection = ((ConnectionFactory) context.lookup("ConnectionFactory")).createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("EM_TRADE.Q");

            System.out.println("Creating sender with destination as queue");
            MessageProducer sender = session.createProducer(queue);
            System.out.println("Sender created");

            System.out.println("Creating text message on session to be sent");
            TextMessage textMessage = session.createTextMessage("SELL APL 1000 SHARES");
            textMessage.setStringProperty("trader.name", "Mark");
            System.out.println("text message created");

            System.out.println("Sending message");
            sender.send(textMessage);
            System.out.println("Message sent.");

            connection.close();

        } catch (JMSException | NamingException e) {
            throw new RuntimeException(e);
        }
    }
}