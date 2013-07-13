package com.iteye.wwwcomy.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.exolab.jms.client.JmsConnectionFactory;

/**
 * 
 * @author Liuxn
 * 
 */
public class TopicSender {
	public static void main(String[] s) {
		Context context = null;
		JmsConnectionFactory factory = null;
		TopicConnection conn = null;
		String factoryname = "ConnectionFactory";
		String topicname = "topic1";
		Topic topic = null;
		TopicPublisher topicpublisher = null;

		int count = 10;
		String message = "publishmessage";
		TopicSession session = null;
		try {
			context = new InitialContext();
			factory = (JmsConnectionFactory) context.lookup(factoryname);
			topic = (Topic) context.lookup(topicname);
			// TODO 此处用的openJms7.6的jar包，没有createTopicConnection方法，可能会有问题
			conn = (TopicConnection) factory.createConnection();
			session = conn.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			topicpublisher = session.createPublisher(topic);
			for (int i = 0; i < count; i++) {
				TextMessage tmessage = session.createTextMessage();
				tmessage.setText(message + i);
				topicpublisher.publish(tmessage);
				System.out.println(tmessage.getText());
			}
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (context != null) {
				try {
					context.close();
				} catch (NamingException exception) {
					exception.printStackTrace();
				}
			}

			if (conn != null) {
				try {
					conn.close();
				} catch (JMSException exception) {
					exception.printStackTrace();
				}
			}
		}
	}
}