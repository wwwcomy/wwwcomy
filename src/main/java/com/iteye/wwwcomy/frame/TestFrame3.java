package com.iteye.wwwcomy.frame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class TestFrame3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Frame3 f = new Frame3();
		f.launch();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

}

class Frame3 extends JFrame {
	private static final long serialVersionUID = 1L;

	private int i = 0;

	public void launch() {
		JButton b1 = new JButton("Start");
		final JTextField text1 = new JTextField(20);
		text1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case 37:
					text1.setText("Fuck Left");
					break;
				case 38:
					text1.setText("Fuck Up");
					break;
				case 39:
					text1.setText("Fuck Right");
					break;
				case 40:
					text1.setText("Fuck Down");
					break;
				default:
					break;
				}
			}
		});
		b1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				i++;
				text1.setText("Start" + i);
			}
		});
		setLayout(new GridLayout(2, 1));
		add(b1);
		add(text1);
		this.pack();
		setVisible(true);
	}
}
