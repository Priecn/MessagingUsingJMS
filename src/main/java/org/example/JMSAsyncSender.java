package org.example;

import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class JMSAsyncSender {
    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();

        try(JMSContext jmsContext = connectionFactory.createContext()) {

            Queue queue = jmsContext.createQueue(Common.QUEUE_NAME);

            // looking for one thread (ack)
            CountDownLatch countDownLatch = new CountDownLatch(1);

            // message acknowledgement listener
            JMSCompletionListener jmsCompletionListener = new JMSCompletionListener(countDownLatch);

            jmsContext.createProducer()
                    .setAsync(jmsCompletionListener)
                    .send(queue, "Message text.");

            System.out.println("Message sent ...");

            IntStream.range(0, 5).forEach(n-> {
                System.out.println("Processing...");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            countDownLatch.await();

            System.out.println("End processing.");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}