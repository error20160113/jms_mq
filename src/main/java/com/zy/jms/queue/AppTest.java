package com.zy.jms.queue;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class AppTest {

	private static final String url="tcp://127.0.0.1:61616";
	private static final String queueName="queue-test-9";
	
	public static void main(String[] args) throws JMSException {

		// 1.创建ConnectionFactory连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

		// 2.创建连接
		Connection connection = connectionFactory.createConnection();

		// 3.启动连接
		connection.start();

		// 4.创建会话
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

		// 5.创建目标
		Destination destination = session.createQueue(queueName);

		// 6.创建生产者
		MessageProducer messageProducer = session.createProducer(destination);

		for (int i = 0; i < 100; i++) {
			// 7.创建消息
			TextMessage textMeasurer = session.createTextMessage("test: " + i);

			// 8.发布消息
			messageProducer.send(textMeasurer);

			System.out.println("发送消息:" + textMeasurer.getText());

		}

		// 9.关闭连接
		// connection.stop();

		// 6.创建消费者
		MessageConsumer messageConsumer = session.createConsumer(destination);


		// 7.创建一个监听器
		messageConsumer.setMessageListener(new MessageListener() {

			public void onMessage(Message message) {

				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("0接收消息: " + textMessage.getText());
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

	
	
	
	}

}
