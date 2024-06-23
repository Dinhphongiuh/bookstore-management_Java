package UI;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DAO_NhanVien;
import entity.NhanVien;

/**
 * 
 * Nguyễn Thành Luân
 *
 */
public class FormThemNhanVien extends JFrame implements ActionListener {
	JComboBox<NhanVien> cbxTinhTrang, cbGioiTinh;
	ImageIcon background;
	private static DAO_NhanVien dao_nv = new DAO_NhanVien();
	private QuanLiNhanVien ttnv;
	private JLabel lbmaNV;
	private JLabel lbtenNV;
	private JLabel lbdiachiNV;
	private JLabel lbemailNV;
	private JLabel lbsdtNV;
	private JLabel lblChucVu;
	private JTextField txtMaNV;
	private JTextField txtTenNV;
	private JTextField txtDcNV;
	private JTextField txtemail;
	private JTextField txtSdtNV;
	private JComboBox cbxChucVu;
	public static JButton btnThem;
	private JComboBox cbxGioiTinh;
	private DefaultTableModel modeltable;
	JTable table;
	public static List<NhanVien> listNhanVien;

	public FormThemNhanVien() {
		setTitle("Thêm Nhân Viên");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(680, 550);
		setLocationRelativeTo(null);

		ImageIcon imgDefault = new ImageIcon("src//Icon//iconCuaHangSach.png");
		Image imgDefault1 = imgDefault.getImage();
		Image newImgDefault1 = imgDefault1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		imgDefault = new ImageIcon(newImgDefault1);
		this.setIconImage(imgDefault.getImage());
		ttnv = new QuanLiNhanVien();

		JPanel pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 680, 500);

		JLabel lbTitle = new JLabel("Quản Lý Nhân Viên");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lbTitle.setForeground(new Color(255, 113, 51));
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
		lblChucVu = new JLabel("Chức vụ: ");

		txtMaNV = new JTextField();
		txtMaNV.setEditable(false);
		txtMaNV.setBackground(null);
		txtTenNV = new JTextField();
		txtDcNV = new JTextField();
		txtemail = new JTextField();
		txtSdtNV = new JTextField();
		cbxChucVu = new JComboBox();
		cbxChucVu.addItem("Nhân Viên");
		cbxChucVu.addItem("Chủ Cửa Hàng");
		cbxChucVu.setPreferredSize(new Dimension(170, 25));

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
		lblChucVu.setBounds(20, 280, 140, 30);
		cbxChucVu.setBounds(150, 280, 150, 30);

		pnInput.add(lbmaNV);
		pnInput.add(lbtenNV);
		pnInput.add(lbdiachiNV);
		pnInput.add(lbemailNV);
		pnInput.add(lbsdtNV);
		pnInput.add(lblChucVu);

		pnInput.add(txtMaNV);
		pnInput.add(txtTenNV);
		pnInput.add(txtDcNV);
		pnInput.add(txtemail);
		pnInput.add(txtSdtNV);
		pnInput.add(cbxChucVu);

		btnThem = new JButton("Thêm Nhân Viên");
		btnThem.setBounds(480, 450, 170, 50);
		btnThem.setBackground(new Color(255, 113, 51));
		// btnThem.setOpaque(true);

		JLabel lblGioiTinh = new JLabel("Giới tính");
		cbxGioiTinh = new JComboBox();
		cbxGioiTinh.addItem("Nam");
		cbxGioiTinh.addItem("Nữ");
		cbxGioiTinh.setPreferredSize(new Dimension(170, 25));

		lblGioiTinh.setBounds(20, 380, 140, 30);
		add(lblGioiTinh);
		cbxGioiTinh.setBounds(150, 380, 150, 30);
		add(cbxGioiTinh);

		pnMain.add(pnInput);
		add(btnThem);
		add(pnMain);

		btnThem.addActionListener(this);

