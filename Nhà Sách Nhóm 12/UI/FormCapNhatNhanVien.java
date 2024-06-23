 package UI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dao.DAO_NhanVien;
import entity.NhanVien;

public class FormCapNhatNhanVien extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private DAO_NhanVien dao_nv = new DAO_NhanVien();
	private QuanLiNhanVien ttnv = new QuanLiNhanVien();
	private JButton btnCapNhat;
	private JLabel lbmaNV;
	private JLabel lbtenNV;
	private JLabel lbdiachiNV;
	private JLabel lbemailNV;
	private JLabel lbsdtNV;
	private JLabel lbChucVu;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtDcNV;
	private JTextField txtemail;
	private JTextField txtSdtNV;
	private JComboBox<String> cbxGioiTinh;
	private JComboBox cbxChucVu;
	public FormCapNhatNhanVien(NhanVien nv) {

		// TODO Auto-generated constructor stub
		setTitle("Cập Nhật Nhân Viên");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(680, 550);
		setLocationRelativeTo(null);

		JPanel pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 680, 500);

		JLabel lbTitle = new JLabel("Quản Lý Nhân Viên");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lbTitle.setForeground(new Color(255,113,51));
		lbTitle.setBounds(225, 10, 300, 30);
		pnMain.add(lbTitle);

		JPanel pnInput = new JPanel();
		pnInput.setLayout(null);
		pnInput.setBounds(0, 50, 680, 550);

		lbmaNV = new JLabel("Mã Nhân Viên: ");
		lbtenNV = new JLabel("Tên Nhân Viên: ");
		lbdiachiNV = new JLabel("Địa Chỉ: ");
		lbemailNV = new JLabel("Email");
		lbsdtNV = new JLabel("Số Điện Thoại: ");
		lbChucVu = new JLabel("Chức Vụ");

		txtMaNV = new JTextField();
		txtTenNV = new JTextField();
		txtDcNV = new JTextField();
		txtemail = new JTextField();
		txtSdtNV = new JTextField();
		cbxChucVu = new JComboBox();
		cbxChucVu.addItem("Nhân Viên");
		cbxChucVu.addItem("Chủ Cửa Hàng");
		cbxChucVu.setPreferredSize(new Dimension(170, 25));

		txtMaNV.setText(nv.getMaNV());
		txtMaNV.setEditable(false);
		txtMaNV.setBackground(null);
		txtTenNV.setText(nv.getTenNV());
		txtDcNV.setText(nv.getDiaChi());
		txtemail.setText(nv.getEmail());
		txtSdtNV.setText(nv.getSoDT());
		cbxChucVu.setSelectedItem(nv.getChucVu());
		
		lbmaNV.setBounds(20, 30, 130, 30);
		txtMaNV.setBounds(150, 30, 450, 30);
		lbtenNV.setBounds(20, 80, 130, 30);
		txtTenNV.setBounds(150, 80, 450, 30);
		lbdiachiNV.setBounds(20, 130, 130, 30);
		txtDcNV.setBounds(150, 130, 450, 30);
		lbemailNV.setBounds(20, 180, 140, 30);
		txtemail.setBounds(150, 180, 450, 30);
		lbsdtNV.setBounds(20, 230, 140, 30);
		txtSdtNV.setBounds(150, 230, 450, 30);
		lbChucVu.setBounds(20, 280, 140, 30);
		cbxChucVu.setBounds(150, 280, 150, 30);

		pnInput.add(lbmaNV);
		pnInput.add(lbtenNV);
		pnInput.add(lbdiachiNV);
		pnInput.add(lbemailNV);
		pnInput.add(lbsdtNV);
		pnInput.add(lbChucVu);

		pnInput.add(txtMaNV);
		pnInput.add(txtTenNV);
		pnInput.add(txtDcNV);
		pnInput.add(txtemail);
		pnInput.add(txtSdtNV);
		pnInput.add(cbxChucVu);

		btnCapNhat = new JButton("Cập Nhật");
		btnCapNhat.setBounds(520, 450, 130, 50);
		btnCapNhat.setBackground(new Color(255,113,51));
		btnCapNhat.setOpaque(true); 
    	btnCapNhat.setBorderPainted(false);
		// btnCapNhat.setOpaque(true);

		JLabel lblGioiTinh = new JLabel("Giới tính");
		cbxGioiTinh = new JComboBox<String>();
		cbxGioiTinh.addItem("");
		cbxGioiTinh.addItem("Nam");
		cbxGioiTinh.addItem("Nữ");
		cbxGioiTinh.setPreferredSize(new Dimension(170, 25));
		cbxGioiTinh.setSelectedItem(nv.getGioiTinh());

		lblGioiTinh.setBounds(20, 380, 140, 30);
		add(lblGioiTinh);
		cbxGioiTinh.setBounds(150, 380, 150, 30);
		add(cbxGioiTinh);

		pnMain.add(pnInput);
		add(btnCapNhat);
		add(pnMain);
		btnCapNhat.addActionListener(this);
