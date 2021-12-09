package com.pranab.test.rabbitMq;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;

public class RabbitMQConfiguration {

	
	private RabbitMQConfiguration() {
		
	}
	
	public static void creatExchange() throws IOException, TimeoutException {
		Channel channel=RabbitMQConnectionManager.getChannel();
		channel.exchangeDeclare("Test-Exchange", BuiltinExchangeType.HEADERS, true);
		channel.close();
	}
	
	public static void createQueue() throws IOException, TimeoutException {
		Channel channel=RabbitMQConnectionManager.getChannel();
		channel.queueDeclare("Personal-Item-Queue", true, false, false, null);
		channel.queueDeclare("Mixed-Item-Queue", true, false, false, null);
		channel.queueDeclare("Home-Item-Queue", true, false, false, null);
		channel.close();
	}
	
	public static void createBinding() throws IOException, TimeoutException {
		Channel channel=RabbitMQConnectionManager.getChannel();
		Map<String,Object> personalMap=new HashMap<String,Object>();
		personalMap.put("x-match", "all");
		personalMap.put("h1", "myItems");
		personalMap.put("h2", "secretItems");
		channel.queueBind("Personal-Item-Queue", "Test-Exchange", "", personalMap);
		
		Map<String,Object> mixedMap=new HashMap<String,Object>();
		mixedMap.put("x-match", "any");
		mixedMap.put("h1", "myItems");
		mixedMap.put("h3", "homeItems");
		channel.queueBind("Mixed-Item-Queue", "Test-Exchange", "", mixedMap);
		
		Map<String,Object> homeMap=new HashMap<String,Object>();
		homeMap.put("x-match", "all");
		homeMap.put("h3", "homeItems");
		homeMap.put("h4", "antiqueItems");
		channel.queueBind("Home-Item-Queue", "Test-Exchange", "", homeMap);
		channel.close();
	}
}
