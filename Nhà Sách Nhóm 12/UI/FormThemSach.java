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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
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
import dao.DAO_LoaiSach;
import entity.LoaiSach;
import entity.Sach;
import others.LuuThongTinSach;
import others.ResourceManager;
/**
 * 
 * Lê Võ Hữu Thái
 *
 */
public class FormThemSach extends JFrame implements ActionListener, MouseListener, KeyListener{
	private DAO_Sach sachDao;
	private ArrayList<Sach> listSach;
	private DAO_LoaiSach daoLoaiSach;
	private ArrayList<LoaiSach> listLoaiSach;
	private static Sach s;	
	JPanel pnHinhAnh;
	JPanel pnKhungHinhAnh;
	public static JTextArea  txtTenSach;
	private JLabel lblHinhAnh, lblChonAnh;
	private JButton btnChonAnh, btnLuu, btnLamMoi;
	private JTextField txtMaSach,txtDonGia, txtSoLuongTon, txtTacGia,txtSoTrang;
	//public static String hinhAnh="                       Chưa có hình ảnh";
	public static String hinhAnh="src//img//chuacohinhanh.png";
	private JComboBox<String>  cbxTheLoai, cbxNhaCungCap, cbxNhaXuatBan, cbxNamXuatBan;
	private JDialog jDialog;
	LuuThongTinSach thongTinSach;
	public static TextField txtRefunds;
	JTextField txtLoiNamXuatBan, txtLoiTrang, txtLoi, txtLoiDonGia;

