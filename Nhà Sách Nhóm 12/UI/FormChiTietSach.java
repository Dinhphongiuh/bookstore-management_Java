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

public class FormChiTietSach extends JFrame {
	private DAO_Sach sachDao;
	private ArrayList<Sach> listSach;
	private DAO_LoaiSach daoLoaiSach;
	private ArrayList<LoaiSach> listLoaiSach;

	private JLabel lblHinhAnh, lblChonAnh;
	private JTextField txtMaSach ,txtDonGia, txtSoLuongTon, txtTacGia, txtNamXuatBan,txtSoTrang, txtTheLoai, txtNhaCungCap, txtNhaXuatBan;
	private JTextArea txtTenSach;
	private String hinhAnh="";
	private JDialog jDialog;
	public FormChiTietSach(Sach s) {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sachDao = new DAO_Sach();
		daoLoaiSach = new DAO_LoaiSach();
		listSach = sachDao.getAllTbSach();

		System.out.println(s.getTenSanPham());
		setTitle("Thông Tin Chi Tiết Sách");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(680,550);
		setLocationRelativeTo(null);

		jDialog = new JDialog();
		jDialog.setTitle("Thông Tin Chi Tiết Sách");
		//jDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		jDialog.setSize(900,580);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);
		//NORTH
		JPanel pnNorth = new JPanel();
		pnNorth.setLayout(new BoxLayout(pnNorth, BoxLayout.Y_AXIS));
		pnNorth.setBackground(Color.RED);

		JPanel pnTieuDe = new JPanel();
		//pnTieuDe.setBackground(Color.BLUE);
		JLabel lblTieuDe = new JLabel("Thông tin sách");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		pnTieuDe.add(lblTieuDe);

		JPanel pnHinhAnh = new JPanel();
		lblHinhAnh = new JLabel("");
		String path = s.getHinhAnh().trim();
		hinhAnh = s.getHinhAnh().trim();
		
		lblHinhAnh.setPreferredSize(new Dimension(250,200));
		if(s.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
			JPanel pnKhungHinhAnh = new JPanel();
			pnKhungHinhAnh.setPreferredSize(new Dimension(250,200));
			pnKhungHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			pnKhungHinhAnh.setLayout(new BoxLayout(pnKhungHinhAnh, BoxLayout.Y_AXIS));
			pnKhungHinhAnh.add(Box.createHorizontalStrut(80));
			lblHinhAnh.setText("Chưa có hình ảnh");
			pnKhungHinhAnh.add(lblHinhAnh);
			pnHinhAnh.add(pnKhungHinhAnh);
		}
		else {
			lblHinhAnh.setPreferredSize(new Dimension(250,200));
			lblHinhAnh.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			lblHinhAnh.setIcon(ResizeImage(path));
			pnHinhAnh.add(lblHinhAnh);
		}
		

		pnNorth.add(pnTieuDe);
		pnNorth.add(pnHinhAnh);

		jDialog.add(pnNorth,BorderLayout.NORTH);

		Font fontLbl =new Font("Arial",Font.BOLD,18);
		//CENTER
		JPanel pnCenter = new JPanel();
		
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
		txtTacGia.setEditable(false);
		txtTacGia.setText(s.getTacGia());
		pnMaSachTacGia.add(lblTacGia);
		pnMaSachTacGia.add(txtTacGia);

		JPanel pnTenSach = new JPanel();
		pnTenSach.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenSach = new JLabel("Tên sách: ");
		lblTenSach.setFont(fontLbl);
		txtTenSach = new JTextArea(3, 41/2);
		txtTenSach.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenSach.setLineWrap(true);
		txtTenSach.setWrapStyleWord(true);
		txtTenSach.setEditable(false);
		txtTenSach.setBackground(new Color(224, 235, 235));
		txtTenSach.setText(s.getTenSanPham());
		JLabel lblNamXuatBan = new JLabel("Năm xuất bản: ");
		lblNamXuatBan.setFont(fontLbl);
		txtNamXuatBan = new JTextField(20);
		txtNamXuatBan.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNamXuatBan.setEditable(false);
		txtNamXuatBan.setText(String.valueOf(s.getNamXuatBan()));
		pnTenSach.add(lblTenSach);
		pnTenSach.add(txtTenSach);
		pnTenSach.add(Box.createHorizontalStrut(15));
		pnTenSach.add(lblNamXuatBan);
		pnTenSach.add(txtNamXuatBan);

		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JPanel pnDonGia = new JPanel();
		pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDonGia = new JLabel("Đơn Giá: ");
		lblDonGia.setFont(fontLbl);
		txtDonGia = new JTextField(20);
		txtDonGia.setEditable(false);
		txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGia.setText(String.valueOf(formatter.format(s.getGiaBan())));
		JLabel lblNhaXuatBan = new JLabel("Nhà xuất bản: ");
		lblNhaXuatBan.setFont(fontLbl);
		txtNhaXuatBan = new JTextField(20);
		txtNhaXuatBan.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNhaXuatBan.setText(s.getNhaXuatBan());
		txtNhaXuatBan.setEditable(false);
		pnDonGia.add(lblDonGia);
		pnDonGia.add(txtDonGia);
		pnDonGia.add(Box.createHorizontalStrut(10));
		pnDonGia.add(lblNhaXuatBan);
		pnDonGia.add(txtNhaXuatBan);

		JPanel pnNhaCungCap = new JPanel();
		pnNhaCungCap.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp: " );
		lblNhaCungCap.setFont(fontLbl);
		txtNhaCungCap = new JTextField(20);
		txtNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNhaCungCap.setText(s.getNhaCungCap());
		txtNhaCungCap.setEditable(false);
		JLabel lblSoTrang = new JLabel("Số trang: ");
		lblSoTrang.setFont(fontLbl);
		txtSoTrang = new JTextField(20);
		txtSoTrang.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoTrang.setText(String.valueOf(s.getSoTrang()));
		txtSoTrang.setEditable(false);
		pnNhaCungCap.add(lblNhaCungCap);
		pnNhaCungCap.add(txtNhaCungCap);
		pnNhaCungCap.add(Box.createHorizontalStrut(10));
		pnNhaCungCap.add(lblSoTrang);
		pnNhaCungCap.add(txtSoTrang);

		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: ");
		lblSoLuongTon.setFont(fontLbl);
		txtSoLuongTon = new JTextField(20);
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongTon.setEditable(false);
		txtSoLuongTon.setText(String.valueOf(s.getSoLuong()));
		JLabel lblTheLoai = new JLabel("Thể loại: ");
		lblTheLoai.setFont(fontLbl);
		txtTheLoai = new JTextField(20);
		txtTheLoai.setEditable(false);
		txtTheLoai.setFont(new Font("Arial", Font.PLAIN, 16));
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		String tenLoaiSach = null;
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		for(LoaiSach loaiSach : listLoaiSach) {
			if(s.getLoaiSach().getMaLoaiSach().equals(loaiSach.getMaLoaiSach())) {
				tenLoaiSach = loaiSach.getTenLoaiSach();
			}
		}
		txtTheLoai.setText(tenLoaiSach);
		
		pnSoLuongTon.add(lblSoLuongTon);
		pnSoLuongTon.add(txtSoLuongTon);
		pnSoLuongTon.add(Box.createHorizontalStrut(10));
		pnSoLuongTon.add(lblTheLoai);
		pnSoLuongTon.add(txtTheLoai);

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
		pnThongTin.add(pnTenSach);
		pnThongTin.add(pnDonGia);
		pnThongTin.add(pnNhaCungCap);
		pnThongTin.add(pnSoLuongTon);

		pnCenter.add(pnThongTin);

		jDialog.add(pnCenter, BorderLayout.CENTER);	
		jDialog.setVisible(true);
	}
	public static void main(String[] args) {
		//new FormChiTietSach().setVisible(true);
	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
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
}
