package com.iteye.wwwcomy.lxn.nio;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioEchoClient {

	public static void main(String[] args) throws IOException {
		Selector selector = Selector.open();

		SocketChannel sc = SocketChannel.open();
		sc.configureBlocking(false);
		sc.connect(new InetSocketAddress(7777));
		sc.register(selector, SelectionKey.OP_CONNECT);

		BufferedReader stdReader = new BufferedReader(new InputStreamReader(System.in));
		String line;

		Iterator<SelectionKey> keys;
		SelectionKey key;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int len;
		while (selector.select() >= 0) {
			keys = selector.selectedKeys().iterator();
			while (keys.hasNext()) {
				key = keys.next();
				keys.remove();

				if (key.isConnectable()) {
					SocketChannel sc2 = (SocketChannel) key.channel();
					if (sc2.isConnectionPending()) {
						sc2.finishConnect();
					}
					key.interestOps(SelectionKey.OP_WRITE);
				}
				if (key.isWritable()) {
					// block here
					System.out.println("Please input something, \"exit\" to return.");
					if ((line = stdReader.readLine()) != null) {
						SocketChannel sc2 = (SocketChannel) key.channel();
						if (line.equals("exit")) {
							sc2.close();
							System.exit(0);
							return;
						}
						sc2.write(ByteBuffer.wrap(line.getBytes()));
						key.interestOps(SelectionKey.OP_READ);
						// 这里即使加上 Thread.sleep(3000);
						// 也不会影响console的输入,怀疑是console的输入是在另外一个线程的，加上之后可以看到server端还是会等待几秒才能收到console中输入的内容
					}
				}
				if (key.isReadable()) {
					SocketChannel sc2 = (SocketChannel) key.channel();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					buffer.clear();
					while ((len = sc2.read(buffer)) > 0) {
						buffer.flip();
						baos.write(buffer.array(), 0, len);
					}
					System.out.println("Receive from server: " + baos.toString());
					key.interestOps(SelectionKey.OP_WRITE);
				}
			}
		}
	}

}
