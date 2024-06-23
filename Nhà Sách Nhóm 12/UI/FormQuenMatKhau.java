package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.AbstractBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import connectDB.ConnectDB;
import dao.DAO_TaiKhoan;
import entity.NhanVien;
import entity.TaiKhoan;

public class FormQuenMatKhau extends JDialog implements ActionListener {

	private JTextField txtEmail;

	private JButton btnDMK;
	private DAO_TaiKhoan taiKhoanDao;

	public FormQuenMatKhau() {
		taiKhoanDao=new DAO_TaiKhoan();
		setTitle("Quên mật khẩu");
		setSize(600, 350);

		setLocationRelativeTo(null);
		setResizable(false);
		AbstractBorder brdr = new TextBubbleBorder(new Color(255, 153, 51), 2, 0, 1);

		JPanel pRight;
		add(pRight = new JPanel(), BorderLayout.CENTER); 
		pRight.setPreferredSize(new Dimension(0, 380)); 
		pRight.setLayout(null); 
		JLabel lblTieuDe=new JLabel("QUÊN MẬT KHẨU");
		
		lblTieuDe.setBounds(200, 30, 200, 30);
		lblTieuDe.setFont(new Font("Time New Roman", Font.BOLD, 22));
		lblTieuDe.setForeground(new Color(255, 153, 51));
		pRight.add(lblTieuDe);
		JLabel lblEmail;
		pRight.add(lblEmail = new JLabel("Email: "));
		pRight.add(txtEmail = new JTextField());

		lblEmail.setBounds(150, 130, 80, 30);

		txtEmail.setBounds(250, 130, 180, 30);

		pRight.add(btnDMK = new JButton("Gửi"));

		btnDMK.setBackground(Color.GRAY);
		btnDMK.setForeground(Color.WHITE);
		btnDMK.setBounds(200, 230, 180, 30);
		btnDMK.setBackground(new Color(255, 153, 51));

		btnDMK.addActionListener(this);

	}



	public static void main(String[] args) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o.equals(btnDMK)) {
			if(txtEmail.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Cần nhập email để cấp lại mật khẩu");
			}
			else {
				if(taiKhoanDao.kiemTraEmail(txtEmail.getText()) != null) {
					NhanVien nv = taiKhoanDao.kiemTraEmail(txtEmail.getText());
					GuiEmail(txtEmail.getText());
					JOptionPane.showMessageDialog(null, "Mật khẩu mới đã được gửi tới email của " + nv.getTenNV());
					dispose();
				}
				else {
					JOptionPane.showMessageDialog(null, "Email chưa được đăng ký với cửa hàng");
				}
			}
		}
	}
	
	public void GuiEmail(String email) {
		String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String lower = "abcdefghijklmnopqrstuvwxyz";
		String num = "0123456789";
		String kethop = upper + lower + num;
		int len = 6;
		char[] matKhau = new char[len];
		Random r = new Random();
		for (int i = 0; i < len; i++) {
			matKhau[i] = kethop.charAt(r.nextInt(kethop.length()));
		}
		
		if(taiKhoanDao.capNhatMatKhauVoiEmail(email, new String(matKhau))) {
			try {
				Properties properties = new Properties();
				properties.put("mail.smtp.auth", "true");
				properties.put("mail.smtp.starttls.enable", "true");
				properties.put("mail.smtp.host", "smtp.gmail.com");
				properties.put("mail.smtp.port", "587");
				Session session = Session.getDefaultInstance(properties,
					new Authenticator() {
						@Override
						protected PasswordAuthentication getPasswordAuthentication() {
							// TODO Auto-generated method stub
							return new PasswordAuthentication("levohuuthai@gmail.com","Levohuuthai123");
						}			
				});
				Message message = new MimeMessage(session);
				message.setSubject("Mật khẩu được cấp lại !");
				MimeBodyPart tinNhan = new MimeBodyPart();				
				tinNhan.setContent("<h1>Mật khẩu của bạn được cấp lại từ nhà sách Flames !</h1><br>","text/html; charset=UTF-8");
				
				MimeBodyPart mkm = new MimeBodyPart();
				mkm.setContent("<b>Mật khẩu mới: </b>"+new String(matKhau)+"<br>","text/html; charset=UTF-8");	
							
				MimeMultipart multipart = new MimeMultipart();
				multipart.addBodyPart(tinNhan);
				multipart.addBodyPart(mkm);
				message.setContent(multipart);
				message.setFrom(new InternetAddress(email));
				message.setRecipient(RecipientType.TO, new InternetAddress(email));
				message.setSentDate(new java.util.Date());
				
				Transport.send(message);
			} catch (Exception e2) {
				
			}
		}
	}
}