//		txtMaNV.addKeyListener(new KeyAdapter() {			????Cập nhật thì gõ mã để hiện làm gì????
//			@Override
//			public void keyReleased(KeyEvent e) {
//
//				NhanVien nv = dao_nv.getNhanVienbyHDId("maNhanVien", txtMaNV.getText());
//				if (nv != null) {
//
//					txtTenNV.setText(nv.getTenNV());
//					txtDcNV.setText(nv.getDiaChi());
//					txtemail.setText(nv.getEmail());
//					txtSdtNV.setText(nv.getSoDT());
//					txtChucVu.setText(nv.getChucVu());
//					cbxGioiTinh.setSelectedItem(nv.getGioiTinh());
//
//				} else {
//
//					txtTenNV.setText("");
//					txtDcNV.setText("");
//					txtemail.setText("");
//					txtSdtNV.setText("");
//					txtChucVu.setText("");
//
//				}
//
//			}
//		});
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnCapNhat)) {
			try {
				if (validData()) {
					JFrame f = new JFrame();
					int hoi = JOptionPane.showConfirmDialog(f, "Nhân viên này sẽ được cập nhật", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (hoi == JOptionPane.YES_OPTION) {
						String maNV = txtMaNV.getText();
						String tenNV = txtTenNV.getText();
						String diaChi = txtDcNV.getText();
						String email = txtemail.getText();
						String soDT = txtSdtNV.getText();
						String gioiTinh = cbxGioiTinh.getSelectedItem().toString();
						String chucVu = cbxChucVu.getSelectedItem().toString();
						NhanVien nv = new NhanVien(maNV, tenNV, email,diaChi, soDT, gioiTinh, chucVu);
						dao_nv.update(nv);
						JOptionPane.showMessageDialog(null, "Cập nhật nhân viên thành công");
					}
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public void SearchNV(String properties) throws SQLException {
		DAO_NhanVien dao_nv = new DAO_NhanVien();
		FormCapNhatNhanVien nv = new FormCapNhatNhanVien(null);
	}

	private boolean validData() {

		String tenNV= txtTenNV.getText();
		String diaChi = txtDcNV.getText();
		String email = txtemail.getText();
		String soDT = txtSdtNV.getText();
		String chucVu=cbxChucVu.getSelectedItem().toString();
		String gioiTinh=cbxGioiTinh.getSelectedItem().toString();
		if(!(tenNV.length()>0)){

			JOptionPane.showMessageDialog(null, "Tên nhân viên không trống " );
		
			return false;
		}
		if(!(diaChi.length()>0)) {
			JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống " );
			return false;
		}
		
		if(!(email.length()>0 )) {
			JOptionPane.showMessageDialog(null, "Email không được để trống");
			return false;
		}
		if( !email.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			JOptionPane.showMessageDialog(null, "Email sai cú pháp");
			return false;
		}
		if(!(soDT.length()>0 )) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không được bỏ trống");
			return false;
		}
		if(!(soDT.matches("^[0][1-9][0-9]{8}$"))) {
			JOptionPane.showMessageDialog(null, "Số điện thoại gồm 10 kí tự số và bắt đầu từ kí tự 0");
			return false;
		}
		if(!(chucVu.length()>0 )) {
			JOptionPane.showMessageDialog(null, "Email không được để trống");
			return false;
		}
		if(!(gioiTinh.length()>0 )) {
			JOptionPane.showMessageDialog(null, "Email không được để trống");
			return false;
		}
		return rootPaneCheckingEnabled;

	}
}