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
import dao.DAO_DungCuHocTap;
import dao.DAO_LoaiDCHT;
import dao.DAO_LoaiSach;
import entity.DungCuHocTap;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;

public class FormChiTietDCHT extends JFrame {
	private DAO_DungCuHocTap daoDCHT;
	private ArrayList<DungCuHocTap> listDungCuHocTap;
	private DAO_LoaiDCHT daoLoaiDCHT;
	private ArrayList<LoaiDungCuHocTap> listLoaiDCHT;

	private JLabel lblHinhAnh;
	private JTextField txtMaDCHT ,txtDonGia, txtSoLuongTon, txtThuongHieu, txtXuatXu,txtChatLieu, txtTheLoai, txtNhaCungCap;
	private JTextArea txtTenDCHT;
	private JDialog jDialog;
	public FormChiTietDCHT(DungCuHocTap dcht) {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoDCHT = new DAO_DungCuHocTap();
		daoLoaiDCHT = new DAO_LoaiDCHT();
		listDungCuHocTap = daoDCHT.getAllTbDCHT();

		setTitle("Thông Tin Chi Tiết Dụng Cụ Học Tập");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);	
		setSize(930,580);
		setLocationRelativeTo(null);

		jDialog = new JDialog();
		jDialog.setTitle("Thông tin dụng cụ học tập");
		//jDialog.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		jDialog.setSize(930,580);
		jDialog.setLocationRelativeTo(null);
		jDialog.setModal(true);
		//NORTH
		JPanel pnNorth = new JPanel();
		pnNorth.setLayout(new BoxLayout(pnNorth, BoxLayout.Y_AXIS));
		pnNorth.setBackground(Color.RED);

		JPanel pnTieuDe = new JPanel();
		//pnTieuDe.setBackground(Color.BLUE);
		JLabel lblTieuDe = new JLabel("Thông tin dụng cụ học tập");
		lblTieuDe.setFont(new Font("Time New Roman",Font.BOLD,20));
		pnTieuDe.add(lblTieuDe);

		JPanel pnHinhAnh = new JPanel();
		lblHinhAnh = new JLabel("");
		String path = dcht.getHinhAnh().trim();
		//hinhAnh = dcht.getHinhAnh().trim();
		
