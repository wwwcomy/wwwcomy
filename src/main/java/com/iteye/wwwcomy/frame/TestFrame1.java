package com.iteye.wwwcomy.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class TestFrame1 {
	public static void main(String[] args) {
		Frame1 f = new Frame1();
		f.begin();
	}
}

class Frame1 extends JFrame {

	private static final long serialVersionUID = 1L;

	JTextField num1;
	JTextField num2;
	JTextField num3;
	JLabel label1;
	JButton btn;

	public void begin() {
		num1 = new JTextField(10);
		num2 = new JTextField(10);
		num3 = new JTextField(15);
		label1 = new JLabel("+");
		btn = new JButton("=");
		setBounds(70, 70, 500, 300);
		setLayout(new FlowLayout());
		add(num1);
		add(label1);
		add(num2);
		add(btn);
		add(num3);
		btn.addActionListener(new monitor());
		pack();
		setVisible(true);
	}

	private class monitor implements Action {

		@Override
		public void actionPerformed(ActionEvent e) {
			int n1 = Integer.parseInt(num1.getText());
			int n2 = Integer.parseInt(num2.getText());
			int n3 = n1 + n2;
			num3.setText(String.valueOf(n3));
		}

		@Override
		public Object getValue(String key) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void putValue(String key, Object value) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setEnabled(boolean b) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean isEnabled() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void addPropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removePropertyChangeListener(PropertyChangeListener listener) {
			// TODO Auto-generated method stub

		}
	}
}
