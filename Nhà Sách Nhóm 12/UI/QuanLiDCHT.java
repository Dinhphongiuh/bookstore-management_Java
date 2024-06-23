package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
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
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import connectDB.ConnectDB;
import dao.DAO_Sach;
import dao.DAO_DungCuHocTap;
import dao.DAO_LoaiDCHT;
import dao.DAO_LoaiSach;
import entity.DungCuHocTap;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;
import entity.SanPham;
import others.KeyTextField;
import others.WrapLayout;


public class QuanLiDCHT extends JPanel implements ActionListener,MouseListener, KeyListener{
	public static QuanLiDCHT qldcht;

	private static DAO_Sach sachDao;
	private static ArrayList<Sach> listSach;
	private DAO_LoaiSach daoLoaiSach;
	private ArrayList<LoaiSach> listLoaiSach;
	private static DAO_DungCuHocTap daoDCHT;
	private static ArrayList<DungCuHocTap> listDCHT;
	private DAO_LoaiDCHT daoLoaiDCHT;
	private ArrayList<LoaiDungCuHocTap> listLoaiDCHT;

	private static JPanel pnCenterSach;

	private static JPanel pnCenterDCHT;
	private JButton btnThemSach, btnThemDCHT, btnThemExcel, btnThemDCHTExcel;
	private JTextField txtTim, txtTimDCHT;
	private JButton btnTim, btnTimDCHT;
	private JPanel pnTT;

	private static JPanel pnDSS;

	private static JPanel pnDSDCHT;
	private JLabel lblHinhAnh;
	private JButton  btnVanHoc, btnKinhTe,  btnSachThieuNhi,  btnGiaoKhoa, btnNgoaiNgu, btnTamLy, btnBut, btnThuoc;
	private JLabel lblVanHoc, lblKinhTe, lblSachThieuNhi, lblGiaoKhoa, lblNgoaiNgu, lblTamLy, lblKhoaHocKyThuat, lblNuCong, lblAmNhac, lblBaoChi,
	lblBut, lblThuoc, lblGom, lblCompa, lblHopBut, lblTapVo;
	public static JComboBox  cbxNhaXB, cbxLocThuongHieu, cbxXuatXu;
	public JComboBox cbxTinhTrang , cbxSapXepGiaSLT, cbxTinhTrangDCHT, cbxSapXepGiaDCHT;
	public static String hinhAnh="                       Chưa có hình ảnh";
	public static String maSach = "";
	public static String maDCHT = "";
	
