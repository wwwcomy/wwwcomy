package com.iteye.wwwcomy.llk;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	private static final int width = 800;
	private static final int height = 600;

	private static CardHolder cardHolder = new CardHolder();

	public static MainFrame frame;

	public static void main(String[] args) {
		frame = new MainFrame();
		frame.start();
	}

	private void start() {
		frame.setBackground(Color.black);
		setSize(width, height);
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
					if (card == selectedCard) {
						cardHolder.setSelectedCard(null);
						frame.repaint();
						return;
					}
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
		frame.setJMenuBar(new MainFrameMenuBar(cardHolder, this));
		frame.setVisible(true);
		// new Thread(new PaintThread()).start();
	}

	Image offScreen = null;

	@Override
	public void paint(Graphics g) {

		offScreen = this.createImage(width, height);
		Graphics g2=offScreen.getGraphics();  

		super.paint(g2);
		cardHolder.draw(g2);
		
		g.drawImage(offScreen, 0, 0, null);
		
		
	}

}
