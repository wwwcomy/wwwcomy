package com.iteye.wwwcomy.jms;
/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.io.Serializable;
import java.util.ArrayList;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.pool.PooledConnectionFactory;

import com.iteye.wwwcomy.lxn.utils.DebugUtil;

/**
 * A simple tool for publishing messages
 * 
 * 
 */
@SuppressWarnings("unused")
public class JMSProducer { // implements Runnable {

	// private static Object lockResults = new Object();
	private static int parallelThreads = 1;
	private Destination destination;
	// private int messageCount = 1;//10;
	private long sleepTime;
	private boolean verbose = false;
	private int messageSize = 255;
	private long timeToLive = 3600000;
	private int soTimeout = 5000;
	private String user = ActiveMQConnection.DEFAULT_USER;
	private String password = ActiveMQConnection.DEFAULT_PASSWORD;
	// "tcp://1.1.7.44:61616"; // ActiveMQConnection.DEFAULT_BROKER_URL;
	// "nio://1.1.7.46:61616"; // ActiveMQConnection.DEFAULT_BROKER_URL;
	private String url = "tcp://1.1.7.44:61616";
	private String subject = "TOOL.DEFAULT";
	private boolean topic = false;
	private boolean transacted = true;
	private boolean persistent = true;
  
    private String getName() {
    	return this.getClass().getCanonicalName();
    }

    public void showParameters() {
//        System.out.println("Connecting to URL: " + url + " (" + user + ":" + password + ")");
//        System.out.println("Publishing a Message with size " + messageSize + " to " + (topic ? "topic" : "queue") + ": " + subject);
//        System.out.println("Using " + (persistent ? "persistent" : "non-persistent") + " messages");
//        System.out.println("Sleeping between publish " + sleepTime + " ms");
//        System.out.println("Running " + parallelThreads + " parallel threads");
//        if (timeToLive != 0) {
//            System.out.println("Messages time to live " + timeToLive + " ms");
//        }
    }

	public void start() {
		// "failover://tcp://localhost:61616";
	    // ActiveMQConnection.DEFAULT_BROKER_URL = "failover://"+url;
		this.run();
	}
	
	private static PooledConnectionFactory PCF;
	
	private synchronized PooledConnectionFactory getPoolFactory() {
		if (PCF!=null)
			return PCF;
        // Create the connection.
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
        connectionFactory.setSendTimeout(soTimeout);
        connectionFactory.setCloseTimeout(soTimeout);
        connectionFactory.setUseAsyncSend(true);
        connectionFactory.setUseCompression(true);
        return PCF = new PooledConnectionFactory(connectionFactory);
	}
	
	private Connection getConnection() throws Throwable {
        Connection c = getPoolFactory().createConnection(); // connectionFactory.createConnection();
        c.start();
        return c;
	}

	public void run() {
        MessageProducer producer = null;
        Session session = null;
        Connection conn = null;
        try {
            // Create the session
            // 第一个参数是否使用事务:当消息发送者向消息提供者（即消息代理）发送消息时，消息发送者等待消息代理的确认，没有回应则抛出异常，消息发送程序负责处理这个错误。
            // 第二个参数消息的确认模式：
            // AUTO_ACKNOWLEDGE ： 指定消息提供者在每次收到消息时自动发送确认。消息只向目标发送一次，但传输过程中可能因为错误而丢失消息。
            // CLIENT_ACKNOWLEDGE ： 由消息接收者确认收到消息，通过调用消息的acknowledge()方法（会通知消息提供者收到了消息）
            // DUPS_OK_ACKNOWLEDGE ： 指定消息提供者在消息接收者没有确认发送时重新发送消息（这种确认模式不在乎接收者收到重复的消息）。
        	conn = getConnection();
            // Create the session.
        	session = conn.createSession(transacted, Session.AUTO_ACKNOWLEDGE);
            if (topic) {
                destination = session.createTopic(subject);
            } else {
                destination = session.createQueue(subject);
            }
            // Create the producer.
            producer = session.createProducer(destination);
            if (persistent) {
                producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            } else {
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            }
//            if (timeToLive != 0) {
//                producer.setTimeToLive(timeToLive);
//            }
            // Start sending messages
            sendLoop(session, producer);
//            synchronized (lockResults) {
//                ActiveMQConnection c = (ActiveMQConnection) connection;
////                System.out.println("[" + this.getName() + "] Results:\n");
//                c.getConnectionStats().dump(new IndentPrinter());
//            }
        } catch (Throwable e) {
//        	if (e.getCause() instanceof org.apache.activemq.transport.RequestTimedOutIOException) {
//            	if (connection!=null)
//            		synchronized(Connection.class) {
//            			if (connection!=null) {
//		    	            try {
//		    	                connection.close();
//		    	            } catch (Throwable ignore) {
//		    	            }
//		    	            connection = null;
//            			}
//            		}
//        	}
            DebugUtil.error(e);
        } finally {
        	if (producer!=null)
                try {
                	producer.close();
                } catch (Throwable ignore) {
                }
        	if (session!=null)
	            try {
	                session.close();
	            } catch (Throwable ignore) {
	            }
        	if (conn!=null)
	            try {
	            	conn.close();
	            } catch (Throwable ignore) {
	            }
        }
    }

