package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import connectDB.ConnectDB;
import dao.DAO_NhanVien;
import dao.DAO_TaiKhoan;
import entity.NhanVien;
import entity.Sach;
import entity.TaiKhoan;

public class QuanLiTaiKhoan extends JPanel implements MouseListener, ActionListener, KeyListener{
	DefaultTableModel modeltable;
	JTable table;
	JTextField txtMaNV, txtTenNV;
	JButton btnLamMoi, btnTaoTaiKhoan, btnCapTaiKhoan;
	List<TaiKhoan> listTaiKhoan;
	List<NhanVien> listNhanVien;
	DAO_TaiKhoan dao_tk;
	DAO_NhanVien dao_nv;
	NhanVien nhanVien;
	public QuanLiTaiKhoan() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		pnNorth.setLayout(new BorderLayout());
		JLabel lblTieuDe = new JLabel("Quản Lý Tài Khoản");
		Font font = new Font("Arial", Font.BOLD, 25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnNorth.add(lblTieuDe, BorderLayout.NORTH);

		JPanel pnNorthHang2 = new JPanel();
		pnNorthHang2.setLayout(new BoxLayout(pnNorthHang2, BoxLayout.X_AXIS));
		//NorthLeft
		JPanel pnNorthLeft = new JPanel();
		pnNorthLeft.setLayout(new BoxLayout(pnNorthLeft, BoxLayout.X_AXIS));

		//		Border bdTimKiem = BorderFactory.createLineBorder(Color.black);
		//		TitledBorder titleBorderTimTKiem = new TitledBorder(bdTimKiem, "Thông tin tài khoản nhân viên");
		TitledBorder titleBorderTimTKiem = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông tin tài khoản nhân viên", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		titleBorderTimTKiem.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		//titleBorderTimTKiem.setTitleColor(new Color(0, 102, 255));
		JPanel pnTimKiem = new JPanel();
		pnTimKiem.setLayout(new BoxLayout(pnTimKiem, BoxLayout.X_AXIS));
		pnTimKiem.setBorder(titleBorderTimTKiem);
		JPanel pnTimMa = new JPanel();
		JLabel lblTimMa = new JLabel("Mã Nhân Viên: ");
		lblTimMa.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtMaNV = new JTextField(15);	
		txtMaNV.setFont(new Font("Tahoma", Font.PLAIN, 17));



		pnTimMa.add(lblTimMa);pnTimMa.add(txtMaNV);

		JPanel pnTenNV = new JPanel();
		JLabel lblTenNV= new JLabel("Tên Nhân Viên: ");
		lblTenNV.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtTenNV = new JTextField(15);
		txtTenNV.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtTenNV.setEditable(false);
		pnTenNV.add(lblTenNV);pnTenNV.add(txtTenNV);

		pnTimKiem.add(pnTimMa);
		pnTimKiem.add(pnTenNV);
		pnNorthHang2.add(pnTimKiem);

		//NorthRight
		JPanel pnNorthRight = new JPanel();

		pnNorthRight.setLayout(new BoxLayout(pnNorthRight, BoxLayout.X_AXIS));

		Border bdChucNang = BorderFactory.createLineBorder(Color.black);

		TitledBorder titleBorderChucNang = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông Tin Khác", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		titleBorderChucNang.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		JPanel pnChucNang = new JPanel();
		pnChucNang.setLayout(new BoxLayout(pnChucNang, BoxLayout.X_AXIS));
		pnChucNang.setBorder(titleBorderChucNang);
		JPanel pnHang1 = new JPanel();
	
		
		JLabel lblImageTaoTaiKhoan = new JLabel(ResizeImage("src//Icon//add-user.png"));
		JLabel lblTaoTaiKhoan = new JLabel("  Tạo tài khoản");
		btnTaoTaiKhoan = new JButton("");
		btnTaoTaiKhoan.setLayout(new BorderLayout());
		btnTaoTaiKhoan.add(lblImageTaoTaiKhoan,BorderLayout.WEST);
		btnTaoTaiKhoan.add(lblTaoTaiKhoan,BorderLayout.CENTER);
		btnTaoTaiKhoan.setBackground(new Color(255, 153, 51));
		lblTaoTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnTaoTaiKhoan.setPreferredSize(new Dimension(160, 50));
		lblTaoTaiKhoan.setForeground(Color.WHITE);
		
		JLabel lblImageCapLaiMK = new JLabel(ResizeImage("src//Icon//reset-password.png"));
		JLabel lblCapLaiMK = new JLabel("  Cấp lại mật khẩu");
		btnCapTaiKhoan = new JButton("");
		btnCapTaiKhoan.setLayout(new BorderLayout());
		btnCapTaiKhoan.add(lblImageCapLaiMK,BorderLayout.WEST);
		btnCapTaiKhoan.add(lblCapLaiMK,BorderLayout.CENTER);
		btnCapTaiKhoan.setBackground(new Color(255, 153, 51));
		lblCapLaiMK.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnCapTaiKhoan.setPreferredSize(new Dimension(185, 50));
		lblCapLaiMK.setForeground(Color.WHITE);
		pnHang1.add(btnTaoTaiKhoan);pnHang1.add(btnCapTaiKhoan);
		
		JPanel pnHang2 = new JPanel();	
		JLabel lblImageLamMoi = new JLabel(ResizeImage("src//Icon//refresh.png"));
		JLabel lblLamMoi = new JLabel("  Làm mới");
		btnLamMoi = new JButton("");
		btnLamMoi.setLayout(new BorderLayout());
		btnLamMoi.add(lblImageLamMoi,BorderLayout.WEST);
		btnLamMoi.add(lblLamMoi,BorderLayout.CENTER);
		btnLamMoi.setBackground(new Color(255, 153, 51));
		lblLamMoi.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnLamMoi.setPreferredSize(new Dimension(140, 50));
		lblLamMoi.setForeground(Color.WHITE);
		pnHang1.add(btnLamMoi);
		pnChucNang.add(pnHang1);
		pnNorthHang2.add(pnChucNang);

		pnNorth.add(pnNorthHang2);

		add(pnNorth, BorderLayout.NORTH);


		JPanel pnCenter = new JPanel();
		pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));
		pnCenter.setPreferredSize(new Dimension(580, 1000));
		String[] chuoi = {"Mã Nhân Viên","Tên Nhân Viên", "Mật Khẩu","Chức Vụ"};
		modeltable = new DefaultTableModel(chuoi,0){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; 
			}
		};;;
		table = new JTable(modeltable) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));

				return c;
			}
		};
		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.setRowHeight(30);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setShowGrid(false);
		table.getTableHeader().setBackground(new Color(255, 153, 51));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.white);


		JScrollPane sc = new JScrollPane(table);
		sc.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
		pnCenter.add(sc);
		add(pnCenter,BorderLayout.CENTER);

		dao_tk = new DAO_TaiKhoan();
		dao_nv = new DAO_NhanVien();
		loadTaiKhoanNhanVien();

		table.addMouseListener(this);
		btnCapTaiKhoan.addActionListener(this);
		btnTaoTaiKhoan.addActionListener(this);
		btnLamMoi.addActionListener(this);
		txtMaNV.addKeyListener(this);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int row = table.getSelectedRow();
		txtMaNV.setText(table.getValueAt(row, 0).toString());
		txtTenNV.setText(table.getValueAt(row, 1).toString());
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		int row = table.getSelectedRow();
		if(o.equals(btnCapTaiKhoan)) {

							
			if(row < 0)
				JOptionPane.showMessageDialog(null, "Vui lòng chọn tài khoản!");
			else {
				int hoi=JOptionPane.showConfirmDialog(this, "Tài khoản "+txtTenNV.getText()+" sẽ được cập nhật lại mật khẩu","Chú ý",JOptionPane.YES_NO_OPTION);
				if(hoi==JOptionPane.YES_OPTION) { 
					String maNhanVien = table.getValueAt(row, 0).toString();
					if(dao_tk.CapLaiMatKhau(maNhanVien)) {
						table.setValueAt(DAO_TaiKhoan.maHoaMatKhau("123456"), row, 2);
						JOptionPane.showMessageDialog(null, "Mật khẩu đã được cấp lại");
					}
					else {
						JOptionPane.showMessageDialog(null, "Mật khẩu cấp lại không thành công");
					}

				}
			}
		}
		else if(o.equals(btnTaoTaiKhoan)){
			if(txtMaNV.getText().equalsIgnoreCase(""))
				JOptionPane.showMessageDialog(null, "Mã nhân viên không được trống");
			else 
			{
				String maNhanVien = txtMaNV.getText();
				String tenNhanVien = txtTenNV.getText();
				TaiKhoan taiKhoan = new TaiKhoan(new NhanVien(maNhanVien), "123456");
				try 
				{
					if(dao_tk.taoTaiKhoan(taiKhoan)) 
					{	
						JOptionPane.showMessageDialog(null, "Tạo tài khoản thành công");
						loadTaiKhoanNhanVien();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Nhân viên này đã có tài khoản");
						
					}
				} catch(Exception e1) {
					System.out.println(e1);

				}
			}
		}
		else if(o.equals(btnLamMoi)){
			loadTaiKhoanNhanVien();
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		KiemTraCoThongTaiKhoan();
	}
	public void loadTaiKhoanNhanVien() 
	{
		listTaiKhoan = dao_tk.getAllTaiKhoan();
		listNhanVien = dao_nv.layHetNhanVien();
		modeltable.setRowCount(0);
		table.removeAll();
		for(TaiKhoan tk : listTaiKhoan)
		{
			for(NhanVien nv : listNhanVien) {
				if(tk.getNhanVien().getMaNV().equals(nv.getMaNV())) {
					modeltable.addRow(new Object[] {tk.getNhanVien().getMaNV(),nv.getTenNV(),tk.getMatKhau(), nv.getChucVu()});
				}
			}
		}

	}
		
		private boolean KiemTraCoThongTaiKhoan() 
		{		//Nếu không có thì tạo mới
			ConnectDB.getInstance();
			
			
			nhanVien = new NhanVien();
			if(dao_nv.layNhanVienTheoMa(txtMaNV.getText().trim())!=null) {
				nhanVien = dao_nv.layNhanVienTheoMa(txtMaNV.getText().trim());
				txtTenNV.setText(nhanVien.getTenNV());

				return true;
			}
			else {
				txtTenNV.setText("");
				return false;
			}
	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