		lblHinhAnh.setPreferredSize(new Dimension(250,200));
		if(dcht.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
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

		JPanel pnMaSachThuongHieu = new JPanel();
		pnMaSachThuongHieu.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblmaDCHT = new JLabel("Mã dụng cụ học tập: ");
		lblmaDCHT.setFont(fontLbl);
		txtMaDCHT = new JTextField(20);
		txtMaDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
		txtMaDCHT.setText(dcht.getMaSanPham());
		txtMaDCHT.setEditable(false);
		pnMaSachThuongHieu.add(lblmaDCHT);
		pnMaSachThuongHieu.add(txtMaDCHT);
		pnMaSachThuongHieu.add(Box.createHorizontalStrut(10));
		JLabel lblThuongHieu = new JLabel("Thương hiệu: ");
		lblThuongHieu.setFont(fontLbl);
		txtThuongHieu = new JTextField(20);
		txtThuongHieu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtThuongHieu.setEditable(false);
		txtThuongHieu.setText(dcht.getThuongHieu());
		pnMaSachThuongHieu.add(lblThuongHieu);
		pnMaSachThuongHieu.add(txtThuongHieu);

		JPanel pnTenDCHT = new JPanel();
		pnTenDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblTenDCHT = new JLabel("Tên dụng cụ học tập: ");
		lblTenDCHT.setFont(fontLbl);
		txtTenDCHT = new JTextArea(3, 41/2);
		txtTenDCHT.setLineWrap(true);
		txtTenDCHT.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTenDCHT.setWrapStyleWord(true);
		txtTenDCHT.setEditable(false);
		txtTenDCHT.setBackground(new Color(224, 235, 235));
		txtTenDCHT.setText(dcht.getTenSanPham());
		JLabel lblChatLieu = new JLabel("Chất liệu: ");
		lblChatLieu.setFont(fontLbl);
		txtChatLieu = new JTextField(20);
		txtChatLieu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtChatLieu.setEditable(false);
		txtChatLieu.setText(String.valueOf(dcht.getChatLieu()));
		pnTenDCHT.add(lblTenDCHT);
		pnTenDCHT.add(txtTenDCHT);
		pnTenDCHT.add(Box.createHorizontalStrut(15));
		pnTenDCHT.add(lblChatLieu);
		pnTenDCHT.add(txtChatLieu);

		DecimalFormat formatter = new DecimalFormat("###,###,###");
		JPanel pnDonGia = new JPanel();
		pnDonGia.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblDonGia = new JLabel("Đơn Giá: ");
		lblDonGia.setFont(fontLbl);
		txtDonGia = new JTextField(20);
		txtDonGia.setEditable(false);
		txtDonGia.setFont(new Font("Arial", Font.PLAIN, 16));
		txtDonGia.setText(String.valueOf(formatter.format(dcht.getGiaBan())));
		JLabel lblXuatXu = new JLabel("Xuất xứ: ");
		lblXuatXu.setFont(fontLbl);
		txtXuatXu = new JTextField(20);
		txtXuatXu.setText(dcht.getXuatXu());
		txtXuatXu.setFont(new Font("Arial", Font.PLAIN, 16));
		txtXuatXu.setEditable(false);
		pnDonGia.add(lblDonGia);
		pnDonGia.add(txtDonGia);
		pnDonGia.add(Box.createHorizontalStrut(10));
		pnDonGia.add(lblXuatXu);
		pnDonGia.add(txtXuatXu);

		JPanel pnNhaCungCap = new JPanel();
		pnNhaCungCap.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblNhaCungCap = new JLabel("Nhà cung cấp: " );
		lblNhaCungCap.setFont(fontLbl);
		txtNhaCungCap = new JTextField(20);
		txtNhaCungCap.setFont(new Font("Arial", Font.PLAIN, 16));
		txtNhaCungCap.setText(dcht.getNhaCungCap());
		txtNhaCungCap.setEditable(false);
		JLabel lblTheLoai = new JLabel("Thể loại: ");
		lblTheLoai.setFont(fontLbl);
		txtTheLoai = new JTextField(20);
		txtTheLoai.setFont(new Font("Arial", Font.PLAIN, 16));
		txtTheLoai.setEditable(false);
		listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
		String tenLoaiDCHT = null;
		listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
		for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {
			if(dcht.getLoaiDungCuHocTap().getMaLoaiDCHT().equals(loaiDCHT.getMaLoaiDCHT())) {
				tenLoaiDCHT = loaiDCHT.getTenLoaiDCHT();
			}
		}
		txtTheLoai.setText(tenLoaiDCHT);
		pnNhaCungCap.add(lblNhaCungCap);
		pnNhaCungCap.add(txtNhaCungCap);
		pnNhaCungCap.add(Box.createHorizontalStrut(10));
		pnNhaCungCap.add(lblTheLoai);
		pnNhaCungCap.add(txtTheLoai);

		JPanel pnSoLuongTon = new JPanel();
		pnSoLuongTon.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: ");
		lblSoLuongTon.setFont(fontLbl);
		txtSoLuongTon = new JTextField(20);
		txtSoLuongTon.setFont(new Font("Arial", Font.PLAIN, 16));
		txtSoLuongTon.setEditable(false);
		txtSoLuongTon.setText(String.valueOf(dcht.getSoLuong()));
		pnSoLuongTon.add(lblSoLuongTon);
		pnSoLuongTon.add(txtSoLuongTon);
		pnSoLuongTon.add(Box.createHorizontalStrut(10));

		lblmaDCHT.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblNhaCungCap.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblDonGia.setPreferredSize(lblTenDCHT.getPreferredSize());
		lblSoLuongTon.setPreferredSize(lblTenDCHT.getPreferredSize());

		lblChatLieu.setPreferredSize(lblThuongHieu.getPreferredSize());
		lblXuatXu.setPreferredSize(lblThuongHieu.getPreferredSize());
		lblTheLoai.setPreferredSize(lblThuongHieu.getPreferredSize());

		pnThongTin.add(pnMaSachThuongHieu);
		pnThongTin.add(pnTenDCHT);
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
