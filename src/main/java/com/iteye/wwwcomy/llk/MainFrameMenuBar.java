package com.iteye.wwwcomy.llk;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainFrameMenuBar extends JMenuBar {

	private static final long serialVersionUID = -7722190096259756717L;

	private String[] fileItems = new String[] { "重排", "退出" };

	CardHolder holder;
	JFrame mainFrame;

	public MainFrameMenuBar(CardHolder cardHolder, final JFrame mainFrame) {
		this.holder = cardHolder;
		this.mainFrame = mainFrame;
		JMenu fileMenu = new JMenu("File");
		ActionListener defaultListener = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if ("重排".equals(event.getActionCommand())) {
					if (holder != null) {
						holder.makeCardsRandom();
						mainFrame.repaint();
					}
				} else {
					System.exit(0);
				}
			}
		};
		for (int i = 0; i < fileItems.length; i++) {
			JMenuItem item = new JMenuItem(fileItems[i]);
			item.addActionListener(defaultListener);
			fileMenu.add(item);
		}
		add(fileMenu);
	}

}
