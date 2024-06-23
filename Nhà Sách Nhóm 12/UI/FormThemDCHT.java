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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
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
import java.util.regex.Pattern;

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
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import connectDB.ConnectDB;
import dao.DAO_Sach;
import dao.DAO_DungCuHocTap;
import dao.DAO_LoaiDCHT;
import dao.DAO_LoaiSach;
import entity.DungCuHocTap;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;
import others.LuuThongTinDCHT;
import others.LuuThongTinSach;
import others.ResourceManager;


public class FormThemDCHT extends JFrame implements ActionListener, MouseListener, KeyListener{
	private DAO_DungCuHocTap daoDCHT;
	private ArrayList<DungCuHocTap> listDCHT;
	private DAO_LoaiDCHT daoLoaiDCHT;
	private ArrayList<LoaiDungCuHocTap> listLoaiDCHT;
	private static DungCuHocTap dcht;	
	JPanel pnHinhAnh;
	JPanel pnKhungHinhAnh;
	public static JTextArea  txtTenDCHT;
	private JLabel lblHinhAnh, lblChonAnh;
	private JButton btnChonAnh, btnLuu, btnLamMoi;
	private JTextField txtMaDCHT, txtDonGia, txtSoLuongTon, txtLoi, txtLoiDonGia;
	//public static String hinhAnh="                       Chưa có hình ảnh";
	public static String hinhAnh="src//img//chuacohinhanh.png";
	private JComboBox  cbxTheLoai, cbxNhaCungCap, cbxThuongHieu, cbxChatLieu, cbxXuatXu;
	private JDialog jDialog;
	LuuThongTinSach data;
	LuuThongTinDCHT duLieuDCHT;
	public FormThemDCHT() {

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoDCHT = new DAO_DungCuHocTap();
		daoLoaiDCHT = new DAO_LoaiDCHT();
		listDCHT = daoDCHT.getAllTbDCHT();
		dcht = new DungCuHocTap();

		jDialog = new JDialog();
		jDialog.setTitle("Thêm Dụng Cụ Học Tập");
		//jDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		jDialog.setSize(950,680);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);
		ImageIcon imgDefault = new ImageIcon("src//Icon//iconCuaHangSach.png");
		Image imgDefault1 = imgDefault.getImage();
		Image newImgDefault1 = imgDefault1.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
		imgDefault = new ImageIcon(newImgDefault1);
		jDialog.setIconImage(imgDefault.getImage());

		//NORTH
		JPanel pnNorth = new JPanel();
		pnNorth.setLayout(new BoxLayout(pnNorth, BoxLayout.Y_AXIS));
		pnNorth.setBackground(Color.RED);



		JPanel pnTieuDe = new JPanel();
		//pnTieuDe.setBackground(Color.BLUE);
		JLabel lblTieuDe = new JLabel("Thêm Dụng Cụ Học Tập");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		pnTieuDe.add(lblTieuDe);


		pnHinhAnh = new JPanel();
		//pnHinhAnh.setLayout(new BoxLayout(pnHinhAnh, BoxLayout.X_AXIS));
		//pnHinhAnh.setPreferredSize(new Dimension(250,160));
		//pnHinhAnh.setBackground(Color.YELLOW);
		lblHinhAnh = new JLabel("");

		String path = dcht.getHinhAnh();

		lblHinhAnh.setLayout(new FlowLayout(FlowLayout.CENTER));
		lblHinhAnh.setIcon(ResizeImage("src//img//chuacohinhanh.png"));
		lblHinhAnh.setText(hinhAnh);
		lblHinhAnh.setPreferredSize(new Dimension(250,160));
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pnHinhAnh.add(lblHinhAnh);


		JPanel pnChonAnh = new JPanel();
		//pnChonAnh.setBackground(Color.PINK);
		lblChonAnh = new JLabel("  Chọn ảnh");
		lblChonAnh.setFont(new Font("Arial", Font.BOLD, 16));
		btnChonAnh = new JButton("");
		JLabel lblImgChonAnh = new JLabel(ResizeImageIcon("src//Icon//upload1.png"));
		btnChonAnh.setLayout(new BorderLayout());
		btnChonAnh.add(lblImgChonAnh,BorderLayout.WEST);
		btnChonAnh.add(lblChonAnh,BorderLayout.CENTER);
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

		JPanel pnMaThuongHieuDCHT = new JPanel();
		pnMaThuongHieuDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMaDCHT = new JLabel("Mã dụng cụ học tập: ");
		lblMaDCHT.setFont(fontLbl);
		txtMaDCHT = new JTextField(20);
		txtMaDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaDCHT.setEditable(false);
		pnMaThuongHieuDCHT.add(lblMaDCHT);
		pnMaThuongHieuDCHT.add(txtMaDCHT);
		pnMaThuongHieuDCHT.add(Box.createHorizontalStrut(10));
		JLabel lblThuongHieu = new JLabel("Thương hiệu: ");
		lblThuongHieu.setFont(fontLbl);
		cbxThuongHieu = new JComboBox();
		cbxThuongHieu.setEditable(true);
		cbxThuongHieu.setPreferredSize(new Dimension(258,25));
		loadComboThuongHieu();
		pnMaThuongHieuDCHT.add(lblThuongHieu); 
		pnMaThuongHieuDCHT.add(cbxThuongHieu);

		JPanel pnTenChatLieuDCHT = new JPanel();
		pnTenChatLieuDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenDCHT = new JLabel("Tên dụng cụ học tập: ");
		lblTenDCHT.setFont(fontLbl);
		txtTenDCHT = new JTextArea(3, 41/2);
		txtTenDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenDCHT.requestFocus();
		txtTenDCHT.setLineWrap(true);
		txtTenDCHT.setWrapStyleWord(true);
		JLabel lblChatLieu = new JLabel("Chất liệu: ");
		lblChatLieu.setFont(fontLbl);
		cbxChatLieu = new JComboBox();
		loadComboChatLieu();
		cbxChatLieu.setPreferredSize(new Dimension(258,25));
		pnTenChatLieuDCHT.add(lblTenDCHT);
		pnTenChatLieuDCHT.add(txtTenDCHT);
		pnTenChatLieuDCHT.add(Box.createHorizontalStrut(15));
		pnTenChatLieuDCHT.add(lblChatLieu);
		pnTenChatLieuDCHT.add(cbxChatLieu);

		JPanel pnLoiDonGia = new JPanel();
		pnLoiDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));	
		txtLoiDonGia = new JTextField(20);	
		txtLoiDonGia.setBorder(null);
		txtLoiDonGia.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoiDonGia.setEditable(false);		
		pnLoiDonGia.add(Box.createHorizontalStrut(185));
		pnLoiDonGia.add(txtLoiDonGia);
		
		JPanel pnDonGia = new JPanel();
		pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDonGia = new JLabel("Đơn Giá: ");
		lblDonGia.setFont(fontLbl);
		txtDonGia = new JTextField(20);
		txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
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
					catch (ParseException e3) {
						// TODO: handle exception
						e3.printStackTrace();
					}
					catch (NumberFormatException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				String donGia = txtDonGia.getText().replaceAll(",", "");
				if(!kiemTraNhapSoThuc(donGia) ) {
					txtDonGia.setForeground(Color.red);
				}
				else {
					txtDonGia.setForeground(Color.black);
				}
			}
		});
		JLabel lblXuatXu = new JLabel("Xuất xứ: ");
		lblXuatXu.setFont(fontLbl);
		cbxXuatXu = new JComboBox();
		cbxXuatXu.setEditable(true);
		loadComboXuatXu();
		cbxXuatXu.setPreferredSize(new Dimension(258,25));
		pnDonGia.add(lblDonGia);
		pnDonGia.add(txtDonGia);
		pnDonGia.add(Box.createHorizontalStrut(10));
		pnDonGia.add(lblXuatXu);
		pnDonGia.add(cbxXuatXu);

		JPanel pnNhaCungCap = new JPanel();
		pnNhaCungCap.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp: ");
		lblNhaCungCap.setFont(fontLbl);
		cbxNhaCungCap = new JComboBox();
		cbxNhaCungCap.setEditable(true);
		List<String> listNCC = new ArrayList<String>();
		for(DungCuHocTap dcht : listDCHT) {  
			listNCC.add(dcht.getNhaCungCap().trim());
		}
		Set<String> listNhaCungCapKhongTrung =  new LinkedHashSet<String>(listNCC);
		for(String s : listNhaCungCapKhongTrung) {  
			cbxNhaCungCap.addItem(s);
		}
		cbxNhaCungCap.setPreferredSize(new Dimension(258,25));
		JLabel lblTheLoai = new JLabel("Thể loại: ");			//Thể loại
		lblTheLoai.setFont(fontLbl);
		cbxTheLoai = new JComboBox();
		listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
		List<String> listTheLoai = new ArrayList<String>();
		for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
			listTheLoai.add(loaiDCHT.getTenLoaiDCHT());
		}
		Set<String> listTheLoaiKhongTrung =  new LinkedHashSet<String>(listTheLoai);
		for(String s : listTheLoaiKhongTrung) {  
			cbxTheLoai.addItem(s);
		}
		cbxTheLoai.setPreferredSize(new Dimension(258,25));
		pnNhaCungCap.add(lblNhaCungCap);
		pnNhaCungCap.add(cbxNhaCungCap);
		pnNhaCungCap.add(Box.createHorizontalStrut(10));
		pnNhaCungCap.add(lblTheLoai);
		pnNhaCungCap.add(cbxTheLoai);

		JPanel pnLoi = new JPanel();
		pnLoi.setLayout(new FlowLayout(FlowLayout.LEFT));	
		txtLoi = new JTextField(20);
		txtLoi.setBorder(null);
		txtLoi.setFont(new Font("Arial", Font.ITALIC, 14));
		txtLoi.setEditable(false);
		pnLoi.add(Box.createHorizontalStrut(185));
		pnLoi.add(txtLoi);
		
		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: ");
		lblSoLuongTon.setFont(fontLbl);
		txtSoLuongTon = new JTextField(20);
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
				else if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText()) ) {
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
		pnSoLuongTon.add(lblSoLuongTon);
		pnSoLuongTon.add(txtSoLuongTon);
		pnSoLuongTon.add(Box.createHorizontalStrut(10));

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
		pnChucNang.add(btnLamMoi);
		pnChucNang.add(btnLuu);

		btnLamMoi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLuu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnChonAnh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		lblMaDCHT.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblNhaCungCap.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblDonGia.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblNhaCungCap.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblSoLuongTon.setPreferredSize(lblTenDCHT.getPreferredSize());

		lblChatLieu.setPreferredSize(lblThuongHieu.getPreferredSize());
		lblXuatXu.setPreferredSize(lblThuongHieu.getPreferredSize());
		lblTheLoai.setPreferredSize(lblThuongHieu.getPreferredSize());


		pnThongTin.add(pnMaThuongHieuDCHT);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnTenChatLieuDCHT);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnDonGia);
		pnThongTin.add(pnLoiDonGia);
		pnThongTin.add(Box.createVerticalStrut(-5));
		pnThongTin.add(pnNhaCungCap);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnSoLuongTon);
		pnThongTin.add(pnLoi);
		pnThongTin.add(Box.createVerticalStrut(15));
		pnThongTin.add(pnChucNang);

		pnCenter.add(pnThongTin);

		jDialog.add(pnCenter, BorderLayout.CENTER);


		btnChonAnh.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnChonAnh.addMouseListener(this);
		btnLuu.addActionListener(this);