	public FormThemSach() {

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sachDao = new DAO_Sach();
		daoLoaiSach = new DAO_LoaiSach();
		listSach = sachDao.getAllTbSach();
		s = new Sach();

		jDialog = new JDialog();
		jDialog.setTitle("Thêm Sách");
		//jDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		jDialog.setSize(950,680);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);
		
		ImageIcon imgDefault = new ImageIcon("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\iconCuaHangSach.png");
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
		JLabel lblTieuDe = new JLabel("Thêm Sách");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		pnTieuDe.add(lblTieuDe);


		pnHinhAnh = new JPanel();
		//pnHinhAnh.setLayout(new BoxLayout(pnHinhAnh, BoxLayout.X_AXIS));
		//pnHinhAnh.setPreferredSize(new Dimension(250,160));
		//pnHinhAnh.setBackground(Color.YELLOW);
		lblHinhAnh = new JLabel("");

		String path = s.getHinhAnh();

		
		//lblHinhAnh.setFont(new Font("Arial", Font.PLAIN, 16));
		lblHinhAnh.setLayout(new FlowLayout(FlowLayout.CENTER));
		lblHinhAnh.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\img\\chuacohinhanh.png"));
		lblHinhAnh.setText(hinhAnh);
		lblHinhAnh.setPreferredSize(new Dimension(250,160));
		lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		pnHinhAnh.add(lblHinhAnh);


		JPanel pnChonAnh = new JPanel();
		//pnChonAnh.setBackground(Color.PINK);
		lblChonAnh = new JLabel("  Chọn ảnh");
		lblChonAnh.setFont(new Font("Arial", Font.BOLD, 16));
		btnChonAnh = new JButton("");
		JLabel lblImgChonAnh = new JLabel(ResizeImageIcon("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\upload1.png"));
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


		//CENTER
		JPanel pnCenter = new JPanel();
		//pnCenter.setLayout(new BoxLayout(pnCenter, BoxLayout.Y_AXIS));

		JPanel pnThongTin = new JPanel();
		pnThongTin.setLayout(new BoxLayout(pnThongTin, BoxLayout.Y_AXIS));		
		pnThongTin.add(Box.createVerticalStrut(10));

		Font fontLbl =new Font("Arial",Font.BOLD,18);

		JPanel pnMaSachTacGia = new JPanel();
		pnMaSachTacGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblmaSach = new JLabel("Mã sách: ");
		lblmaSach.setFont(fontLbl);
		txtMaSach = new JTextField(20);
		txtMaSach.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaSach.setEditable(false);
		pnMaSachTacGia.add(lblmaSach);
		pnMaSachTacGia.add(txtMaSach);
		pnMaSachTacGia.add(Box.createHorizontalStrut(10));
		JLabel lblTacGia = new JLabel("Tác giả: ");
		lblTacGia.setFont(fontLbl);
		txtTacGia = new JTextField(20);
		txtTacGia.setFont(new Font("Arial", Font.PLAIN, 16));
		//txtTacGia.se	tEditable(false);
		pnMaSachTacGia.add(lblTacGia); 
		pnMaSachTacGia.add(txtTacGia);

		JPanel pnTenSach = new JPanel();
		pnTenSach.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenSach = new JLabel("Tên sách: ");
		lblTenSach.setFont(fontLbl);
		txtTenSach = new JTextArea(3, 41/2);
		txtTenSach.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenSach.requestFocus();
		txtTenSach.setLineWrap(true);
		txtTenSach.setWrapStyleWord(true);

		JLabel lblNhaXuatBan = new JLabel("Nhà xuất bản: ");
		lblNhaXuatBan.setFont(fontLbl);
		cbxNhaXuatBan = new JComboBox();
		cbxNhaXuatBan.setEditable(true);
		loadComboNhaXuatBan();
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

			}
		});

		JLabel lblNamXuatBan = new JLabel("Năm xuất bản: ");
		lblNamXuatBan.setFont(fontLbl);
		LocalDateTime localDate = LocalDateTime.now();
		int year = localDate.getYear();
		cbxNamXuatBan = new JComboBox();
		cbxNamXuatBan.setEditable(true);
		for (int i =  year; i >= 1980; i--) {
			cbxNamXuatBan.addItem(String.valueOf(i));
		}
		cbxNamXuatBan.setPreferredSize(new Dimension(258,25));
		cbxNamXuatBan.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
			//			public void keyPressed(KeyEvent event) { 
			//
			//				System.out.println("hahahihi");
			//				if(cbxNamXuatBan.getSelectedItem().equals("")) {
			//					txtLoiNamXuatBan.setText("");
			//				}			
			//				else if(!kiemTraNhapSoNguyen((String) cbxNamXuatBan.getSelectedItem()) ) {
			//					System.out.println("haha");
			//					cbxNamXuatBan.setForeground(Color.red);
			//					txtLoiNamXuatBan.setText("*Lỗi: Năm xuất bản phải là số nguyên");
			//					txtLoiNamXuatBan.setForeground(Color.red);
			//				}
			//				else {
			//					cbxNamXuatBan.setForeground(Color.black);
			//					txtLoiNamXuatBan.setText("");
			//				}
			//			}

			@Override
			public void keyReleased(KeyEvent event) {
				if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			
					if(cbxNamXuatBan.getSelectedItem().equals("")) {
						txtLoiNamXuatBan.setText("");
					}			
					else if(!kiemTraNhapSoNguyen((String) cbxNamXuatBan.getSelectedItem()) ) {
						System.out.println("haha");
						cbxNamXuatBan.setForeground(Color.red);
						txtLoiNamXuatBan.setText("*Lỗi: Năm xuất bản phải là số nguyên");
						txtLoiNamXuatBan.setForeground(Color.red);
					}
					else {
						cbxNamXuatBan.setForeground(Color.black);
						txtLoiNamXuatBan.setText("");
					}
				}
			}

		});

		pnDonGia.add(lblDonGia);
		pnDonGia.add(txtDonGia);
		pnDonGia.add(Box.createHorizontalStrut(10));
		pnDonGia.add(lblNamXuatBan);
		pnDonGia.add(cbxNamXuatBan);

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
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp: ");
		lblNhaCungCap.setFont(fontLbl);
		cbxNhaCungCap = new JComboBox();
		cbxNhaCungCap.setEditable(true);
		List<String> listNCC = new ArrayList<String>();
		for(Sach sach : listSach) {  
			listNCC.add(sach.getNhaCungCap().trim());
		}
		Set<String> listNhaCungCapKhongTrung =  new LinkedHashSet<String>(listNCC);
		for(String s : listNhaCungCapKhongTrung) {  
			cbxNhaCungCap.addItem(s);
		}
		cbxNhaCungCap.setPreferredSize(new Dimension(258,25));
		JLabel lblSoTrang = new JLabel("Số trang: ");
		lblSoTrang.setFont(fontLbl);
		txtSoTrang = new JTextField(20);
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
		//txtSoTrang.setEditable(false);
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
		pnLoi.add(Box.createHorizontalStrut(550));
		pnLoi.add(txtLoi);

		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTheLoai = new JLabel("Thể loại: ");
		lblTheLoai.setFont(fontLbl);
		cbxTheLoai = new JComboBox();
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		List<String> listTheLoai = new ArrayList<String>();
		for(LoaiSach loaisach : listLoaiSach) {  
			listTheLoai.add(loaisach.getTenLoaiSach());
		}

		Set<String> listTheLoaiKhongTrung =  new LinkedHashSet<String>(listTheLoai);
		for(String s : listTheLoaiKhongTrung) {  
			cbxTheLoai.addItem(s);
		}
		cbxTheLoai.setPreferredSize(new Dimension(258,25));

		pnSoLuongTon.add(lblTheLoai);
		pnSoLuongTon.add(cbxTheLoai);
		pnSoLuongTon.add(Box.createHorizontalStrut(10));
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

		lblmaSach.setPreferredSize(lblNhaCungCap.getPreferredSize());
		lblTenSach.setPreferredSize(lblNhaCungCap.getPreferredSize());
		lblDonGia.setPreferredSize(lblNhaCungCap.getPreferredSize());
		lblTheLoai.setPreferredSize(lblNhaCungCap.getPreferredSize());

		lblTacGia.setPreferredSize(lblNamXuatBan.getPreferredSize());
		lblNhaXuatBan.setPreferredSize(lblNamXuatBan.getPreferredSize());
		lblSoTrang.setPreferredSize(lblNamXuatBan.getPreferredSize());
		lblSoLuongTon.setPreferredSize(lblNamXuatBan.getPreferredSize());


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
		btnLamMoi.addActionListener(this);
		btnChonAnh.addMouseListener(this);
		btnLuu.addActionListener(this);
		//txtTenSach.addKeyListener(this);
		//txtNamXuatBan.addKeyListener(this);
		//txtTenSach.addKeyListener(new KeyTextArea(txtTenSach,1));
		dinhDangMaSach();

		jDialog.addWindowListener(new WindowAdapter() {
			//Mở lên là load
			@Override
			public void windowOpened(WindowEvent e) {
				DecimalFormat formatter = new DecimalFormat("###,###,###");
				thongTinSach = new LuuThongTinSach();
				txtTenSach.requestFocus();
				Sach sach = new Sach();
				try {
					thongTinSach = (LuuThongTinSach) ResourceManager.load("luuThongTinSach");
					//Kiểm tra không có sách thì lưu, có sách thì load từ file lên
					//if(sachDao.LaySachTheoTenSach(thongTinSach.tenSanPham.trim())==null) {
					System.out.println(thongTinSach.hinhAnh);
					lblHinhAnh.setIcon(ResizeImage(thongTinSach.hinhAnh));
					txtTenSach.setText(thongTinSach.tenSanPham);
					txtTenSach.requestFocus();
					txtTacGia.setText(thongTinSach.tacGia);
					//cbxNamXuatBan.setSelectedItem(String.valueOf(thongTinSach.namXuatBan));
					txtDonGia.setText(String.valueOf(thongTinSach.donGia));
					//cbxNhaXuatBan.setSelectedItem(thongTinSach.nhaXuatBan);
					//cbxNhaCungCap.setSelectedItem(thongTinSach.nhaCungCap);
					txtSoTrang.setText(String.valueOf(thongTinSach.soTrang));
					cbxTheLoai.setSelectedItem(String.valueOf(thongTinSach.theLoai));
					txtSoLuongTon.setText(String.valueOf(thongTinSach.soLuongThem));
					String donGia = txtDonGia.getText().replaceAll(",", "");
					if(!kiemTraNhapSoNguyen(txtSoTrang.getText()) ) {
						txtSoTrang.setForeground(Color.red);
					}
					else {
						txtSoTrang.setForeground(Color.black);
					}

					if(!kiemTraNhapSoNguyen(txtSoLuongTon.getText()) ) {
						txtSoLuongTon.setForeground(Color.red);
					}
					else {
						txtSoLuongTon.setForeground(Color.black);
					}
					if(!kiemTraNhapSoThuc(donGia) ) {
						txtDonGia.setForeground(Color.red);
					}
					else {
						txtDonGia.setForeground(Color.black);
					}
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
				saveData();

			}
		});
		jDialog.setVisible(true);
	}
	public static void main(String[] args) {
		//new FormThemSach().setVisible(true);
	}
	public void loadComboNhaXuatBan() 
	{
		List<String> l = new ArrayList<String>();
		for(Sach sach : listSach) {  
			l.add(sach.getNhaXuatBan().trim());
		}
		Set<String> listNXBKhongTrung =  new LinkedHashSet<String>(l);
		for(String s : listNXBKhongTrung) {  
			cbxNhaXuatBan.addItem(s);
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
		//		else if(cbxNamXuatBan.getSelectedItem().equals("")) {
		//			cbxNamXuatBan.requestFocus();
		//			JOptionPane.showMessageDialog(this, "Năm xuất bản không đc trống");
		//			return false;
		//		}
		else if(!kiemTraNhapSoNguyen((String) cbxNamXuatBan.getSelectedItem()) ) {
			cbxNamXuatBan.requestFocus();	
			JOptionPane.showMessageDialog(jDialog,"Năm xuất bản phải là số nguyên !");
			return false;
		}
		//		else if(Integer.parseInt((String) cbxNamXuatBan.getSelectedItem()) < 1900 || Integer.parseInt((String) cbxNamXuatBan.getSelectedItem()) > year) {
		//			cbxNamXuatBan.requestFocus();
		//			JOptionPane.showMessageDialog(this,"Năm xuất bản phải lớn hơn 1900 và nhỏ hơn " + year);
		//			return false;
		//		}
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
		else if(cbxNamXuatBan.getSelectedItem().equals("")) {
			cbxNamXuatBan.requestFocus();
			JOptionPane.showMessageDialog(this, "Năm xuất bản không đc trống");
			return false;
		}
		else if(cbxNhaCungCap.getSelectedItem().equals("")) {
			JOptionPane.showMessageDialog(this, "Nhà cung cấp không đc trống");
			return false;
		}
		else if(txtSoTrang.getText().trim().length()==0) {
			txtSoTrang.requestFocus();
			JOptionPane.showMessageDialog(this, "Số trang không đc trống");
			return false;
		}
		else if(!kiemTraNhapSoNguyen(txtSoTrang.getText())) {
			txtSoTrang.requestFocus();
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
			JOptionPane.showMessageDialog(this, "Số lượng tồn phải là số nguyên");
			return false;
		}

		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Object obj = e.getSource();
		if(obj.equals(btnChonAnh)) 
		{
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
			} catch (Exception e2) 
			{
				JOptionPane.showMessageDialog(this, "Vui lòng chọn ảnh !");
			}
		}
		else if(obj.equals(btnLuu)) 
		{
			if(kiemTraNhapLieu()) 
			{
				String donGia = txtDonGia.getText().replaceAll(",", "");
				if(!kiemTraNhapSoThuc(donGia)) 
				{
					txtDonGia.requestFocus();
					JOptionPane.showMessageDialog(this, "Đơn giá phải là số");
				}
				else 
				{
					String ncc = (String) cbxNhaCungCap.getSelectedItem();
					String nhaXuatBan = (String) cbxNhaXuatBan.getSelectedItem();
					listLoaiSach = daoLoaiSach.getAllLoaiSach();
					String loaiSach = null;
					for (int i = 0; i < listLoaiSach.size(); i++) 
					{
						if (cbxTheLoai.getSelectedItem().toString()
								.equalsIgnoreCase(listLoaiSach.get(i).getTenLoaiSach())) {
							loaiSach = listLoaiSach.get(i).getMaLoaiSach();
						}	
					}
					Sach sach = new Sach();
					if(hinhAnh.trim().equals("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\img\\chuacohinhanh.png")) 
					{
						sach = new Sach(txtMaSach.getText(), txtTenSach.getText(), Double.parseDouble(donGia), ncc, 
								Integer.parseInt(txtSoLuongTon.getText()), hinhAnh, 
								"Sách", true, txtTacGia.getText(), Integer.parseInt((String) cbxNamXuatBan.getSelectedItem()), nhaXuatBan, 
								Integer.parseInt(txtSoTrang.getText()), new LoaiSach(loaiSach));
					}
					else 
					{
						sach = new Sach(txtMaSach.getText(), txtTenSach.getText(), Double.parseDouble(donGia), ncc, 
								Integer.parseInt(txtSoLuongTon.getText()), "C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\img"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)), 
								"Sách", true, txtTacGia.getText(), Integer.parseInt((String) cbxNamXuatBan.getSelectedItem()), nhaXuatBan, 
								Integer.parseInt(txtSoTrang.getText()), new LoaiSach(loaiSach));		
						LuuAnh();
					}
					
					sachDao.themSach(sach);
					QuanLiDCHT.taiComBoxNhaXuatBan();				
					JOptionPane.showMessageDialog(this, "Thêm thành công");
					//lblHinhAnh.setIcon(ResizeImage(""));
					hinhAnh = "                       Chưa có hình ảnh";
					QuanLiSach.qls.inSach(sach);
					
					jDialog.dispose();
				}
			}
		}
		else if(obj.equals(btnLamMoi)) {
			lblHinhAnh.setIcon(ResizeImage(""));
			lblHinhAnh.setText("                       Chưa có hình ảnh");
			txtTenSach.setText("");
			txtTenSach.requestFocus();
			txtTacGia.setText("");
			cbxNamXuatBan.setSelectedItem("2021");
			txtDonGia.setText("");
			txtSoTrang.setText("");
			txtSoLuongTon.setText("");
			cbxNhaXuatBan.setSelectedItem("NXB Văn Học");
			cbxNhaCungCap.setSelectedItem("Nhà Xuất Bản Kim Đồng");
			cbxTheLoai.setSelectedItem("Văn Học");
			txtLoi.setText("");
			txtLoiTrang.setText("");
			txtLoiNamXuatBan.setText("");
			txtLoiDonGia.setText("");
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
		//KiemTraCoThongTinSach();
		//saveData();

	}
	//Lưu dữ liệu vào file 
	public void saveData() 
	{
		LuuThongTinSach thongTinSach = new LuuThongTinSach();
		thongTinSach.hinhAnh = hinhAnh;
		thongTinSach.tenSanPham = txtTenSach.getText();
		thongTinSach.tacGia = txtTacGia.getText();
		//thongTinSach.namXuatBan = (String) cbxNamXuatBan.getSelectedItem();
		thongTinSach.donGia = txtDonGia.getText();
		//thongTinSach.nhaXuatBan = (String) cbxNhaXuatBan.getSelectedItem();
		//thongTinSach.nhaCungCap = (String) cbxNhaCungCap.getSelectedItem();
		thongTinSach.soTrang =  txtSoTrang.getText();
		thongTinSach.soLuongThem =  txtSoLuongTon.getText();
		thongTinSach.theLoai = (String) cbxTheLoai.getSelectedItem();
		try {
			ResourceManager.save(thongTinSach, "luuThongTinSach");
		} catch (Exception e1) {
			//System.out.println(e1.getMessage());
		}
	}

	//	private boolean KiemTraCoThongTinSach() {		//Nếu không có thì tạo mới
	//		DecimalFormat formatter = new DecimalFormat("###,###,###");
	//		s = new Sach();
	//		if(sachDao.LaySachTheoTenSach(txtTenSach.getText().trim())!=null) {
	//			s = sachDao.LaySachTheoTenSach(txtTenSach.getText().trim());
	//			lblHinhAnh.setIcon(ResizeImage(s.getHinhAnh()));
	//			txtMaSach.setText(s.getMaSanPham());
	//			txtTacGia.setText(s.getTacGia());
	//			txtNamXuatBan.setText(String.valueOf(s.getNamXuatBan()));
	//			txtDonGia.setText(String.valueOf(formatter.format(s.getGiaBan())));
	//			txtDonGia.setForeground(Color.black);
	//			cbxNhaXuatBan.setSelectedItem(s.getNhaXuatBan());
	//			cbxNhaCungCap.setSelectedItem(s.getNhaCungCap());
	//			txtSoTrang.setText(String.valueOf(s.getSoTrang()));
	//			txtSoLuongTon.setText(String.valueOf(s.getSoLuong()));
	//			String tenLoaiSach = null;
	//			listLoaiSach = daoLoaiSach.getAllLoaiSach();
	//			for(LoaiSach loaiSach : listLoaiSach) {
	//				if(s.getLoaiSach().getMaLoaiSach().equals(loaiSach.getMaLoaiSach())) {
	//					tenLoaiSach = loaiSach.getTenLoaiSach();
	//				}
	//			}
	//			cbxTheLoai.setSelectedItem(tenLoaiSach);
	//			//xoaChu(s.getTacGia());
	//			btnChonAnh.setEnabled(false);
	//			txtTacGia.setEditable(false);
	//			txtNamXuatBan.setEditable(false);
	//			txtDonGia.setEditable(false);
	//			txtSoTrang.setEditable(false);
	//			txtSoLuongTon.setEditable(false);
	//			cbxTheLoai.setEnabled(false);
	//			cbxNhaCungCap.setEnabled(false);
	//			cbxNhaXuatBan.setEnabled(false);
	//
	//			txtSoTrang.setForeground(Color.black);
	//			txtNamXuatBan.setForeground(Color.black);
	//			txtDonGia.setForeground(Color.black);
	//
	//			return true;
	//
	//		}
	//		else {
	//			dinhDangMaSach();
	//			lblHinhAnh.setIcon(ResizeImage(hinhAnh));
	//			txtTacGia.setText("");
	//			txtNamXuatBan.setText("");
	//			txtDonGia.setText("");
	//			txtSoTrang.setText("");
	//			txtSoLuongTon.setText("");
	//			cbxNhaXuatBan.setSelectedItem("NXB Văn Học");
	//			cbxNhaCungCap.setSelectedItem("Nhà Xuất Bản Kim Đồng");
	//
	//			btnChonAnh.setEnabled(true);
	//			txtTacGia.setEditable(true);
	//			txtNamXuatBan.setEditable(true);
	//			txtDonGia.setEditable(true);
	//			txtSoTrang.setEditable(true);
	//			cbxTheLoai.setEnabled(true);
	//			cbxNhaCungCap.setEnabled(true);
	//			cbxNhaXuatBan.setEnabled(true);
	//			
	//			txtSoTrang.setForeground(Color.black);
	//			txtNamXuatBan.setForeground(Color.black);
	//			txtDonGia.setForeground(Color.black);
	//			return false;
	//		}
	//	}

	private String DinhDanhLaiNguonAnh(String a) 
	{
		String b= "";
		for(int i =0;i<a.length();i++)
		{
			char c = a.charAt(i);
			b+=c;
			if(c=='\\')
				{
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
		System.out.println(hinhAnh + "Dong 967");
		try {	
			in = new FileInputStream(DinhDanhLaiNguonAnh(hinhAnh));
			//System.out.println(XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
			FileOutputStream ou = new FileOutputStream("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\img"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
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
				System.out.println(b + "Dong 983");
				System.out.println(bou + "Dong 984");
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
	private void dinhDangMaSach() 
	{
		String maSach = "";
		maSach += "SPS";
		if(String.valueOf(sachDao.layMaSachLonNhat()).length() == 2) {
			maSach += "00";
		}
		else if(String.valueOf(sachDao.layMaSachLonNhat()).length() == 3) {
			maSach += "0";
		}
		else if(String.valueOf(sachDao.layMaSachLonNhat()).length() == 1) {
			maSach += "000";
		}
		maSach += String.valueOf(sachDao.layMaSachLonNhat()+1);
		txtMaSach.setText(maSach);
	}

}