	public QuanLiDCHT() 
	{

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		sachDao = new DAO_Sach();
		daoLoaiSach = new DAO_LoaiSach();
		daoDCHT = new DAO_DungCuHocTap();
		daoLoaiDCHT = new DAO_LoaiDCHT();
		qldcht = this;

		setSize(new Dimension(1000, 1000));
		setBackground(new Color(255, 255, 255));
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setLayout(new BorderLayout());
		JTextField txtNhap = new JTextField(15);
		pnCenterSach = new JPanel();
		pnCenterDCHT = new JPanel();

		//Vói sách
		//pnCenterSach.setBackground(new Color(230, 247, 255));
		pnCenterSach.setLayout(new BorderLayout());
		pnCenterDCHT.setLayout(new BorderLayout());

		JPanel pnLocThemSach = new JPanel();
		pnLocThemSach.setLayout(new BoxLayout(pnLocThemSach, BoxLayout.Y_AXIS));
		pnLocThemSach.setBackground(new Color(255, 255, 255));
		
		//Khoảng cách sách
		JPanel pnKhoangCach = new JPanel();	
		pnKhoangCach.setLayout(new BoxLayout(pnKhoangCach, BoxLayout.Y_AXIS));
		pnKhoangCach.setBackground(new Color(255, 255, 255));
		pnKhoangCach.add(Box.createVerticalStrut(15));

		JPanel pnKhoangCach1 = new JPanel();
		pnKhoangCach1.setLayout(new BoxLayout(pnKhoangCach1, BoxLayout.Y_AXIS));
		pnKhoangCach1.setBackground(new Color(255, 255, 255));
		pnKhoangCach1.add(Box.createVerticalStrut(8));


		pnDSS = new JPanel();
		pnDSS.setLayout(new WrapLayout());
		pnDSS.setBackground(new Color(255, 255, 255));

		//Lọc sách theo thể loại
		CompoundBorder cbTuaDeLoc = new CompoundBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK), new EmptyBorder(0,35,0,0));
		JPanel pnGRTitleLocTheLoai = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnGRTitleLocTheLoai.setBackground(new Color(255, 255, 255));
		pnGRTitleLocTheLoai.setBorder(new EmptyBorder(0,35,-5,0));
		JPanel pnTitleLocTheLoai = new JPanel();

		pnTitleLocTheLoai.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnTitleLocTheLoai.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnTitleLocTheLoai.setBackground(new Color(255, 255, 255));
		ImageIcon imgSearch = new ImageIcon("src//Icon//find.png");
		JLabel lblIconSearch = new JLabel(imgSearch);
		JLabel lblTuaDeLoc = new JLabel("Chọn thể loại sách cần tìm");
		JLabel lblKhoangCachBorder = new JLabel("                      "
				+ "                                                                                       ");
		pnTitleLocTheLoai.add(lblTuaDeLoc);
		pnTitleLocTheLoai.add(lblIconSearch);
		pnTitleLocTheLoai.add(lblKhoangCachBorder);

		pnGRTitleLocTheLoai.add(pnTitleLocTheLoai);

		JPanel pnLocSachTheLoai = new JPanel();
		pnLocSachTheLoai.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnLocSachTheLoai.setBorder(new EmptyBorder(0, 26, 0, 0));
		pnLocSachTheLoai.setBackground(new Color(255, 255, 255));
		CompoundBorder cb = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 255, 255)), new EmptyBorder(6,10,6,10));

		lblVanHoc = new JLabel("Văn Học");
		lblVanHoc.setFont(new Font("Arial", Font.BOLD, 14));
		lblVanHoc.setBorder(cb);
		lblKinhTe = new JLabel("Kinh Tế");
		lblKinhTe.setFont(new Font("Arial", Font.BOLD, 14));
		lblKinhTe.setBorder(cb);
		lblSachThieuNhi = new JLabel("Sách Thiếu Nhi");
		lblSachThieuNhi.setFont(new Font("Arial", Font.BOLD, 14));
		lblSachThieuNhi.setBorder(cb);
		lblGiaoKhoa = new JLabel("Sách Giáo Khoa");
		lblGiaoKhoa.setFont(new Font("Arial", Font.BOLD, 14));
		lblGiaoKhoa.setBorder(cb);
		lblNgoaiNgu = new JLabel("Ngoại Ngữ");
		lblNgoaiNgu.setFont(new Font("Arial", Font.BOLD, 14));
		lblNgoaiNgu.setBorder(cb);
		lblTamLy = new JLabel("Tâm Lý");
		lblTamLy.setFont(new Font("Arial", Font.BOLD, 14));	
		lblTamLy.setBorder(cb);
		lblKhoaHocKyThuat = new JLabel("Khoa Học - Kỹ Thuật");
		lblKhoaHocKyThuat.setFont(new Font("Arial", Font.BOLD, 14));
		lblKhoaHocKyThuat.setBorder(cb);
		lblNuCong = new JLabel("Dạy Nấu Ăn");
		lblNuCong.setFont(new Font("Arial", Font.BOLD, 14));
		lblNuCong.setBorder(cb);
		lblAmNhac = new JLabel("Âm Nhạc - Mỹ Thuật - Thời Trang");
		lblAmNhac.setFont(new Font("Arial", Font.BOLD, 14));
		lblAmNhac.setBorder(cb);
		lblBaoChi = new JLabel("Báo - Tạp Chí");
		lblBaoChi.setFont(new Font("Arial", Font.BOLD, 14));
		lblBaoChi.setBorder(cb);

		pnLocSachTheLoai.add(lblVanHoc);
		pnLocSachTheLoai.add(lblKinhTe);
		pnLocSachTheLoai.add(lblSachThieuNhi);
		pnLocSachTheLoai.add(lblGiaoKhoa);
		pnLocSachTheLoai.add(lblNgoaiNgu);
		pnLocSachTheLoai.add(lblTamLy);
		pnLocSachTheLoai.add(lblKhoaHocKyThuat);
		pnLocSachTheLoai.add(lblNuCong);
		pnLocSachTheLoai.add(lblAmNhac);
		pnLocSachTheLoai.add(lblBaoChi);

		JPanel pnSapXepLoc = new JPanel();
		pnSapXepLoc.setBackground(new Color(255, 255, 255));
		pnSapXepLoc.setLayout(new BoxLayout(pnSapXepLoc, BoxLayout.X_AXIS));
		pnSapXepLoc.add(Box.createHorizontalStrut(40));

		//Sắp xếp sách
		JPanel pnSapXep = new JPanel();
		pnSapXep.setBackground(new Color(255, 255, 255));
		pnSapXep.setLayout(new BoxLayout(pnSapXep, BoxLayout.X_AXIS));

		JLabel lblSapXep = new JLabel("Sắp xếp theo: ");
		lblSapXep.setFont(new Font("Arial", Font.BOLD, 14));
		JPanel pnCbxLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));		//pnCbxLeft để nhóm những thằng sau lại gần
		pnCbxLeft.setBackground(new Color(255, 255, 255));
		cbxSapXepGiaSLT = new JComboBox();
		cbxSapXepGiaSLT.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		cbxSapXepGiaSLT.addItem("Giá cao nhất");
		cbxSapXepGiaSLT.addItem("Giá thấp nhất");
		cbxSapXepGiaSLT.addItem("Số lượng tồn nhiều nhất");
		cbxSapXepGiaSLT.addItem("Số lượng tồn thấp nhất");
		cbxSapXepGiaSLT.setPreferredSize(new Dimension(170,25));

		pnCbxLeft.add(cbxSapXepGiaSLT);

		pnSapXep.add(lblSapXep);
		pnSapXep.add(Box.createHorizontalStrut(5));


		//Loc theo tác giả và nhà xuất bản và tình trạng
		JPanel pnLoc = new JPanel();
		pnLoc.setLayout(new BoxLayout(pnLoc, BoxLayout.X_AXIS));
		pnLoc.setBackground(new Color(255, 255, 255));
		//lọc tác giả

		//lọc nxb
		JLabel lblLocNhaXB = new JLabel("Lọc theo Nhà xuất bản: ");
		lblLocNhaXB.setFont(new Font("Arial", Font.BOLD, 14));
		cbxNhaXB = new JComboBox();
		taiComBoxNhaXuatBan();

		cbxNhaXB.setPreferredSize(new Dimension(170,25));
		pnLoc.add(lblLocNhaXB);
		pnLoc.add(Box.createHorizontalStrut(5));
		pnLoc.add(cbxNhaXB);
		pnLoc.add(Box.createHorizontalStrut(15));
		//lọc tình trạng
		JLabel lblLocTinhTrang = new JLabel("Lọc theo tình trạng: ");
		lblLocTinhTrang.setFont(new Font("Arial", Font.BOLD, 14));
		cbxTinhTrang = new JComboBox();
		cbxTinhTrang.addItem("Còn hàng");
		cbxTinhTrang.addItem("Hết hàng");
		cbxTinhTrang.addItem("Ngừng kinh doanh");
		cbxTinhTrang.setPreferredSize(new Dimension(170,25));
		pnLoc.add(lblLocTinhTrang);
		pnLoc.add(Box.createHorizontalStrut(5));
		pnLoc.add(cbxTinhTrang);

		pnCbxLeft.add(pnLoc);

		pnSapXep.add(pnCbxLeft);
		pnSapXepLoc.add(pnSapXep);

		Font font = new Font("Arial",Font.BOLD , 13);

		//Tìm và thêm sách
		JPanel pnTimSach = new JPanel();
		pnTimSach.setBackground(new Color(255, 255, 255));
		JLabel lblTim = new JLabel("Tìm kiếm");
		lblTim.setFont(new Font("Arial", Font.BOLD, 14));
		lblTim.setIcon(new ImageIcon("src//Icon//find1.png"));
		txtTim = new JTextField(25);
		txtTim.setPreferredSize(new Dimension(25, 27));
		JLabel lblTimKiem = new JLabel("Tìm kiếm");
		lblTimKiem.setFont(new Font("Arial", Font.BOLD, 14));
		TextPrompt tp7 = new TextPrompt("Nhập tên sách hoặc tác giả", txtTim);
		tp7.setForeground(new Color(46, 46, 31));
		tp7.changeAlpha(0.5f);
		tp7.changeStyle(Font.ITALIC);
		btnTim = new JButton("");
		btnTim.setBackground(new Color(255, 133, 51));
		lblTimKiem.setFont(font);
		lblTimKiem.setForeground(Color.WHITE);
		btnTim.add(lblTimKiem);
		pnTimSach.add(lblTim);
		pnTimSach.add(txtTim);
		pnTimSach.add(btnTim);

		JPanel pnThemSach = new JPanel();
		pnThemSach.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnThemSach.setBackground(new Color(255, 255, 255));
		JLabel lblThemSach = new JLabel(" Thêm sản phẩm");
		btnThemSach = new JButton("");
		btnThemSach.setBackground(new Color(255, 133, 51));
		btnThemSach.setPreferredSize(new Dimension(160, 40));
		lblThemSach.setFont(font);
		lblThemSach.setForeground(Color.WHITE);
		JLabel lblImgThemSach = new JLabel(ResizeImage("src//Icon//add-icon.png"));
		btnThemSach.setLayout(new BorderLayout());
		btnThemSach.add(lblImgThemSach,BorderLayout.WEST);
		btnThemSach.add(lblThemSach,BorderLayout.CENTER);
		//Thêm excel
		JLabel lblThemSachExcel = new JLabel("  Nhập Excel");
		btnThemExcel = new JButton("");
		btnThemExcel.setBackground(new Color(255, 133, 51));
		btnThemExcel.setPreferredSize(new Dimension(160, 40));
		lblThemSachExcel.setFont(font);
		lblThemSachExcel.setForeground(Color.WHITE);
		JLabel lblImgThemSachExcel = new JLabel(ResizeImage("src//Icon//import_icon-icons.com_52387.png"));
		btnThemExcel.setLayout(new BorderLayout());
		btnThemExcel.add(lblImgThemSachExcel,BorderLayout.WEST);
		btnThemExcel.add(lblThemSachExcel,BorderLayout.CENTER);

		pnThemSach.add(btnThemSach);pnThemSach.add(btnThemExcel);

		JPanel pnTimThemSach = new JPanel();
		pnTimThemSach.setLayout(new BorderLayout());
		pnTimThemSach.add(pnTimSach,BorderLayout.CENTER);
		pnTimThemSach.add(pnThemSach,BorderLayout.EAST);

		pnLocThemSach.add(pnTimThemSach);
		pnLocThemSach.add(pnKhoangCach1);
		pnLocThemSach.add(pnGRTitleLocTheLoai);
		pnLocThemSach.add(pnLocSachTheLoai);
		pnLocThemSach.add(pnKhoangCach);

		pnLocThemSach.add(pnSapXepLoc);
		pnLocThemSach.add(pnKhoangCach1);


		//Với dụng cụ học tập
		//Khoảng cách dungcuhoctap
		JPanel pnKhoangCachDCHT = new JPanel();
		pnKhoangCachDCHT.setLayout(new BoxLayout(pnKhoangCachDCHT, BoxLayout.Y_AXIS));
		pnKhoangCachDCHT.setBackground(new Color(255, 255, 255));
		pnKhoangCachDCHT.add(Box.createVerticalStrut(15));

		JPanel pnKhoangCachDCHT1 = new JPanel();
		pnKhoangCachDCHT1.setLayout(new BoxLayout(pnKhoangCachDCHT1, BoxLayout.Y_AXIS));
		pnKhoangCachDCHT1.setBackground(new Color(255, 255, 255));
		pnKhoangCachDCHT1.add(Box.createVerticalStrut(8));

		JPanel pnLocThemDCHT = new JPanel();
		pnLocThemDCHT.setLayout(new BoxLayout(pnLocThemDCHT, BoxLayout.Y_AXIS));
		pnLocThemDCHT.setBackground(new Color(255, 255, 255));

		pnDSDCHT = new JPanel();
		pnDSDCHT.setBackground(new Color(255, 255, 255));
		pnDSDCHT.setLayout(new WrapLayout());

		JPanel pnTimDCHT = new JPanel();
		pnTimDCHT.setBackground(new Color(255, 255, 255));
		JLabel lblTimDCHT = new JLabel("Tìm kiếm");
		lblTimDCHT.setFont(new Font("Arial", Font.BOLD, 14));
		lblTimDCHT.setIcon(new ImageIcon("Icon/find1.png"));
		txtTimDCHT = new JTextField(25);
		txtTimDCHT.setPreferredSize(new Dimension(25, 27));
		TextPrompt tpTimDCHT = new TextPrompt("Nhập tên dụng cụ học tập", txtTimDCHT);
		tpTimDCHT.setForeground(new Color(46, 46, 31));
		tpTimDCHT.changeAlpha(0.5f);
		tpTimDCHT.changeStyle(Font.ITALIC);
		JLabel lblTimKiemDCHT = new JLabel("Tìm kiếm");
		lblTimKiemDCHT.setForeground(Color.WHITE);
		lblTimKiemDCHT.setFont(font);
		btnTimDCHT = new JButton("");
		btnTimDCHT.setBackground(new Color(255, 133, 51));
		btnTimDCHT.add(lblTimKiemDCHT);
		pnTimDCHT.add(lblTimDCHT);
		pnTimDCHT.add(txtTimDCHT);
		pnTimDCHT.add(btnTimDCHT);

		JPanel pnThemDCHT = new JPanel();
		pnThemDCHT.setLayout(new FlowLayout(FlowLayout.RIGHT));
		pnThemDCHT.setBackground(new Color(255, 255, 255));
		JLabel lblThemDCHT = new JLabel(" Thêm sản phẩm");
		lblThemDCHT.setFont(font);
		btnThemDCHT = new JButton("");
		btnThemDCHT.setBackground(new Color(255, 133, 51));
		lblThemDCHT.setFont(font);
		lblThemDCHT.setForeground(Color.WHITE);
		btnThemDCHT.setPreferredSize(new Dimension(160, 40));
		JLabel lblImgThemDCHT = new JLabel(ResizeImage("src//Icon//add-icon.png"));
		btnThemDCHT.setLayout(new BorderLayout());
		btnThemDCHT.add(lblImgThemDCHT,BorderLayout.WEST);
		btnThemDCHT.add(lblThemDCHT,BorderLayout.CENTER);
		btnThemDCHT.add(lblThemDCHT);
		//Thêm dụng cụ học tập excel
		JLabel lblThemDCHTExcel = new JLabel("  Nhập Excel");
		btnThemDCHTExcel = new JButton("");
		btnThemDCHTExcel.setBackground(new Color(255, 133, 51));
		btnThemDCHTExcel.setPreferredSize(new Dimension(160, 40));
		lblThemDCHTExcel.setFont(font);
		lblThemDCHTExcel.setForeground(Color.WHITE);
		JLabel lblImgThemDCHTExcel = new JLabel(ResizeImage("src//Icon//import_icon-icons.com_52387.png"));
		btnThemDCHTExcel.setLayout(new BorderLayout());
		btnThemDCHTExcel.add(lblImgThemDCHTExcel,BorderLayout.WEST);
		btnThemDCHTExcel.add(lblThemDCHTExcel,BorderLayout.CENTER);
		pnThemDCHT.add(btnThemDCHT);
		pnThemDCHT.add(btnThemDCHTExcel);

		JPanel pnTimThemDCHT = new JPanel();
		pnTimThemDCHT.setLayout(new BorderLayout());
		pnTimThemDCHT.add(pnTimDCHT,BorderLayout.CENTER);
		pnTimThemDCHT.add(pnThemDCHT,BorderLayout.EAST);

		//Lọc sắp xếp dụng cụ học tập
		CompoundBorder cbTuaDeLocDCHT = new CompoundBorder( BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK), new EmptyBorder(0,35,0,0));
		JPanel pnGRTitleLocTheLoaiDCHT = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pnGRTitleLocTheLoaiDCHT.setBackground(new Color(255, 255, 255));
		pnGRTitleLocTheLoaiDCHT.setBorder(new EmptyBorder(0,35,-5,0));
		JPanel pnTitleLocTheLoaiDCHT = new JPanel();

		pnTitleLocTheLoaiDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnTitleLocTheLoaiDCHT.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pnTitleLocTheLoaiDCHT.setBackground(new Color(255, 255, 255));
		//		ImageIcon imgSearch = new ImageIcon("Icon/find.png");
		//		JLabel lblIconSearch = new JLabel(imgSearch);
		JLabel lblTuaDeLocDCHT = new JLabel("Chọn thể loại dụng cụ học tập cần tìm");
		JLabel lblKhoangCachBorderDCHT = new JLabel("                                                                                                                                          ");
		pnTitleLocTheLoaiDCHT.add(lblTuaDeLocDCHT);
		//		pnTitleLocTheLoai.add(lblIconSearch);
		pnTitleLocTheLoaiDCHT.add(lblKhoangCachBorderDCHT);

		pnGRTitleLocTheLoaiDCHT.add(pnTitleLocTheLoaiDCHT);

		JPanel pnLocDCHTTheLoai = new JPanel();
		pnLocDCHTTheLoai.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnLocDCHTTheLoai.setBorder(new EmptyBorder(0, 26, 0, 0));
		pnLocDCHTTheLoai.setBackground(new Color(255, 255, 255));
		CompoundBorder cbDCHT = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 255, 255)), new EmptyBorder(6,10,6,10));
		lblBut = new JLabel("Bút");
		lblBut.setBorder(cbDCHT);
		lblBut.setFont(new Font("Arial", Font.BOLD, 14));
		lblThuoc = new JLabel("Thước");
		lblThuoc.setFont(new Font("Arial", Font.BOLD, 14));
		lblThuoc.setBorder(cbDCHT);
		lblGom = new JLabel("Gôm - Tẩy");
		lblGom.setFont(new Font("Arial", Font.BOLD, 14));
		lblGom.setBorder(cbDCHT);
		lblCompa = new JLabel("Compa");
		lblCompa.setFont(new Font("Arial", Font.BOLD, 14));
		lblCompa.setBorder(cbDCHT);
		lblHopBut = new JLabel("Hộp Bút");
		lblHopBut.setFont(new Font("Arial", Font.BOLD, 14));
		lblHopBut.setBorder(cbDCHT);
		lblTapVo = new JLabel("Tập - Vở");
		lblTapVo.setFont(new Font("Arial", Font.BOLD, 14));
		lblTapVo.setBorder(cbDCHT);

		pnLocDCHTTheLoai.add(lblBut);
		pnLocDCHTTheLoai.add(lblThuoc);
		pnLocDCHTTheLoai.add(lblGom);
		pnLocDCHTTheLoai.add(lblCompa);
		pnLocDCHTTheLoai.add(lblHopBut);
		pnLocDCHTTheLoai.add(lblTapVo);

		JPanel pnSapXepLocDCHT = new JPanel();
		pnSapXepLocDCHT.setBackground(new Color(255, 255, 255));
		pnSapXepLocDCHT.setLayout(new BoxLayout(pnSapXepLocDCHT, BoxLayout.X_AXIS));
		pnSapXepLocDCHT.add(Box.createHorizontalStrut(40));

		//Sắp xếp sách
		JPanel pnSapXepDCHT = new JPanel();
		pnSapXepDCHT.setBackground(new Color(255, 255, 255));
		pnSapXepDCHT.setLayout(new BoxLayout(pnSapXepDCHT, BoxLayout.X_AXIS));

		JLabel lblSapXepDCHT = new JLabel("Sắp xếp theo: ");
		lblSapXepDCHT.setFont(new Font("Arial", Font.BOLD, 14));
		JPanel pnCbxLeftDCHT = new JPanel(new FlowLayout(FlowLayout.LEFT));		//pnCbxLeft để nhóm những thằng sau lại gần
		pnCbxLeftDCHT.setBackground(new Color(255, 255, 255));
		cbxSapXepGiaDCHT = new JComboBox();
		cbxSapXepGiaDCHT.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
		cbxSapXepGiaDCHT.addItem("Giá cao nhất");
		cbxSapXepGiaDCHT.addItem("Giá thấp nhất");
		cbxSapXepGiaDCHT.addItem("Số lượng tồn nhiều nhất");
		cbxSapXepGiaDCHT.addItem("Số lượng tồn thấp nhất");
		cbxSapXepGiaDCHT.setPreferredSize(new Dimension(170,25));

		pnCbxLeftDCHT.add(cbxSapXepGiaDCHT);

		pnSapXepDCHT.add(lblSapXepDCHT);
		pnSapXepDCHT.add(Box.createHorizontalStrut(5));


		//Loc theo thương hiệu và xuất xứ và tình trạng
		JPanel pnLocDCHT = new JPanel();
		pnLocDCHT.setLayout(new BoxLayout(pnLocDCHT, BoxLayout.X_AXIS));
		pnLocDCHT.setBackground(new Color(255, 255, 255));
		//lọc tác giả
		JLabel lblLocThuongHieu = new JLabel("Lọc theo thương hiệu: ");
		lblLocThuongHieu.setFont(new Font("Arial", Font.BOLD, 14));
		cbxLocThuongHieu = new JComboBox();
		taiComBoxThuongHieu();
		cbxLocThuongHieu.setPreferredSize(new Dimension(170,25));
		pnLocDCHT.add(Box.createHorizontalStrut(15));
		pnLocDCHT.add(lblLocThuongHieu);
		pnLocDCHT.add(Box.createHorizontalStrut(5));
		pnLocDCHT.add(cbxLocThuongHieu);
		pnLocDCHT.add(Box.createHorizontalStrut(15));
		//lọc nxb
		JLabel lblXuatXu = new JLabel("Lọc theo xuất xứ: ");
		//lblXuatXu.setFont(new Font("Arial", Font.BOLD, 14));
		cbxXuatXu = new JComboBox();
		taiComBoxXuatXu();
		cbxXuatXu.setPreferredSize(new Dimension(170,25));
		pnLocDCHT.add(lblXuatXu);
		pnLocDCHT.add(Box.createHorizontalStrut(5));
		pnLocDCHT.add(cbxXuatXu);
		pnLocDCHT.add(Box.createHorizontalStrut(15));
		//lọc tình trạng
		JLabel lblLocTinhTrangDCHT = new JLabel("Lọc theo tình trạng: ");
		//lblLocTinhTrangDCHT.setFont(new Font("Arial", Font.BOLD, 14));
		cbxTinhTrangDCHT = new JComboBox();
		cbxTinhTrangDCHT.addItem("Còn hàng");
		cbxTinhTrangDCHT.addItem("Hết hàng");
		cbxTinhTrangDCHT.addItem("Ngừng kinh doanh");
		cbxTinhTrangDCHT.setPreferredSize(new Dimension(170,25));
		pnLocDCHT.add(lblLocTinhTrangDCHT);
		pnLocDCHT.add(Box.createHorizontalStrut(5));
		pnLocDCHT.add(cbxTinhTrangDCHT);

		pnCbxLeftDCHT.add(pnLocDCHT);

		pnSapXepDCHT.add(pnCbxLeftDCHT);
		pnSapXepLocDCHT.add(pnSapXepDCHT);


		pnLocThemDCHT.add(pnTimThemDCHT);
		pnLocThemDCHT.add(pnKhoangCachDCHT1);
		pnLocThemDCHT.add(pnGRTitleLocTheLoaiDCHT);
		pnLocThemDCHT.add(pnLocDCHTTheLoai);
		pnLocThemDCHT.add(pnKhoangCachDCHT);

		pnLocThemDCHT.add(pnSapXepLocDCHT);
		//pnLocThemDCHT.add(pnKhoangCachDCHT1);;

		//add lại
		pnCenterSach.add(pnLocThemSach,BorderLayout.NORTH);
		pnCenterDCHT.add(pnLocThemDCHT,BorderLayout.NORTH);	

