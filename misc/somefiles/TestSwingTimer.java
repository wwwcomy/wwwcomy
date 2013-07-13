import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.Timer;

public class TestSwingTimer {
	public static void main(String[] args) {
		ActionListener taskAction = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Time::" + (new Date().toLocaleString()));
				// 显示当前时间
			}
		};
		new Timer(1000, taskAction).start();// Timer每一秒轮转一次
		try {
			Thread.sleep(8 * 1000);// 利用线程设定程序存在时间为3秒
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		System.exit(0);// 结束程序进程
	}
}