package com.pranab.test.rabbitMq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQConnectionManager {

	private static Connection connection=null;
	private static RabbitMQConnectionManager conMngr=null;
	public static Channel getChannel() {
		Channel channel=null;
		if(conMngr==null) {
			conMngr=new RabbitMQConnectionManager();
		}		
		 try {
			 channel=connection.createChannel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return channel;
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private RabbitMQConnectionManager() {
		ConnectionFactory factory=new ConnectionFactory();		
		try {
			connection=factory.newConnection("amqp://guest:guest@localhost:5672/");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}
}
