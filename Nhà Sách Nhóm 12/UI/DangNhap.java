
package UI;

import java.awt.BorderLayout;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;
import javax.swing.plaf.ColorUIResource;

import connectDB.ConnectDB;
import dao.DAO_TaiKhoan;
import entity.TaiKhoan;

import java.awt.event.FocusAdapter;

//import dao.TaiKhoan_DAO;
//import entity.TaiKhoan;
import javax.swing.border.*;

/*
 * Trần Đình Phong
 * */
public class DangNhap extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private JLabel lblTieuDe;
	private JLabel lbliCon1;
	private JLabel lblTaiKhoan;
	private JTextField txtTaiKhoan;
	private JLabel lblMK;
	private JPasswordField txtMK;
	private JButton btnLog;
	private JButton btnExit;
	private JLabel lblTitle;
	private DAO_TaiKhoan taiKhoan;

	public DangNhap() {
		taiKhoan = new DAO_TaiKhoan();
		ConnectDB con = new ConnectDB();
		try {
			con.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection conn = con.getConnection();
		setTitle("Nhà sách Nhóm 12");
		setSize(600, 350);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		AbstractBorder brdr = new TextBubbleBorder(new Color(255, 153, 51), 2, 0, 1);
		
		ImageIcon imgDefault = new ImageIcon("src//Icon//iconCuaHangSach.png");
		Image imgDefault1 = imgDefault.getImage();
		Image newImgDefault1 = imgDefault1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		imgDefault = new ImageIcon(newImgDefault1);
		this.setIconImage(imgDefault.getImage());
		// NORTH
		JPanel pNorth;
		add(pNorth = new JPanel(), BorderLayout.NORTH);
//			pNorth.add(lblTieuDe = new JLabel("Đăng nhập"));
//			pNorth.setBorder(BorderFactory.createLineBorder(Color.blue));
//			lblTieuDe.setFont(new Font("SansSerif", Fontin.BOLD, 30));
		getRootPane().setBorder(brdr);
		// WEST;
		JPanel pLeft;
		add(pLeft = new JPanel(), BorderLayout.WEST);
		// pLeft.setBorder(BorderFactory.createLineBorder(Color.blue));


		pLeft.add(lbliCon1 = new JLabel(ResizeImage("src//Icon/imgLogin.png")));
		lbliCon1.setPreferredSize(new Dimension(220, 210));
		lbliCon1.setBounds(200, 100, 220, 210);
		// EAST
		JPanel pRight;
		add(pRight = new JPanel(), BorderLayout.CENTER); // b1
		pRight.setPreferredSize(new Dimension(0, 380)); // b2
		pRight.setLayout(null); // b3

		pRight.add(lblTieuDe = new JLabel("ĐĂNG NHẬP"));
		pRight.add(lblTaiKhoan = new JLabel("Tài khoản"));
		pRight.add(txtTaiKhoan = new JTextField());
		pRight.add(lblMK = new JLabel("Mật khẩu"));
		pRight.add(txtMK = new JPasswordField());

		// Gan vao vi tri
		lblTieuDe.setBounds(120, 30, 200, 30);
		lblTieuDe.setFont(new Font("Time New Roman", Font.BOLD, 22));
		lblTieuDe.setForeground(new Color(255, 153, 51));

		//
		lblTaiKhoan.setBounds(35, 80, 80, 30);
		lblMK.setBounds(35, 130, 80, 30);

		txtTaiKhoan.setBounds(100, 80, 180, 30);
		txtTaiKhoan.setText("NV001");
		txtMK.setBounds(100, 130, 180, 30);
		txtMK.setText("123456");
		pRight.add(btnLog = new JButton("Đăng nhập"));
		btnLog.setForeground(Color.WHITE);
		btnLog.setBackground(new Color(255, 153, 51));
		btnLog.setBounds(100, 180, 180, 30);
		
		pRight.add(btnExit = new JButton("Quên mật khẩu"));
		btnExit.setBackground(Color.GRAY);
		btnExit.setForeground(Color.WHITE);
		
		btnExit.setBounds(200, 230, 120, 30);

		// Gắn sự kiện
		btnLog.addActionListener(this);
		btnExit.addActionListener(this);
		txtMK.addKeyListener(this);

	}

	public static void main(String[] args) throws InterruptedException {
		ManHinhKhoiDong manHinhKhoiDong= new ManHinhKhoiDong();
		manHinhKhoiDong.setVisible(true);
		manHinhKhoiDong.setLocationRelativeTo(null);
		for (int i = 0; i < 101; i++) {
			Thread.sleep(15);
			manHinhKhoiDong.progressBar.setValue(i);			
			manHinhKhoiDong.progressBar.setString("Đang tải "+Integer.toString(i)+"%");		
			if(i==100) {
				manHinhKhoiDong.setVisible(false);
				new DangNhap().setVisible(true);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void dangNhap() throws Exception {
		
		TaiKhoan tk=taiKhoan.login(txtTaiKhoan.getText(), txtMK.getText());
		if (tk!=null) {
			ChucNang gdc = new ChucNang(txtTaiKhoan.getText());
			gdc.setVisible(true);
			dispose();
			return ;
		} else {
			JOptionPane.showMessageDialog(this, "Tài khoản hoặc mật khẩu của bạn không hợp lệ!");
			return ;
		}
	}
//	String maHoa = Base64.getEncoder().encodeToString("123456".getBytes());
//	System.out.println(maHoa +"mahoa");
	
//	String giaiMa = new String (Base64.getMimeDecoder().decode(maHoa));
//	System.out.println(giaiMa + " Giải mã");
	
	
	
	//LOGIN
	//String giaiMaMatKhau = new String (Base64.getMimeDecoder().decode(password));
//			String giaiMaMatKhau = Base64.getEncoder().encodeToString(password.getBytes());	//Giair mã bằng ma hoa
//			System.out.println(giaiMaMatKhau);
//	
	private ImageIcon validationImage(java.net.URL imgURL, int width, int height) {
		ImageIcon image = null;
		if (imgURL != null) {
			image = (new ImageIcon(((new ImageIcon(imgURL)).getImage()).getScaledInstance(width, height,
					java.awt.Image.SCALE_SMOOTH)));
		} else {
			JOptionPane.showMessageDialog(this, "Icon image not found.");
		}
		return image;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if (o.equals(btnLog)) {
			try 
			{
				dangNhap();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (o.equals(btnExit)) {
			FormQuenMatKhau qmk=new FormQuenMatKhau();
			qmk.setVisible(true);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {//
		// TODO Auto-generated method stub
//		if(e.getKeyCode() == KeyEvent.VK_ENTER)
//		{
//			try {
//				dangNhap();
//			} catch (ClassNotFoundException | SQLException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(190, 180, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	
}
