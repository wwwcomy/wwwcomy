package com.iteye.wwwcomy;
import java.awt.BorderLayout;
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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

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
	public JLabel consoleLabel = new JLabel("Console output:");
	public JTextField text = new JTextField();
	public JButton fileChooseButton = new JButton("...");
	public JTextArea jta = new JTextArea();
	public JButton startRenaming = new JButton("Start");
	public JScrollPane jsp = new JScrollPane(jta);

	public void launchFrame() {
		setLayout(new BorderLayout(5, 5));
		JPanel jp = new JPanel();
		setBounds(300, 400, 350, 400);
		jp.setLayout(new MigLayout("wrap 3"));
		jta.setEditable(false);
		jta.setLineWrap(true);
		text.setEditable(false);
		text.setText(IPhoneResourceRenamer.FOLDER_NAME);
		jp.add(label);
		jp.add(text, "width 80:200:200");
		jp.add(fileChooseButton, "gap unrelated");
		jp.add(consoleLabel, "wrap");
		jp.add(jsp, "width 320:320:320, height 280:280:280, span 3");
		add("South", startRenaming);

		add(jp, BorderLayout.CENTER);
		fileChooseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser(IPhoneResourceRenamer.FOLDER_NAME);
				jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				jfc.showOpenDialog(null);
				File file = jfc.getSelectedFile();
				if (file != null && file.isDirectory()) {
					text.setText(file.getAbsolutePath());
				} else {
					System.out.println("Should select a directory");
				}
			}
		});
		startRenaming.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IPhoneResourceRenamer renamer = new IPhoneResourceRenamer();
				try {
					renamer.beginTask(jta, text.getText());
				} catch (Exception e1) {
					e1.printStackTrace();
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
