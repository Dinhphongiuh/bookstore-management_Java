package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.DAO_DungCuHocTap;
import dao.DAO_HoaDon;
import dao.DAO_KhachHang;
import dao.DAO_NhanVien;
import dao.DAO_Sach;
import dao.DAO_ThongKe;
import entity.ChiTietHoaDonDCHT;
import entity.ChiTietHoaDonSach;
import entity.DungCuHocTap;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Sach;
import entity.SanPham;


public class QuanLiThongKe extends JPanel implements ActionListener{
	private JDateChooser jdbd;
	private JDateChooser jdkt;
	JButton btnthongke;
	String tuNgay;
	String denNgay;
	DAO_ThongKe daoThongKe;
	DAO_HoaDon daoHoaDon;
	DAO_Sach daoSach;
	DAO_DungCuHocTap daoDungCuHocTap;
	DefaultTableModel modeltable;
	DefaultTableModel modeltableHoaDon;
	DefaultTableModel modeltableHoaDonNhanVien;
	JLabel lblSoLuongSPNgay, lblSoTienNgay, lblNgaySoLuongHoaDon, 
	lblSoLuongHoaDon, lblSoLuongSPChuaBan;
	JButton btnQuy1, btnQuy2, btnQuy3, btnQuy4, btnXuatExcel;
	JComboBox<String> cbxChonNam;
	JTable table;
	JTable tableHoaDon;
	JTable tableHoaDonNhanVien;
	ArrayList<ChiTietHoaDonSach> listCTHDSACH;
	ArrayList<ChiTietHoaDonDCHT> listCTHDDCHT;
	public static ArrayList<HoaDon> listHoaDon;
	ArrayList<Sach> listSach;
	ArrayList<NhanVien> listNV;
	ArrayList<KhachHang> listKH;
	ArrayList<DungCuHocTap> listDCHT;
	JLabel  txtTitleTopSP,txtTitleTopNhanVien,txtTitleTopHoaDon;
	JTextPane txtTitleSPChuaBan, txtTitleSoLuongHoaDon, txtTitleDoanhThu, txtTittleSanPhamTuNgay;
	DAO_NhanVien nhanVien_dao;
	DAO_KhachHang khachHang_dao;
	public static ArrayList<SanPham> dsSanPham;
	public static String maNhanVien = null;

	HSSFWorkbook workbook;

	public QuanLiThongKe(String maNV) throws Exception {

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoThongKe = new DAO_ThongKe();
		daoHoaDon = new DAO_HoaDon();
		daoSach = new DAO_Sach();
		daoDungCuHocTap = new DAO_DungCuHocTap();
		nhanVien_dao = new DAO_NhanVien();
		khachHang_dao = new DAO_KhachHang();
		maNhanVien = maNV;
		setLayout(new BorderLayout());
		JPanel pnNorth = new JPanel();
		JLabel lblTieuDe = new JLabel("Quản Lý Thống kê");
		Font font = new Font("Arial", Font.BOLD, 25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(Color.RED);
		pnNorth.add(lblTieuDe);
		add(pnNorth, BorderLayout.NORTH);

		JPanel pnWest = new JPanel();
		pnWest.setLayout(new BoxLayout(pnWest, BoxLayout.Y_AXIS));
		pnWest.setPreferredSize(new Dimension(580, 1000));
		String[] chuoi = {"Top","Tên Sản Phẩm", "Số Lượng Đã Bán","Số Lượng Còn Lại"};

		modeltable = new DefaultTableModel(chuoi,0){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; 
			}
		};;
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
		table.setEnabled(true);

		table.getTableHeader().setBackground(new Color(255, 153, 51));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.white);
		Border borderHeader = BorderFactory.createLineBorder(new Color(255, 102, 0));
		table.getTableHeader().setBorder(borderHeader);

		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		table.getColumn("Top").setCellRenderer(midRenderer);
		//table.getColumn("Số lượng đã bán").setMaxWidth(45);
		table.getColumnModel().getColumn(0).setMaxWidth(50);

		table.getColumnModel().getColumn(1).setMaxWidth(270);
		table.getColumnModel().getColumn(2).setMaxWidth(140);
		table.getColumnModel().getColumn(3).setMaxWidth(140);
		table.getColumn("Số Lượng Đã Bán").setCellRenderer(midRenderer);
		table.getColumn("Số Lượng Còn Lại").setCellRenderer(midRenderer);
		table.setRowHeight(30);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		//		table.setShowHorizontalLines(false);
		//		table.setShowVerticalLines(false);
		table.setShowGrid(false);


