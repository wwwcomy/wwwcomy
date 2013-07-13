package com.iteye.wwwcomy.frame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;

public class TestFrame2 {

	public static void main(String[] args) {
		Frame2 f = new Frame2();
		f.launch();
	}

}

class Frame2 extends JFrame {
	private static final long serialVersionUID = 1L;

	Frame2() {
		super();
	}

	public void launch() {
		setSize(100, 100);
		setLocation(300, 200);
		this.setBackground(Color.green);
		this.getContentPane().setBackground(Color.green);
		this.addMouseListener(new mouseListener(this));
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private List<Point> p = new LinkedList<Point>();

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		for (Point ps : p)
			// g.drawArc(ps.x, ps.y, 5, 5, 0, 1);
			g.fillOval(ps.x, ps.y, 5, 5);
		// super.paint(g);
	}

	public void addPoint(int x, int y) {
		p.add(new Point(x, y));
		this.repaint();
	}

	class mouseListener extends MouseAdapter {
		Frame2 f;

		public mouseListener(Frame2 f) {
			this.f = f;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			f.addPoint(e.getX(), e.getY());
		}
	}
}
