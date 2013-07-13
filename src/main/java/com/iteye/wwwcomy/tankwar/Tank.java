package com.iteye.wwwcomy.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;

/**
 * @author Administrator
 * 
 */
public class Tank {
	// public Tank() {
	// this(true, 1, Color.BLACK, 5, 5);
	// }

	WarFrame wf;

	public Tank(WarFrame wf, boolean evil, int blood, Color c, int x, int y) {
		this.wf = wf;
		this.isEvil = evil;
		this.bloodMount = blood;
		this.c = c;
		this.x = x;
		this.y = y;
	}

	boolean isLive = true;
	public final static int SIZE = 25;
	private final static int SPEED = 5;
	private boolean isEvil;
	private int bloodMount;
	private Color c;
	// 初始位置
	int x;
	int y;
	Direction d = Direction.STOP;
	private Direction lastDir = Direction.D;
	public int id;

	private int step = new Random().nextInt(17);

	private int step1 = new Random().nextInt(15);

	/**
	 * 坦克绘画
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		if (!isLive)
			return;
		if (isEvil() && step > 15) {
			Direction[] dir = Direction.values();
			Random r = new Random();
			int rs = r.nextInt(8);
			d = dir[rs];
			lastDir = d;
			step = 0;
		}
		if (isEvil() && step1 > 12) {
			wf.listBullet.add(this.fire());
			step1 = 0;
		}
		if (isEvil()) {
			step++;
			step1++;
		}

		Color color = g.getColor();
		g.setColor(this.c);
		if (!isEvil)
			g.fillRect(x, y - 10, (SIZE * this.bloodMount / 10), 9);
		g.drawString(String.valueOf(this.id), x, y - 15);
		g.fillOval(this.getX(), this.getY(), SIZE, SIZE);
		g.setColor(color);
		move();
		int[] a = getLastPoint(this.lastDir, this.x, this.y);
		g.drawLine(x + SIZE / 2, y + SIZE / 2, a[0], a[1]);

	}

	public boolean cWithTank(Tank t) {
		if (this.isLive && t.isLive && this != t
				&& this.getRect().intersects(t.getRect()))
			return true;
		return false;
	}

	public void cWithTanks(List<Tank> l) {
		for (int i = 0; i < l.size(); i++) {
			if (cWithTank(l.get(i))) {
				this.x = lastx;
				this.y = lasty;
				l.get(i).x = l.get(i).lastx;
				l.get(i).y = l.get(i).lasty;
			}
		}
	}

	int lastx;
	int lasty;

	public void move() {
		lastx = x;
		lasty = y;
		switch (this.d) {
		case L:
			this.x -= SPEED;
			break;
		case LU:
			this.x -= SPEED;
			this.y -= SPEED;
			break;
		case U:
			this.y -= SPEED;
			break;
		case RU:
			this.x += SPEED;
			this.y -= SPEED;
			break;
		case R:
			this.x += SPEED;
			break;
		case RD:
			this.x += SPEED;
			this.y += SPEED;
			break;
		case D:
			this.y += SPEED;
			break;
		case LD:
			this.x -= SPEED;
			this.y += SPEED;
			break;
		default:
			break;
		}
		if (this.x + SIZE > MainFrame.width)
			this.x = MainFrame.width - SIZE;
		if (this.y + SIZE > MainFrame.height)
			this.y = MainFrame.height - SIZE;
		if (this.x < 0)
			this.x = 0;
		if (this.y < 20)
			this.y = 20;
		if (this.getRect().intersects(wf.w.getRect())) {
			this.x = lastx;
			this.y = lasty;
		}
	}

	public void stop() {
		this.d = Direction.STOP;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, SIZE, SIZE);
	}

	private boolean bL = false, bU = false, bR = false, bD = false;

	public Bullet fire() {
		if (!isLive)
			return null;
		Bullet b = new Bullet(this.lastDir, this.x, this.y, this, this.wf);
		return b;
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37:
			bL = true;
			break;
		case 38:
			bU = true;
			break;
		case 39:
			bR = true;
			break;
		case 40:
			bD = true;
			break;
		case KeyEvent.VK_CONTROL:
			wf.listBullet.add(this.fire());
			break;
		}
		setDir(bL, bU, bR, bD);
	}

	private void setDir(boolean bL, boolean bU, boolean bR, boolean bD) {
		if (bL && !bU && !bR && !bD)
			this.d = Direction.L;
		else if (bL && bU && !bR && !bD)
			this.d = Direction.LU;
		else if (!bL && bU && !bR && !bD)
			this.d = Direction.U;
		else if (!bL && bU && bR && !bD)
			this.d = Direction.RU;
		else if (!bL && !bU && bR && !bD)
			this.d = Direction.R;
		else if (!bL && !bU && bR && bD)
			this.d = Direction.RD;
		else if (!bL && !bU && !bR && bD)
			this.d = Direction.D;
		else if (bL && !bU && !bR && bD)
			this.d = Direction.LD;
		else
			this.d = Direction.STOP;

		if (this.d != Direction.STOP)
			this.lastDir = this.d;

	}

	public boolean isEvil() {
		return isEvil;
	}

	public void setEvil(boolean isEvil) {
		this.isEvil = isEvil;
	}

	public int getBloodMount() {
		return bloodMount;
	}

	public void setBloodMount(int bloodMount) {
		this.bloodMount = bloodMount;
	}

	public Color getC() {
		return c;
	}

	public void setC(Color c) {
		this.c = c;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37:
			bL = false;
			break;
		case 38:
			bU = false;
			break;
		case 39:
			bR = false;
			break;
		case 40:
			bD = false;
			break;
		case KeyEvent.VK_A:
			fireS();
		}
		setDir(bL, bU, bR, bD);
	}

	private void fireS() {
		Direction[] ds = Direction.values();
		for (Direction d : ds)
			wf.listBullet.add(new Bullet(d, this.x, this.y, this, this.wf));
	}

	public static int[] getLastPoint(Direction d, int x, int y) {
		int rx = 0;
		int ry = 0;
		switch (d) {
		case L:
			rx = x;
			ry = y + Tank.SIZE / 2;
			break;
		case LU:
			rx = x;
			ry = y;
			break;
		case U:
			rx = x + Tank.SIZE / 2;
			ry = y;
			break;
		case RU:
			rx = x + Tank.SIZE;
			ry = y;
			break;
		case R:
			rx = x + Tank.SIZE;
			ry = y + Tank.SIZE / 2;
			break;
		case RD:
			rx = x + Tank.SIZE;
			ry = y + Tank.SIZE;
			break;
		case D:
			rx = x + Tank.SIZE / 2;
			ry = y + Tank.SIZE;
			break;
		case LD:
			rx = x;
			ry = y + Tank.SIZE;
			break;
		default:
			break;
		}
		int a[] = { rx, ry };
		return a;
	}
}
