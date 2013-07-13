package com.iteye.wwwcomy.tankwar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {

	int x;
	int y;
	int width;
	int height;
	Color c = Color.green;

	public Wall(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, height);
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(this.c);
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
}