    protected void sendLoop(Session session, MessageProducer producer) throws Exception {
    	int messageCount = messages.size(); 
    	JMSMessage o;
        for (int i = 0; i < messageCount || messageCount == 0; i++) {
        	o = messages.get(i);
        	Message message;
        	if (o.type==JMSType.BYTES) {
        		message = session.createBytesMessage();
        		message.setJMSType("bytes");
            	((BytesMessage)message).writeBytes((byte[])o.data);
        	} else if (o.type==JMSType.OBJECT) {
        		message = session.createObjectMessage((Serializable)o.data);
        		message.setJMSType("object");
        	} else { // JMSType.STRING
                message = session.createTextMessage((String)o.data);
        		message.setJMSType("string");
        	}
//            if (verbose) {
//            }
            producer.send(message);
            if (transacted) { // 消息太多 会传输失败 创建几个消息就发送几个
                session.commit();
            }
        }
    } 

	private ArrayList<JMSMessage> messages = new ArrayList<JMSMessage>();

    /**
     * @param s
     */
    public void addMessage(JMSMessage s) {
    	this.messages.add(s);
    }
//    /**
//     * @deprecated
//     * @param s
//     */
//    public void setMessage(JMSMessage s) {
//    	this.messages.add(s);
//    }
//
//  public void setParallelThreads(int parallelThreads) {
//      if (parallelThreads < 1) {
//          parallelThreads = 1;
//      }
//      this.parallelThreads = parallelThreads;
//  }

    public void setPersistent(boolean durable) {
        this.persistent = durable;
    }

    /**
     * @deprecated
     * @param messageCount
     */
    public void setMessageCount(int messageCount) {
        //this.messageCount = messageCount;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public void setPassword(String pwd) {
        this.password = pwd;
    }

    public void setSleepTime(long sleepTime) {
        this.sleepTime = sleepTime;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setTopic(boolean topic) {
        this.topic = topic;
    }

    public void setQueue(boolean queue) {
        this.topic = !queue;
    }

    public void setTransacted(boolean transacted) {
        this.transacted = transacted;
    }

    public void setUrl(String url) {
        this.url = url;
    }

	public void setUser(String user) {
        this.user = user;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }
    
    public long getSleepTime() {
		return sleepTime;
	}

	public boolean isVerbose() {
		return verbose;
	}

	public int getMessageSize() {
		return messageSize;
	}

	public long getTimeToLive() {
		return timeToLive;
	}

	public String getUser() {
		return user;
	}

	public String getPassword() {
		return password;
	}

	public String getUrl() {
		return url;
	}

	public String getSubject() {
		return subject;
	}

	public boolean isTopic() {
		return topic;
	}

	public boolean isTransacted() {
		return transacted;
	}

	public boolean isPersistent() {
		return persistent;
	}

	public int getSoTimeout() {
		return soTimeout;
	}

	public void setSoTimeout(int soTimeout) {
		this.soTimeout = soTimeout;
	}
	
}
