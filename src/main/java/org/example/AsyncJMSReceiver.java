package org.example;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AsyncJMSReceiver implements MessageListener {

    public AsyncJMSReceiver() {
        try {
            Context context = new InitialContext();
            Connection connection = ((ConnectionFactory) context.lookup("ConnectionFactory")).createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = (Queue) context.lookup("EM_TRADE.Q");

            System.out.println("Creating sender with destination as queue");
            MessageProducer sender = session.createProducer(queue);
            System.out.println("Sender created");

            System.out.println("Creating receiver with destination as queue");
            MessageConsumer receiver = session.createConsumer(queue);
            System.out.println("receiver created");

            System.out.println("RRegistering receiver");
            receiver.setMessageListener(this);
            System.out.println("waiting for messages...");

        } catch (JMSException | NamingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("message arrived at queue, reading it.!!");
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("message received. Message is " + textMessage.getText() + " Properties { trader.name = " + textMessage.getStringProperty("trader.name") +" }");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        Thread threadToRunReceiver = new Thread(AsyncJMSReceiver::new);
        threadToRunReceiver.start();
    }
}
