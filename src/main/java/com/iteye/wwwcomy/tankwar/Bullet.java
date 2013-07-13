package com.iteye.wwwcomy.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

public class Bullet {
	private final static int SPEED = 15;
	private final static int SIZE = 5;
	private Color c = Color.green;
	private Direction d;
	private boolean isAlive = false;
	private int x;
	private int y;
	private WarFrame wf;

	private Tank tank;

	public Bullet(Direction d, int x, int y, Tank t, WarFrame wf) {
		this.d = d;
		int[] a = Tank.getLastPoint(d, x, y);
		this.x = a[0];
		this.y = a[1];
		this.isAlive = true;
		this.tank = t;
		this.wf = wf;
	}

	public void move() {
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
		if (x < 0 || y < 0 || x > MainFrame.width || y > MainFrame.height)
			this.isAlive = false;
	}

	public void draw(Graphics g) {
		if (!this.isAlive)
			return;
		Color color = g.getColor();
		g.setColor(this.c);
		g.fillOval(this.x, this.y, SIZE, SIZE);
		g.setColor(color);
		move();
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, SIZE, SIZE);
	}

	public boolean hitTank(Tank t) {
		if (this.getRect().intersects(wf.w.getRect())) {
			this.isAlive = false;
			return false;
		}

		// 不是一拨儿的
		if (t.isLive && this.tank.isEvil() != t.isEvil()
				&& this.getRect().intersects(t.getRect())) {
			this.isAlive = false;
			if (!t.isEvil()) {
				t.setBloodMount(t.getBloodMount() - 1);
				if (t.getBloodMount() <= 0) {
					t.isLive = false;
					wf.ex.add(new Explode(x, y, wf));
					return true;
				}
			} else {
				t.isLive = false;
				wf.ex.add(new Explode(x, y, wf));
				return true;
			}
		}
		return false;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public void hitTanks(List<Tank> tankList) {
		for (int i = 0; i < tankList.size(); i++) {
			if (hitTank(tankList.get(i)))
				tankList.remove(i);
		}

	}
}
