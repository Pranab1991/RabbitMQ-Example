package com.pranab.test.rabbitMq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class RabbitMQSub implements Runnable{

	public static void subscribe() throws IOException, TimeoutException {
		Channel channel=RabbitMQConnectionManager.getChannel();
		DeliverCallback deliverCallback=( consumerTag, message) -> {
			System.out.println("consumerTag : "+consumerTag);
			System.out.println(new String(message.getBody()));
			System.out.println("envelope : "+ message.getEnvelope() );
			};
		CancelCallback cancelCallback=(String consumerTag)-> {
			System.out.println("consumerTag cancled : "+consumerTag);
			};
		channel.basicConsume("Personal-Item-Queue", true, deliverCallback, cancelCallback);
		channel.basicConsume("Mixed-Item-Queue", true, deliverCallback, cancelCallback);
		channel.basicConsume("Home-Item-Queue", true, deliverCallback, cancelCallback);
		channel.close();
        RabbitMQConnectionManager.closeConnection();
	}
	
	@Override
	public void run() {
		try {
			subscribe();
		} catch (IOException | TimeoutException e) {
			e.printStackTrace();
		}
		
	}

}