//		JScrollPane jscSach = new JScrollPane(pnCenterSach);
		JScrollPane jscDCHT = new JScrollPane(pnCenterDCHT);

		JTabbedPane tab = new JTabbedPane();
//		tab.add(jscSach,"Sách");
		tab.add(jscDCHT,"Dụng cụ học tập");

		add(tab,BorderLayout.CENTER);
		add(jscDCHT, BorderLayout.CENTER);

		lblVanHoc.setForeground(Color.BLACK);
		lblKinhTe.setForeground(Color.BLACK);
		lblSachThieuNhi.setForeground(Color.BLACK);
		lblGiaoKhoa.setForeground(Color.BLACK);
		lblNgoaiNgu.setForeground(Color.BLACK);
		lblTamLy.setForeground(Color.BLACK);

		//CursorSach
		lblVanHoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblKinhTe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblSachThieuNhi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGiaoKhoa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNgoaiNgu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTamLy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemSach.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblKhoaHocKyThuat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNuCong.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblAmNhac.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblBaoChi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		//CursoDungCuHocTap
		lblBut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblThuoc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblGom.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCompa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblHopBut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTapVo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemDCHT.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnThemDCHTExcel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


		//MouseActionListenerSach
		lblVanHoc.addMouseListener(this);
		lblKinhTe.addMouseListener(this);
		lblSachThieuNhi.addMouseListener(this);
		lblGiaoKhoa.addMouseListener(this);
		lblNgoaiNgu.addMouseListener(this);
		lblTamLy.addMouseListener(this);
		lblKhoaHocKyThuat.addMouseListener(this);//lblKhoaHocKyThuat, lblNuCong, lblAmNhac, lblBaoChi
		lblNuCong.addMouseListener(this);
		lblAmNhac.addMouseListener(this);
		lblBaoChi.addMouseListener(this);
		btnThemSach.addActionListener(this);
		btnThemExcel.addActionListener(this);
		btnTim.addActionListener(this);
		cbxNhaXB.addActionListener(this);
		cbxTinhTrang.addActionListener(this);
		cbxSapXepGiaSLT.addActionListener(this);

		//MouseActionListenerDCHT
		lblBut.addMouseListener(this);
		lblThuoc.addMouseListener(this);
		lblGom.addMouseListener(this);
		lblCompa.addMouseListener(this);
		lblHopBut.addMouseListener(this);
		lblTapVo.addMouseListener(this);
		btnThemDCHT.addActionListener(this);
		btnThemDCHTExcel.addActionListener(this);
		btnTimDCHT.addActionListener(this);
		cbxLocThuongHieu.addActionListener(this);
		cbxTinhTrangDCHT.addActionListener(this);
		cbxSapXepGiaDCHT.addActionListener(this);
		cbxXuatXu.addActionListener(this);

		txtTim.addKeyListener(new KeyTextField(txtTim,1));	//1 là sách, 2 là dcht
		txtTimDCHT.addKeyListener(new KeyTextField(txtTimDCHT,2));
		txtTim.addKeyListener(this);

		//Load Văn học
		lblVanHoc.setBorder(cb);
		lblVanHoc.setForeground(Color.WHITE);
		lblVanHoc.setBackground(new Color(255, 133, 51));
		lblVanHoc.setOpaque(true);
		taiVanHoc();
		//Bút
		lblBut.setBorder(cb);
		lblBut.setForeground(Color.WHITE);
		lblBut.setBackground(new Color(255, 133, 51));
		lblBut.setOpaque(true);
		taiBut();
		//TaiSanPhamSach();		
		//TaiSanPhamDCHT();

	}

	public static void TaiSanPhamSach() {
		listSach = sachDao.getAllTbSach(); 
		inDanhSachSach(listSach);
	}
	public static void TaiSanPhamDCHT() {
		listDCHT = daoDCHT.getAllTbDCHT(); 
		inDanhSachDCHT(listDCHT);
	}
	//Load combobox
	public static void taiComBoxNhaXuatBan() {
		listSach = sachDao.getAllTbSach();
		List<String> l = new ArrayList<String>();
		cbxNhaXB.removeAllItems();
		for(Sach sach : listSach) {  
			l.add(sach.getNhaXuatBan().trim());
		}
		Set<String> listNXBKhongTrung =  new LinkedHashSet<String>(l);		//Xử lí k trùng list bằng set
		for(String s : listNXBKhongTrung) {  
			cbxNhaXB.addItem(s);
		}

	}
	public static void taiComBoxThuongHieu() {
		listDCHT = daoDCHT.getAllTbDCHT();
		List<String> l = new ArrayList<String>();
		cbxLocThuongHieu.removeAllItems();
		for(DungCuHocTap dcht : listDCHT) {  
			l.add(dcht.getThuongHieu().trim());
		}
		Set<String> listThuongHieuKhongTrung =  new LinkedHashSet<String>(l);		//Xử lí k trùng list bằng set
		for(String dcht : listThuongHieuKhongTrung) {  
			cbxLocThuongHieu.addItem(dcht);
		}

	}
	public static void taiComBoxXuatXu() {
		listDCHT = daoDCHT.getAllTbDCHT();
		List<String> l = new ArrayList<String>();
		cbxXuatXu.removeAllItems();
		for(DungCuHocTap dcht : listDCHT) {  
			l.add(dcht.getXuatXu().trim());
		}
		Set<String> listXuatXuKhongTrung =  new LinkedHashSet<String>(l);		//Xử lí k trùng list bằng set
		for(String dcht : listXuatXuKhongTrung) {  
			cbxXuatXu.addItem(dcht);
		}

	}
	public void taiVanHoc() 
	{
		listLoaiSach = daoLoaiSach.getAllLoaiSach();
		for(LoaiSach loaisach : listLoaiSach) 
		{  
			if(loaisach.getTenLoaiSach().equals(lblVanHoc.getText())) 
			{			
				listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
				inDanhSachSach(listSach);
			}
		}
	}

	public void taiBut() {
		listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
		for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
			if(loaiDCHT.getTenLoaiDCHT().equals(lblBut.getText())) {			
				listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
				inDanhSachDCHT(listDCHT);

			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object object = e.getSource();
		CompoundBorder cb = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 133, 51)), new EmptyBorder(6,10,6,10));
		CompoundBorder cbTrang = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 255, 255)), new EmptyBorder(6,10,6,10));
		if(object.equals(lblVanHoc)) {
			lblVanHoc.setBorder(cb);
			lblVanHoc.setForeground(Color.WHITE);
			lblVanHoc.setBackground(new Color(255, 133, 51));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			//Kiểm tra tên loại sách
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblVanHoc.getText())) {			
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}	
		else if(object.equals(lblKinhTe)) {
			lblKinhTe.setBorder(cb);
			lblKinhTe.setForeground(Color.WHITE);
			lblKinhTe.setBackground(new Color(255, 133, 51));
			lblKinhTe.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblKinhTe.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);
				}

			}
		}
		else if(object.equals(lblSachThieuNhi)) {
			lblSachThieuNhi.setBorder(cb);
			lblSachThieuNhi.setForeground(Color.WHITE);
			lblSachThieuNhi.setBackground(new Color(255, 133, 51));
			lblSachThieuNhi.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblSachThieuNhi.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);
				}

			}
		}
		else if(object.equals(lblGiaoKhoa)) {
			lblGiaoKhoa.setBorder(cb);
			lblGiaoKhoa.setForeground(Color.WHITE);
			lblGiaoKhoa.setBackground(new Color(255, 133, 51));
			lblGiaoKhoa.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblGiaoKhoa.getText())) {			
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}
		else if(object.equals(lblNgoaiNgu)) {
			lblNgoaiNgu.setBorder(cb);
			lblNgoaiNgu.setForeground(Color.WHITE);
			lblNgoaiNgu.setBackground(new Color(255, 133, 51));
			lblNgoaiNgu.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblNgoaiNgu.getText())) {	
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);
				}
			}
		}
		else if(object.equals(lblTamLy)) {
			lblTamLy.setBorder(cb);
			lblTamLy.setForeground(Color.WHITE);
			lblTamLy.setBackground(new Color(255, 133, 51));
			lblTamLy.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblTamLy.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}//lblKhoaHocKyThuat, lblNuCong, lblAmNhac, lblBaoChi
		else if(object.equals(lblKhoaHocKyThuat)) {
			lblKhoaHocKyThuat.setBorder(cb);
			lblKhoaHocKyThuat.setForeground(Color.WHITE);
			lblKhoaHocKyThuat.setBackground(new Color(255, 133, 51));
			lblKhoaHocKyThuat.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			lblTamLy.setBorder(cbTrang);
			lblTamLy.setForeground(Color.BLACK);
			lblTamLy.setBackground(new Color(255, 255, 255));
			lblTamLy.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblKhoaHocKyThuat.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}
		else if(object.equals(lblNuCong)) {
			lblNuCong.setBorder(cb);
			lblNuCong.setForeground(Color.WHITE);
			lblNuCong.setBackground(new Color(255, 133, 51));
			lblNuCong.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);		
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblNuCong.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}
		else if(object.equals(lblAmNhac)) {
			lblAmNhac.setBorder(cb);
			lblAmNhac.setForeground(Color.WHITE);
			lblAmNhac.setBackground(new Color(255, 133, 51));
			lblAmNhac.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblBaoChi.setBorder(cbTrang);
			lblBaoChi.setForeground(Color.BLACK);
			lblBaoChi.setBackground(new Color(255, 255, 255));
			lblBaoChi.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblAmNhac.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}
		else if(object.equals(lblBaoChi)) {
			lblBaoChi.setBorder(cb);
			lblBaoChi.setForeground(Color.WHITE);
			lblBaoChi.setBackground(new Color(255, 133, 51));
			lblBaoChi.setOpaque(true);
			lblVanHoc.setBorder(cbTrang);
			lblVanHoc.setForeground(Color.BLACK);
			lblVanHoc.setBackground(new Color(255, 255, 255));
			lblVanHoc.setOpaque(true);
			lblKinhTe.setBorder(cbTrang);
			lblKinhTe.setForeground(Color.BLACK);
			lblKinhTe.setBackground(new Color(255, 255, 255));
			lblKinhTe.setOpaque(true);
			lblSachThieuNhi.setBorder(cbTrang);
			lblSachThieuNhi.setForeground(Color.BLACK);
			lblSachThieuNhi.setBackground(new Color(255, 255, 255));
			lblSachThieuNhi.setOpaque(true);
			lblGiaoKhoa.setBorder(cbTrang);
			lblGiaoKhoa.setForeground(Color.BLACK);
			lblGiaoKhoa.setBackground(new Color(255, 255, 255));
			lblGiaoKhoa.setOpaque(true);
			lblNgoaiNgu.setBorder(cbTrang);
			lblNgoaiNgu.setForeground(Color.BLACK);
			lblNgoaiNgu.setBackground(new Color(255, 255, 255));
			lblNgoaiNgu.setOpaque(true);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblKhoaHocKyThuat.setForeground(Color.BLACK);
			lblKhoaHocKyThuat.setBackground(new Color(255, 255, 255));
			lblKhoaHocKyThuat.setOpaque(true);
			lblNuCong.setBorder(cbTrang);
			lblNuCong.setForeground(Color.BLACK);
			lblNuCong.setBackground(new Color(255, 255, 255));
			lblNuCong.setOpaque(true);
			lblAmNhac.setBorder(cbTrang);
			lblAmNhac.setForeground(Color.BLACK);
			lblAmNhac.setBackground(new Color(255, 255, 255));
			lblAmNhac.setOpaque(true);
			listLoaiSach = daoLoaiSach.getAllLoaiSach();
			for(LoaiSach loaisach : listLoaiSach) {  
				if(loaisach.getTenLoaiSach().equals(lblBaoChi.getText())) {		
					listSach = sachDao.getSachTheoMaLoaiSach(loaisach.getMaLoaiSach());
					inDanhSachSach(listSach);

				}
			}
		}

		//=======================MouseClick DCHT lblBut, lblThuoc, lblGom, lblCompa, lblHopBut, lblTapVo==============
		if(object.equals(lblBut)) {
			lblBut.setBorder(cb);
			lblBut.setForeground(Color.WHITE);
			lblBut.setBackground(new Color(255, 133, 51));
			lblBut.setOpaque(true);
			lblThuoc.setBorder(cbTrang);
			lblThuoc.setForeground(Color.BLACK);
			lblThuoc.setBackground(new Color(255, 255, 255));
			lblThuoc.setOpaque(true);
			lblGom.setBorder(cbTrang);
			lblGom.setForeground(Color.BLACK);
			lblGom.setBackground(new Color(255, 255, 255));
			lblGom.setOpaque(true);
			lblCompa.setBorder(cbTrang);
			lblCompa.setForeground(Color.BLACK);
			lblCompa.setBackground(new Color(255, 255, 255));
			lblCompa.setOpaque(true);
			lblHopBut.setBorder(cbTrang);
			lblHopBut.setForeground(Color.BLACK);
			lblHopBut.setBackground(new Color(255, 255, 255));
			lblHopBut.setOpaque(true);
			lblTapVo.setBorder(cbTrang);
			lblTapVo.setForeground(Color.BLACK);
			lblTapVo.setBackground(new Color(255, 255, 255));
			lblTapVo.setOpaque(true);
			//Kiểm tra tên loại dụng cụ học tập
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblBut.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}	
		else if(object.equals(lblThuoc)) {
			lblThuoc.setBorder(cb);
			lblThuoc.setForeground(Color.WHITE);
			lblThuoc.setBackground(new Color(255, 133, 51));
			lblThuoc.setOpaque(true);
			lblBut.setBorder(cbTrang);
			lblBut.setForeground(Color.BLACK);
			lblBut.setBackground(new Color(255, 255, 255));
			lblBut.setOpaque(true);
			lblGom.setBorder(cbTrang);
			lblGom.setForeground(Color.BLACK);
			lblGom.setBackground(new Color(255, 255, 255));
			lblGom.setOpaque(true);
			lblCompa.setBorder(cbTrang);
			lblCompa.setForeground(Color.BLACK);
			lblCompa.setBackground(new Color(255, 255, 255));
			lblCompa.setOpaque(true);
			lblHopBut.setBorder(cbTrang);
			lblHopBut.setForeground(Color.BLACK);
			lblHopBut.setBackground(new Color(255, 255, 255));
			lblHopBut.setOpaque(true);
			lblTapVo.setBorder(cbTrang);
			lblTapVo.setForeground(Color.BLACK);
			lblTapVo.setBackground(new Color(255, 255, 255));
			lblTapVo.setOpaque(true);
			//Kiểm tra tên loại dụng cụ học tập
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblThuoc.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}
		else if(object.equals(lblGom)) {
			lblGom.setBorder(cb);
			lblGom.setForeground(Color.WHITE);
			lblGom.setBackground(new Color(255, 133, 51));
			lblGom.setOpaque(true);
			lblBut.setBorder(cbTrang);
			lblBut.setForeground(Color.BLACK);
			lblBut.setBackground(new Color(255, 255, 255));
			lblBut.setOpaque(true);
			lblThuoc.setBorder(cbTrang);
			lblThuoc.setForeground(Color.BLACK);
			lblThuoc.setBackground(new Color(255, 255, 255));
			lblThuoc.setOpaque(true);
			lblCompa.setBorder(cbTrang);
			lblCompa.setForeground(Color.BLACK);
			lblCompa.setBackground(new Color(255, 255, 255));
			lblCompa.setOpaque(true);
			lblHopBut.setBorder(cbTrang);
			lblHopBut.setForeground(Color.BLACK);
			lblHopBut.setBackground(new Color(255, 255, 255));
			lblHopBut.setOpaque(true);
			lblTapVo.setBorder(cbTrang);
			lblTapVo.setForeground(Color.BLACK);
			lblTapVo.setBackground(new Color(255, 255, 255));
			lblTapVo.setOpaque(true);
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblGom.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}
		else if(object.equals(lblCompa)) {
			lblCompa.setBorder(cb);
			lblCompa.setForeground(Color.WHITE);
			lblCompa.setBackground(new Color(255, 133, 51));
			lblCompa.setOpaque(true);
			lblBut.setBorder(cbTrang);
			lblBut.setForeground(Color.BLACK);
			lblBut.setBackground(new Color(255, 255, 255));
			lblBut.setOpaque(true);
			lblThuoc.setBorder(cbTrang);
			lblThuoc.setForeground(Color.BLACK);
			lblThuoc.setBackground(new Color(255, 255, 255));
			lblThuoc.setOpaque(true);
			lblGom.setBorder(cbTrang);
			lblGom.setForeground(Color.BLACK);
			lblGom.setBackground(new Color(255, 255, 255));
			lblGom.setOpaque(true);
			lblHopBut.setBorder(cbTrang);
			lblHopBut.setForeground(Color.BLACK);
			lblHopBut.setBackground(new Color(255, 255, 255));
			lblHopBut.setOpaque(true);
			lblTapVo.setBorder(cbTrang);
			lblTapVo.setForeground(Color.BLACK);
			lblTapVo.setBackground(new Color(255, 255, 255));
			lblTapVo.setOpaque(true);
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblCompa.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}
		else if(object.equals(lblHopBut)) {
			lblHopBut.setBorder(cb);
			lblHopBut.setForeground(Color.WHITE);
			lblHopBut.setBackground(new Color(255, 133, 51));
			lblHopBut.setOpaque(true);
			lblBut.setBorder(cbTrang);
			lblBut.setForeground(Color.BLACK);
			lblBut.setBackground(new Color(255, 255, 255));
			lblBut.setOpaque(true);
			lblThuoc.setBorder(cbTrang);
			lblThuoc.setForeground(Color.BLACK);
			lblThuoc.setBackground(new Color(255, 255, 255));
			lblThuoc.setOpaque(true);
			lblGom.setBorder(cbTrang);
			lblGom.setForeground(Color.BLACK);
			lblGom.setBackground(new Color(255, 255, 255));
			lblGom.setOpaque(true);
			lblCompa.setBorder(cbTrang);
			lblCompa.setForeground(Color.BLACK);
			lblCompa.setBackground(new Color(255, 255, 255));
			lblCompa.setOpaque(true);
			lblTapVo.setBorder(cbTrang);
			lblTapVo.setForeground(Color.BLACK);
			lblTapVo.setBackground(new Color(255, 255, 255));
			lblTapVo.setOpaque(true);
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblHopBut.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}
		else if(object.equals(lblTapVo)) {
			lblTapVo.setBorder(cb);
			lblTapVo.setForeground(Color.WHITE);
			lblTapVo.setBackground(new Color(255, 133, 51));
			lblTapVo.setOpaque(true);
			lblBut.setBorder(cbTrang);
			lblBut.setForeground(Color.BLACK);
			lblBut.setBackground(new Color(255, 255, 255));
			lblBut.setOpaque(true);
			lblThuoc.setBorder(cbTrang);
			lblThuoc.setForeground(Color.BLACK);
			lblThuoc.setBackground(new Color(255, 255, 255));
			lblThuoc.setOpaque(true);
			lblGom.setBorder(cbTrang);
			lblGom.setForeground(Color.BLACK);
			lblGom.setBackground(new Color(255, 255, 255));
			lblGom.setOpaque(true);
			lblCompa.setBorder(cbTrang);
			lblCompa.setForeground(Color.BLACK);
			lblCompa.setBackground(new Color(255, 255, 255));
			lblCompa.setOpaque(true);
			lblHopBut.setBorder(cbTrang);
			lblHopBut.setForeground(Color.BLACK);
			lblHopBut.setBackground(new Color(255, 255, 255));
			lblHopBut.setOpaque(true);
			listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
			for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {  
				if(loaiDCHT.getTenLoaiDCHT().equals(lblTapVo.getText())) {			
					listDCHT = daoDCHT.layDCHTTheoMaLoaiDCHT(loaiDCHT.getMaLoaiDCHT());
					inDanhSachDCHT(listDCHT);

				}
			}
		}
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
		Object object = e.getSource();
		CompoundBorder cb = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 113, 51)), new EmptyBorder(6,10,6,10));
		CompoundBorder cbTrang = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 255, 255)), new EmptyBorder(6,10,6,10));
		if(object.equals(lblVanHoc)) {
			lblVanHoc.setBorder(cb);	
			lblKinhTe.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);		
			lblGiaoKhoa.setBorder(cbTrang);		
			lblNgoaiNgu.setBorder(cbTrang);			
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}	
		else if(object.equals(lblKinhTe)) {
			lblKinhTe.setBorder(cb);			
			lblVanHoc.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);}
		else if(object.equals(lblSachThieuNhi)) {
			lblSachThieuNhi.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblGiaoKhoa)) {
			lblGiaoKhoa.setBorder(cb);
			lblVanHoc.setBorder(cbTrang);			
			lblKinhTe.setBorder(cbTrang);			
			lblSachThieuNhi.setBorder(cbTrang);			
			lblNgoaiNgu.setBorder(cbTrang);			
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblNgoaiNgu)) {
			lblNgoaiNgu.setBorder(cb);	
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblTamLy)) {
			lblTamLy.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		//lblKhoaHocKyThuat, lblNuCong, lblAmNhac, lblBaoChi
		else if(object.equals(lblKhoaHocKyThuat)) {
			lblKhoaHocKyThuat.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblNuCong)) {
			lblNuCong.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblAmNhac)) {
			lblAmNhac.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
			lblBaoChi.setBorder(cbTrang);
		}
		else if(object.equals(lblBaoChi)) {
			lblBaoChi.setBorder(cb);		
			lblVanHoc.setBorder(cbTrang);
			lblKinhTe.setBorder(cbTrang);
			lblGiaoKhoa.setBorder(cbTrang);
			lblSachThieuNhi.setBorder(cbTrang);
			lblNgoaiNgu.setBorder(cbTrang);
			lblTamLy.setBorder(cbTrang);
			lblNuCong.setBorder(cbTrang);
			lblAmNhac.setBorder(cbTrang);
			lblKhoaHocKyThuat.setBorder(cbTrang);
		}

		//MouseEnter dụng cụ học tập lblBut, lblThuoc, lblGom, lblCompa, lblHopBut, lblTapVo
		if(object.equals(lblBut)) {
			lblBut.setBorder(cb);	
			lblGom.setBorder(cbTrang);
			lblCompa.setBorder(cbTrang);		
			lblHopBut.setBorder(cbTrang);		
			lblTapVo.setBorder(cbTrang);			
			lblThuoc.setBorder(cbTrang);
		}	
		else if(object.equals(lblThuoc)) {
			lblThuoc.setBorder(cb);			
			lblBut.setBorder(cbTrang);
			lblGom.setBorder(cbTrang);
			lblCompa.setBorder(cbTrang);
			lblHopBut.setBorder(cbTrang);
			lblTapVo.setBorder(cbTrang);
		}
		else if(object.equals(lblGom)) {
			lblGom.setBorder(cb);			
			lblBut.setBorder(cbTrang);
			lblThuoc.setBorder(cbTrang);
			lblCompa.setBorder(cbTrang);
			lblHopBut.setBorder(cbTrang);
			lblTapVo.setBorder(cbTrang);
		}
		else if(object.equals(lblCompa)) {
			lblCompa.setBorder(cb);
			lblBut.setBorder(cbTrang);
			lblThuoc.setBorder(cbTrang);
			lblGom.setBorder(cbTrang);
			lblHopBut.setBorder(cbTrang);
			lblTapVo.setBorder(cbTrang);
		}
		else if(object.equals(lblHopBut)) {
			lblHopBut.setBorder(cb);	
			lblBut.setBorder(cbTrang);
			lblThuoc.setBorder(cbTrang);
			lblGom.setBorder(cbTrang);
			lblCompa.setBorder(cbTrang);
			lblTapVo.setBorder(cbTrang);
		}
		else if(object.equals(lblTapVo)) {
			lblTapVo.setBorder(cb);		
			lblBut.setBorder(cbTrang);
			lblThuoc.setBorder(cbTrang);
			lblGom.setBorder(cbTrang);
			lblCompa.setBorder(cbTrang);
			lblHopBut.setBorder(cbTrang);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		Object object = e.getSource();
		CompoundBorder cb = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 113, 51)), new EmptyBorder(6,10,6,10));
		CompoundBorder cbTrang = new CompoundBorder( BorderFactory.createLineBorder(new Color(255, 255, 255)), new EmptyBorder(6,10,6,10));
		if(object.equals(lblVanHoc)) {
			lblVanHoc.setBorder(cbTrang);
		}	
		else if(object.equals(lblKinhTe)) {
			lblKinhTe.setBorder(cbTrang);		
		}
		else if(object.equals(lblSachThieuNhi)) {
			lblSachThieuNhi.setBorder(cbTrang);
		}
		else if(object.equals(lblGiaoKhoa)) {
			lblGiaoKhoa.setBorder(cbTrang);	
		}
		else if(object.equals(lblNgoaiNgu)) {
			lblNgoaiNgu.setBorder(cbTrang);
		}
		else if(object.equals(lblTamLy)) {
			lblTamLy.setBorder(cbTrang);
		}
		//lblKhoaHocKyThuat, lblNuCong, lblAmNhac, lblBaoChi
		else if(object.equals(lblKhoaHocKyThuat)) {
			lblKhoaHocKyThuat.setBorder(cbTrang);
		}
		else if(object.equals(lblNuCong)) {
			lblNuCong.setBorder(cbTrang);
		}
		else if(object.equals(lblAmNhac)) {
			lblAmNhac.setBorder(cbTrang);
		}
		else if(object.equals(lblBaoChi)) {
			lblBaoChi.setBorder(cbTrang);
		}

		//MouseExit dụng cụ học tập lblBut, lblThuoc, lblGom, lblCompa, lblHopBut, lblTapVo
		if(object.equals(lblBut)) {
			lblBut.setBorder(cbTrang);
		}	
		else if(object.equals(lblThuoc)) {
			lblThuoc.setBorder(cbTrang);		
		}
		else if(object.equals(lblGom)) {
			lblGom.setBorder(cbTrang);
		}
		else if(object.equals(lblCompa)) {
			lblCompa.setBorder(cbTrang);	
		}
		else if(object.equals(lblHopBut)) {
			lblHopBut.setBorder(cbTrang);
		}
		else if(object.equals(lblTapVo)) {
			lblTapVo.setBorder(cbTrang);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object object = e.getSource();
		if(object.equals(btnThemSach)) {
			new FormThemSach();
			FormThemSach.txtTenSach.requestFocus();
		}
		if(object.equals(btnThemExcel)) { 
			File excelFile;
			FileInputStream excelFIS = null;
			BufferedInputStream excelBIS = null;  
			XSSFWorkbook excelJtableImport = null;


			String defaultCurrentDirectoryPath = "D:\\"; 
			JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
			int excelChooser = excelFileChooser.showOpenDialog(null);
			ArrayList<Sach> dsSach = new ArrayList<Sach>();
			if(excelChooser == JFileChooser.APPROVE_OPTION){
				int hoi=JOptionPane.showConfirmDialog(this, "Sách sẽ được thêm vào từ excel","Chú ý",JOptionPane.YES_NO_OPTION);
				if(hoi==JOptionPane.YES_OPTION) { 
					try {
						excelFile = excelFileChooser.getSelectedFile();
						excelFIS = new FileInputStream(excelFile);
						excelBIS = new BufferedInputStream(excelFIS);
						excelJtableImport = new XSSFWorkbook(excelFIS);
						XSSFSheet excelSheet = excelJtableImport.getSheet("Sheet1");

						for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
							maSach = "";					 
							String mm = dinhDangMaSach();					
							XSSFRow excelRow = excelSheet.getRow(row);
							//String exelMa = excelRow.getCell(0).getStringCellValue();
							String exelTenSP = excelRow.getCell(0).getStringCellValue();
							double exelGiaBan = excelRow.getCell(1).getNumericCellValue();
							String exelNhaCungCap = excelRow.getCell(2).getStringCellValue();
							int exelSoLuong = (int) excelRow.getCell(3).getNumericCellValue();
							//hinhAnh = excelRow.getCell(4).getStringCellValue();
							hinhAnh = "src//img//chuacohinhanh.png";
							String exelLoaiSP = excelRow.getCell(4).getStringCellValue();
							boolean exelTinhTrang = excelRow.getCell(5).getBooleanCellValue();
							String exelTacGia = excelRow.getCell(6).getStringCellValue();
							int exelNamXuatBan = (int) excelRow.getCell(7).getNumericCellValue();
							String exelNhaXB = excelRow.getCell(8).getStringCellValue();
							int exelSoTrang = (int) excelRow.getCell(9).getNumericCellValue();
							listLoaiSach = daoLoaiSach.getAllLoaiSach();
							String loaiSach = null;
							for (int i = 0; i < listLoaiSach.size(); i++) {
								if (excelRow.getCell(10).getStringCellValue()
										.equalsIgnoreCase(listLoaiSach.get(i).getTenLoaiSach())) {
									loaiSach = listLoaiSach.get(i).getMaLoaiSach();
								}
							}
							Sach s = new Sach(mm,exelTenSP,exelGiaBan,exelNhaCungCap,exelSoLuong, hinhAnh, exelLoaiSP ,
									exelTinhTrang, exelTacGia, exelNamXuatBan, exelNhaXB, exelSoTrang,new LoaiSach(loaiSach));			
							dsSach.add(s);											
							sachDao.themSach(s);
							//LuuAnh();		
						}				
					} catch (FileNotFoundException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					catch (IOException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
				inDanhSachSach(dsSach);
			}
		}
		else if(object.equals(btnThemDCHTExcel)) { 
			File excelFile;
			FileInputStream excelFIS = null;
			BufferedInputStream excelBIS = null;  
			XSSFWorkbook excelJtableImport = null;

			String defaultCurrentDirectoryPath = "D:\\"; 
			JFileChooser excelFileChooser = new JFileChooser(defaultCurrentDirectoryPath);
			int excelChooser = excelFileChooser.showOpenDialog(null);
			listDCHT = new ArrayList<DungCuHocTap>();
			if(excelChooser == JFileChooser.APPROVE_OPTION){
				int hoi=JOptionPane.showConfirmDialog(this, "Dụng cụ học tập sẽ được thêm vào từ excel","Chú ý",JOptionPane.YES_NO_OPTION);
				if(hoi==JOptionPane.YES_OPTION) { 
					try {
						excelFile = excelFileChooser.getSelectedFile();
						excelFIS = new FileInputStream(excelFile);
						excelBIS = new BufferedInputStream(excelFIS);
						excelJtableImport = new XSSFWorkbook(excelFIS);
						XSSFSheet excelSheet = excelJtableImport.getSheet("Sheet1");

						for (int row = 1; row <= excelSheet.getLastRowNum(); row++) {
							maDCHT = "";					 
							String maDungCuHocTap = dinhDangMaDCHT();					
							XSSFRow excelRow = excelSheet.getRow(row);
							//String exelMa = excelRow.getCell(0).getStringCellValue();
							String exelTenSP = excelRow.getCell(0).getStringCellValue();
							double exelGiaBan = excelRow.getCell(1).getNumericCellValue();
							String exelNhaCungCap = excelRow.getCell(2).getStringCellValue();
							int exelSoLuong = (int) excelRow.getCell(3).getNumericCellValue();
							//hinhAnh = excelRow.getCell(4).getStringCellValue();
							hinhAnh = "src//img//chuacohinhanh.png";
							String exelLoaiSP = excelRow.getCell(4).getStringCellValue();
							boolean exelTinhTrang = excelRow.getCell(5).getBooleanCellValue();
							String exelThuongHieu = excelRow.getCell(6).getStringCellValue();
							String exelXuatXu =  excelRow.getCell(7).getStringCellValue();
							String exelChatLieu = excelRow.getCell(8).getStringCellValue();
							listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
							String loaiDCHT = null;
							for (int i = 0; i < listLoaiDCHT.size(); i++) {
								if (excelRow.getCell(9).getStringCellValue()
										.equalsIgnoreCase(listLoaiDCHT.get(i).getTenLoaiDCHT())) {
									loaiDCHT = listLoaiDCHT.get(i).getMaLoaiDCHT();
								}
							}
							DungCuHocTap dcht = new DungCuHocTap(maDungCuHocTap,exelTenSP,exelGiaBan,exelNhaCungCap,exelSoLuong
									, hinhAnh, exelLoaiSP ,
									exelTinhTrang, exelThuongHieu, exelXuatXu, exelChatLieu,new LoaiDungCuHocTap(loaiDCHT));			
							listDCHT.add(dcht);											
							daoDCHT.themDCHT(dcht);		
						}				
					} catch (FileNotFoundException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
					catch (IOException e2) {
						JOptionPane.showMessageDialog(null, e2.getMessage());
					}
				}
				inDanhSachDCHT(listDCHT);
			}
		}
		if(object.equals(btnThemDCHT)) {
			new FormThemDCHT();
			FormThemDCHT.txtTenDCHT.requestFocus();
		}
		if(object.equals(btnTim)) {
			listSach = sachDao.timKiemtenSachTacGia(txtTim.getText());	
			if(listSach.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy tên sách hoặc tác giả");
				txtTim.requestFocus();
				txtTim.selectAll();
			}
			else {
				inDanhSachSach(listSach);
			}
		}
		if(object.equals(btnTimDCHT)) {
			listDCHT = daoDCHT.timKiemtenDCHT(txtTimDCHT.getText());	
			if(listDCHT.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy tên dụng cụ học tập");
				txtTimDCHT.requestFocus();
				txtTimDCHT.selectAll();
			}
			else {
				inDanhSachDCHT(listDCHT);
			}
		}
		if(object.equals(cbxSapXepGiaSLT)) {
			if(cbxSapXepGiaSLT.getSelectedItem().equals("Giá cao nhất")) {
				listSach = sachDao.sapXepGiaSLT("desc","donGia");		
				inDanhSachSach(listSach);
			}
			else if(cbxSapXepGiaSLT.getSelectedItem().equals("Giá thấp nhất")) {
				listSach = sachDao.sapXepGiaSLT("asc", "donGia");		
				inDanhSachSach(listSach);
			}
			else if(cbxSapXepGiaSLT.getSelectedItem().equals("Số lượng tồn nhiều nhất")) {
				listSach = sachDao.sapXepGiaSLT("desc", "soLuong");		
				inDanhSachSach(listSach);
			}
			else if(cbxSapXepGiaSLT.getSelectedItem().equals("Số lượng tồn thấp nhất")) {
				listSach = sachDao.sapXepGiaSLT("asc", "soLuong");		
				inDanhSachSach(listSach);
			}
		}
		if(object.equals(cbxSapXepGiaDCHT)) {
			if(cbxSapXepGiaDCHT.getSelectedItem().equals("Giá cao nhất")) {
				listDCHT = daoDCHT.sapXepGiaSLT("desc","donGia");		
				inDanhSachDCHT(listDCHT);
			}
			else if(cbxSapXepGiaDCHT.getSelectedItem().equals("Giá thấp nhất")) {
				listDCHT = daoDCHT.sapXepGiaSLT("asc", "donGia");		
				inDanhSachDCHT(listDCHT);
			}
			else if(cbxSapXepGiaDCHT.getSelectedItem().equals("Số lượng tồn nhiều nhất")) {
				listDCHT = daoDCHT.sapXepGiaSLT("desc", "soLuong");		
				inDanhSachDCHT(listDCHT);
			}
			else if(cbxSapXepGiaDCHT.getSelectedItem().equals("Số lượng tồn thấp nhất")) {
				listDCHT = daoDCHT.sapXepGiaSLT("asc", "soLuong");		
				inDanhSachDCHT(listDCHT);
			}
		}
		if(object.equals(cbxNhaXB)) {
			listSach = sachDao.locNhaXuatBan((String) cbxNhaXB.getSelectedItem());				
			inDanhSachSach(listSach);
		}
		if(object.equals(cbxLocThuongHieu)) {
			listDCHT = daoDCHT.locThuongHieu((String) cbxLocThuongHieu.getSelectedItem());				
			inDanhSachDCHT(listDCHT);
		}
		if(object.equals(cbxXuatXu)) {
			listDCHT = daoDCHT.locXuatXu((String) cbxXuatXu.getSelectedItem());				
			inDanhSachDCHT(listDCHT);
		}
		if(object.equals(cbxTinhTrang)) {
			listSach = sachDao.locTinhTrang((String) cbxTinhTrang.getSelectedItem());	
			pnDSS.removeAll();
			pnDSS.revalidate();
			if(((String) cbxTinhTrang.getSelectedItem() == "Hết hàng")) {
				listSach = sachDao.locTinhTrangHetHang();
				pnDSS.removeAll();
				pnDSS.revalidate();
				inDanhSachSach(listSach);
			}
			try {  
				for(Sach sach : listSach) {  
					if(sach.getTinhTrang()) {
						if(((String) cbxTinhTrang.getSelectedItem() == "Còn hàng") && sach.getSoLuong() > 0){
							if(listSach.size() <=2) {
								JPanel pnItem = new SachTrongQLSP(sach); 			
								GridLayout layout = new GridLayout(4,2);
								pnDSS.setLayout(layout);
								layout.setVgap(1);
								layout.setHgap(15);
								pnDSS.add(pnItem,BorderLayout.NORTH); 
								pnCenterSach.add(pnDSS,BorderLayout.CENTER);
							}
							else {
								JPanel pnItem = new SachTrongQLSP(sach); 			
								GridLayout layout = new GridLayout(listSach.size(),2);
								pnDSS.setLayout(layout);
								layout.setVgap(1);
								layout.setHgap(15);
								pnDSS.add(pnItem); 
								pnCenterSach.add(pnDSS,BorderLayout.CENTER);
							}
						}
					}
					else {
						if(((String) cbxTinhTrang.getSelectedItem() == "Ngừng kinh doanh")){							
							inDanhSachSach(listSach);
						}
					}

				}
				pnCenterSach.revalidate();		 
			} catch (Exception e2) { 
				JOptionPane.showMessageDialog(this, e2); }
		}
		if(object.equals(cbxTinhTrangDCHT)) {
			listDCHT = daoDCHT.locTinhTrang((String) cbxTinhTrangDCHT.getSelectedItem());	
			pnDSDCHT.removeAll();
			pnDSDCHT.revalidate();
			if(((String) cbxTinhTrangDCHT.getSelectedItem() == "Hết hàng")) {
				listDCHT = daoDCHT.locTinhTrangHetHang();
				pnDSDCHT.removeAll();
				pnDSDCHT.revalidate();
				inDanhSachDCHT(listDCHT);
			}
			try {  
				for(DungCuHocTap dcht : listDCHT) {  
					if(dcht.getTinhTrang()) {
						if(((String) cbxTinhTrangDCHT.getSelectedItem() == "Còn hàng") && dcht.getSoLuong() > 0){
							if(listDCHT.size() <=2) {
								JPanel pnItem = new DungCuHocTapTrongQLSP(dcht); 			
								GridLayout layout = new GridLayout(4,2);
								pnDSDCHT.setLayout(layout);
								layout.setVgap(1);
								layout.setHgap(15);
								pnDSDCHT.add(pnItem,BorderLayout.NORTH); 
								pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);
							}
							else {
								JPanel pnItem = new DungCuHocTapTrongQLSP(dcht); 			
								GridLayout layout = new GridLayout(listDCHT.size(),2);
								pnDSDCHT.setLayout(layout);
								layout.setVgap(1);
								layout.setHgap(15);
								pnDSDCHT.add(pnItem); 
								pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);
							}
						}
					}
					else {
						if(((String) cbxTinhTrangDCHT.getSelectedItem() == "Ngừng kinh doanh")){							
							inDanhSachDCHT(listDCHT);
						}
					}

				}
				pnCenterDCHT.revalidate();		 
			} catch (Exception e2) { 
				JOptionPane.showMessageDialog(this, e2); }
		}
	}
	public static void inDanhSachSach(ArrayList<Sach> listSach) {
		pnDSS.removeAll();
		pnDSS.revalidate();
		if(listSach.size() == 0) {
			Font font = new Font("Arial",Font.BOLD , 18);
			JPanel pnKhongCoHang = new JPanel();
			pnKhongCoHang.setBackground(Color.WHITE);
			pnKhongCoHang.setLayout(new BoxLayout(pnKhongCoHang, BoxLayout.X_AXIS));
			JLabel lblKhongCoHang = new JLabel("Không có mặt hàng nào hết hàng");	
			lblKhongCoHang.setFont(font);
			pnKhongCoHang.add(Box.createHorizontalStrut(35));
			pnKhongCoHang.add(lblKhongCoHang);
			pnDSS.add(pnKhongCoHang,BorderLayout.NORTH); 
			pnCenterSach.add(pnDSS,BorderLayout.CENTER);
		}
	
		else {
			try {  
				for(Sach sach : listSach) {  
					if(listSach.size() <=2) {
						JPanel pnItem = new SachTrongQLSP(sach); 			
						GridLayout layout = new GridLayout(4,2);
						pnDSS.setLayout(layout);
						layout.setVgap(1);
						layout.setHgap(15);
						pnDSS.add(pnItem,BorderLayout.NORTH); 
						pnCenterSach.add(pnDSS,BorderLayout.CENTER);
					}
					else {
						JPanel pnItem = new SachTrongQLSP(sach); 			
						GridLayout layout = new GridLayout(listSach.size(),2);
						pnDSS.setLayout(layout);
						layout.setVgap(1);
						layout.setHgap(15);
						pnDSS.add(pnItem); 
						pnCenterSach.add(pnDSS,BorderLayout.CENTER);
					}
				}
				pnCenterSach.revalidate();		 
			} catch (Exception e2) { 
				JOptionPane.showMessageDialog(null, e2); }
		}
	}

	public void inSach(Sach sach) {
		pnDSS.removeAll();
		pnDSS.revalidate();
		try {  											
			JPanel pnItem = new SachTrongQLSP(sach); 			
			GridLayout layout = new GridLayout(4,2);
			//BorderLayout layout = new BorderLayout();	
			pnDSS.setLayout(layout);
			layout.setVgap(1);
			layout.setHgap(15);
			pnDSS.add(pnItem,BorderLayout.NORTH); 
			pnCenterSach.add(pnDSS,BorderLayout.CENTER);						
			pnCenterSach.revalidate();		 
		} catch (Exception e2) { 
			JOptionPane.showMessageDialog(this, e2); }
	}
	public static void inDanhSachDCHT(ArrayList<DungCuHocTap> dungCuHocTap) {
		pnDSDCHT.removeAll();
		pnDSDCHT.revalidate();
		if(listDCHT.size() == 0) {
			Font font = new Font("Arial",Font.BOLD , 18);
			JPanel pnKhongCoHang = new JPanel();
			pnKhongCoHang.setBackground(Color.WHITE);
			pnKhongCoHang.setLayout(new BoxLayout(pnKhongCoHang, BoxLayout.X_AXIS));
			JLabel lblKhongCoHang = new JLabel("Không có mặt hàng nào hết hàng");	
			lblKhongCoHang.setFont(font);
			pnKhongCoHang.add(Box.createHorizontalStrut(35));
			pnKhongCoHang.add(lblKhongCoHang);
			pnDSDCHT.add(pnKhongCoHang,BorderLayout.NORTH); 
			pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);
		}
		
		else {
			try {  
				for(DungCuHocTap dcht : listDCHT) {  
					if(listDCHT.size() <=2) {
						JPanel pnItem = new DungCuHocTapTrongQLSP(dcht); 			
						GridLayout layout = new GridLayout(4,2);
						pnDSDCHT.setLayout(layout);
						layout.setVgap(1);
						layout.setHgap(15);
						pnDSDCHT.add(pnItem,BorderLayout.NORTH); 
						pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);
					}
					else {
						JPanel pnItem = new DungCuHocTapTrongQLSP(dcht); 			
						GridLayout layout = new GridLayout(listDCHT.size(),2);
						pnDSDCHT.setLayout(layout);
						layout.setVgap(1);
						layout.setHgap(15);
						pnDSDCHT.add(pnItem); 
						pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);
					}
				}
				pnCenterDCHT.revalidate();		 
			} catch (Exception e2) { 
				JOptionPane.showMessageDialog(null, e2); }
		}
	}
	public void inDCHT(DungCuHocTap dcht) {
		pnDSDCHT.removeAll();
		pnDSDCHT.revalidate();
		try {  											
			JPanel pnItem = new DungCuHocTapTrongQLSP(dcht); 			
			GridLayout layout = new GridLayout(3,2);
			pnDSDCHT.setLayout(layout);
			layout.setVgap(1);
			layout.setHgap(15);
			pnDSDCHT.add(pnItem,BorderLayout.NORTH); 
			pnCenterDCHT.add(pnDSDCHT,BorderLayout.CENTER);						
			pnCenterDCHT.revalidate();		 
		} catch (Exception e2) { 
			JOptionPane.showMessageDialog(this, e2); }
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
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			listSach = sachDao.timKiemtenSachTacGia(txtTim.getText());	
			if(listSach.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy tên sách hoặc tác giả");
			}
			else {
				inDanhSachSach(listSach);
			}
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
	private static HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
		HSSFFont font = workbook.createFont();
		font.setBold(true);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		return style;
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
			FileOutputStream ou = new FileOutputStream("img\\"+XuLyLayTenAnh(DinhDanhLaiNguonAnh(hinhAnh)));
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
	private String dinhDangMaSach() {

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
		return maSach;
	}
	private String dinhDangMaDCHT() {
		maDCHT = "";
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
		return maDCHT;
	}
}
