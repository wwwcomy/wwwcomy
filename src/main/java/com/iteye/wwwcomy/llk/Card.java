package com.iteye.wwwcomy.llk;

import java.awt.Font;
import java.awt.Graphics;

/**
 * POJO of card
 * 
 * @author wwwcomy
 * 
 */
public class Card {
	public final static int width = 50;
	public final static int height = 50;
	private int x;
	private int y;

	private boolean selected = false;
	private int value;

	public Card(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	public void draw(Graphics g) {
		int drawX = (x + 1) * width;
		int drawY = (y + 1) * height;
		g.drawRect(drawX, drawY, width, height);
		Font font = g.getFont();
		Font font2 = new Font(null, 0, 35);
		g.setFont(font2);
		g.drawString(String.valueOf(value), drawX, drawY + height);
		g.setFont(font);
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

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Card:" + value + ",X=" + this.x + "," + "Y=" + this.y;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
