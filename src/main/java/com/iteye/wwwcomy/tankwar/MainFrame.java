package com.iteye.wwwcomy.tankwar;

import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainFrame {
	WarFrame mainFrame;

	public final static int width = 800;
	public final static int height = 600;

	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
		mf.start();
	}

	public void start() {
		mainFrame = new WarFrame("TANK WAR", width, height);
		mainFrame.launchFrame();
		new PaintThread().start();
	}

	class PaintThread extends Thread {
		@Override
		public void run() {
			while (true) {
				mainFrame.repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

class WarFrame extends Frame {
	private static final long serialVersionUID = 1L;

	Tank myTank = new Tank(this, false, 10, Color.red, 20, 20);
	List<Tank> tankList = new LinkedList<Tank>();
	List<Explode> ex = new LinkedList<Explode>();
	List<Bullet> listBullet = new ArrayList<Bullet>();
	private int width;
	private int height;
	Image offScreen = null;
	Wall w;
	NetClient nc = new NetClient(this);

	public WarFrame() {
		super();
		this.width = 800;
		this.height = 600;
	}

	public WarFrame(String title, int x, int y) {
		super(title);
		this.width = x;
		this.height = y;
		// for (int i = 0; i < 10; i++) {
		// Tank t = new Tank(this, true, 1, Color.PINK, 730, (i + 1) * 55);
		// tankList.add(t);
		//	}
	}

	/*
	 * @TODO 感觉没起作用啊, 双缓冲机制 (non-Javadoc)
	 * 
	 * @see javax.swing.JFrame#update(java.awt.Graphics)
	 */
	@Override
	public void update(Graphics g) {
		// super.update(g);
		if (offScreen == null)
			offScreen = this.createImage(width, height);
		Graphics gOffScreen = offScreen.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.white);
		gOffScreen.fillRect(0, 0, width, height);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreen, 0, 0, null);
	}

	public void paint(Graphics g) {
		// super.paint(g);

		w = new Wall(350, 300, 230, 70);
		w.draw(g);
		g.drawString("Bullet Count: " + listBullet.size(), 10, 50);

		g.drawString("Tank Count: " + tankList.size(), 10, 70);
		g.drawString("Explode Count: " + ex.size(), 10, 90);
		Color c = g.getColor();
		myTank.draw(g);
		for (Tank t : tankList) {
			t.draw(g);
			t.cWithTanks(tankList);
		}
		for (int i = 0; i < ex.size(); i++) {
			if (!ex.get(i).isAlive) {
				ex.remove(i);
				continue;
			}
			ex.get(i).draw(g);
		}
		if (listBullet.size() > 0) {
			for (int i = 0; i < listBullet.size(); i++) {
				if (listBullet.get(i) != null && listBullet.get(i).isAlive()) {
					listBullet.get(i).draw(g);
					listBullet.get(i).hitTanks(tankList);
					listBullet.get(i).hitTank(myTank);
				} else
					listBullet.remove(i);
			}
		}
		g.setColor(c);
	}

	public void launchFrame() {
		setBackground(Color.white);
		// getContentPane().setBackground(Color.white);
		setBounds(300, 100, width, height);
		setResizable(false);
		this.addKeyListener(new KeyMoniter());
		setVisible(true);
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		nc.connect("127.0.0.1", TankServer.port);
	}

	private class KeyMoniter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}
	}
}