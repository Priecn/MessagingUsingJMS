package org.example;

import javax.jms.CompletionListener;
import javax.jms.Message;
import java.util.concurrent.CountDownLatch;

public class JMSCompletionListener implements CompletionListener {

    private final CountDownLatch countDownLatch;
    public JMSCompletionListener (CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }
    @Override
    public void onCompletion(Message message) {
        countDownLatch.countDown();
        System.out.println("Message acknowledge by server");
    }

    @Override
    public void onException(Message message, Exception e) {
        countDownLatch.countDown();
        System.out.println("EXCEPTION!!!");
        e.printStackTrace();
    }
}
