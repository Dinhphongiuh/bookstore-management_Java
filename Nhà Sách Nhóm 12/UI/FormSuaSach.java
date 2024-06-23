package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import connectDB.ConnectDB;
import dao.DAO_Sach;
import dao.DAO_LoaiSach;
import entity.LoaiSach;
import entity.Sach;

public class FormSuaSach extends JFrame implements ActionListener, MouseListener, KeyListener{
	private DAO_Sach sachDao;
	private ArrayList<Sach> listSach;
	private DAO_LoaiSach daoLoaiSach;
	private ArrayList<LoaiSach> listLoaiSach;
	private static Sach sachStatic;

	private JLabel lblHinhAnh, lblChonAnh;
	private JButton btnChonAnh, btnLuu, btnLamMoi;
	private JTextField txtMaSach ,txtDonGia, txtSoLuongTon, txtTacGia, txtNamXuatBan,txtSoTrang, txtLoi, txtLoiDonGia,txtLoiNamXuatBan, txtLoiTrang;
	private JTextArea txtTenSach;
	private String hinhAnh="Chưa có hình ảnh";
	private JComboBox  cbxTheLoai, cbxNhaCungCap, cbxNhaXuatBan;
	private JDialog jDialog;
	public FormSuaSach(Sach s) {
		sachStatic = s;
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sachDao = new DAO_Sach();
		daoLoaiSach = new DAO_LoaiSach();
		listSach = sachDao.getAllTbSach();

		setTitle("Sửa Sách");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(950,680);
		setLocationRelativeTo(null);

		jDialog = new JDialog();
		jDialog.setTitle("Sửa Sách");
		//jDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		jDialog.setSize(950,700);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);
		//NORTH
		JPanel pnNorth = new JPanel();
		pnNorth.setLayout(new BoxLayout(pnNorth, BoxLayout.Y_AXIS));
		pnNorth.setBackground(Color.RED);



		JPanel pnTieuDe = new JPanel();
		//pnTieuDe.setBackground(Color.BLUE);
		JLabel lblTieuDe = new JLabel("Sửa thông tin sách");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		pnTieuDe.add(lblTieuDe);


		JPanel pnHinhAnh = new JPanel();
		//pnHinhAnh.setBackground(Color.YELLOW);
		lblHinhAnh = new JLabel("");
		String path = sachStatic.getHinhAnh();
		hinhAnh = sachStatic.getHinhAnh();

		if(s.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
			lblHinhAnh.setText(s.getHinhAnh());
		}
		else {
			lblHinhAnh.setIcon(ResizeImage(path));
		}


		lblHinhAnh.setPreferredSize(new Dimension(250,200));
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pnHinhAnh.add(lblHinhAnh);

		JPanel pnChonAnh = new JPanel();
		//pnChonAnh.setBackground(Color.PINK);
		lblChonAnh = new JLabel("Chọn ảnh");
		btnChonAnh = new JButton("");
		btnChonAnh.add(lblChonAnh);
		btnChonAnh.setBackground(new Color(255, 113, 51));
		lblChonAnh.setForeground(Color.WHITE);
		pnChonAnh.add(btnChonAnh);

		pnNorth.add(pnTieuDe);
		pnNorth.add(pnHinhAnh);
		pnNorth.add(pnChonAnh);

		jDialog.add(pnNorth,BorderLayout.NORTH);

		Font fontLbl =new Font("Arial",Font.BOLD,18);
		//CENTER
		JPanel pnCenter = new JPanel();
		//pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));

		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));		
		pnThongTin.add(Box.createVerticalStrut(10));

		JPanel pnMaSachTacGia = new JPanel();
		pnMaSachTacGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblmaSach = new JLabel("Mã sách: ");
		lblmaSach.setFont(fontLbl);
		txtMaSach = new JTextField(20);
		txtMaSach.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaSach.setText(s.getMaSanPham());
		txtMaSach.setEditable(false);
		pnMaSachTacGia.add(lblmaSach);
		pnMaSachTacGia.add(txtMaSach);
		pnMaSachTacGia.add(Box.createHorizontalStrut(10));
		JLabel lblTacGia = new JLabel("Tác giả: ");
		lblTacGia.setFont(fontLbl);
		txtTacGia = new JTextField(20);
		txtTacGia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTacGia.setText(s.getTacGia());
		pnMaSachTacGia.add(lblTacGia);
		pnMaSachTacGia.add(txtTacGia);

		JPanel pnTenSach = new JPanel();
		pnTenSach.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenSach = new JLabel("Tên sách: ");
		lblTenSach.setFont(fontLbl);
		txtTenSach = new JTextArea(3, 41/2);
		txtTenSach.setLineWrap(true);
		txtTenSach.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenSach.setWrapStyleWord(true);
		txtTenSach.setText(s.getTenSanPham());
		
		JLabel lblNhaXuatBan = new JLabel("Nhà xuất bản: ");
		lblNhaXuatBan.setFont(fontLbl);
		cbxNhaXuatBan = new JComboBox();
		cbxNhaXuatBan.setEditable(true);
		cbxNhaXuatBan.setSelectedItem(s.getNhaXuatBan());
		listSach = sachDao.getAllTbSach();
		List<String> l = new ArrayList<String>();
		for(Sach sach : listSach) {  
			l.add(sach.getNhaXuatBan().trim());
		}
		Set<String> listNXBKhongTrung =  new LinkedHashSet<String>(l);
		for(String nxb : listNXBKhongTrung) {  
			cbxNhaXuatBan.addItem(nxb);
		}
		cbxNhaXuatBan.setPreferredSize(new Dimension(258,25));	
		pnTenSach.add(lblTenSach);
		pnTenSach.add(txtTenSach);
		pnTenSach.add(Box.createHorizontalStrut(15));
		pnTenSach.add(lblNhaXuatBan);
		pnTenSach.add(cbxNhaXuatBan);

		JPanel pnLoiDonGia = new JPanel();
		pnLoiDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));	
		 txtLoiDonGia = new JTextField(20);	
		txtLoiDonGia.setBorder(null);
		txtLoiDonGia.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoiDonGia.setEditable(false);		

		txtLoiNamXuatBan = new JTextField(20);	
		txtLoiNamXuatBan.setBorder(null);
		txtLoiNamXuatBan.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoiNamXuatBan.setEditable(false);	

		pnLoiDonGia.add(Box.createHorizontalStrut(125));
		pnLoiDonGia.add(txtLoiDonGia);
		pnLoiDonGia.add(Box.createHorizontalStrut(165));
		pnLoiDonGia.add(txtLoiNamXuatBan);
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JPanel pnDonGia = new JPanel();
		pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDonGia = new JLabel("Đơn Giá: ");
		lblDonGia.setFont(fontLbl);
		txtDonGia = new JTextField(20);
		txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGia.setText(String.valueOf(formatter.format(s.getGiaBan())));	
		txtDonGia.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				String donGia = txtDonGia.getText().replaceAll(",", "");
				if(txtDonGia.getText().equals("")) {
					txtLoiDonGia.setText("");
				}						
				else if(!kiemTraNhapSoThuc(donGia) ) {
					txtDonGia.setForeground(Color.red);
					txtLoiDonGia.setText("*Lỗi: Đơn giá phải là số");
					txtLoiDonGia.setForeground(Color.red);
				}
				else {
					txtDonGia.setForeground(Color.black);
					txtLoiDonGia.setText("");
					double money;
					NumberFormat num = NumberFormat.getNumberInstance();
					try {
						money = num.parse(txtDonGia.getText()).doubleValue();
						txtDonGia.setText(num.format(money));
						double cusMoney = 0;
						if (!txtDonGia.getText().equalsIgnoreCase(""))		
							cusMoney = num.parse(txtDonGia.getText()).doubleValue();
					} 
					
					catch (NumberFormatException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					catch (ParseException e3) {
						// TODO: handle exception
						//e3.printStackTrace();
					}
					
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		JLabel lblNamXuatBan = new JLabel("Năm xuất bản: ");
		lblNamXuatBan.setFont(fontLbl);
		txtNamXuatBan = new JTextField(20);
		txtNamXuatBan.setText(String.valueOf(s.getNamXuatBan()));
		txtNamXuatBan.setFont(new Font("Arial", Font.PLAIN, 16));
		pnDonGia.add(lblDonGia);
		pnDonGia.add(txtDonGia);
		pnDonGia.add(Box.createHorizontalStrut(10));
		pnDonGia.add(lblNamXuatBan);
		pnDonGia.add(txtNamXuatBan);

		
		JPanel pnLoiTrang = new JPanel();
		pnLoiTrang.setLayout(new FlowLayout(FlowLayout.LEFT));	
		txtLoiTrang = new JTextField(20);	
		txtLoiTrang.setBorder(null);
		txtLoiTrang.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoiTrang.setEditable(false);		
		pnLoiTrang.add(Box.createHorizontalStrut(550));
		pnLoiTrang.add(txtLoiTrang);
		
		JPanel pnNhaCungCap = new JPanel();
		pnNhaCungCap.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp: " );
		lblNhaCungCap.setFont(fontLbl);
		cbxNhaCungCap = new JComboBox();
		cbxNhaCungCap.setEditable(true);
		cbxNhaCungCap.setSelectedItem(s.getNhaCungCap());
		List<String> listNCC = new ArrayList<String>();
		for(Sach sach : listSach) {  
			listNCC.add(sach.getNhaCungCap().trim());
		}
		Set<String> listNhaCungCapKhongTrung =  new LinkedHashSet<String>(listNCC);
		for(String ncc : listNhaCungCapKhongTrung) {  
			cbxNhaCungCap.addItem(ncc);
		}
		cbxNhaCungCap.setPreferredSize(new Dimension(258,25));
		JLabel lblSoTrang = new JLabel("Số trang: ");
		lblSoTrang.setFont(fontLbl);
		txtSoTrang = new JTextField(20);
		txtSoTrang.setText(String.valueOf(s.getSoTrang()));
		txtSoTrang.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoTrang.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(txtSoTrang.getText().equals("")) {
					txtLoiTrang.setText("");
				}			
				else if(!kiemTraNhapSoNguyen(txtSoTrang.getText()) ) {
					txtSoTrang.setForeground(Color.red);
					txtLoiTrang.setText("*Lỗi: Số trang phải là số nguyên");
					txtLoiTrang.setForeground(Color.red);
				}
				else {
					txtSoTrang.setForeground(Color.black);
					txtLoiTrang.setText("");
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		pnNhaCungCap.add(lblNhaCungCap);
		pnNhaCungCap.add(cbxNhaCungCap);
		pnNhaCungCap.add(Box.createHorizontalStrut(10));
		pnNhaCungCap.add(lblSoTrang);
		pnNhaCungCap.add(txtSoTrang);

		JPanel pnLoi = new JPanel();
		pnLoi.setLayout(new FlowLayout(FlowLayout.LEFT));	
		txtLoi = new JTextField(20);
		txtLoi.setBorder(null);
		txtLoi.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoi.setEditable(false);
		pnLoi.add(Box.createHorizontalStrut(125));
		pnLoi.add(txtLoi);
		
		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: ");
		lblSoLuongTon.setFont(fontLbl);
		txtSoLuongTon = new JTextField(20);
		txtSoLuongTon.setText(String.valueOf(s.getSoLuong()));
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
		
		txtSoLuongTon.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub

			}
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				if(txtSoLuongTon.getText().equals("")) {
					txtLoi.setText("");
				}
				else if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText()) ) 
				{
					txtSoLuongTon.setForeground(Color.red);
					//txtLoi = new JTextField(20);
					txtLoi.setText("*Lỗi: Số lượng tồn phải là số nguyên");
					txtLoi.setForeground(Color.red);
				}
				else {
					txtSoLuongTon.setForeground(Color.black);
					txtLoi.setText("");
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}
		});
		JLabel lblTheLoai = new JLabel("Thể loại: ");
		lblTheLoai.setFont(fontLbl);
		cbxTheLoai = new JComboBox();
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		List<String> listTheLoai = new ArrayList<String>();
		for(LoaiSach loaisach : listLoaiSach) {  
			listTheLoai.add(loaisach.getTenLoaiSach());
		}

		Set<String> listTheLoaiKhongTrung =  new LinkedHashSet<String>(listTheLoai);
		for(String theloai : listTheLoaiKhongTrung) {  
			cbxTheLoai.addItem(theloai);
		}
		String tenLoaiSach = null;
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		for(LoaiSach loaiSach : listLoaiSach) {
			if(s.getLoaiSach().getMaLoaiSach().equals(loaiSach.getMaLoaiSach())) {
				tenLoaiSach = loaiSach.getTenLoaiSach();
			}
		}
		cbxTheLoai.setSelectedItem(tenLoaiSach);


		cbxTheLoai.setPreferredSize(new Dimension(258,25));
		pnSoLuongTon.add(lblSoLuongTon);
		pnSoLuongTon.add(txtSoLuongTon);
		pnSoLuongTon.add(Box.createHorizontalStrut(10));
		pnSoLuongTon.add(lblTheLoai);
		pnSoLuongTon.add(cbxTheLoai);



		JPanel pnChucNang = new JPanel();
		pnChucNang.setLayout(new FlowLayout(FlowLayout.RIGHT));
		JLabel lblLuu = new JLabel("Lưu");
		lblLuu.setFont(new Font("Arial", Font.BOLD, 16));
		btnLuu = new JButton("");
		btnLuu.setBackground(new Color(255, 113, 51));
		lblLuu.setForeground(Color.WHITE);
		btnLuu.add(lblLuu);
		JLabel lblLamMoi = new JLabel("Làm mới");
		lblLamMoi.setFont(new Font("Arial", Font.BOLD, 16));
		btnLamMoi = new JButton("");
		btnLamMoi.setBackground(new Color(255, 113, 51));
		lblLamMoi.setForeground(Color.WHITE);
		btnLamMoi.add(lblLamMoi);
		//pnChucNang.add(btnLamMoi);
		pnChucNang.add(btnLuu);

		btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnChonAnh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblmaSach.setPreferredSize(lblNhaCungCap.getPreferredSize());
		lblTenSach.setPreferredSize(lblNhaCungCap.getPreferredSize());
		lblDonGia.setPreferredSize(lblNhaCungCap.getPreferredSize());

		lblSoLuongTon.setPreferredSize(lblNhaCungCap.getPreferredSize());

		lblTacGia.setPreferredSize(lblNamXuatBan.getPreferredSize());
		lblNhaXuatBan.setPreferredSize(lblNamXuatBan.getPreferredSize());
		//lblNhaXuatBan.setPreferredSize(lblNhaXuatBan.getPreferredSize());
		lblSoTrang.setPreferredSize(lblNamXuatBan.getPreferredSize());
		lblTheLoai.setPreferredSize(lblNamXuatBan.getPreferredSize());


		pnThongTin.add(pnMaSachTacGia);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnTenSach);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnDonGia);
		pnThongTin.add(pnLoiDonGia);
		pnThongTin.add(Box.createVerticalStrut(-5));
		pnThongTin.add(pnNhaCungCap);
		pnThongTin.add(pnLoiTrang);
		pnThongTin.add(Box.createVerticalStrut(-5));
		pnThongTin.add(pnSoLuongTon);
		pnThongTin.add(pnLoi);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnChucNang);

		pnCenter.add(pnThongTin);

		jDialog.add(pnCenter, BorderLayout.CENTER);
		btnChonAnh.addActionListener(this);
		btnChonAnh.addMouseListener(this);
		btnLuu.addActionListener(this);
		txtSoTrang.addKeyListener(this);
		txtNamXuatBan.addKeyListener(this);
		txtDonGia.addKeyListener(this);
		txtSoLuongTon.addKeyListener(this);

		jDialog.setVisible(true);
	}
	public static void main(String[] args) {
		new FormThemSach().setVisible(true);
	}

	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	private boolean kiemTraNhapSoNguyen(String nhap) {
		try {
			int a = Integer.parseInt(nhap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	private boolean kiemTraNhapSoThuc(String nhap) {
		try {
			double a = Double.parseDouble(nhap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean kiemTraNhapLieu() {
		LocalDateTime localDate = LocalDateTime.now();
		int year = localDate.getYear();
		String donGia = txtDonGia.getText().replaceAll(",", "");
		if(txtTenSach.getText().trim().length()==0) {
			txtTenSach.requestFocus();
			JOptionPane.showMessageDialog(this, "Tên sách không đc trống");
			return false;
		}
		else if(txtTacGia.getText().trim().length()==0) {
			txtTacGia.requestFocus();
			JOptionPane.showMessageDialog(this, "Tác giả không đc trống");
			return false;
		}
		else if(txtNamXuatBan.getText().trim().length()==0) {
			txtNamXuatBan.requestFocus();
			JOptionPane.showMessageDialog(this, "Năm xuất bản không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoNguyen(txtNamXuatBan.getText()) ) {
			txtNamXuatBan.requestFocus();
			txtNamXuatBan.selectAll();
			JOptionPane.showMessageDialog(jDialog,"Năm xuất bản phải là số nguyên !");
			//jDialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			//jDialog.setVisible(true);
			return false;
		}
		else if(Integer.parseInt(txtNamXuatBan.getText()) < 1950 || Integer.parseInt(txtNamXuatBan.getText()) > year) {
			txtNamXuatBan.requestFocus();
			txtNamXuatBan.selectAll();
			JOptionPane.showMessageDialog(this,"Năm xuất bản phải lớn hơn 1950 và nhỏ hơn" +year);
			return false;
		}
		else if(txtDonGia.getText().trim().length()==0) {
			txtDonGia.requestFocus();
			JOptionPane.showMessageDialog(this, "Đơn giá không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoThuc(donGia)) {
			txtDonGia.requestFocus();
			txtDonGia.selectAll();
			JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
			return false;
		}
		else if(txtDonGia.getText().length()>9&&Float.parseFloat(txtDonGia.getText())>999999999) {
			txtDonGia.requestFocus();
			txtDonGia.selectAll();
			JOptionPane.showMessageDialog(this,"Đơn giá quá lớn !");
			return false;
		}
		else if(txtNamXuatBan.getText().trim().length()==0) {
			txtNamXuatBan.requestFocus();
			JOptionPane.showMessageDialog(this, "Năm xuất bản không đc trống");
			return false;
		}
		else if(txtSoTrang.getText().trim().length()==0) {
			txtSoTrang.requestFocus();
			JOptionPane.showMessageDialog(this, "Số trang không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoNguyen(txtSoTrang.getText())) {
			txtSoTrang.requestFocus();
			txtSoTrang.selectAll();
			JOptionPane.showMessageDialog(this, "Số trang phải là số nguyên");
			return false;
		}
		else if(txtSoLuongTon.getText().trim().length()==0) {
			txtSoLuongTon.requestFocus();
			JOptionPane.showMessageDialog(this, "Số lượng tồn không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText())) {
			txtSoLuongTon.requestFocus();
			txtSoLuongTon.selectAll();
			JOptionPane.showMessageDialog(this, "Số lượng tồn phải là số nguyên");
			return false;
		}
		return true;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj.equals(btnChonAnh)) {
			try {
				JFileChooser file = new JFileChooser();
				file.setCurrentDirectory(new File(System.getProperty("user.home")));
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
				file.addChoosableFileFilter(filter);
				file.setFileFilter(filter);
				int result = file.showSaveDialog(null);
				if(result == JFileChooser.APPROVE_OPTION) {
					File selectedFile = file.getSelectedFile();
					hinhAnh = selectedFile.getAbsolutePath();
					lblHinhAnh.setIcon(ResizeImage(hinhAnh));
				}

			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh !");
			}

		}
		else if(obj.equals(btnLuu)) {
			if(kiemTraNhapLieu()) {
				DecimalFormat formatter = new DecimalFormat("###,###,###");
				int hoi=JOptionPane.showConfirmDialog(this, "Quyển sách này sẽ được cập nhật","Chú ý",JOptionPane.YES_NO_OPTION);
				String donGia = txtDonGia.getText().replaceAll(",", "");
				String ncc = (String) cbxNhaCungCap.getSelectedItem();
				String nhaXuatBan = (String) cbxNhaXuatBan.getSelectedItem();
				listLoaiSach = daoLoaiSach.getAllLoaiSach();
				String loaiSach = null;
				
				if(hoi==JOptionPane.YES_OPTION) { 
					for (int i = 0; i < listLoaiSach.size(); i++) {
						if (cbxTheLoai.getSelectedItem().toString()
								.equalsIgnoreCase(listLoaiSach.get(i).getTenLoaiSach())) {
							loaiSach = listLoaiSach.get(i).getMaLoaiSach();
						}
					}
					Sach sach = new Sach();
					boolean tinhTrangSach = true;
					if(sachStatic.getSoLuong() > 0) {
						sachDao.suaTinhTrangSach(tinhTrangSach, sachStatic.getMaSanPham());
						tinhTrangSach = true;
					}
					else {
						if(sachStatic.getTinhTrang()) {
							sachDao.suaTinhTrangSach(tinhTrangSach, sachStatic.getMaSanPham());
							tinhTrangSach = true;
						}
						else {
							sachDao.suaTinhTrangSach(!tinhTrangSach, sachStatic.getMaSanPham());
							tinhTrangSach = false;
						}
					}
					try {
						if(hinhAnh.trim().equals("Chưa có hình ảnh")) {
							sach = new Sach(txtMaSach.getText(), txtTenSach.getText(), Double.parseDouble(donGia), ncc, 
									Integer.parseInt(txtSoLuongTon.getText()), sachStatic.getHinhAnh(), 
									"Sách", tinhTrangSach, txtTacGia.getText(), Integer.parseInt(txtNamXuatBan.getText()), nhaXuatBan, 
									Integer.parseInt(txtSoTrang.getText()), new LoaiSach(loaiSach));	
							System.out.println(1);
						}
						else if(sachStatic.getHinhAnh().equals("src//img//"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)))) //Hình ảnh đúng
						{
							sach = new Sach(txtMaSach.getText(), txtTenSach.getText(), Double.parseDouble(donGia), ncc, 
									Integer.parseInt(txtSoLuongTon.getText()), XuLyLayTenAnh(DinhDanhLaiNguonAnh(sachStatic.getHinhAnh())), 
									"Sách", tinhTrangSach, txtTacGia.getText(), Integer.parseInt(txtNamXuatBan.getText()), nhaXuatBan, 
									Integer.parseInt(txtSoTrang.getText()), new LoaiSach(loaiSach));	
							System.out.println(2);
						}
						else			//Chọn lại hình ảnh
						{
							System.out.println(3);
							sach = new Sach(txtMaSach.getText(), txtTenSach.getText(), Double.parseDouble(donGia), ncc, 
									Integer.parseInt(txtSoLuongTon.getText()), "src//img//"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)), 
									"Sách", tinhTrangSach, txtTacGia.getText(), Integer.parseInt(txtNamXuatBan.getText()), nhaXuatBan, 
									Integer.parseInt(txtSoTrang.getText()), new LoaiSach(loaiSach));
							LuuAnh();
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(this, "Không thể sửa");
					}
					
					sachDao.suaSach(sach);
					sachDao.suaSLTSach(Integer.parseInt(txtSoLuongTon.getText()), txtMaSach.getText());
					//sachDao.LaySachTheoTenSach(sach.getTenSanPham());	
					QuanLiSach.taiComBoxNhaXuatBan();
					QuanLiSach.qls.inSach(sach);
					JOptionPane.showMessageDialog(null , "Sửa thành công","Thông báo",JOptionPane.INFORMATION_MESSAGE);
					jDialog.dispose();
				}
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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

	}
	@Override
	public void mouseExited(MouseEvent e) {		

	}



	private String DinhDanhLaiNguonAnh(String a) {
		String b= "";
		for(int i =0;i<a.length();i++)
		{
			char c = a.charAt(i);
			b+=c;
			if(c=='\\'){
				b+="\\";
			}
		}

		return b;
	}

	private String XuLyLayTenAnh(String nguon) {

		StringBuilder str = new StringBuilder(nguon);
		str = str.reverse();
		String tenAnh="";
		for(int i =0;i<str.length();i++) {
			if(str.charAt(i)=='\\')
				break;
			else
				tenAnh+=str.charAt(i);
		}
		str=new StringBuilder(tenAnh);
		return str.reverse().toString();
	}

	//Lưu ảnh vào thư mục img khi thêm
	private void LuuAnh() {
		FileInputStream in;
		try {
			in = new FileInputStream(DinhDanhLaiNguonAnh(hinhAnh));
			//System.out.println(XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
			FileOutputStream ou = new FileOutputStream("src//img//"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
			BufferedInputStream bin = new BufferedInputStream(in);
			BufferedOutputStream bou = new BufferedOutputStream(ou);
			int b= 0;
			while(b!=-1) {
				try {
					b = bin.read();
					bou.write(b);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
			try {
				bin.close();
				bou.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			System.out.println("Copy thành công");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		String donGia = txtDonGia.getText().replaceAll(",", "");
		if(!kiemTraNhapSoNguyen(txtSoTrang.getText()) ) {
			txtSoTrang.setForeground(Color.red);
		}
		else {
			txtSoTrang.setForeground(Color.black);
		}
		if(!kiemTraNhapSoNguyen(txtNamXuatBan.getText()) ) {
			txtNamXuatBan.setForeground(Color.red);
		}
		else {
			txtNamXuatBan.setForeground(Color.black);
		}
		if(!kiemTraNhapSoThuc(donGia) ) {
			txtDonGia.setForeground(Color.red);
		}
		else {
			txtDonGia.setForeground(Color.black);
		}
		if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText()) ) {
			txtSoLuongTon.setForeground(Color.red);
		}
		else {
			txtSoLuongTon.setForeground(Color.black);
		}
		double money;
		NumberFormat num = NumberFormat.getNumberInstance();
		try {
			money = num.parse(txtDonGia.getText()).doubleValue();
			txtDonGia.setText(num.format(money));
			double cusMoney = 0;
			if (!txtDonGia.getText().equalsIgnoreCase(""))		
				cusMoney = num.parse(txtDonGia.getText()).doubleValue();
		} catch (NumberFormatException | ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	}

}
