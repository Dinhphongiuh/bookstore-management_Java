package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.DAO_KhachHang;
import entity.KhachHang;
/**
 * 
 * Nguyễn Thành Luân
 *
 */
public class FormThemKhachHang extends JFrame implements ActionListener {
	ImageIcon background;
	private DAO_KhachHang dao_kh = new DAO_KhachHang();
	private QuanLiKhachHang ttkh = new QuanLiKhachHang();
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtDcKH;
	private JTextField txtSdtKH;
	private JButton btnThem;
	private DefaultTableModel modeltable;
	JTable table;

	public FormThemKhachHang() 
	{
		setTitle("Thêm Khách Hàng");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(680, 550);
		setLocationRelativeTo(null);

		JPanel pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 680, 500);

		JLabel lbTitle = new JLabel("Form Thêm Khách Hàng");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lbTitle.setForeground(new Color(255,113,51));
		lbTitle.setBounds(225, 10, 300, 30);
		pnMain.add(lbTitle);

		JPanel pnInput = new JPanel();
		pnInput.setLayout(null);
		pnInput.setBounds(0, 50, 680, 550);

		JLabel lbmaKH = new JLabel("Mã Khách Hàng: ");
		JLabel lbtenKH = new JLabel("Tên Khách Hàng: ");
		JLabel lbdiachiKH = new JLabel("Địa Chỉ: ");
		JLabel lbsdtKH = new JLabel("Số Điện Thoại: ");
		 txtMaKH = new JTextField();	
		 txtMaKH.setEditable(false);
		 txtMaKH.setBackground(null);
		 txtTenKH = new JTextField();			//XEM LẠI TẤT CẢ CODE CHỖ NÀY
		 txtDcKH = new JTextField();			//XEM LẠI TẤT CẢ CODE CHỖ NÀY
		 txtSdtKH = new JTextField();

		lbmaKH.setBounds(20, 30, 130, 30);
		txtMaKH.setBounds(150, 30, 450, 30);
		lbtenKH.setBounds(20, 110, 130, 30);
		txtTenKH.setBounds(150, 110, 450, 30);
		lbdiachiKH.setBounds(20, 180, 130, 30);
		txtDcKH.setBounds(150, 180, 450, 30);
		lbsdtKH.setBounds(20, 250, 140, 30);
		txtSdtKH.setBounds(150, 250, 450, 30);

		
		
		pnInput.add(lbmaKH);
		pnInput.add(lbtenKH);
		pnInput.add(lbdiachiKH);
		pnInput.add(lbsdtKH);
		pnInput.add(txtMaKH);
		pnInput.add(txtTenKH);
		pnInput.add(txtDcKH);
		pnInput.add(txtSdtKH);

		btnThem = new JButton("Thêm Khách Hàng");		//?????Vẫn không hiểu tại sao ở đây Thêm
		btnThem.setBounds(480, 450, 170, 50);
		btnThem.setBackground(new Color(255,113,51));

		pnMain.add(pnInput);
		add(btnThem);
		add(pnMain);

		txtMaKH.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {

				KhachHang kh = dao_kh.getKhachHangbyHDId("maKhachHang", txtMaKH.getText());
				if (kh != null) 
				{
					txtTenKH.setText(kh.getTenKH());
					txtDcKH.setText(kh.getDiaChi());
					txtSdtKH.setText(kh.getSoDT());
				} else 
				{
					txtTenKH.setText("");
					txtDcKH.setText("");
					txtSdtKH.setText("");
				}

			}
		});

		btnThem.addActionListener(this);
		dinhDangMaKH();
	}

	public static void main(String[] args) {
		FormThemKhachHang ttkh = new FormThemKhachHang();
		// ttnv.setBackground(new ImageIcon("Icon/1767.jpg"));
		ttkh.setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			if(kiemTraNhapLieu()) 
			{
//				String maKH = txtMaKH.getText();
				String maKH = txtMaKH.getText();
				String tenKH = txtTenKH.getText();
				String diaChi = txtDcKH.getText();			
				String soDT = txtSdtKH.getText();
				KhachHang kh = new KhachHang(maKH, tenKH, soDT, diaChi);

				dao_kh.themKH(kh);
				ttkh.model.addRow(new Object[] {kh.getMaKH(), kh.getTenKH(), kh.getSoDT(), kh.getDiaChi() });
				JOptionPane.showMessageDialog(this, "Thêm thành công");
			}
		}

	}

	private boolean kiemTraNhapLieu() {
		String tenKH = txtTenKH.getText();
		String diaChi = txtDcKH.getText();
		String sdt = txtSdtKH.getText();
		if ((tenKH.length() == 0)) {
			JOptionPane.showMessageDialog(null, "Tên Khách Hàng không trống ");
			return false;
		}
		if ((diaChi.length() == 0)) {
			JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống ");
			return false;
		}
		
		if(!(sdt.length()>0 )) {
			JOptionPane.showMessageDialog(null, "Số điện thoại không được bỏ trống");
			return false;
		}
		if(!(sdt.matches("^[0][1-9][0-9]{8}$"))) {
			JOptionPane.showMessageDialog(null, "Số điện thoại gồm 10 kí tự số và bắt đầu từ kí tự 0");
			return false;
		}

		return true;
	}

	public void SearchKH(String properties) throws SQLException 
	{
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		FormThemKhachHang kh1 = new FormThemKhachHang();
	}

	private void loadKH() throws SQLException {
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		modeltable = dao_kh.getAllKH();
		table.setModel(modeltable);
	}
	private void dinhDangMaKH() 
	{
		String maKH = "";
		maKH += "KH";
		if(String.valueOf(dao_kh.layMaKHLonNhat()).length() == 2)
		{
			maKH += "0";
		}

		else if(String.valueOf(dao_kh.layMaKHLonNhat()).length() == 1) {
			maKH += "00";
		}
		maKH += String.valueOf(dao_kh.layMaKHLonNhat() + 1);
		txtMaKH.setText(maKH);
	}
}
