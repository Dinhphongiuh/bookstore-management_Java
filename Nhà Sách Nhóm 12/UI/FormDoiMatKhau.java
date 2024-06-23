package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Connection;

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
import entity.TaiKhoan;

public class FormDoiMatKhau extends JDialog implements ActionListener {
	
	private JTextField txtMaNV;
	private JPasswordField txtMKCu;
	private JPasswordField txtMKMoi;
	private JButton btnDMK;
	private DAO_TaiKhoan taiKhoanDao;

	public FormDoiMatKhau() {
		taiKhoanDao=new DAO_TaiKhoan();
		setTitle("Đổi mật khẩu");
		setSize(600, 350);

		setLocationRelativeTo(null);
		setResizable(false);
		AbstractBorder brdr = new TextBubbleBorder(new Color(255, 153, 51), 2, 0, 1);

		// EAST
		JPanel pRight;
		add(pRight = new JPanel(), BorderLayout.CENTER); // b1
		pRight.setPreferredSize(new Dimension(0, 380)); // b2
		pRight.setLayout(null); // b3
		JLabel lblTieuDe=new JLabel("ĐỔI MẬT KHẨU");
		
		lblTieuDe.setBounds(200, 30, 200, 30);
		lblTieuDe.setFont(new Font("Time New Roman", Font.BOLD, 22));
		lblTieuDe.setForeground(new Color(255, 153, 51));
		pRight.add(lblTieuDe);
		JLabel lbMaNV;
		pRight.add(lbMaNV = new JLabel("Mã nhân viên"));
		pRight.add(txtMaNV = new JTextField());
		JLabel lblMKCu;
		pRight.add(lblMKCu = new JLabel("Mật khẩu cũ"));
		pRight.add(txtMKCu = new JPasswordField());

		JLabel lblMKMoi;
		pRight.add(lblMKMoi = new JLabel("Mật khẩu mới"));
		pRight.add(txtMKMoi = new JPasswordField());

		// Gan vao vi tri

		//
		lbMaNV.setBounds(150, 80, 80, 30);
		lblMKCu.setBounds(150, 130, 80, 30);
		lblMKMoi.setBounds(150, 180, 80, 30);
		txtMaNV.setBounds(250, 80, 180, 30);
		txtMKCu.setBounds(250, 130, 180, 30);
		txtMKMoi.setBounds(250, 180, 180, 30);
		pRight.add(btnDMK = new JButton("Lưu thay đổi"));

		btnDMK.setBackground(Color.GRAY);
		btnDMK.setForeground(Color.WHITE);
		btnDMK.setBounds(200, 230, 180, 30);
		btnDMK.setBackground(new Color(255, 153, 51));

		// Gắn sự kiện
		btnDMK.addActionListener(this);

	}



	public static void main(String[] args) {
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o=e.getSource();
		if(o.equals(btnDMK)) {
			System.out.println(ChucNang.maNhanVien);
			TaiKhoan tk=taiKhoanDao.login(txtMaNV.getText(),txtMKCu.getText());
			if(tk!=null) {
				if(ChucNang.maNhanVien.equals(txtMaNV.getText())) {
					taiKhoanDao.updateMK(txtMaNV.getText(), txtMKMoi.getText());
					JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công");
					txtMaNV.setText("");
					txtMKCu.setText("");
					txtMKMoi.setText("");
				}
				else {
					JOptionPane.showMessageDialog(this, "Không thể đổi mật khẩu của nhân viên khác");
				}
			}
			else {
				JOptionPane.showMessageDialog(this, "Mã nhân viên hoặc mật khẩu không hợp lệ");
			}
		}
		
	}
}
