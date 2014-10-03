package com.iteye.wwwcomy.llk;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static CardHolder cardHolder = new CardHolder();

	public static void main(String[] args) {
		final MainFrame frame = new MainFrame();
		frame.setSize(800, 600);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		frame.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				Card card = cardHolder.getCardByLocation(x, y);
				if (card == null)
					return;
				if (cardHolder.getSelectedCard() != null) {
					Card selectedCard = cardHolder.getSelectedCard();
					boolean destroy = cardHolder.judgeSame(selectedCard, card);
					if (destroy) {
						System.out.println("Matched!");
						cardHolder.removeCard(selectedCard);
						cardHolder.removeCard(card);
					}
					cardHolder.setSelectedCard(null);
				} else {
					cardHolder.setSelectedCard(card);
				}
				System.out.println(card);
				frame.repaint();
			}
		});
		frame.setVisible(true);
	}

	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.red);
		cardHolder.draw(g);
		g.setColor(c);
	}
}
