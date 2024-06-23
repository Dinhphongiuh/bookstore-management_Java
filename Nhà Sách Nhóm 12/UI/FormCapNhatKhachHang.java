package UI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import dao.DAO_KhachHang;
import entity.KhachHang;
import entity.NhanVien;

public class FormCapNhatKhachHang extends JFrame implements ActionListener 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon background;
	private DAO_KhachHang dao_kh = new DAO_KhachHang();
	private QuanLiKhachHang ttkh = new QuanLiKhachHang();
	private JTextField txtMaKH;
	private JTextField txtTenKH;
	private JTextField txtDcKH;
	private JTextField txtSdtKH;
	private DefaultTableModel modeltable;
	JTable table;
	private JButton btnCapNhat;
	private JLabel lbmaKH;
	private JLabel lbtenKH;
	private JLabel lbdiachiKH;
	private JLabel lbsdtKH;

	public FormCapNhatKhachHang(KhachHang kh) {
		setTitle("Cập Nhật Khách Hàng");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		setSize(680, 550);
		setLocationRelativeTo(null);

		JPanel pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 680, 500);

		JLabel lbTitle = new JLabel("Quản Lý Khách Hàng");
		lbTitle.setFont(new Font("Arial", Font.BOLD, 25));
		lbTitle.setForeground(new Color(255,113,51));
		lbTitle.setBounds(225, 10, 300, 30);
		pnMain.add(lbTitle);

		JPanel pnInput = new JPanel();
		pnInput.setLayout(null);
		pnInput.setBounds(0, 50, 680, 550);

		lbmaKH = new JLabel("Mã Khách Hàng: ");
		lbtenKH = new JLabel("Tên Khách Hàng: ");
		lbdiachiKH = new JLabel("Địa Chỉ: ");
		lbsdtKH = new JLabel("Số Điện Thoại: ");
		
		txtMaKH = new JTextField();
		txtTenKH = new JTextField();
		txtDcKH = new JTextField();
		txtSdtKH = new JTextField();
		
		txtMaKH.setText(kh.getMaKH());
		txtMaKH.setEditable(false);
		txtMaKH.setBackground(null);
		txtTenKH.setText(kh.getTenKH());
		txtDcKH.setText(kh.getDiaChi());
		txtSdtKH.setText(kh.getSoDT());

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

		btnCapNhat = new JButton("Cập Nhật");
		btnCapNhat.setBounds(560, 450, 100, 50);
		btnCapNhat.setBackground(new Color(255,113,51));

		pnMain.add(pnInput);
		add(btnCapNhat);
		add(pnMain);

		btnCapNhat.addActionListener(this);
//
//		txtMaKH.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyReleased(KeyEvent e) {
//
//				KhachHang kh = dao_kh.getKhachHangbyHDId("maKhachHang", txtMaKH.getText());
//				if (kh != null) {
//
//					txtTenKH.setText(kh.getTenKH());
//					txtDcKH.setText(kh.getDiaChi());
//					txtSdtKH.setText(kh.getSoDT());
//				} else {
//
//					txtTenKH.setText("");
//					txtDcKH.setText("");
//					txtSdtKH.setText("");
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
			try 
			{
				if (validData()) {
					JFrame f = new JFrame();
					int hoi = JOptionPane.showConfirmDialog(f, "Khách Hàng này sẽ được cập nhật", "Chú ý",
							JOptionPane.YES_NO_OPTION);
					if (hoi == JOptionPane.YES_OPTION) {
						String maKH = txtMaKH.getText();
						String tenKH = txtTenKH.getText();
						String diaChi = txtDcKH.getText();
						String soDT = txtSdtKH.getText();
						KhachHang kh = new KhachHang(maKH, tenKH, soDT, diaChi);
						dao_kh.update(kh);
						JOptionPane.showMessageDialog(null, "Cập nhật Khách Hàng thành công");
						dispose();
					}
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

	}

	private boolean validData() {
		String tenKH = txtTenKH.getText();
		String diaChi = txtDcKH.getText();
		String soDT = txtSdtKH.getText();
		if (!(tenKH.length() > 0)) {

			JOptionPane.showMessageDialog(null, "Tên Khách Hàng không trống ");

			return false;
		}
		if (!(diaChi.length() > 0)) {
			JOptionPane.showMessageDialog(null, "Địa chỉ không được để trống ");
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
		return rootPaneCheckingEnabled;

	}

	public void SearchKH(String properties) throws SQLException {
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		FormCapNhatKhachHang kh1 = new FormCapNhatKhachHang(null);
	}

	private void loadKH() throws SQLException 
	{
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		modeltable = dao_kh.getAllKH();
		table.setModel(modeltable);
	}
}
