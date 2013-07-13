package com.iteye.wwwcomy.tankwar;

import java.awt.Color;
import java.awt.Graphics;

public class Explode {
	int x, y;
	boolean isAlive = true;

	@SuppressWarnings("unused")
	private WarFrame mf;

	public Explode(int x, int y, WarFrame mf) {
		this.x = x;
		this.y = y;
		this.mf = mf;
	}

	int[] a = { 4, 7, 12, 18, 24, 28, 34, 44, 22, 5 };

	int step = 0;

	public void draw(Graphics g) {
		if (!this.isAlive)
			return;
		if (step == a.length) {
			this.isAlive = false;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.orange);
		g.fillOval(x, y, a[step], a[step]);
		g.setColor(c);
		step++;
	}
}
