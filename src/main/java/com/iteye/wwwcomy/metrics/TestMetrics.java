package com.iteye.wwwcomy.metrics;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;

/**
 * 给Metrics发送消息,Metrics可以开发用于统计的服务
 * http://docs.hostedgraphite.com/languageguide/lg_java
 * .html#sending-a-metric-via-tcp
 * 
 * @author Liuxn
 * 
 */
public class TestMetrics {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new TestMetrics().launch();
	}

	public void launch() {
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			long lTime = System.currentTimeMillis();
			Socket conn = null;
			try {
				int num = random.nextInt(100);
				System.out.println("正在发送第" + i + "条消息:" + num);
				conn = new Socket("mc27", 2003);
				DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
				int iTime = Math.round(lTime / 1000);
				// 格式： graphite显示path 空格 数值 空格 以秒为单位的UTC时间
				dos.writeBytes("1.1.7.81 " + num + " " + iTime + "\n");
				conn.close();
				Thread.sleep(100);
			} catch (Throwable e) {
				e.printStackTrace();
			} finally {
				if (conn != null) {
					try {
						conn.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