//		txtTenDCHT.addKeyListener(this);
		//txtNamXuatBan.addKeyListener(this);
//		txtTenDCHT.addKeyListener(new KeyTextArea(txtTenDCHT,2));
		dinhDangMaDCHT();

		jDialog.addWindowListener(new WindowAdapter() {
			//Mở lên là load
			@Override
			public void windowOpened(WindowEvent e) {
				DecimalFormat formatter = new DecimalFormat("###,###,###");
				duLieuDCHT = new LuuThongTinDCHT();
				txtTenDCHT.requestFocus();
				DungCuHocTap dcht = new DungCuHocTap();
				try {
					duLieuDCHT = (LuuThongTinDCHT) ResourceManager.load("luuThongTinDCHT");
					lblHinhAnh.setIcon(ResizeImage(duLieuDCHT.hinhAnh));
					txtTenDCHT.setText(duLieuDCHT.tenSanPham);
					txtTenDCHT.requestFocus();
					cbxThuongHieu.setSelectedItem(duLieuDCHT.thuongHieu);
					cbxChatLieu.setSelectedItem(duLieuDCHT.chatLieu);
					txtDonGia.setText(String.valueOf(duLieuDCHT.donGia));
					cbxXuatXu.setSelectedItem(duLieuDCHT.xuatXu);
					cbxNhaCungCap.setSelectedItem(duLieuDCHT.nhaCungCap);
					cbxTheLoai.setSelectedItem(String.valueOf(duLieuDCHT.theLoai));
					txtSoLuongTon.setText(String.valueOf(duLieuDCHT.soLuongThem));
					String donGia = txtDonGia.getText().replaceAll(",", "");
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
					txtDonGia.setEditable(true);
				} catch (EOFException e1) {
					// ... this is fine
				} catch(IOException e2) {
					// handle exception which is not expected
					e2.printStackTrace(); 
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

			@Override
			public void windowClosing(WindowEvent e) {
				luuThongTinDCHT();

			}
		});

		jDialog.setVisible(true);
	}
	public static void main(String[] args) {
		//new FormThemSach().setVisible(true);
	}
	public void loadComboThuongHieu() {
		List<String> l = new ArrayList<String>();
		for(DungCuHocTap dcht : listDCHT) {  
			l.add(dcht.getThuongHieu().trim());
		}
		Set<String> listThuongHieuKhongTrung =  new LinkedHashSet<String>(l);
		for(String s : listThuongHieuKhongTrung) {  
			cbxThuongHieu.addItem(s);
		}
	}
	public void loadComboXuatXu() {
		List<String> l = new ArrayList<String>();
		for(DungCuHocTap dcht : listDCHT) {  
			l.add(dcht.getXuatXu().trim());
		}
		Set<String> listXuatXuKhongTrung =  new LinkedHashSet<String>(l);
		for(String s : listXuatXuKhongTrung) {  
			cbxXuatXu.addItem(s);
		}
	}
	public void loadComboChatLieu() {
		List<String> l = new ArrayList<String>();
		for(DungCuHocTap dcht : listDCHT) {  
			l.add(dcht.getChatLieu().trim());
		}
		Set<String> listChatLieuKhongTrung =  new LinkedHashSet<String>(l);
		for(String s : listChatLieuKhongTrung) {  
			cbxChatLieu.addItem(s);
		}
	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(250, 160, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public ImageIcon ResizeImageIcon(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(30, 30, Image.SCALE_SMOOTH);
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
		if(txtTenDCHT.getText().trim().length()==0) {
			txtTenDCHT.requestFocus();
			JOptionPane.showMessageDialog(this, "Tên dụng cụ học tập không đc trống");
			return false;
		}
		else if(txtDonGia.getText().trim().length()==0) {
			txtDonGia.requestFocus();
			JOptionPane.showMessageDialog(this, "Đơn giá không đc trống");
			return false;
		}
		else if(txtDonGia.getText().length()>9&&Float.parseFloat(txtDonGia.getText())>999999999) {
			txtDonGia.requestFocus();
			txtDonGia.selectAll();
			JOptionPane.showMessageDialog(this,"Đơn giá quá lớn !");
			return false;
		}
		else if(cbxNhaCungCap.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "Nhà cung cấp không đc trống");
			return false;
		}
		else if(cbxXuatXu.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "Xuất xứ không đc trống");
			return false;
		}
		else if(txtSoLuongTon.getText().trim().length()==0) {
			txtSoLuongTon.requestFocus();
			JOptionPane.showMessageDialog(this, "Số lượng tồn không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText())) {
			txtSoLuongTon.requestFocus();
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
					//lblHinhAnh.setText(hinhAnh);
				}
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh !");
			}
		}
		else if(obj.equals(btnLuu)) {
			if(kiemTraNhapLieu()) {
					String donGia = txtDonGia.getText().replaceAll(",", "");
					if(!kiemTraNhapSoThuc(donGia)) {
						txtDonGia.requestFocus();
						JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
					}
					else {
						String ncc = (String) cbxNhaCungCap.getSelectedItem();
						String thuongHieu = (String) cbxThuongHieu.getSelectedItem();
						String xuatXu = (String) cbxXuatXu.getSelectedItem();
						String chatLieu = (String) cbxChatLieu.getSelectedItem();
						listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
						String loaiDCHT = null;
						for (int i = 0; i < listLoaiDCHT.size(); i++) {
							if (cbxTheLoai.getSelectedItem().toString()
									.equalsIgnoreCase(listLoaiDCHT.get(i).getTenLoaiDCHT())) {
								loaiDCHT = listLoaiDCHT.get(i).getMaLoaiDCHT();
							}
						}					
						if(hinhAnh.trim().equals("src//img//chuacohinhanh.png")) {
							dcht = new DungCuHocTap(txtMaDCHT.getText(),txtTenDCHT.getText(),Double.parseDouble(donGia),
									ncc,Integer.parseInt(txtSoLuongTon.getText()),hinhAnh,"Dụng cụ học tập",
									true,thuongHieu,xuatXu,chatLieu, new LoaiDungCuHocTap(loaiDCHT));
						}
						else {
							dcht = new DungCuHocTap(txtMaDCHT.getText(),txtTenDCHT.getText(),Double.parseDouble(donGia),
									ncc,Integer.parseInt(txtSoLuongTon.getText()),"src//img////"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)),
									"Dụng cụ học tập",true,thuongHieu,xuatXu,chatLieu, new LoaiDungCuHocTap(loaiDCHT));
							LuuAnh();
						}
						daoDCHT.themDCHT(dcht);
						QuanLiDCHT.taiComBoxThuongHieu();
						QuanLiDCHT.taiComBoxXuatXu();
						JOptionPane.showMessageDialog(this, "Thêm thành công");
						QuanLiDCHT.qldcht.inDCHT(dcht);
						hinhAnh = "                       Chưa có hình ảnh";
						jDialog.dispose();
					}
			}
		}
		else if(obj.equals(btnLamMoi)) {
			lblHinhAnh.setIcon(ResizeImage(""));
			txtTenDCHT.setText("");
			txtTenDCHT.requestFocus();
			txtDonGia.setText("");
			txtSoLuongTon.setText("");
			cbxThuongHieu.setSelectedItem("Stacom");
			cbxChatLieu.setSelectedItem("Nhựa");
			cbxNhaCungCap.setSelectedItem("Cty Văn Phòng Sáng Tạo (Stacom)");
			cbxTheLoai.setSelectedItem("Bút");
			txtLoiDonGia.setText("");
			txtLoi.setText("");
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

		//saveData();

	}
	//Lưu dữ liệu vào file 
	public void luuThongTinDCHT() {
		LuuThongTinDCHT thongTinDCHT = new LuuThongTinDCHT();
		thongTinDCHT.hinhAnh = hinhAnh;
		thongTinDCHT.tenSanPham = txtTenDCHT.getText();
		thongTinDCHT.thuongHieu = (String) cbxThuongHieu.getSelectedItem();
		thongTinDCHT.chatLieu = (String) cbxChatLieu.getSelectedItem();
		thongTinDCHT.donGia = txtDonGia.getText();
		thongTinDCHT.xuatXu = (String) cbxXuatXu.getSelectedItem();
		thongTinDCHT.nhaCungCap = (String) cbxNhaCungCap.getSelectedItem();
		thongTinDCHT.theLoai = (String) cbxTheLoai.getSelectedItem();
		thongTinDCHT.soLuongThem = txtSoLuongTon.getText();
		try {
			ResourceManager.save(thongTinDCHT, "luuThongTinDCHT");
		} catch (Exception e1) {
			//System.out.println(e1.getMessage());
		}
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
		FileInputStream in = null;
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
			}  catch(IOException e2) {
				// handle exception which is not expected
				e2.printStackTrace(); 
			} 

			System.out.println("Copy thành công");
		} catch (FileNotFoundException e) {
			// TODO: handle exception
			//SachTrongQLSP.lblHinhAnh.setText(hinhAnh);
			//System.out.println(hinhAnh);
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	private void dinhDangMaDCHT() {
		String maDCHT = "";
		maDCHT += "SPDCHT";
		if(String.valueOf(daoDCHT.layMaDCHTLonNhat()).length() == 2) {
			maDCHT += "00";
		}
		else if(String.valueOf(daoDCHT.layMaDCHTLonNhat()).length() == 3) {
			maDCHT += "0";
		}
		else if(String.valueOf(daoDCHT.layMaDCHTLonNhat()).length() == 1) {
			maDCHT += "000";
		}
		maDCHT += String.valueOf(daoDCHT.layMaDCHTLonNhat()+1);
		txtMaDCHT.setText(maDCHT);
	}

}
