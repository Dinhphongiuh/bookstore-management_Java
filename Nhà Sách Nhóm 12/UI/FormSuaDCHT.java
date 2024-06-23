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
import dao.DAO_DungCuHocTap;
import dao.DAO_LoaiDCHT;
import dao.DAO_LoaiSach;
import entity.DungCuHocTap;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;

public class FormSuaDCHT extends JFrame implements ActionListener, MouseListener, KeyListener{
	private DAO_DungCuHocTap daoDCHT;
	private ArrayList<DungCuHocTap> listDCHT;
	private DAO_LoaiDCHT daoLoaiDCHT;
	private ArrayList<LoaiDungCuHocTap> listLoaiDCHT;
	private static DungCuHocTap dchtStatic;

	private JLabel lblHinhAnh, lblChonAnh;
	private JButton btnChonAnh, btnLuu, btnLamMoi;
	private JTextField txtMaDCHT, txtDonGia, txtSoLuongTon,txtLoi, txtLoiDonGia;
	private JTextArea txtTenDCHT;
	private String hinhAnh="                       Chưa có hình ảnh";
	private JComboBox  cbxTheLoai, cbxNhaCungCap, cbxThuongHieu, cbxChatLieu, cbxXuatXu;
	private JDialog jDialog;
	public FormSuaDCHT(DungCuHocTap dcht) {
		dchtStatic = dcht;
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoDCHT = new DAO_DungCuHocTap();
		daoLoaiDCHT = new DAO_LoaiDCHT();
		listDCHT = daoDCHT.getAllTbDCHT();

		setTitle("Sửa Dụng Cụ Học Tập");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(680,550);
		setLocationRelativeTo(null);

		jDialog = new JDialog();
		jDialog.setTitle("Sửa dụng cụ học tập");
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
		String path = dchtStatic.getHinhAnh();
		hinhAnh = dchtStatic.getHinhAnh();

		if(dcht.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
			lblHinhAnh.setText(dcht.getHinhAnh());
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

		JPanel pnMaThuongHieuDCHT = new JPanel();
		pnMaThuongHieuDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblMaDCHT = new JLabel("Mã dụng cụ học tập: ");
		lblMaDCHT.setFont(fontLbl);
		txtMaDCHT = new JTextField(20);
		txtMaDCHT.setFont(fontLbl);
		txtMaDCHT.setText(dcht.getMaSanPham());
		txtMaDCHT.setEditable(false);
		txtMaDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
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
		txtTenDCHT.setText(dcht.getTenSanPham());
		txtTenDCHT.requestFocus();
		txtTenDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
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

		pnLoiDonGia.add(Box.createHorizontalStrut(190));
		pnLoiDonGia.add(txtLoiDonGia);	
		
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JPanel pnDonGia = new JPanel();
		pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDonGia = new JLabel("Đơn Giá: ");
		lblDonGia.setFont(fontLbl);
		txtDonGia = new JTextField(20);
		txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGia.setText(String.valueOf(formatter.format(dcht.getGiaBan())));
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
		for(DungCuHocTap dcht1 : listDCHT) {  
			listNCC.add(dcht1.getNhaCungCap().trim());
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
		pnLoi.add(Box.createHorizontalStrut(190));
		pnLoi.add(txtLoi);
		
		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: ");
		lblSoLuongTon.setFont(fontLbl);
		txtSoLuongTon = new JTextField(20);
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
		txtSoLuongTon.setText(String.valueOf(dcht.getSoLuong()));
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
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
		//pnChucNang.add(btnLamMoi);
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
		pnThongTin.add(Box.createVerticalStrut(-5));
		pnThongTin.add(pnChucNang);

		pnCenter.add(pnThongTin);

		jDialog.add(pnCenter, BorderLayout.CENTER);
		btnChonAnh.addActionListener(this);
		btnChonAnh.addMouseListener(this);
		btnLuu.addActionListener(this);
		txtDonGia.addKeyListener(this);
		txtSoLuongTon.addKeyListener(this);

		jDialog.setVisible(true);
	}
	public static void main(String[] args) {
		//	new FormThemSach().setVisible(true);
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
				int hoi=JOptionPane.showConfirmDialog(this, "Dụng cụ học tập này sẽ được cập nhật","Chú ý",JOptionPane.YES_NO_OPTION);
				String ncc = (String) cbxNhaCungCap.getSelectedItem();
				String thuongHieu = (String) cbxThuongHieu.getSelectedItem();
				String xuatXu = (String) cbxXuatXu.getSelectedItem();
				String chatLieu = (String) cbxChatLieu.getSelectedItem();
				listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
				String loaiDCHT = null;
				String donGia = txtDonGia.getText().replaceAll(",", "");
				if(hoi==JOptionPane.YES_OPTION) { 
					for (int i = 0; i < listLoaiDCHT.size(); i++) {
						if (cbxTheLoai.getSelectedItem().toString()
								.equalsIgnoreCase(listLoaiDCHT.get(i).getTenLoaiDCHT())) {
							loaiDCHT = listLoaiDCHT.get(i).getMaLoaiDCHT();
						}
					}		
					DungCuHocTap dcht = new DungCuHocTap();
					boolean tinhTrangSach = true;
					if(dchtStatic.getSoLuong() > 0) {
						daoDCHT.SuaTinhTrangDCHT(tinhTrangSach, dchtStatic.getMaSanPham());
						tinhTrangSach = true;
					}
					else {
						if(dchtStatic.getTinhTrang()) {
							daoDCHT.SuaTinhTrangDCHT(tinhTrangSach, dchtStatic.getMaSanPham());
							tinhTrangSach = true;
						}
						else {
							daoDCHT.SuaTinhTrangDCHT(!tinhTrangSach, dchtStatic.getMaSanPham());
							tinhTrangSach = false;
						}
					}
					try {
						if(hinhAnh.trim().equals("Chưa có hình ảnh")) {
							dcht = new DungCuHocTap(txtMaDCHT.getText(),txtTenDCHT.getText(),Double.parseDouble(donGia),
									ncc,Integer.parseInt(txtSoLuongTon.getText()),dchtStatic.getHinhAnh(),"Dụng cụ học tập",
									true,thuongHieu,xuatXu,chatLieu, new LoaiDungCuHocTap(loaiDCHT));	
						}
						else if(dchtStatic.getHinhAnh().equals("src//img//"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)))) //Hình ảnh đúng
						{
							dcht = new DungCuHocTap(txtMaDCHT.getText(),txtTenDCHT.getText(),Double.parseDouble(donGia),
									ncc,Integer.parseInt(txtSoLuongTon.getText()), XuLyLayTenAnh(DinhDanhLaiNguonAnh(dchtStatic.getHinhAnh())),
									"Dụng cụ học tập", true,thuongHieu,xuatXu,chatLieu, new LoaiDungCuHocTap(loaiDCHT));

						}
						else			//Chọn lại hình ảnh
						{
							dcht = new DungCuHocTap(txtMaDCHT.getText(),txtTenDCHT.getText(),Double.parseDouble(donGia),
									ncc,Integer.parseInt(txtSoLuongTon.getText()), "src//img//"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)),
									"Dụng cụ học tập", true,thuongHieu,xuatXu,chatLieu, new LoaiDungCuHocTap(loaiDCHT));
							LuuAnh();
						}
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(this, "Không thể sửa");
					}

					daoDCHT.suaDCHT(dcht);		
					//sachDao.LaySachTheoTenSach(sach.getTenSanPham());	
					QuanLiDCHT.taiComBoxThuongHieu();
					QuanLiDCHT.taiComBoxXuatXu();
					QuanLiDCHT.qldcht.inDCHT(dcht);
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
