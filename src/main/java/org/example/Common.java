package org.example;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Queue;

public abstract class Common {

    public static final String MQ_HOST_PORT = "tcp://localhost:61616";
    public static final String QUEUE_NAME = "EM_TRADE.Q";

}