		txtMaNV.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				NhanVien nv = dao_nv.getNhanVienbyHDId("maNhanVien", txtMaNV.getText());
				if (nv != null) {

					txtTenNV.setText(nv.getTenNV());
					txtDcNV.setText(nv.getDiaChi());
					txtemail.setText(nv.getEmail());
					txtSdtNV.setText(nv.getSoDT());
					cbxChucVu.setSelectedItem(nv.getChucVu());
					cbxGioiTinh.setSelectedItem(nv.getChucVu());

				} else {

					txtTenNV.setText("");
					txtDcNV.setText("");
					txtemail.setText("");
					txtSdtNV.setText("");
					cbxChucVu.setSelectedItem("");
					cbxGioiTinh.setSelectedItem("");

				}

			}
		});

		dinhDangMaNhanVien();

	}

	public static void main(String[] args) {
		FormThemNhanVien ttnv = new FormThemNhanVien();
		// ttnv.setBackground(new ImageIcon("Icon/1767.jpg"));
		ttnv.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if (kiemTraNhapLieu()) {
				String maNV = txtMaNV.getText();
				String tenNV = txtTenNV.getText();
				String diaChi = txtDcNV.getText();
				String email = txtemail.getText();
				String soDT = txtSdtNV.getText();
				String chucVu = cbxChucVu.getSelectedItem().toString();
				String gioiTinh = cbxGioiTinh.getSelectedItem().toString();

				NhanVien nv = new NhanVien(maNV, tenNV, email, diaChi, soDT, gioiTinh, chucVu);

				dao_nv.themNV(nv);
				ttnv.model.addRow(new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getEmail(), nv.getDiaChi(),
						nv.getSoDT(), nv.getGioiTinh(), nv.getChucVu() });
//				try {
//					UI_ThongTinNhanVien.qlnv.model = dao_nv.getAllNV();
//				} catch (SQLException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				UI_ThongTinNhanVien.qlnv.table.setModel(UI_ThongTinNhanVien.qlnv.model);
				JOptionPane.showMessageDialog(this, "Thêm thành công");
				dispose();
			}
		}

	}

//	public void loadNV() throws SQLException {
//		UI_ThongTinNhanVien.qlnv.model = dao_nv.getAllNV();
//		UI_ThongTinNhanVien.qlnv.table.setModel(UI_ThongTinNhanVien.qlnv.model);
//	}
	// public static void loadNV() {
	// listNhanVien = dao_nv.layHetNhanVien();
	//
	// for (NhanVien nhanVien : listNhanVien) {
	// System.out.println("hihi");
	// UI_ThongTinNhanVien.qlnv.model.addRow(new Object[]
	// {nhanVien.getMaNV(),nhanVien.getTenNV(),nhanVien.getDiaChi(),nhanVien.getSoDT(),nhanVien.getGioiTinh(),
	// nhanVien.getChucVu()});
	//
	// }
	// }
	private boolean kiemTraNhapLieu() {
		String tenNV = txtTenNV.getText();
		String diaChi = txtDcNV.getText();
		String email = txtemail.getText();
		String soDT = txtSdtNV.getText();
		String ChucVu = cbxGioiTinh.getSelectedItem().toString();
		String gioiTinh = cbxGioiTinh.getSelectedItem().toString();
		if (!(tenNV.length() > 0)) {

			JOptionPane.showMessageDialog(null, "Tên nhân viên không trống ");

			return false;
		}
		if (!(diaChi.length() > 0)) {
			JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống ");
			return false;
		}

		if (!(email.length() > 0)) {
			JOptionPane.showMessageDialog(null, "Email không được để trống");
			return false;
		}
		if (!email.matches(
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
			JOptionPane.showMessageDialog(null, "Email sai cú pháp");
			return false;
		}
		if (!(soDT.length() > 0)) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không được bỏ trống");
			return false;
		}
		if (!(soDT.matches("^[0][1-9][0-9]{8}$"))) {
			JOptionPane.showMessageDialog(null, "Số điện thoại gồm 10 kí tự số và bắt đầu từ kí tự 0");
			return false;
		}
		return rootPaneCheckingEnabled;

	}

	public void SearchNV(String properties) throws SQLException {
		DAO_NhanVien dao_nv = new DAO_NhanVien();
		FormThemNhanVien nv1 = new FormThemNhanVien();
	}

	// private void loadNV() throws SQLException {
	// DAO_NhanVien dao_nv = new DAO_NhanVien();
	// modeltable= dao_nv.getAllNV();
	// table.setModel(modeltable);
	// }
	private void dinhDangMaNhanVien() {
		String maNhanVien = "";
		maNhanVien += "NV";
		if (String.valueOf(dao_nv.layMaNVLonNhat()).length() == 2) {
			maNhanVien += "0";
		}

		else if (String.valueOf(dao_nv.layMaNVLonNhat()).length() == 1) {
			maNhanVien += "00";
		}
		maNhanVien += String.valueOf(dao_nv.layMaNVLonNhat() + 1);
		txtMaNV.setText(maNhanVien);
	}
}
