package com.iteye.wwwcomy;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class MainFrame {

	public static void main(String[] args) {
		MyFrame frame = new MyFrame("Resource Renamer");
		frame.launchFrame();
	}

}

class MyFrame extends JFrame {
	MyFrame() {
		super();
	}

	MyFrame(String title) {
		super(title);
	}

	private static final long serialVersionUID = 1L;
	public JLabel label = new JLabel("文件目录");
	public JTextField text = new JTextField();
	public JButton fileChooseButton = new JButton("...");
	public JTextArea jta = new JTextArea();

	public void launchFrame() {
		JPanel jp = new JPanel();
		setBounds(300, 400, 300, 400);
		add(jp);
		jta.setEditable(false);
		jp.add(label);
		jp.add(text);
		jp.add(fileChooseButton);
		jp.add(jta);
		fileChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				jfc.showOpenDialog(null);
				File file = jfc.getSelectedFile();
				if (file != null && file.isDirectory()) {
					text.setText(file.getAbsolutePath());
				} else {
					throw new RuntimeException("Should select a directory");
				}
			}
		});
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				System.exit(0);
			}
		});
		setVisible(true);
	}

}
