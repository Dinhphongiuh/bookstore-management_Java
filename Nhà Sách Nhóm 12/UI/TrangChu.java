package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
/**
 * 
 * Đoàn Hữu Phúc
 *
 */
public class TrangChu extends JPanel{
	ImageIcon background;
	JPanel pnMain = new JPanel();
	 JLabel lblBackground;
	 ClockThread clockThread;
	 
	 JLabel lblNV;
	private JLabel lblLogo;
	public TrangChu() {
		setSize(1400,800);
		setLayout(new BorderLayout());
		
		ImageIcon img = new ImageIcon("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\pexels-pixabay-415071.jpg");
		Image img1 = img.getImage();
		Image temp_img = img1.getScaledInstance(1400,800, Image.SCALE_SMOOTH);
		img = new ImageIcon(temp_img);
		lblBackground = new JLabel("",img,JLabel.CENTER);
		lblBackground.setBounds(0,0,1400,800);
		lblBackground.setLayout(new BoxLayout(lblBackground, BoxLayout.Y_AXIS));
		//lblBackground.setLayout(new BorderLayout());

		Font fontGio =new Font("Arial",Font.BOLD,20);
		Font fontTen =new Font("Arial",Font.BOLD,50);
		Font fontND =new Font("Arial",Font.BOLD | Font.ITALIC,18);
		JLabel lblGio  = new JLabel("");
		lblGio.setFont(fontGio);
		lblGio.setForeground(new Color(255, 113, 51));
		lblLogo = new JLabel(ResizeImage("src//Icon//logo-removebg-preview.png"));
		lblLogo.setPreferredSize(new Dimension (140,50));
		JLabel lblTen  = new JLabel("Hệ thống quản lý nhà sách Nhóm 12");
		lblTen.setForeground(Color.RED);
		lblTen.setFont(fontTen);
		
		JLabel lblNoiDung = new JLabel("<html>Ứng dụng dành cho những cửa hàng sách muốn tìm một phần mềm hỗ trợ quản lí cửa hàng để giúp cho các nhân viên, chủ cửa hàng  <BR>"
				+ " quản lí cửa hàng một cách tối ưu. <BR>"
				+ " Chúng tôi đang cố gắng tạo ra một ứng dụng dễ dàng sử dụng đối với mọi người độ hiệu quả cao và xử lí nhanh, chính xác và có thể <BR>"
				+ " mở rộng thêm nếu cần thiết. <BR>"
				+ " Sự góp ý của các bạn sẽ góp phần giúp chúng tôi phát triển hơn.Sự hài lòng của quý khách luôn là tiêu chí hàng đầu của chúng tôi<BR>"
				+ " mở rộng thêm nếu cần thiết. <BR>"
				+ "</html>",JLabel.CENTER);
		lblNoiDung.setFont(fontND);
		lblNoiDung.setForeground(new Color(255, 113, 51));
		
		lblGio.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblNoiDung.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		clockThread = new ClockThread(lblGio);
		clockThread.start();
	
		lblBackground.add(Box.createVerticalStrut(20));
		lblBackground.add(lblGio);
		lblBackground.add(Box.createVerticalStrut(0));
		lblBackground.add(lblLogo);
		lblBackground.add(Box.createVerticalStrut(0));
		lblBackground.add(lblTen);
		lblBackground.add(Box.createVerticalStrut(3));
		lblBackground.add(lblNoiDung);
		add(lblBackground,BorderLayout.CENTER);			
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TrangChu trangchu = new TrangChu();
		trangchu.setVisible(true);
	}
	public ImageIcon ResizeImage(String ImagePath)
	 {
		 ImageIcon MyImage = new ImageIcon(ImagePath);
		 Image img = MyImage.getImage();
		 Image newImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		 ImageIcon image = new ImageIcon(newImg);
	     return image;
	 }
}
