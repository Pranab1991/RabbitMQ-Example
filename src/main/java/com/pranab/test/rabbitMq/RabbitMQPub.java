package com.pranab.test.rabbitMq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Channel;

public class RabbitMQPub implements Runnable {
	
	public static void publish() throws IOException, TimeoutException {
		Channel channel=RabbitMQConnectionManager.getChannel();
		String message="Personal message";
		Map<String,Object> personalMap=new HashMap<String,Object>();
		personalMap.put("h1", "myItems");
		personalMap.put("h2", "secretItems");
		BasicProperties properties=new BasicProperties().builder().headers(personalMap).build();
		channel.basicPublish("Test-Exchange", "", properties, message.getBytes());
		channel.close();
	}
	
	

	@Override
	public void run() {
		try {
			publish();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

}