		JScrollPane sc = new JScrollPane(table);
		sc.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.black));
		txtTitleTopSP = new JLabel();
		txtTitleTopSP.setFont(new Font("Arial", Font.BOLD, 14));

		txtTitleTopSP.setText("Top sản phẩm đã bán");
		//		txtTitleTopSP.setForeground(Color.WHITE);
		//		StyledDocument documentStyle0 = txtTitleTopSP.getStyledDocument();
		//		SimpleAttributeSet centerAttribute0 = new SimpleAttributeSet();
		//		StyleConstants.setAlignment(centerAttribute0, StyleConstants.ALIGN_CENTER);
		//		documentStyle0.setParagraphAttributes(0, documentStyle0.getLength(), centerAttribute0, false);

		//Table top nhân viên của chủ cửa hàng
		String[] chuoi1 = {"Top","Mã Nhân Viên", "Tên Nhân Viên","Tổng Tiền"};
		modeltableHoaDon = new DefaultTableModel(chuoi1,0){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; 
			}
		};;
		tableHoaDon = new JTable(modeltableHoaDon) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));

				return c;
			}
		};
		tableHoaDon.setEnabled(true);

		tableHoaDon.getTableHeader().setBackground(new Color(255, 153, 51));
		tableHoaDon.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableHoaDon.getTableHeader().setForeground(Color.white);
		tableHoaDon.getTableHeader().setBorder(borderHeader);
		tableHoaDon.getColumn("Top").setCellRenderer(midRenderer);
		//table.getColumn("Số lượng đã bán").setMaxWidth(45);
		tableHoaDon.getColumnModel().getColumn(0).setMaxWidth(50);
		tableHoaDon.getColumnModel().getColumn(1).setMaxWidth(140);
		tableHoaDon.getColumnModel().getColumn(2).setMaxWidth(200);
		tableHoaDon.getColumnModel().getColumn(3).setMaxWidth(200);
		tableHoaDon.getColumn("Tổng Tiền").setCellRenderer(midRenderer);
		tableHoaDon.setRowHeight(30);
		tableHoaDon.setFont(new Font("Arial", Font.PLAIN, 13));
		tableHoaDon.setShowGrid(false);
		JScrollPane sc1 = new JScrollPane(tableHoaDon);
		sc1.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.black));
		txtTitleTopNhanVien = new JLabel();
		txtTitleTopNhanVien.setFont(new Font("Arial", Font.BOLD, 14));

		txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất");
		//txtTitleTopNhanVien.setForeground(new Color(255, 153, 51));
		//pnWest.setBackground(Color.WHITE);
		//Table hóa của đơn nhân viên 
		String[] chuoi2 = {"Top","Mã Hóa Đơn", "Ngày Lập","Tổng Tiền","Tên khách hàng"};
		modeltableHoaDonNhanVien = new DefaultTableModel(chuoi2,0){
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; 
			}
		};;
		tableHoaDonNhanVien = new JTable(modeltableHoaDonNhanVien) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));

				return c;
			}
		};
		tableHoaDonNhanVien.setEnabled(true);
		tableHoaDonNhanVien.getTableHeader().setBackground(new Color(255, 153, 51));
		tableHoaDonNhanVien.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		tableHoaDonNhanVien.getTableHeader().setForeground(Color.white);
		tableHoaDonNhanVien.getTableHeader().setBorder(borderHeader);
		tableHoaDonNhanVien.getColumn("Top").setCellRenderer(midRenderer);
		//table.getColumn("Số lượng đã bán").setMaxWidth(45);
		tableHoaDonNhanVien.getColumnModel().getColumn(0).setMaxWidth(50);
		tableHoaDonNhanVien.getColumnModel().getColumn(1).setMaxWidth(130);
		tableHoaDonNhanVien.getColumnModel().getColumn(2).setMaxWidth(150);
		tableHoaDonNhanVien.getColumnModel().getColumn(3).setMaxWidth(180);
		tableHoaDonNhanVien.getColumnModel().getColumn(4).setMaxWidth(180);
		tableHoaDonNhanVien.getColumn("Tổng Tiền").setCellRenderer(midRenderer);
		tableHoaDonNhanVien.setRowHeight(30);
		tableHoaDonNhanVien.setFont(new Font("Arial", Font.PLAIN, 13));
		tableHoaDonNhanVien.setShowGrid(false);
		JScrollPane sc2 = new JScrollPane(tableHoaDonNhanVien);
		sc1.setBorder(BorderFactory.createMatteBorder(0, 0, 3, 3, Color.black));
		txtTitleTopHoaDon = new JLabel();
		txtTitleTopHoaDon.setFont(new Font("Arial", Font.BOLD, 14));

		txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất");


		pnWest.add(txtTitleTopSP);
		pnWest.add(Box.createVerticalStrut(10));
		pnWest.add(sc);
		NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
		if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
			pnWest.add(Box.createVerticalStrut(10));
			pnWest.add(txtTitleTopNhanVien);
			pnWest.add(Box.createVerticalStrut(10));
			pnWest.add(sc1);
		}
		else {
			pnWest.add(Box.createVerticalStrut(10));
			pnWest.add(txtTitleTopHoaDon);
			pnWest.add(Box.createVerticalStrut(10));
			pnWest.add(sc2);
		}


		add(pnWest,BorderLayout.WEST);
		//Ket Thuc WEST
		JPanel pncen = new JPanel();
		pncen.setLayout(new BorderLayout());
		add(pncen,BorderLayout.CENTER);

		JPanel pnRightNorth = new JPanel();
		pnRightNorth.setLayout(new BoxLayout(pnRightNorth, BoxLayout.Y_AXIS));
		pncen.add(pnRightNorth);

		JPanel pnhang0= new JPanel();
		pnhang0.setLayout(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTuNgay = new JLabel("Từ ngày:    ");
		lblTuNgay.setFont(new Font("Arial", Font.BOLD, 14));
		pnhang0.add(lblTuNgay);
		pnhang0.add(jdbd = new JDateChooser());
		jdbd.setDateFormatString("dd/MM/yyyy");
		jdbd.setFont(new Font("Arial", Font.BOLD, 14));
		jdbd.setPreferredSize( new Dimension(200,25) );
		pnhang0.add(Box.createHorizontalStrut(20));	
		JLabel lblDenNgay = new JLabel("Đến ngày:    ");
		lblDenNgay.setFont(new Font("Arial", Font.BOLD, 14));
		pnhang0.add(lblDenNgay);
		pnhang0.add(jdkt = new JDateChooser());

		jdkt.setDateFormatString("dd/MM/yyyy");
		jdkt.setFont(new Font("Arial", Font.BOLD, 14));

		jdkt.setPreferredSize( new Dimension(200,25) );
		pnhang0.add(Box.createHorizontalStrut(30));
		btnthongke = new JButton("Thống Kê");
		btnthongke.setBackground(new Color(255, 133, 51));
		btnthongke.setForeground(Color.WHITE);
		pnhang0.add(btnthongke);
		pnRightNorth.add(pnhang0);

		//		JPanel pnhang1= new JPanel();
		//		pnhang1.setLayout(new FlowLayout(FlowLayout.CENTER));
		//		
		//		JLabel lbLoc = new JLabel("Lọc ");
		//		pnhang1.add(lbLoc);
		//		lbLoc.setFont(new Font("Tahoma", Font.BOLD, 13));
		//		
		//		pnRightNorth.add(pnhang1);

		JPanel pnhang2= new JPanel();
		pnhang2.setLayout(new FlowLayout(FlowLayout.LEFT));
		JLabel lblChonNam = new JLabel("Chọn Năm:");
		//lblChonNam.setFont(fontLbl);
		cbxChonNam = new JComboBox();
		cbxChonNam.setEditable(true);
		LocalDateTime localDate = LocalDateTime.now();
		int year = localDate.getYear();
		for (int i =  year; i >= 2015; i--) {
			cbxChonNam.addItem(String.valueOf(i));
		}
		cbxChonNam.setPreferredSize(new Dimension(75,20));
		pnhang2.add(lblChonNam);
		pnhang2.add(cbxChonNam);
		pnhang2.add(Box.createHorizontalStrut(20));
		btnQuy1 = new JButton("Thống Kê Quý I");	
		btnQuy2 = new JButton("Thống Kê Quý II");
		btnQuy3 = new JButton("Thống Kê Quý III");
		btnQuy4 = new JButton("Thống Kê Quý IV");
		pnhang2.add(btnQuy1);
		btnQuy1.setBackground(new Color(255, 133, 51));
		btnQuy1.setForeground(Color.WHITE);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy2);
		btnQuy2.setBackground(new Color(255, 133, 51));
		btnQuy2.setForeground(Color.WHITE);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy3);
		btnQuy3.setBackground(new Color(255, 133, 51));
		btnQuy3.setForeground(Color.WHITE);
		pnhang2.add(Box.createHorizontalStrut(15));
		pnhang2.add(btnQuy4);
		btnQuy4.setBackground(new Color(255, 133, 51));
		btnQuy4.setForeground(Color.WHITE);
		pnRightNorth.add(pnhang2);

		pncen.add(pnRightNorth,BorderLayout.NORTH);

		//Ô thống kê
		JPanel pnCenNorth = new JPanel();
		pnCenNorth.setLayout(new BoxLayout(pnCenNorth, BoxLayout.Y_AXIS));
		pncen.add(pnCenNorth);
		pnCenNorth.add(Box.createVerticalStrut(20));

		JPanel pnHang3= new JPanel();
		pnHang3.setLayout(new FlowLayout(FlowLayout.CENTER));	

		//========================================== Ô1

		txtTitleSoLuongHoaDon = new JTextPane();
		txtTitleSoLuongHoaDon.setFont(new Font("Arial", Font.BOLD, 16));
		txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất");
		txtTitleSoLuongHoaDon.setForeground(Color.WHITE);
		StyledDocument documentStyle1 = txtTitleSoLuongHoaDon.getStyledDocument();
		SimpleAttributeSet centerAttribute1 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute1, StyleConstants.ALIGN_CENTER);
		documentStyle1.setParagraphAttributes(0, documentStyle1.getLength(), centerAttribute1, false);
		lblNgaySoLuongHoaDon = new JLabel("");
		lblSoLuongHoaDon = new JLabel("? sản phẩm");
		lblSoLuongHoaDon.setForeground(Color.WHITE);
		lblSoLuongHoaDon.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnDoanhThuQuy_SoTienQuy = new JPanel();	
		pnDoanhThuQuy_SoTienQuy.setPreferredSize(new Dimension(300,250));
		pnDoanhThuQuy_SoTienQuy.setLayout(new GridLayout(2,1));


		txtTitleSoLuongHoaDon.setBackground(new Color(179, 179, 0));
		JPanel pnSoTienQuy = new JPanel();
		pnSoTienQuy.setBackground(new Color(179, 179, 0));
		pnSoTienQuy.add(lblSoLuongHoaDon);

		pnDoanhThuQuy_SoTienQuy.add(txtTitleSoLuongHoaDon);
		pnDoanhThuQuy_SoTienQuy.add(pnSoTienQuy);

		//========================================== Ô2
		txtTitleDoanhThu = new JTextPane();
		txtTitleDoanhThu.setText("Tổng doanh thu");
		txtTitleDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));	
		txtTitleDoanhThu.setForeground(Color.WHITE);
		StyledDocument documentStyle2 = txtTitleDoanhThu.getStyledDocument();
		SimpleAttributeSet centerAttribute2 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute2, StyleConstants.ALIGN_CENTER);
		documentStyle2.setParagraphAttributes(0, documentStyle2.getLength(), centerAttribute2, false);
		txtTitleDoanhThu.setFont(new Font("Arial", Font.BOLD, 16));

		lblSoTienNgay = new JLabel("? VND");
		lblSoTienNgay.setForeground(Color.WHITE);
		lblSoTienNgay.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnDoanhThuTuNgay_SoTienNgay = new JPanel();
		pnDoanhThuTuNgay_SoTienNgay.setPreferredSize(new Dimension(300,250));
		pnDoanhThuTuNgay_SoTienNgay.setLayout(new GridLayout(2,1));

		txtTitleDoanhThu.setBackground(new Color(51, 133, 255));
		JPanel pnSoTienNgay = new JPanel();
		pnSoTienNgay.setBackground(new Color(51, 133, 255));
		pnSoTienNgay.add(lblSoTienNgay);

		pnDoanhThuTuNgay_SoTienNgay.add(txtTitleDoanhThu);
		pnDoanhThuTuNgay_SoTienNgay.add(pnSoTienNgay);

		pnHang3.add(pnDoanhThuQuy_SoTienQuy);
		pnHang3.add(Box.createHorizontalStrut(40));
		pnHang3.add(pnDoanhThuTuNgay_SoTienNgay);

		JPanel pnHang4= new JPanel();
		pnHang4.setLayout(new FlowLayout(FlowLayout.CENTER));	

		//========================================== Ô3
		txtTitleSPChuaBan = new JTextPane();
		txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
		txtTitleSPChuaBan.setForeground(Color.WHITE);
		txtTitleSPChuaBan.setFont(new Font("Arial", Font.BOLD, 16));	
		StyledDocument documentStyle = txtTitleSPChuaBan.getStyledDocument();
		SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
		documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

		lblSoLuongSPChuaBan = new JLabel("? sản phẩm");
		lblSoLuongSPChuaBan.setForeground(Color.WHITE);
		lblSoLuongSPChuaBan.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnSanPhamQuy_SoLuongQuy = new JPanel();	
		pnSanPhamQuy_SoLuongQuy.setPreferredSize(new Dimension(300,250));
		pnSanPhamQuy_SoLuongQuy.setLayout(new GridLayout(2,1));

		txtTitleSPChuaBan.setBackground(new Color(255, 51, 51));
		JPanel pnSoLuongQuy = new JPanel();
		pnSoLuongQuy.setBackground(new Color(255, 51, 51));
		pnSoLuongQuy.add(lblSoLuongSPChuaBan);

		pnSanPhamQuy_SoLuongQuy.add(txtTitleSPChuaBan);
		pnSanPhamQuy_SoLuongQuy.add(pnSoLuongQuy);

		//========================================== Ô4
		txtTittleSanPhamTuNgay = new JTextPane();
		txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán");
		txtTittleSanPhamTuNgay.setFont(new Font("Arial", Font.BOLD, 16));	
		StyledDocument documentStyle4 = txtTittleSanPhamTuNgay.getStyledDocument();
		SimpleAttributeSet centerAttribute4 = new SimpleAttributeSet();
		StyleConstants.setAlignment(centerAttribute4, StyleConstants.ALIGN_CENTER);
		documentStyle4.setParagraphAttributes(0, documentStyle4.getLength(), centerAttribute4, false);


		lblSoLuongSPNgay = new JLabel("? sản phẩm");
		lblSoLuongSPNgay.setForeground(Color.WHITE);
		lblSoLuongSPNgay.setFont(new Font("Arial", Font.BOLD, 16));
		JPanel pnSoLuongTuNgay_SoLuongSPNgay = new JPanel();
		pnSoLuongTuNgay_SoLuongSPNgay.setPreferredSize(new Dimension(300,250));
		pnSoLuongTuNgay_SoLuongSPNgay.setLayout(new GridLayout(2,1));
		txtTittleSanPhamTuNgay.setForeground(Color.WHITE);
		txtTittleSanPhamTuNgay.setBackground(new Color(0, 179, 60));
		JPanel pnSoLuongSPNgay = new JPanel();
		pnSoLuongSPNgay.setBackground(new Color(0, 179, 60));
		pnSoLuongSPNgay.add(lblSoLuongSPNgay);

		pnSoLuongTuNgay_SoLuongSPNgay.add(txtTittleSanPhamTuNgay);
		pnSoLuongTuNgay_SoLuongSPNgay.add(pnSoLuongSPNgay);

		pnHang4.add(pnSanPhamQuy_SoLuongQuy);
		pnHang4.add(Box.createHorizontalStrut(40));
		pnHang4.add(pnSoLuongTuNgay_SoLuongSPNgay);

		pnCenNorth.add(pnHang3);
		pnCenNorth.add(pnHang4);
		pncen.add(pnCenNorth,BorderLayout.CENTER);


		JPanel pnSouth = new JPanel();
		pnSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(pnSouth,BorderLayout.SOUTH);

		JLabel lblXuatBaoCaoExcel = new JLabel("  Xuất báo cáo");
		lblXuatBaoCaoExcel.setFont(new Font("Arial", Font.BOLD, 16));
		lblXuatBaoCaoExcel.setForeground(Color.WHITE);
		JLabel lblImgXuatBaoCaoExcel = new JLabel(ResizeImage("src//Icon//xls.png"));
		btnXuatExcel = new JButton("");
		btnXuatExcel.setLayout(new BorderLayout());
		btnXuatExcel.add(lblImgXuatBaoCaoExcel,BorderLayout.WEST);
		btnXuatExcel.add(lblXuatBaoCaoExcel,BorderLayout.CENTER);
		btnXuatExcel.setBackground(new Color(255, 133, 51));
		btnXuatExcel.setForeground(Color.WHITE);
		btnXuatExcel.setPreferredSize(new Dimension(200,40));

		pnSouth.add(btnXuatExcel,BorderLayout.EAST);

		btnthongke.addActionListener(this);
		btnQuy1.addActionListener(this);
		btnQuy2.addActionListener(this);
		btnQuy3.addActionListener(this);
		btnQuy4.addActionListener(this);
		cbxChonNam.addActionListener(this);
		btnXuatExcel.addActionListener(this);




	}

	private Dimension Dimension(int i, int j) {
		// TODO Auto-generated method stub
		return null;
	}

	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public void mouseClicked(MouseEvent e) {
		Object Objecet= e.getSource();

	}

	private boolean KiemTraNhapLieu() {
		try {
			SimpleDateFormat dcn = new SimpleDateFormat("yyyy-MM-dd");
			String ngayBD= "", ngayKT="";
			ngayBD = dcn.format(jdbd.getDate() );
			ngayKT = dcn.format(jdkt.getDate());
			LocalDate ngayHienTai = LocalDate.now();
			LocalDate nbd = LocalDate.parse(ngayBD);
			LocalDate nkt = LocalDate.parse(ngayKT);

			if(nbd.compareTo(nkt)>0) {
				JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải bé hơn ngày kết thúc!");
				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày cần thống kê !");
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnthongke)) {
			if(KiemTraNhapLieu()) {

				DecimalFormat formatter = new DecimalFormat("###,###,###,###");
				SimpleDateFormat sdfSQL = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfIn = new SimpleDateFormat("dd-MM-yyyy");
				tuNgay = "";
				denNgay = "";
				tuNgay = sdfSQL.format(jdbd.getDate());
				denNgay= sdfSQL.format(jdkt.getDate());

				NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
				if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
					txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoLuongHoaDonTheoNgayChon(tuNgay,denNgay)) +" hóa đơn");

					txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoNgayChon(tuNgay,denNgay)) +" sản phẩm");

					txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
					lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

					txtTitleDoanhThu.setText("Tổng doanh thu từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoNgayChon(tuNgay,denNgay))) +" VND");

					txtTitleTopSP.setText("Top sản phẩm đã bán từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "+sdfIn.format(jdkt.getDate()));
					txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "
							+sdfIn.format(jdkt.getDate()));

					try {
						loadTK();
						loadTKHoaDonCCH();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				else {	//Của nhân viên
					txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoLuongHoaDonTheoNgayChonNV(tuNgay,denNgay,maNhanVien)) +" hóa đơn");

					txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					try {
						lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoNgayChonNhanVien(tuNgay,denNgay,maNhanVien)) +" sản phẩm");
					} catch (NumberFormatException e2) {
						e2.printStackTrace();
					}

					txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
					lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

					txtTitleDoanhThu.setText("Tổng doanh thu từ ngày " +sdfIn.format(jdbd.getDate()) + " đến ngày " +sdfIn.format(jdkt.getDate()));
					lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoNgayChonNV(tuNgay,denNgay,maNhanVien))) +" VND");

					txtTitleTopSP.setText("Top sản phẩm đã bán từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "+sdfIn.format(jdkt.getDate()));
					txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất từ ngày "+sdfIn.format(jdbd.getDate())+" đến ngày "+sdfIn.format(jdkt.getDate()));
					try {
						loadTKSanPhamNhanVien();
						loadTKHoaDonNV();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

		}
		if(o.equals(btnQuy3)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
			if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý III "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuy((String) cbxChonNam.getSelectedItem(),"07","08","09") +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuy((String) cbxChonNam.getSelectedItem(),"07","08","09") +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý III " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"07","08","09"))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());	
				txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất trong quý III năm " + (String) cbxChonNam.getSelectedItem());
				
				try {
					loadTKTheoQuy4("07","08","09");
					loadTKHoaDonCCHTheoQuy("07","08","09");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
//				try {
//					loadTKTheoQuy4("10","11","12");
//					loadTKHoaDonCCHTheoQuy("10","11","12");
//				} catch (SQLException e1) {
//					e1.printStackTrace();
//				}
			}
			else {	//CỦA nhân viên
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý III "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuyNV((String) cbxChonNam.getSelectedItem(),"7","8","9",maNhanVien) +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuyNV((String) cbxChonNam.getSelectedItem(),"7","8","9",maNhanVien) +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý III " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuyNV((String) cbxChonNam.getSelectedItem(),"7","8","9",maNhanVien))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý III năm " + (String) cbxChonNam.getSelectedItem());	
				txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất trong quý III năm " + (String) cbxChonNam.getSelectedItem());	
				try {
					loadTKTheoQuy4NV("7","8","9");
					loadTKHoaDonNVTheoQuy("7","8","9");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if(o.equals(btnQuy4)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
			if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý IV năm "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12") +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12") +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý IV năm " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"10","11","12"))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());		
				txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất  trong quý IV năm " + (String) cbxChonNam.getSelectedItem());

				try {
					loadTKTheoQuy4("10","11","12");
					loadTKHoaDonCCHTheoQuy("10","11","12");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			else { // của nhân viên
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý IV "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuyNV((String) cbxChonNam.getSelectedItem(),"10","11","12",maNhanVien) +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuyNV((String) cbxChonNam.getSelectedItem(),"10","11","12",maNhanVien) +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý IV " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuyNV((String) cbxChonNam.getSelectedItem(),"10","11","12",maNhanVien))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý IV năm " + (String) cbxChonNam.getSelectedItem());		
				txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất trong quý IV năm " + (String) cbxChonNam.getSelectedItem());	
				try {
					loadTKTheoQuy4NV("10","11","12");
					loadTKHoaDonNVTheoQuy("10","11","12");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}

		if(o.equals(btnQuy2)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
			if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý II "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6") +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6") +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý II " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"4","5","6"))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
				txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất trong quý II năm " + (String) cbxChonNam.getSelectedItem());
				try {
					loadTKTheoQuy4("4","5","6");
					loadTKHoaDonCCHTheoQuy("4","5","6");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {

				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý II "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuyNV((String) cbxChonNam.getSelectedItem(),"4","5","6",maNhanVien) +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuyNV((String) cbxChonNam.getSelectedItem(),"4","5","6",maNhanVien) +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý II " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuyNV((String) cbxChonNam.getSelectedItem(),"4","5","6",maNhanVien))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý II năm " + (String) cbxChonNam.getSelectedItem());
				txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất trong quý II năm " + (String) cbxChonNam.getSelectedItem());	
				try {
					loadTKTheoQuy4NV("4","5","6");
					loadTKHoaDonNVTheoQuy("4","5","6");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();

				}
			}
		}
		if(o.equals(btnQuy1)) {
			DecimalFormat formatter = new DecimalFormat("###,###,###,###");

			NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
			if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý I "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3") +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý I năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3") +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý I " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuy((String) cbxChonNam.getSelectedItem(),"1","2","3"))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý I năm " + (String) cbxChonNam.getSelectedItem());
				txtTitleTopNhanVien.setText("Top nhân viên có doanh thu cao nhất trong quý I năm " + (String) cbxChonNam.getSelectedItem());
				try {
					loadTKTheoQuy4("1","2","3");
					loadTKHoaDonCCHTheoQuy("1","2","3");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else {
				txtTitleSoLuongHoaDon.setText("Tổng số lượng hóa đơn đã xuất trong quý I "+ (String) cbxChonNam.getSelectedItem());
				lblSoLuongHoaDon.setText(String.valueOf(daoThongKe.SoHoaDonTheoQuyNV((String) cbxChonNam.getSelectedItem(),"1","2","3",maNhanVien) +" hóa đơn"));

				txtTittleSanPhamTuNgay.setText("Tổng số lượng sản phẩm đã bán trong quý I năm " + (String) cbxChonNam.getSelectedItem());
				lblSoLuongSPNgay.setText(String.valueOf(daoThongKe.SoLuongSanPhamTheoQuyNV((String) cbxChonNam.getSelectedItem(),"1","2","3",maNhanVien) +" sản phẩm"));

				txtTitleSPChuaBan.setText("Số lượng sản phẩm chưa từng bán");
				lblSoLuongSPChuaBan.setText(String.valueOf(daoThongKe.SoLuongSPChuaBan()) +" sản phẩm");

				txtTitleDoanhThu.setText("Tổng doanh thu trong quý I " +  (String) cbxChonNam.getSelectedItem());
				lblSoTienNgay.setText(String.valueOf(formatter.format(daoThongKe.DoanhThuTheoQuyNV((String) cbxChonNam.getSelectedItem(),"1","2","3",maNhanVien))+" VND"));

				txtTitleTopSP.setText("Top sản phẩm đã bán trong quý I năm " + (String) cbxChonNam.getSelectedItem());
				txtTitleTopHoaDon.setText("Top hóa đơn có doanh thu cao nhất trong quý I năm " + (String) cbxChonNam.getSelectedItem());	
				try {
					loadTKTheoQuy4NV("1","2","3");
					loadTKHoaDonNVTheoQuy("1","2","3");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		if (o.equals(btnXuatExcel)) {
			JFileChooser excelFileChooser = new JFileChooser("D:\\");
			excelFileChooser.setDialogTitle("Save As");
			FileNameExtensionFilter fnef = new FileNameExtensionFilter("EXCEL FILES", "xls","xlsx","xlsm");
			excelFileChooser.setFileFilter(fnef);
			int excelChooser = excelFileChooser.showSaveDialog(null);

			if(excelChooser== JFileChooser.APPROVE_OPTION) {			
				workbook = new HSSFWorkbook();

				NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
				if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
					HSSFSheet sheetTopSP = workbook.createSheet("Top sản phẩm đã bán" );
					int rownumTopSP = 2;
					int i = 1; 
					Cell cell;
					Row row;	
					CellStyle cellStyle = createStyleForHeader(sheetTopSP);
					
					org.apache.poi.ss.usermodel.Font fontTieuDe = sheetTopSP.getWorkbook().createFont();
					fontTieuDe.setFontName("Times New Roman"); 
					fontTieuDe.setBold(true);
					fontTieuDe.setFontHeightInPoints((short) 16); // font size
					fontTieuDe.setColor(IndexedColors.BLACK.getIndex()); // text color
					CellStyle cellStyleTieuDe = sheetTopSP.getWorkbook().createCellStyle();
					cellStyleTieuDe.setFont(fontTieuDe);
					row = sheetTopSP.createRow(0);
					cell = row.createCell(0, CellType.STRING);
					cell.setCellValue(""+ txtTitleTopSP.getText());
					cell.setCellStyle(cellStyleTieuDe);
					
					row = sheetTopSP.createRow(1);
					cell = row.createCell(0, CellType.STRING);
					cell.setCellValue("Top");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(1, CellType.STRING);
					cell.setCellValue("Tên sản phẩm");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("Số lượng đã bán");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(3, CellType.STRING);
					cell.setCellValue("Số lượng còn lại");
					cell.setCellStyle(cellStyle);		
					sheetTopSP.setColumnWidth(0, 1500);
					sheetTopSP.setColumnWidth(1, 10500);
					sheetTopSP.setColumnWidth(2, 5000);
					sheetTopSP.setColumnWidth(3, 5000);
					sheetTopSP.autoSizeColumn((short)2);
					for(SanPham sp : dsSanPham) {
						for(Sach s : listSach)
						{
							if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
								row = sheetTopSP.createRow(rownumTopSP);
								cell = row.createCell(0, CellType.STRING);
								cell.setCellValue(i);
								cell = row.createCell(1, CellType.STRING);
								cell.setCellValue(sp.getTenSanPham());
								cell = row.createCell(2, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								cell = row.createCell(3, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								rownumTopSP++;
								i++;
							}
						}
						for(DungCuHocTap dcht : listDCHT)
						{
							if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
								row = sheetTopSP.createRow(rownumTopSP);
								cell = row.createCell(0, CellType.STRING);
								cell.setCellValue(i);
								cell = row.createCell(1, CellType.STRING);
								cell.setCellValue(dcht.getTenSanPham());
								cell = row.createCell(2, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								cell = row.createCell(3, CellType.STRING);
								cell.setCellValue(dcht.getSoLuong());
								rownumTopSP++;
								i++;					
							}
						}
					}
					
					org.apache.poi.ss.usermodel.Font fontTongKet = sheetTopSP.getWorkbook().createFont();
					fontTongKet.setFontName("Times New Roman"); 
					fontTongKet.setBold(true);
					fontTongKet.setFontHeightInPoints((short) 11); // font size
					fontTongKet.setColor(IndexedColors.BLACK.getIndex()); // text color
					CellStyle cellStyleTongKet = sheetTopSP.getWorkbook().createCellStyle();
					cellStyleTongKet.setFont(fontTongKet);
					
					rownumTopSP-=1;
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("");
					
					
					rownumTopSP+=1;					
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTitleSoLuongHoaDon.getText() +": " +lblSoLuongHoaDon.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;					
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTittleSanPhamTuNgay.getText() +": " +lblSoLuongSPNgay.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;				
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTitleDoanhThu.getText() +": " +lblSoTienNgay.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;				
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("");
					
					
					for (int j = 0; j < 35; j++) {
						row = sheetTopSP.createRow(rownumTopSP);
						cell = row.createCell(0, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(1, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(2, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(3, CellType.STRING);
						cell.setCellValue("");
						rownumTopSP++;
					}
					
										
					//Top nhân viên
					HSSFSheet sheetTopNV = workbook.createSheet("Top nhân viên");
					int j=1;
					int rownumTopNV = 2;	
					Cell cellTopNV;
					Row rowTopNV;	
					CellStyle cellStyleTopNV = createStyleForHeader(sheetTopNV);
					rowTopNV = sheetTopNV.createRow(0);
					cellTopNV = rowTopNV.createCell(0, CellType.STRING);
					cellTopNV.setCellValue(""+ txtTitleTopNhanVien.getText());

					rowTopNV = sheetTopNV.createRow(1);
					cellTopNV = rowTopNV.createCell(0, CellType.STRING);
					cellTopNV.setCellValue("Top");
					cellTopNV.setCellStyle(cellStyleTopNV);
					cellTopNV = rowTopNV.createCell(1, CellType.STRING);
					cellTopNV.setCellValue("Mã nhân viên");
					cellTopNV.setCellStyle(cellStyleTopNV);
					cellTopNV = rowTopNV.createCell(2, CellType.STRING);
					cellTopNV.setCellValue("Tên nhân viên");
					cellTopNV.setCellStyle(cellStyleTopNV);
					cellTopNV = rowTopNV.createCell(3, CellType.STRING);
					cellTopNV.setCellValue("Tổng tiền");
					cellTopNV.setCellStyle(cellStyleTopNV);		
					sheetTopNV.setColumnWidth(0, 1500);
					sheetTopNV.setColumnWidth(1, 5000);
					sheetTopNV.setColumnWidth(2, 10500);
					sheetTopNV.setColumnWidth(3, 5000);
					//sheetTopNV.autoSizeColumn((short)2);
					for(HoaDon hd : listHoaDon)
					{
						for(NhanVien nv : listNV)
						{
							if(nv.getMaNV().equals(hd.getNhanvien().getMaNV()) ) {
								rowTopNV = sheetTopNV.createRow(rownumTopNV);

								cellTopNV = rowTopNV.createCell(0, CellType.STRING);
								cellTopNV.setCellValue(j);

								cellTopNV = rowTopNV.createCell(1, CellType.STRING);
								cellTopNV.setCellValue(hd.getNhanvien().getMaNV());

								cellTopNV = rowTopNV.createCell(2, CellType.STRING);
								cellTopNV.setCellValue(nv.getTenNV());

								cellTopNV = rowTopNV.createCell(3, CellType.STRING);
								cellTopNV.setCellValue(hd.getTongTien());

								rownumTopNV++;
								j++;
							}
						}
					}

				}
				else {		//Nếu là nhân viên thì export hóa đơn
					System.out.println("Là nhân viên");
					HSSFSheet sheetTopSP = workbook.createSheet("Top sản phẩm đã bán" );
					int rownumTopSP = 2;
					int i = 1; 
					Cell cell;
					Row row;	
					CellStyle cellStyle = createStyleForHeader(sheetTopSP);
					row = sheetTopSP.createRow(0);
					cell = row.createCell(0, CellType.STRING);
					cell.setCellValue(""+ txtTitleTopSP.getText());
					row = sheetTopSP.createRow(1);
					cell = row.createCell(0, CellType.STRING);
					cell.setCellValue("Top");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(1, CellType.STRING);
					cell.setCellValue("Tên sản phẩm");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("Số lượng đã bán");
					cell.setCellStyle(cellStyle);
					cell = row.createCell(3, CellType.STRING);
					cell.setCellValue("Số lượng còn lại");
					cell.setCellStyle(cellStyle);		
					sheetTopSP.setColumnWidth(0, 1500);
					sheetTopSP.setColumnWidth(1, 10500);
					sheetTopSP.setColumnWidth(2, 5000);
					sheetTopSP.setColumnWidth(3, 5000);
					sheetTopSP.autoSizeColumn((short)2);
					for(SanPham sp : dsSanPham) {
						for(Sach s : listSach)
						{
							if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
								row = sheetTopSP.createRow(rownumTopSP);
								cell = row.createCell(0, CellType.STRING);
								cell.setCellValue(i);
								cell = row.createCell(1, CellType.STRING);
								cell.setCellValue(sp.getTenSanPham());
								cell = row.createCell(2, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								cell = row.createCell(3, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								rownumTopSP++;
								i++;
							}
						}
						for(DungCuHocTap dcht : listDCHT)
						{
							if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
								row = sheetTopSP.createRow(rownumTopSP);
								cell = row.createCell(0, CellType.STRING);
								cell.setCellValue(i);
								cell = row.createCell(1, CellType.STRING);
								cell.setCellValue(dcht.getTenSanPham());
								cell = row.createCell(2, CellType.STRING);
								cell.setCellValue(sp.getSoLuong());
								cell = row.createCell(3, CellType.STRING);
								cell.setCellValue(dcht.getSoLuong());
								rownumTopSP++;
								i++;					
							}
						}
					}
					
					org.apache.poi.ss.usermodel.Font fontTongKet = sheetTopSP.getWorkbook().createFont();
					fontTongKet.setFontName("Times New Roman"); 
					fontTongKet.setBold(true);
					fontTongKet.setFontHeightInPoints((short) 11); // font size
					fontTongKet.setColor(IndexedColors.BLACK.getIndex()); // text color
					CellStyle cellStyleTongKet = sheetTopSP.getWorkbook().createCellStyle();
					cellStyleTongKet.setFont(fontTongKet);
					
					rownumTopSP-=1;
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("");
					
					
					rownumTopSP+=1;					
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTitleSoLuongHoaDon.getText() +": " +lblSoLuongHoaDon.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;					
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTittleSanPhamTuNgay.getText() +": " +lblSoLuongSPNgay.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;				
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue(txtTitleDoanhThu.getText() +": " +lblSoTienNgay.getText());
					cell.setCellStyle(cellStyleTongKet);
					
					rownumTopSP+=1;				
					row = sheetTopSP.createRow(rownumTopSP);
					cell = row.createCell(2, CellType.STRING);
					cell.setCellValue("");
					
					for (int j = 0; j < 35; j++) {
						row = sheetTopSP.createRow(rownumTopSP);
						cell = row.createCell(0, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(1, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(2, CellType.STRING);
						cell.setCellValue("");
						cell = row.createCell(3, CellType.STRING);
						cell.setCellValue("");
						rownumTopSP++;
					}
					//Top hóa đơn
					HSSFSheet sheetTopHD = workbook.createSheet("Top hóa đơn");
					int j=1;
					int rownumTopHD = 2;	
					Cell cellTopHD;
					Row rowTopHD;	
					CellStyle cellStyleTopHD = createStyleForHeader(sheetTopHD);
					rowTopHD = sheetTopHD.createRow(0);
					cellTopHD = rowTopHD.createCell(0, CellType.STRING);
					cellTopHD.setCellValue(""+ txtTitleTopHoaDon.getText());

					rowTopHD = sheetTopHD.createRow(1);
					cellTopHD = rowTopHD.createCell(0, CellType.STRING);
					cellTopHD.setCellValue("Top");
					cellTopHD.setCellStyle(cellStyleTopHD);
					cellTopHD = rowTopHD.createCell(1, CellType.STRING);
					cellTopHD.setCellValue("Mã hóa đơn");
					cellTopHD.setCellStyle(cellStyleTopHD);
					cellTopHD = rowTopHD.createCell(2, CellType.STRING);
					cellTopHD.setCellValue("Tổng tiền");
					cellTopHD.setCellStyle(cellStyleTopHD);
					cellTopHD = rowTopHD.createCell(3, CellType.STRING);
					cellTopHD.setCellValue("Tên khách hàng");
					cellTopHD.setCellStyle(cellStyleTopHD);		
					sheetTopHD.setColumnWidth(0, 1500);
					sheetTopHD.setColumnWidth(1, 5000);
					sheetTopHD.setColumnWidth(2, 5000);
					sheetTopHD.setColumnWidth(3, 10500);
					sheetTopHD.autoSizeColumn((short)2);
					System.out.println(listHoaDon);
					for(HoaDon hd : listHoaDon)
					{
						for(KhachHang kh : listKH)
						{
							if(kh.getMaKH().equals(hd.getKhachHang().getMaKH()) ) {
								rowTopHD = sheetTopHD.createRow(rownumTopHD);

								cellTopHD = rowTopHD.createCell(0, CellType.STRING);
								cellTopHD.setCellValue(j);

								cellTopHD = rowTopHD.createCell(1, CellType.STRING);
								cellTopHD.setCellValue(hd.getMaHoaDon());

								cellTopHD = rowTopHD.createCell(2, CellType.STRING);
								cellTopHD.setCellValue(hd.getTongTien());

								cellTopHD = rowTopHD.createCell(3, CellType.STRING);
								cellTopHD.setCellValue(kh.getTenKH());

								rownumTopHD++;
								j++;
							}
						}
					}
				}

				//=======

				FileOutputStream excelFOU = null;
				BufferedOutputStream excelBOU = null;
				try {
					excelFOU = new FileOutputStream(excelFileChooser.getSelectedFile()+".xls");
					excelBOU = new BufferedOutputStream(excelFOU);
					workbook.write(excelBOU);
					JOptionPane.showMessageDialog(null,	"Xuất báo cáo thành công");
				} catch (FileNotFoundException e2) {
					e2.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				finally {
					try {
						if(excelFOU != null) {
							excelFOU.close();
						}
						if(excelBOU != null) {
							excelFOU.close();
						}
						if(workbook != null) {
							workbook.close();
						}
					} catch (IOException e3) {
						// TODO: handle exception
					}
				}


			}

		}
	}
	//============================Load TABLE
	/**
	 * Load tabel thống kê của chủ cửa hàng theo ngày
	 * @throws SQLException
	 */
	public void loadTK() throws SQLException {
		modeltable.setRowCount(0);
		table.removeAll();
		listCTHDSACH	= daoThongKe.thongKeSanPhamTheoNgay(tuNgay,denNgay);
		listCTHDDCHT	= daoThongKe.thongKeDCHTTheoNgay(tuNgay,denNgay);
		listHoaDon	= (ArrayList<HoaDon>) daoHoaDon.getAllHoaDon();
		listSach	=  daoSach.getAllTbSach();
		listDCHT	=  daoDungCuHocTap.getAllTbDCHT();
		dsSanPham = new ArrayList<SanPham>();

		for(ChiTietHoaDonSach cthds : listCTHDSACH) {
			SanPham sp = new SanPham();

			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(cthds.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthds.getSanPham().getMaSanPham(),s.getTenSanPham(),cthds.getSoLuong());

				}
			}
			dsSanPham.add(sp);

		}
		for(ChiTietHoaDonDCHT cthdDCHT : listCTHDDCHT) {
			SanPham sp = new SanPham();
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(cthdDCHT.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthdDCHT.getSanPham().getMaSanPham(),dcht.getTenSanPham(),cthdDCHT.getSoLuong());
				}
			}
			dsSanPham.add(sp);
		}
		dsSanPham.sort(((o2, o1) -> Integer.compare(o1.getSoLuong(), o2.getSoLuong())));

		int i = 1;
		for(SanPham sp : dsSanPham) {
			//modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong()});
			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), s.getSoLuong()});

				}
			}
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), dcht.getSoLuong()});

				}
			}
		}
	}
	/**
	 * Load thống kê table sản phẩm của nhân viên theo ngày
	 * @throws SQLException
	 */
	public void loadTKSanPhamNhanVien() throws SQLException {
		modeltable.setRowCount(0);
		table.removeAll();
		listCTHDSACH	= daoThongKe.thongKeSanPhamTheoNgayNhanVien(tuNgay,denNgay,maNhanVien);
		listCTHDDCHT	= daoThongKe.thongKeDCHTTheoNgayNhanVien(tuNgay,denNgay,maNhanVien);
		listHoaDon	= (ArrayList<HoaDon>) daoHoaDon.getAllHoaDon();
		listSach	=  daoSach.getAllTbSach();
		listDCHT	=  daoDungCuHocTap.getAllTbDCHT();
		dsSanPham = new ArrayList<SanPham>();

		for(ChiTietHoaDonSach cthds : listCTHDSACH) {
			SanPham sp = new SanPham();

			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(cthds.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthds.getSanPham().getMaSanPham(),s.getTenSanPham(),cthds.getSoLuong());

				}
			}
			dsSanPham.add(sp);

		}
		for(ChiTietHoaDonDCHT cthdDCHT : listCTHDDCHT) {
			SanPham sp = new SanPham();
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(cthdDCHT.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthdDCHT.getSanPham().getMaSanPham(),dcht.getTenSanPham(),cthdDCHT.getSoLuong());
				}
			}
			dsSanPham.add(sp);
		}
		dsSanPham.sort(((o2, o1) -> Integer.compare(o1.getSoLuong(), o2.getSoLuong())));

		int i = 1;
		for(SanPham sp : dsSanPham) {
			//modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong()});
			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), s.getSoLuong()});

				}
			}
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), dcht.getSoLuong()});

				}
			}
		}
	}


	/**
	 * Load Thong Ke Sản phẩm theo quý của chủ cửa hàng
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @throws SQLException
	 */
	public void loadTKTheoQuy4(String thang1, String thang2, String thang3) throws SQLException {
		modeltable.setRowCount(0);
		table.removeAll();
		listCTHDSACH	= daoThongKe.thongKeSanPhamTheoQuy4((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3);
		listCTHDDCHT	= daoThongKe.thongKeDCHTTheoQuy((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3);
		listHoaDon	= (ArrayList<HoaDon>) daoHoaDon.getAllHoaDon();
		listSach	=  daoSach.getAllTbSach();
		listDCHT	=  daoDungCuHocTap.getAllTbDCHT();
		dsSanPham = new ArrayList<SanPham>();

		for(ChiTietHoaDonSach cthds : listCTHDSACH) {
			SanPham sp = new SanPham();

			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(cthds.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthds.getSanPham().getMaSanPham(),s.getTenSanPham(),cthds.getSoLuong());

				}
			}
			dsSanPham.add(sp);

		}
		for(ChiTietHoaDonDCHT cthdDCHT : listCTHDDCHT) {
			SanPham sp = new SanPham();
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(cthdDCHT.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthdDCHT.getSanPham().getMaSanPham(),dcht.getTenSanPham(),cthdDCHT.getSoLuong());
				}
			}
			dsSanPham.add(sp);
		}
		dsSanPham.sort(((o2, o1) -> Integer.compare(o1.getSoLuong(), o2.getSoLuong())));

		int i = 1;
		for(SanPham sp : dsSanPham) {
			//modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong()});
			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), s.getSoLuong()});

				}
			}
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), dcht.getSoLuong()});

				}
			}
		}
	}

	/**
	 * Load Thong Ke Sản phẩm theo quý của nhân viên
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @throws SQLException
	 */
	public void loadTKTheoQuy4NV(String thang1, String thang2, String thang3) throws SQLException {
		modeltable.setRowCount(0);
		table.removeAll();
		listCTHDSACH	= daoThongKe.thongKeSanPhamTheoQuy4NV((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3,maNhanVien);
		listCTHDDCHT	= daoThongKe.thongKeDCHTTheoQuyNV((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3,maNhanVien);
		listHoaDon	= (ArrayList<HoaDon>) daoHoaDon.getAllHoaDon();
		listSach	=  daoSach.getAllTbSach();
		listDCHT	=  daoDungCuHocTap.getAllTbDCHT();
		dsSanPham = new ArrayList<SanPham>();

		for(ChiTietHoaDonSach cthds : listCTHDSACH) {
			SanPham sp = new SanPham();

			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(cthds.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthds.getSanPham().getMaSanPham(),s.getTenSanPham(),cthds.getSoLuong());

				}
			}
			dsSanPham.add(sp);

		}
		for(ChiTietHoaDonDCHT cthdDCHT : listCTHDDCHT) {
			SanPham sp = new SanPham();
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(cthdDCHT.getSanPham().getMaSanPham()) ) {
					sp = new SanPham(cthdDCHT.getSanPham().getMaSanPham(),dcht.getTenSanPham(),cthdDCHT.getSoLuong());
				}
			}
			dsSanPham.add(sp);
		}
		dsSanPham.sort(((o2, o1) -> Integer.compare(o1.getSoLuong(), o2.getSoLuong())));

		int i = 1;
		for(SanPham sp : dsSanPham) {
			//modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong()});
			for(Sach s : listSach)
			{
				if(s.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), s.getSoLuong()});

				}
			}
			for(DungCuHocTap dcht : listDCHT)
			{
				if(dcht.getMaSanPham().equals(sp.getMaSanPham()) ) {
					modeltable.addRow(new Object[] {i++,sp.getTenSanPham(),sp.getSoLuong(), dcht.getSoLuong()});

				}
			}
		}
	}

	/**
	 * Load thống kê nhân viên của chủ cửa hàng theo ngày
	 * @throws SQLException
	 */
	public void loadTKHoaDonCCH() throws SQLException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltableHoaDon.setRowCount(0);
		tableHoaDon.removeAll();
		listHoaDon	= (ArrayList<HoaDon>) daoThongKe.thongKeHoaDonCCHTheoNgay(tuNgay,denNgay);
		listNV = nhanVien_dao.layHetNhanVien();	
		int i = 1;
		for(HoaDon hd : listHoaDon)
		{
			for(NhanVien nv : listNV)
			{
				if(nv.getMaNV().equals(hd.getNhanvien().getMaNV()) ) {
					modeltableHoaDon.addRow(new Object[] {i++,hd.getNhanvien().getMaNV(),nv.getTenNV(),String.valueOf(formatter.format(hd.getTongTien())+" VND")});
				}
			}
		}
	}

	/**
	 * Load thống kê hóa đơn của nhân viên theo ngày
	 * @throws SQLException
	 */
	public void loadTKHoaDonNV() throws SQLException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltableHoaDonNhanVien.setRowCount(0);
		tableHoaDonNhanVien.removeAll();
		listHoaDon	= (ArrayList<HoaDon>) daoThongKe.thongKeHoaDonNVTheoNgay(tuNgay,denNgay,maNhanVien);
		listKH = khachHang_dao.layHetKhachHang();	
		int i = 1;
		for(HoaDon hd : listHoaDon)
		{
			for(KhachHang kh : listKH)
			{
				if(kh.getMaKH().equals(hd.getKhachHang().getMaKH()) ) {
					modeltableHoaDonNhanVien.addRow(new Object[] {i++,hd.getMaHoaDon(),hd.getNgayLapHoaDon(),String.valueOf(formatter.format(hd.getTongTien())+" VND"),kh.getTenKH()});
				}
			}
		}
	}

	/**
	 * Load thống kê hóa đơn của chủ cửa hàng theo quý
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @throws SQLException
	 */
	public void loadTKHoaDonCCHTheoQuy(String thang1, String thang2, String thang3) throws SQLException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltableHoaDon.setRowCount(0);
		tableHoaDon.removeAll();
		listHoaDon	= (ArrayList<HoaDon>) daoThongKe.thongKeHoaDonCCHTheoQuy((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3);
		listNV = nhanVien_dao.layHetNhanVien();	
		int i = 1;
		for(HoaDon hd : listHoaDon)
		{
			for(NhanVien nv : listNV)
			{
				if(nv.getMaNV().equals(hd.getNhanvien().getMaNV()) ) {
					modeltableHoaDon.addRow(new Object[] {i++,hd.getNhanvien().getMaNV(),nv.getTenNV(),String.valueOf(formatter.format(hd.getTongTien())+" VND")});
				}
			}
		}
	}

	/**
	 * Load thống kê hóa đơn của nhân viên theo quý
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @throws SQLException
	 */
	public void loadTKHoaDonNVTheoQuy(String thang1, String thang2, String thang3) throws SQLException {
		DecimalFormat formatter = new DecimalFormat("###,###,###,###");
		modeltableHoaDonNhanVien.setRowCount(0);
		tableHoaDonNhanVien.removeAll();
		listHoaDon	= (ArrayList<HoaDon>) daoThongKe.thongKeHoaDonNVTheoQuy((String) cbxChonNam.getSelectedItem(),thang1,thang2,thang3,maNhanVien);
		listKH = khachHang_dao.layHetKhachHang();	
		int i = 1;
		for(HoaDon hd : listHoaDon)
		{
			for(KhachHang kh : listKH)
			{
				if(kh.getMaKH().equals(hd.getKhachHang().getMaKH()) ) {
					modeltableHoaDonNhanVien.addRow(new Object[] {i++,hd.getMaHoaDon(),hd.getNgayLapHoaDon(),String.valueOf(formatter.format(hd.getTongTien())+" VND"),kh.getTenKH()});
				}
			}
		}
	}
	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
	}
	// Create CellStyle for header
	private static CellStyle createStyleForHeader(Sheet sheet) {
		// Create font
		org.apache.poi.ss.usermodel.Font font = sheet.getWorkbook().createFont();
		font.setFontName("Times New Roman"); 
		font.setBold(true);
		font.setFontHeightInPoints((short) 14); // font size
		font.setColor(IndexedColors.WHITE.getIndex()); // text color


		// Create CellStyle
		CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cellStyle.setBorderBottom(BorderStyle.THIN);


		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		return cellStyle;
	}

	private static void autosizeColumn(Sheet sheet, int lastColumn) {
		for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}
}