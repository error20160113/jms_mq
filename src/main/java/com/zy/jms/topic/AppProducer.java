package com.zy.jms.topic;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 队列主题
 * 消息生产者
 * @author error
 *
 */
public class AppProducer {

	private static final String url="tcp://127.0.0.1:61616";
	private static final String topicName="topic-test";
	
	public static void main(String[] args) throws JMSException {
		
		//1.创建ConnectionFactory连接工厂
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(url);
		
		//2.创建连接
		Connection connection =connectionFactory.createConnection();
		
		//3.启动连接
		connection.start();
		
		//4.创建会话
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//5.创建目标
		Destination destination= session.createTopic(topicName);
		
		//6.创建生产者
		MessageProducer messageProducer= session.createProducer(destination);
		
		for(int i=0;i<100;i++){
			//7.创建消息
			TextMessage textMeasurer=session.createTextMessage("test: "+i);
			
			//8.发布消息
			messageProducer.send(textMeasurer);
			
			System.out.println("发送消息:"+textMeasurer.getText());

		}
		
		//9.关闭连接
		connection.stop();
		
	}
}
