package UI;

import java.text.SimpleDateFormat;

import javax.swing.JLabel;

public class ClockThread extends Thread{
	private JLabel lbl;
	public ClockThread(JLabel lbl) {
		this.lbl = lbl;
	}
	public void run() {
		SimpleDateFormat sdf =new SimpleDateFormat("hh:mm:ss aa");
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = formatter.format(date);
		while(true) {
			java.util.Date now = new java.util.Date();
			String st = sdf.format(now);
			lbl.setText(st +  "   "+strDate);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
}
