package com.pranab.test.rabbitMq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeoutException;

public class Driver 
{
    public static void main( String[] args ) throws IOException, TimeoutException
    {
        RabbitMQConfiguration.creatExchange();
        RabbitMQConfiguration.createQueue();
        RabbitMQConfiguration.createBinding();
        
        ExecutorService service=Executors.newFixedThreadPool(2); 
        service.submit(new RabbitMQPub());
        service.submit(new RabbitMQSub());
        
        service.shutdown();
    }
}
