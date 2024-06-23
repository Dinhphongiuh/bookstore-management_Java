package UI;

import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet.ColorAttribute;
import javax.swing.text.JTextComponent;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.DAO_ChiTietHoaDonDCHT;
import dao.DAO_ChiTietHoaDonSach;
import dao.DAO_DungCuHocTap;
import dao.DAO_HoaDon;
import dao.DAO_KhachHang;
import dao.DAO_NhanVien;
import dao.DAO_Sach;
import entity.ChiTietHoaDonDCHT;
import entity.ChiTietHoaDonSach;
import entity.DungCuHocTap;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Sach;
import entity.SanPham;
import others.InPDF;
import others.PrintSupport;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ActionEvent;
import java.awt.SystemColor;
import java.awt.Window;

import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.AncestorListener;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ListSelectionListener;


public class QuanLiHoaDon extends JPanel
		implements ActionListener, MouseListener, FocusListener, WindowListener, ListSelectionListener {

	private static final long serialVersionUID = 1L;

	private static DAO_HoaDon hoaDonDao;
	private static String maNhanVien;
	private static String prefixMaHoaDon = "HD00";
	private static int tongSoLuong = 0;
	public static double tongTongTien = 0;
	public static double tienKhachDua = 0;
	public static JTextField txtSDT;
	private static JTextField txtDiaChi;
	private JTextField txtMaNhanVien;
	private JTextField txtTenNhanVien;

	public static JTextField txtNgayLHD;
	private static JTextField txtMaSP;
	private JTextField txtTenSanPham;
	private JTextField txtThuongHieu;
	private JTextField txtTacGia;
	private JTextField txtNXB;
	private static JTextField txtSoLuong;
	private static DefaultTableModel tableModel;
	public static JTable table;
	public static JTextField txtTSoLuong;
	public static JTextField txtTongTien;
	private static JTextField tfMaHD;
	private JDateChooser dcNgayLHD;
	private DefaultTableModel tableModel2;
	private DefaultTableModel tableModel1;
	private JTextField txtTenKH;
	private JTextField txtMaKH;
	public static JTextField tfTenKH;
	private static JTextField tfMaKH;
	private JTextField txtSDTTimKiem;
	private static DAO_NhanVien nhanVienDao;
	private static DAO_KhachHang khachHangDao;
	public static QuanLiHoaDon qlhd;
	private static List<ChiTietHoaDonSach> dsChiTietHDSachMua = new ArrayList<ChiTietHoaDonSach>();
	private static List<ChiTietHoaDonDCHT> dsChiTietHDDCHTMua = new ArrayList<ChiTietHoaDonDCHT>();
	private JButton btnThemSP;
	private JButton btnXoaTrang;
	private JButton btnXoaSPCTHD;
	private JButton btnThanhToan;
	private static DAO_ChiTietHoaDonSach chiTietHoaDonSachDao;
	private static DAO_ChiTietHoaDonDCHT chiTietHoaDonDCHTDao;
	private static String tenNhanVien;
	public static DAO_Sach sachDao;
	public static DAO_DungCuHocTap dhctDao;
	private int hoaDonChonHienTai = -1;
	private JTextField txtMaHD;
	private String maHoaDonDuocChon;

	private void xoaRongTableDanhSachHD() {
		tableModel1.setRowCount(0);
		tableModel1.getDataVector().removeAllElements();
		tableModel1.fireTableDataChanged();
	}

	private void xoaRongTableDanhSachHDDSCTSanPhamMua() {
		tableModel.setRowCount(0);
		tableModel.getDataVector().removeAllElements();
		tableModel.fireTableDataChanged();
	}

	public void thongBaoKhongTonTai(String thongbao) {
		JOptionPane.showMessageDialog(this, thongbao);
	}

	@SuppressWarnings("serial")
	public QuanLiHoaDon(String maNhanVien) 
	{
		this.maNhanVien = maNhanVien;
		chiTietHoaDonSachDao = new DAO_ChiTietHoaDonSach();
		chiTietHoaDonDCHTDao = new DAO_ChiTietHoaDonDCHT();
		hoaDonDao = new DAO_HoaDon();
		khachHangDao = new DAO_KhachHang();
		nhanVienDao = new DAO_NhanVien();
		sachDao = new DAO_Sach();
		dhctDao = new DAO_DungCuHocTap();
		int currentLength = hoaDonDao.laySoHoaDon();
		String maHD = this.prefixMaHoaDon + (++currentLength);

		setSize(new Dimension(1000, 1200));

		JPanel pnContainer = new JPanel();
		pnContainer.setSize(new Dimension(1000, 1000));

//		HEADER
		JPanel pTab1 = new JPanel();
		pTab1.setBounds(10, 101, 1500, 687);
		pTab1.setBackground(Color.RED);
		pnContainer.add(pTab1);
		pTab1.setLayout(null);

		JPanel pTab2 = new JPanel();
		pTab2.setVisible(false);
		pTab2.setBounds(10, 101, 1500, 687);
		pnContainer.add(pTab2);
		pTab2.setLayout(null);
		JPanel panelTab = new JPanel();
		JLabel lblNewLabel_1 = new JLabel("Quản lý thông tin hóa đơn");
		lblNewLabel_1.setBounds(606, 0, 526, 72);
		panelTab.add(lblNewLabel_1);
		lblNewLabel_1.setForeground(new Color(184, 134, 11));
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 25));

		// Hóa đơn

		JButton btn1 = new JButton("");
		btn1.setBackground(Color.WHITE);
		btn1.setFocusable(false);
		btn1.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\bill.png"));
		btn1.setBorderPainted(false);
		btn1.setBounds(10, 10, 93, 43);
		panelTab.add(btn1);

		panelTab.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
		panelTab.setBackground(Color.WHITE);
		panelTab.setBounds(0, 0, 1530, 78);
		pnContainer.add(panelTab);
		panelTab.setLayout(null);

		JLabel lbl1 = new JLabel("Hóa đơn");
		lbl1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lbl1.setBounds(25, 55, 77, 23);
		panelTab.add(lbl1);
		
		JButton btn2 = new JButton("");
		btn2.setBounds(135, 10, 93, 43);
		btn2.setBackground(Color.WHITE);
		btn2.setFocusable(false);
		btn2.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\list-alt-solid.png"));
		btn2.setBorderPainted(false);
//		panelTab.add(btn2);

		JLabel lblNewLabel = new JLabel("Danh sách hóa đơn");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(133, 55, 200, 23);
//		panelTab.add(lblNewLabel);

		// Sự kiện btn1, btn2

		// >>>>>>>>>>>>>>>>>>>Tab hóa đơn + danh sách hóa đơn
		btn1.setBackground(new Color(255, 153, 51));
		btn1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn1.setBackground(new Color(255, 153, 51));
				btn2.setBackground(Color.white);
				txtSDT.requestFocus();
				pTab1.setVisible(true);
				pTab2.setVisible(false);
			}
		});
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btn2.setBackground(new Color(255, 153, 51));
				btn1.setBackground(Color.white);
				pTab1.setVisible(false);
				pTab2.setVisible(true);
			}
		});
		// BODY
		int bodyWidth = 1500, bodyHeight = 700;
		int bodyWidthLeft = 300;
		JPanel pnContainerBody = new JPanel();
		pnContainerBody.setLayout(new BoxLayout(pnContainerBody, BoxLayout.X_AXIS));
		pnContainerBody.setBounds(0, 0, bodyWidth, bodyHeight);
		pTab1.add(pnContainerBody);
//		BODY LEFT
		JPanel pnBodyLeft = new JPanel();
		pnBodyLeft.setBounds(0, 0, bodyWidthLeft, bodyHeight);
		pnBodyLeft.setMaximumSize(new Dimension(bodyWidthLeft, bodyHeight));

		pnContainerBody.add(pnBodyLeft);
//		BODY LEFT panel KH
		JPanel pThongTinKhachHang = new JPanel();
		pThongTinKhachHang.setLayout(null);
		pThongTinKhachHang.setMaximumSize(new Dimension(bodyWidthLeft, 300));
		pThongTinKhachHang.setPreferredSize(new Dimension(bodyWidthLeft, bodyHeight / 2 - 40));
		TitledBorder t = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông Tin Khách Hàng", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		t.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		pThongTinKhachHang.setBorder(t);
		pThongTinKhachHang.setBackground(SystemColor.control);
		pnBodyLeft.add(pThongTinKhachHang);

//		

		JLabel lbDiaChi2 = new JLabel("Mã khách hàng");
		lbDiaChi2.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbDiaChi2.setBounds(10, 70, 120, 28);
		pThongTinKhachHang.add(lbDiaChi2);

		tfMaKH = new JTextField();
		tfMaKH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfMaKH.setBounds(120, 70, 150, 27);
		tfMaKH.setEditable(false);
		pThongTinKhachHang.add(tfMaKH);

		JLabel lbDiaChi1 = new JLabel("Tên khách hàng");
		lbDiaChi1.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbDiaChi1.setBounds(10, 120, 120, 28);
		pThongTinKhachHang.add(lbDiaChi1);

		tfTenKH = new JTextField();
		tfTenKH.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfTenKH.setBounds(120, 120, 150, 27);
		tfTenKH.setEditable(false);
		pThongTinKhachHang.add(tfTenKH);

		JLabel lbSDT = new JLabel("SĐT");
		lbSDT.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbSDT.setBounds(10, 170, 120, 28);
		pThongTinKhachHang.add(lbSDT);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSDT.setBounds(120, 170, 150, 27);
		txtSDT.requestFocusInWindow();
//		txtSDT.getParent().requestChildFocus(txtSDT);

		pThongTinKhachHang.add(txtSDT);
//
		JLabel lbDiaChi = new JLabel("Địa chỉ");
		lbDiaChi.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbDiaChi.setBounds(10, 220, 120, 28);
		pThongTinKhachHang.add(lbDiaChi);

		txtDiaChi = new JTextField();
		txtDiaChi.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtDiaChi.setBounds(120, 220, 150, 27);
		txtDiaChi.setEditable(false);
		pThongTinKhachHang.add(txtDiaChi);

//		BODY LEFT panel NHAN VIEN
		JPanel pThongTinNhanVien = new JPanel();
		pThongTinNhanVien.setMaximumSize(new Dimension(bodyWidthLeft, 300));
		pThongTinNhanVien.setPreferredSize(new Dimension(bodyWidthLeft, bodyHeight / 2 - 40));
		TitledBorder t1 = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông Tin Khác", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		t1.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		pThongTinNhanVien.setBorder(t1);
		pThongTinNhanVien.setBackground(SystemColor.control);
		pnBodyLeft.add(pThongTinNhanVien);
		pThongTinNhanVien.setLayout(null);

		JLabel lblNV = new JLabel("Nhân viên");
		lblNV.setBackground(new Color(230, 230, 250));
		lblNV.setBounds(10, 50, 87, 26);
		lblNV.setFont(new Font("Tahoma", Font.BOLD, 13));
		pThongTinNhanVien.add(lblNV);

		txtMaNhanVien = new JTextField();
		txtMaNhanVien.setEditable(false);
		txtMaNhanVien.setText(maNhanVien);
		txtMaNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMaNhanVien.setBounds(120, 50, 120, 27);
		pThongTinNhanVien.add(txtMaNhanVien);

		txtTenNhanVien = new JTextField();
		txtTenNhanVien.setEditable(false);
		NhanVien nhanVien = nhanVienDao.layNhanVienTheoMa(maNhanVien);
		txtTenNhanVien.setText(nhanVien.getTenNV());
		txtTenNhanVien.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTenNhanVien.setBounds(120, 90, 150, 27);
		tenNhanVien = nhanVien.getTenNV();
		pThongTinNhanVien.add(txtTenNhanVien);

		JLabel lblMaHD = new JLabel("Mã Hóa Đơn");

		lblMaHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblMaHD.setBounds(10, 140, 92, 24);
		pThongTinNhanVien.add(lblMaHD);

		tfMaHD = new JTextField();
		tfMaHD.setEditable(false);
		// txtBillNum.setText(maHD);
		// txtBillNum.setBackground(Color.WHITE);
		tfMaHD.setText(maHD);
		tfMaHD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		tfMaHD.setBounds(120, 140, 150, 27);
		pThongTinNhanVien.add(tfMaHD);

		JLabel lbNgayLHD = new JLabel("Ngày LHD");
		lbNgayLHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		lbNgayLHD.setBounds(10, 190, 86, 24);
		pThongTinNhanVien.add(lbNgayLHD);

		txtNgayLHD = new JTextField();
		txtNgayLHD.setEditable(false);
		// txtDateBill.setBackground(Color.WHITE);
		txtNgayLHD.setText(LocalDate.now().toString());
		txtNgayLHD.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNgayLHD.setBounds(120, 190, 150, 27);
		pThongTinNhanVien.add(txtNgayLHD);
//		CENTER
		JPanel pnBodyCenter = new JPanel();
		pnBodyCenter.setBounds(bodyWidthLeft, 0, 20, bodyHeight);
		pnBodyCenter.setMaximumSize(new Dimension(20, bodyHeight));
		pnContainerBody.add(pnBodyCenter);
//		BODY RIGHT
		JPanel pnBodyRight = new JPanel();
		pnBodyRight.setLayout(null);
		int widthRight = bodyWidth - bodyWidthLeft;
		pnBodyRight.setBounds(bodyWidthLeft, 0, widthRight, bodyHeight);
		pnBodyRight.setMaximumSize(new Dimension(widthRight, bodyHeight));
		pnContainerBody.add(pnBodyRight);
//		pnBodyRight.setBackground(Color.WHITE);

//		// Thông tin sản phẩm bán
//
		JPanel pProduct_Bill = new JPanel();
		pProduct_Bill.setLayout(null);
		pProduct_Bill.setBounds(0, 6, widthRight - 200, 200);
		pTab1.add(pProduct_Bill);
		TitledBorder t3 = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Thông Tin Sản Phẩm", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		t3.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		pProduct_Bill.setBorder(t3);
		pProduct_Bill.setBackground(SystemColor.control);
		pProduct_Bill.setLayout(null);

		JPanel pThemSP_Bill = new JPanel();
		pThemSP_Bill.setToolTipText("");
		pThemSP_Bill.setBounds(10, 20, widthRight - 225, 170);
		pProduct_Bill.add(pThemSP_Bill);
		pThemSP_Bill.setLayout(null);

		JLabel lblMaSP = new JLabel("Mã sản phẩm");
		lblMaSP.setBounds(10, 20, 108, 25);
		lblMaSP.setFont(new Font("Tahoma", Font.BOLD, 13));
		pThemSP_Bill.add(lblMaSP);

		txtMaSP = new JTextField();
		txtMaSP.setBackground(Color.white);
		txtMaSP.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtMaSP.setBounds(120, 20, 200, 30);
		pThemSP_Bill.add(txtMaSP);

		JLabel lblTenSP = new JLabel("Tên sản phẩm");
		lblTenSP.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTenSP.setBounds(350, 20, 128, 28);
		pThemSP_Bill.add(lblTenSP);

		txtTenSanPham = new JTextField();
		txtTenSanPham.setBackground(Color.white);
		txtTenSanPham.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTenSanPham.setBounds(460, 20, 200, 30);
		txtTenSanPham.setEditable(false);
		pThemSP_Bill.add(txtTenSanPham);

		JLabel lblTacGia = new JLabel("Tác giả");
		lblTacGia.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTacGia.setBounds(10, 70, 108, 25);
		pThemSP_Bill.add(lblTacGia);

		txtTacGia = new JTextField();
		txtTacGia.setBackground(Color.white);
		txtTacGia.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtTacGia.setBounds(120, 70, 200, 30);
		txtTacGia.setEditable(false);
		pThemSP_Bill.add(txtTacGia);

		JLabel lblThuongHieu = new JLabel("Thương hiệu");
		lblThuongHieu.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblThuongHieu.setBounds(10, 120, 100, 28);
		pThemSP_Bill.add(lblThuongHieu);

		txtThuongHieu = new JTextField();
		txtThuongHieu.setBackground(Color.white);
		txtThuongHieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtThuongHieu.setBounds(120, 120, 200, 30);
		txtThuongHieu.setEditable(false);
		pThemSP_Bill.add(txtThuongHieu);

		JLabel lblNXB = new JLabel("Năm xuất bản");
		lblNXB.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNXB.setBounds(350, 70, 128, 28);
		pThemSP_Bill.add(lblNXB);

		txtNXB = new JTextField();
		txtNXB.setBackground(Color.white);
		txtNXB.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtNXB.setBounds(460, 70, 200, 30);
		txtNXB.setEditable(false);
		pThemSP_Bill.add(txtNXB);

		JLabel lblSoLuong = new JLabel("Số lượng");
		lblSoLuong.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSoLuong.setBounds(350, 120, 70, 28);
		pThemSP_Bill.add(lblSoLuong);

		txtSoLuong = new JTextField();
		txtSoLuong.setBackground(Color.white);
		txtSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtSoLuong.setBounds(460, 120, 200, 30);
		txtSoLuong.setText("0");
		pThemSP_Bill.add(txtSoLuong);

		JPanel panelFunction1 = new JPanel();
		panelFunction1.setBorder(new LineBorder(SystemColor.controlShadow));
		panelFunction1.setBounds(740, 0, 225, 158);

		panelFunction1.setBackground(SystemColor.controlHighlight);
		panelFunction1.setLayout(null);

		btnXoaTrang = new JButton("Xóa Trắng");
		btnXoaTrang.setBackground(new Color(255, 153, 51));
		btnXoaTrang.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\Clear-icon.png"));
		btnXoaTrang.setBounds(20, 24, 170, 51);
		btnXoaTrang.setFont(new Font("Tahoma", Font.BOLD, 13));
		panelFunction1.add(btnXoaTrang);

		btnThemSP = new JButton("Thêm Sản Phẩm");
		btnThemSP.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\add-icon.png"));
		btnThemSP.setBackground(new Color(255, 153, 51));
		btnThemSP.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnThemSP.setBounds(20, 90, 170, 51);
		panelFunction1.add(btnThemSP);

		pThemSP_Bill.add(panelFunction1);

		pnBodyRight.add(pProduct_Bill);

		//
		String[] header = { "STT", "Mã sản phẩm", "Tên sản phẩm", "Nhà xuất bản", "Tác giả", "Năm xuất bản",
				"Thương hiệu", "Số lượng", "Đơn giá", "Thành tiền" };
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(DefaultTableCellRenderer.RIGHT);

		tableModel = new DefaultTableModel(header, 0) {
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return true;
			}

		};

		// chỉnh value tại cột số lượng , sau đó set lại tổng số lương + tiền
		// lúc đầu thêm sản phẩm thì txtTSoLuong đa
		JPanel pnDSSP = new JPanel();
		pnDSSP.setBounds(0, 240, widthRight - 200, 300);
		pTab1.add(pnDSSP);
		TitledBorder t4 = new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Danh Sách SP Bán", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK);
		t4.setTitleFont(new Font("Tahoma", Font.PLAIN, 13));
		pnDSSP.setBorder(t4);
		pnDSSP.setBackground(SystemColor.control);
		pnDSSP.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 25, widthRight - 230, 250);
		pnDSSP.add(scrollPane);

		table = new JTable();
		table.setModel(tableModel);
		table.getTableHeader().setBackground(new Color(255, 153, 51));
		DecimalFormat formatter = new DecimalFormat("###,###,###.00");
		CellEditorListener ChangeNotification = new CellEditorListener() {
			int soLuongPre = 0;
			double thanhTienChenhLech = 0;

			@Override
			public void editingStopped(ChangeEvent e) {
				// TODO Auto-generated method stub
				int column = table.getSelectedColumn();

				int row = table.getSelectedRow();
//					lấy số lượng update
				String soLuongCapNhat = table.getValueAt(row, column).toString();
				String maSanPham = table.getValueAt(row, 1).toString();
//					tìm giá trị sản phẩm ban đầu

				if (maSanPham.startsWith("SPS")) {
					dsChiTietHDSachMua.forEach(sp -> {
						if (sp.getSanPham().getMaSanPham().equals(maSanPham)) {
							soLuongPre = sp.getSoLuong();
							sp.setSoLuong(Integer.valueOf(soLuongCapNhat));
							int soLuongCLSP = Integer.valueOf(soLuongCapNhat) - soLuongPre;

							double thanhTienSPHienTai = sp.getSanPham().getGiaBan();

							thanhTienChenhLech = soLuongCLSP * thanhTienSPHienTai;

							double thanhTienMoi = Integer.valueOf(soLuongCapNhat) * thanhTienSPHienTai;

							sp.setThanhTien(thanhTienMoi);
//								đi set table bằng tiền này
							tableModel.setValueAt(formatter.format(thanhTienMoi), row, column + 2);
						}
					});
				} else {
					dsChiTietHDDCHTMua.forEach(sp -> {
						if (sp.getSanPham().getMaSanPham().equals(maSanPham)) {
							soLuongPre = sp.getSoLuong();
							sp.setSoLuong(Integer.valueOf(soLuongCapNhat));
							int soLuongCLSP = Integer.valueOf(soLuongCapNhat) - soLuongPre;
							double thanhTienSPHienTai = sp.getSanPham().getGiaBan();
							thanhTienChenhLech = soLuongCLSP * sp.getThanhTien();
							double thanhTienMoi = Integer.valueOf(soLuongCapNhat) * thanhTienSPHienTai;
							sp.setThanhTien(thanhTienMoi);
							tableModel.setValueAt(formatter.format(thanhTienMoi), row, column + 2);
//								tableModel.setValueAt(e, row, column)
						}
					});
				}
//					
				int soLuongCL = Integer.valueOf(soLuongCapNhat) - soLuongPre;
//					
				tongSoLuong = tongSoLuong + soLuongCL;

				tongTongTien = tongTongTien + thanhTienChenhLech;
				txtTSoLuong.setText(String.valueOf(tongSoLuong));

				txtTongTien.setText(String.valueOf(formatter.format(tongTongTien)) + "VNĐ");
				soLuongPre = 0;
				thanhTienChenhLech = 0;

			}

			@Override
			public void editingCanceled(ChangeEvent e) {

			}
		};
		table.getDefaultEditor(String.class).addCellEditorListener(ChangeNotification);
		scrollPane.setViewportView(table);

		scrollPane.setViewportView(table);
		table.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.white);
		table.setRowHeight(20);
		table.setAutoCreateRowSorter(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		pnBodyRight.add(pnDSSP);
		// Tổng số lượng
		JPanel panelSoLuong = new JPanel();
		panelSoLuong.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelSoLuong.setBounds(0, 560, 250, 61);
		panelSoLuong.setBackground(SystemColor.control);
		panelSoLuong.setLayout(null);

		JLabel lbTongSoLuong = new JLabel("Tổng số lượng");
		lbTongSoLuong.setHorizontalAlignment(SwingConstants.LEFT);
		lbTongSoLuong.setBounds(10, 12, 150, 28);
		lbTongSoLuong.setForeground(new Color(255, 0, 0));
		lbTongSoLuong.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbTongSoLuong.setVerticalAlignment(SwingConstants.BOTTOM);
		panelSoLuong.add(lbTongSoLuong);
		txtTSoLuong = new JTextField();
		txtTSoLuong.setForeground(Color.RED);
		txtTSoLuong.setBackground(Color.WHITE);
		txtTSoLuong.setEditable(false);
//
		txtTSoLuong.setText("0 ");
		txtTSoLuong.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtTSoLuong.setBounds(142, 12, 100, 36);
		panelSoLuong.add(txtTSoLuong);
		pnBodyRight.add(panelSoLuong);
		// Thanh toán

		JPanel panelTotal = new JPanel();
		panelTotal.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTotal.setBounds(260, 560, 345, 61);
		panelTotal.setBackground(SystemColor.control);
		panelTotal.setLayout(null);

		JLabel lbTongTien = new JLabel("Tổng Tiền");
		lbTongTien.setHorizontalAlignment(SwingConstants.LEFT);
		lbTongTien.setBounds(10, 12, 100, 28);
		lbTongTien.setForeground(new Color(255, 0, 0));
		lbTongTien.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbTongTien.setVerticalAlignment(SwingConstants.BOTTOM);
		panelTotal.add(lbTongTien);
		txtTongTien = new JTextField();
		txtTongTien.setForeground(Color.RED);
		txtTongTien.setBackground(Color.WHITE);
		txtTongTien.setEditable(false);
//
		txtTongTien.setText("0 VNĐ");
		txtTongTien.setFont(new Font("Tahoma", Font.PLAIN, 17));
		txtTongTien.setBounds(142, 12, 190, 36);
		panelTotal.add(txtTongTien);

		pnBodyRight.add(panelTotal);

		JPanel panelFunction2 = new JPanel();
		panelFunction2.setLayout(null);
		panelFunction2.setBounds(610, 559, 400, 61);

		btnXoaSPCTHD = new JButton("Xóa SP CTHD");
		btnXoaSPCTHD.setBackground(new Color(255, 153, 51));

		btnXoaSPCTHD.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\thungrac.png"));
		btnXoaSPCTHD.setBounds(3, 0, 175, 61);
		btnXoaSPCTHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		panelFunction2.add(btnXoaSPCTHD);

		btnThanhToan = new JButton("Thanh Toán");// btnPayments

		btnThanhToan.setBackground(new Color(255, 153, 51));
		btnThanhToan.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\thanhToan.jpg"));
		btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnThanhToan.setBounds(205, 0, 175, 61);
		panelFunction2.add(btnThanhToan);

		pnBodyRight.add(panelFunction2);

		// Danh sách

		JPanel pSearchInfo = new JPanel();
		pSearchInfo.setBorder(
				new TitledBorder(null, "Thông tin tìm kiếm", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pSearchInfo.setBounds(0, 5, 900, 124);
		pTab2.add(pSearchInfo);
		pSearchInfo.setLayout(null);

		JLabel lblmaHD = new JLabel("Mã Hóa Đơn");
		lblmaHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblmaHD.setBounds(10, 31, 82, 27);
		pSearchInfo.add(lblmaHD);

		txtMaHD = new JTextField();
		txtMaHD.setBounds(146, 31, 208, 30);
		pSearchInfo.add(txtMaHD);
		txtMaHD.setColumns(10);

		JLabel lblSDTTimKiem = new JLabel("Số điện thoại");
		lblSDTTimKiem.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblSDTTimKiem.setBounds(10, 70, 107, 27);
		pSearchInfo.add(lblSDTTimKiem);

		txtSDTTimKiem = new JTextField();
		txtSDTTimKiem.setBounds(146, 70, 208, 30);
		pSearchInfo.add(txtSDTTimKiem);
		txtSDTTimKiem.setColumns(10);

		JLabel lblTenKH = new JLabel("Tên Khách Hàng");
		lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTenKH.setBounds(441, 31, 118, 19);
		pSearchInfo.add(lblTenKH);

		JLabel lblNgayLHD = new JLabel("Ngày Lập Hóa Đơn");
		lblNgayLHD.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNgayLHD.setBounds(441, 70, 133, 19);
		pSearchInfo.add(lblNgayLHD);

		txtTenKH = new JTextField();
		txtTenKH.setBounds(589, 31, 208, 30);
		pSearchInfo.add(txtTenKH);
		txtTenKH.setEditable(false);
		txtTenKH.setColumns(10);

		dcNgayLHD = new JDateChooser();
		dcNgayLHD.setBounds(589, 70, 141, 30);
		dcNgayLHD.setDateFormatString("yyyy-MM-dd");
		dcNgayLHD.setDate(Date.valueOf(LocalDate.now()));
		pSearchInfo.add(dcNgayLHD);

		JPanel pChucNang = new JPanel();
		pChucNang.setBorder(new TitledBorder(null, "Chức Năng", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pChucNang.setBounds(910, 5, 400, 124);
		pTab2.add(pChucNang);
		pChucNang.setLayout(null);

		JButton btnTimKiem = new JButton("Tìm kiếm");
		btnTimKiem.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\search-solid.jpg"));

		// btnXoaTrong.setIcon(new ImageIcon("/image/Recycle-Bin-Full-icon.png"));
		btnTimKiem.setBackground(new Color(255, 153, 51));
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnTimKiem.setBounds(20, 22, 153, 40);
		pChucNang.add(btnTimKiem);

		JButton btnXuatHD = new JButton("Xuất Hóa Đơn");
		btnXuatHD.setBackground(new Color(255, 153, 51));
		// btnXuatHD.setIcon(new ImageIcon("/image/Printer-icon.png"));
		btnXuatHD.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnXuatHD.setBounds(20, 75, 330, 37);
		pChucNang.add(btnXuatHD);

		JButton btnLamMoi = new JButton("Làm Mới");
		btnLamMoi.setBackground(new Color(255, 153, 51));
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setIcon(ResizeImage("C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\refresh.png"));
		btnLamMoi.setBounds(190, 22, 160, 40);
		pChucNang.add(btnLamMoi);

		JPanel pHoaDon = new JPanel();
		pHoaDon.setBorder(
				new TitledBorder(null, "Thông tin hóa đơn", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pHoaDon.setBounds(0, 139, 1300, 241);
		pTab2.add(pHoaDon);
		pHoaDon.setLayout(null);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(10, 20, 1270, 211);
		pHoaDon.add(scrollPane1);

		JTable table1 = new JTable();
		table1.getTableHeader().setBackground(new Color(255, 153, 51));
		scrollPane1.setViewportView(table1);
		String[] header1 = { "Mã hóa đơn", "Mã khách hàng", "Tên khách hàng", "Số điện thoại", "Mã nhân viên",
				"Tên nhân viên", "Ngày LHD", "Tổng Tiền" };
		tableModel1 = new DefaultTableModel(header1, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; // To change body of generated methods, choose Tools | Templates.
			}
		};
		scrollPane1.setViewportView(table1);
		table1.setModel(tableModel1);

		List<Object> listObj = hoaDonDao.getAllOrderByNow(maNhanVien);
		listObj.forEach((o) -> {
			tableModel1.addRow((Object[]) o);
		});

		table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				try {

					int selectedRow = table1.getSelectedRow();
					int totalRow = table1.getRowCount();

					if (selectedRow <= totalRow && selectedRow != hoaDonChonHienTai) {
						// Remove table
						tableModel2.setRowCount(0);
						hoaDonChonHienTai = selectedRow;
						String maHoaDon = table1.getValueAt(selectedRow, 0).toString();
						maHoaDonDuocChon = maHoaDon;
						DecimalFormat formatter = new DecimalFormat("###,###,###");
						List<ChiTietHoaDonDCHT> chiTietHoaDonDCHTs = chiTietHoaDonDCHTDao.layCTHDDCHT(maHoaDon);

						chiTietHoaDonDCHTs.forEach((e) -> {

							DungCuHocTap dcht = dhctDao.layDCHTTheoMaDCHT(e.getSanPham().getMaSanPham());
//							String[] header2 = { "Mã hóa đơn", "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Nhà xuất bản", "Nhà cung cấp",
//									"Tác giả", "Năm xuất bản", "Loại sản phẩm", "Xuất xứ", "Số lượng", "Đơn giá", "Thành tiền" };
							Object[] o1 = { e.getHoaDon().getMaHoaDon(), e.getSanPham().getMaSanPham(),
									dcht.getTenSanPham(), dcht.getThuongHieu(), null, dcht.getNhaCungCap(), null, null,
									dcht.getLoaiSanPham(), dcht.getXuatXu(), e.getSoLuong(),
									formatter.format(dcht.getGiaBan()), formatter.format(e.getThanhTien()) };
							tableModel2.addRow(o1);
						});
						List<ChiTietHoaDonSach> chiTietHoaDonSachs = chiTietHoaDonSachDao.layCTHDSach(maHoaDon);

						chiTietHoaDonSachs.forEach((e1) -> {
							Sach sach = sachDao.laySachTheoMaSach(e1.getSanPham().getMaSanPham());
							Object[] o2 = { e1.getHoaDon().getMaHoaDon(), e1.getSanPham().getMaSanPham(),
									sach.getTenSanPham(), null, sach.getNhaXuatBan(), sach.getNhaCungCap(),
									sach.getTacGia(), sach.getNamXuatBan(), sach.getLoaiSanPham(), null,
									e1.getSoLuong(), formatter.format(sach.getGiaBan()),
									formatter.format(e1.getThanhTien()) };
							tableModel2.addRow(o2);
						});
					}

				} catch (Exception e) {
				}

			}
		});
		table1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table1.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
		table1.setRowHeight(20);
		table1.setAutoCreateRowSorter(true);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);

		JPanel pCTHD = new JPanel();
		pCTHD.setBorder(new TitledBorder(null, "Thông tin chi tiết hóa đơn", TitledBorder.LEADING, TitledBorder.TOP,
				null, null));
		pCTHD.setBounds(0, 395, 1300, 234);
		pTab2.add(pCTHD);
		pCTHD.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 24, 1270, 200);
		pCTHD.add(scrollPane_1);

		JTable table2 = new JTable();
		scrollPane_1.setViewportView(table2);
		table2.getTableHeader().setBackground(new Color(255, 153, 51));

		String[] header2 = { "Mã hóa đơn", "Mã sản phẩm", "Tên sản phẩm", "Thương hiệu", "Nhà xuất bản", "Nhà cung cấp",
				"Tác giả", "Năm xuất bản", "Loại sản phẩm", "Xuất xứ", "Số lượng", "Đơn giá", "Thành tiền" };
		tableModel2 = new DefaultTableModel(header2, 0) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false; // To change body of generated methods, choose Tools | Templates.
			}
		};
		scrollPane_1.setViewportView(table2);
		table2.setModel(tableModel2);
		table2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		table2.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 13));
		table2.setRowHeight(20);
		table2.setAutoCreateRowSorter(true);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_NEXT_COLUMN);
		setLayout(null);
		pnContainer.setPreferredSize(new Dimension(1000, 1000));

		pnContainer.setBackground(Color.RED);
		add(pTab1);
		add(pTab2);
		add(panelTab);

		// Sự kiện
		txtSDT.addMouseListener(this);
		txtSDT.addFocusListener(this);
		txtMaSP.addMouseListener(this);
		txtMaSP.addFocusListener(this);
		// Tìm theo số điện thoại
		txtSDTTimKiem.getDocument().addDocumentListener(new DocumentListener() {

			public void warn() {
				if (Integer.parseInt(txtSDTTimKiem.getText()) <= 0) {
					JOptionPane.showMessageDialog(null, "Error: Nhập số điện thoại", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if (txtSDTTimKiem.getText().length() != 0) {

					String soDienThoai = "";
					Pattern pattern = Pattern.compile("^\\d{10}$");
					Matcher matcher = pattern.matcher(txtSDTTimKiem.getText());
					boolean valid = matcher.find();
					if (!txtSDTTimKiem.getText().equals("") && valid) {// nên regex (không đc trống , bắt buộc là 10 số)
						soDienThoai = txtSDTTimKiem.getText();
						KhachHang khachHang = khachHangDao.getKhachHangBySDT(soDienThoai);
						if (khachHang == null) {
							txtTenKH.setText("");
							txtSDTTimKiem.requestFocus();
							txtSDTTimKiem.selectAll();
							JOptionPane.showMessageDialog(null, "Error: Khách hàng không tồn tại", "Error Message",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						txtTenKH.setText(khachHang.getTenKH());

						return;
					}
					if (txtSDTTimKiem.getText().length() != 0 && valid == false) {
						txtTenKH.setText("");
						txtSDTTimKiem.requestFocus();
						return;
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				if (txtSDTTimKiem.getText().length() != 0) {

					String soDienThoai = "";
					Pattern pattern = Pattern.compile("^\\d{10}$");
					Matcher matcher = pattern.matcher(txtSDTTimKiem.getText());
					boolean valid = matcher.find();
					if (!txtSDTTimKiem.getText().equals("") && valid) {// nên regex (không đc trống , bắt buộc là 10 số)
						soDienThoai = txtSDTTimKiem.getText();
						KhachHang khachHang = khachHangDao.getKhachHangBySDT(soDienThoai);

						if (khachHang == null) {
							txtTenKH.setText("");
							txtSDTTimKiem.requestFocus();
							txtSDTTimKiem.selectAll();

							JOptionPane.showMessageDialog(null, "Error: Khách hàng không tồn tại", "Error Message",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						txtTenKH.setText(khachHang.getMaKH());
						return;
					}
					if (txtSDTTimKiem.getText().length() != 0 && valid == false) {
						txtTenKH.setText("");
						txtSDTTimKiem.requestFocus();
						return;
					}
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});
		//
		txtSDT.getDocument().addDocumentListener(new DocumentListener() {

			public void warn() {
				if (Integer.parseInt(txtSDT.getText()) <= 0) {
					JOptionPane.showMessageDialog(null, "Error: Nhập số điện thoại", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				if (txtSDT.getText().length() != 0) {

					String soDienThoai = "";
					Pattern pattern = Pattern.compile("^\\d{10}$");
					Matcher matcher = pattern.matcher(txtSDT.getText());
					boolean valid = matcher.find();
					if (!txtSDT.getText().equals("") && valid) {// nên regex (không đc trống , bắt buộc là 10 số)
						soDienThoai = txtSDT.getText();
						KhachHang khachHang = khachHangDao.getKhachHangBySDT(soDienThoai);

						if (khachHang == null) {
							tfMaKH.setText("");
							tfTenKH.setText("");
							txtDiaChi.setText("");
							txtSDT.requestFocus();
							txtSDT.selectAll();
							JOptionPane.showMessageDialog(null, "Error: Khách hàng không tồn tại", "Error Message",
									JOptionPane.ERROR_MESSAGE);
							return;
						}

						tfMaKH.setText(khachHang.getMaKH());
						tfTenKH.setText(khachHang.getTenKH());
						txtDiaChi.setText(khachHang.getDiaChi());

						return;
					}
					if (txtSDT.getText().length() != 0 && valid == false) {
						tfMaKH.setText("");
						tfTenKH.setText("");
						txtDiaChi.setText("");
						txtSDT.requestFocus();
						return;
					}
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				if (txtSDT.getText().length() != 0) {

					String soDienThoai = "";
					Pattern pattern = Pattern.compile("^\\d{10}$");
					Matcher matcher = pattern.matcher(txtSDT.getText());
					boolean valid = matcher.find();
					if (!txtSDT.getText().equals("") && valid) {// nên regex (không đc trống , bắt buộc là 10 số)
						soDienThoai = txtSDT.getText();
						KhachHang khachHang = khachHangDao.getKhachHangBySDT(soDienThoai);

						if (khachHang == null) {
							tfMaKH.setText("");
							tfTenKH.setText("");
							txtDiaChi.setText("");
							txtSDT.requestFocus();
							txtSDT.selectAll();

							JOptionPane.showMessageDialog(null, "Error: Khách hàng không tồn tại", "Error Message",
									JOptionPane.ERROR_MESSAGE);

							return;
						}

						tfMaKH.setText(khachHang.getMaKH());
						tfTenKH.setText(khachHang.getTenKH());
						txtDiaChi.setText(khachHang.getDiaChi());

						return;
					}
					if (txtSDT.getText().length() != 0 && valid == false) {
						tfMaKH.setText("");
						tfTenKH.setText("");
						txtDiaChi.setText("");
						txtSDT.requestFocus();
						return;
					}
				}

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub

			}
		});
		// Listen for changes in the text
		txtMaSP.getDocument().addDocumentListener(new DocumentListener() {

			public void warn() {
				if (Integer.parseInt(txtMaSP.getText()) <= 0) {
					JOptionPane.showMessageDialog(null, "Error: Nhập mã sản phẩm", "Error Message",
							JOptionPane.ERROR_MESSAGE);
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {

				if (txtMaSP.getText().startsWith("SPS")) {
					Sach sach = sachDao.laySachTheoMaSach(txtMaSP.getText());
					if (sach == null) {
						txtTenSanPham.setText("");
						txtTacGia.setText("");
						txtNXB.setText("");
						txtThuongHieu.setText("");
						return;
					}
					String tenSP = sach.getTenSanPham();
					String subTenSP = tenSP.length() > 25 ? tenSP.substring(0, 25) + "..." : tenSP;
					txtTenSanPham.setText(subTenSP);
					txtTacGia.setText(sach.getTacGia());
					txtNXB.setText(Integer.toString(sach.getNamXuatBan()));
				} else {
					DungCuHocTap dungcuhoctap = dhctDao.layDCHTTheoMaDCHT(txtMaSP.getText());
					if (dungcuhoctap == null) {
						txtTenSanPham.setText("");
						txtTacGia.setText("");
						txtNXB.setText("");
						txtThuongHieu.setText("");
						return;
					}
					String tenSP = dungcuhoctap.getTenSanPham();
					String subTenSP = tenSP.length() > 25 ? tenSP.substring(0, 25) + "..." : tenSP;
					txtTenSanPham.setText(subTenSP);
					txtThuongHieu.setText(dungcuhoctap.getThuongHieu());
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

				if (txtMaSP.getText().contains("SPS")) {
					Sach sach = sachDao.laySachTheoMaSach(txtMaSP.getText());
					if (sach == null) {
						txtTenSanPham.setText("");
						txtTacGia.setText("");
						txtNXB.setText("");
						txtThuongHieu.setText("");
						return;
					}
					String tenSP = sach.getTenSanPham();
					String subTenSP = tenSP.length() > 25 ? tenSP.substring(0, 25) + "..." : tenSP;
					txtTenSanPham.setText(subTenSP);
					txtTacGia.setText(sach.getTacGia());
					txtNXB.setText(Integer.toString(sach.getNamXuatBan()));
					return;
				} else if (txtMaSP.getText().contains("SPDCHT")) {
					DungCuHocTap dungcuhoctap = dhctDao.layDCHTTheoMaDCHT(txtMaSP.getText());
					if (dungcuhoctap == null) {
						txtTenSanPham.setText("");
						txtTacGia.setText("");
						txtNXB.setText("");
						txtThuongHieu.setText("");
						return;
					}
					String tenSP = dungcuhoctap.getTenSanPham();
					String subTenSP = tenSP.length() > 25 ? tenSP.substring(0, 25) + "..." : tenSP;
					txtTenSanPham.setText(subTenSP);

					txtThuongHieu.setText(dungcuhoctap.getThuongHieu());
					return;
				}
				txtTenSanPham.setText("");
				txtTacGia.setText("");
				txtNXB.setText("");
				txtThuongHieu.setText("");

//			
			}

			@Override
			public void changedUpdate(DocumentEvent e) {

			}
		});

		btnThemSP.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnLamMoi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dcNgayLHD.setDate(Date.valueOf(LocalDate.now()));

				List<Object> listObj = hoaDonDao.getAllOrderByNow(maNhanVien);

				if (listObj.size() == 0) {

					xoaRongTableDanhSachHD();
					return;
				} else {
					tableModel1.getDataVector().removeAllElements();
					listObj.forEach((o) -> {
						tableModel1.addRow((Object[]) o);
					});
				}

			}

		});
		btnXoaSPCTHD.addActionListener(this);
		btnXuatHD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (maHoaDonDuocChon == null) {
					thongBaoKhongTonTai("Vui long chọn hóa đơn cần xuất");
				}
				HoaDonSanPhamCanXuat hdXuat = new HoaDonSanPhamCanXuat(maHoaDonDuocChon);
				InPDF.printComponent(hdXuat.textArea);
				maHoaDonDuocChon = null;
				String cmds[] = new String[] { "cmd", "/c", "C:\\Users\\Dell\\Desktop\\a.pdf" };
				try {
					Runtime.getRuntime().exec(cmds);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}

		});
		btnTimKiem.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String maHD = txtMaHD.getText();
//				TH tìm theo mã hóa đơn
				if (maHD.length() != 0) {
					tableModel1.getDataVector().removeAllElements();
					try {
						Object[] hoadDon = hoaDonDao.timHoaDonById(maHD, maNhanVien);

						if (hoadDon == null) {
							xoaRongTableDanhSachHD();
							return;
						}
						tableModel1.addRow(hoadDon);
						return;

					} catch (SQLException e1) {

						e1.printStackTrace();
					}
				}
//			
				String sdt = txtSDTTimKiem.getText();

				if (sdt.length() != 0) {
					KhachHang khachHang = khachHangDao.getKhachHangBySDT(sdt);
					if (khachHang == null) {
						xoaRongTableDanhSachHD();
						thongBaoKhongTonTai("Khách hàng không tồn tại");
						return;
					}
					txtTenKH.setText(khachHang.getTenKH());
					List<Object> hoaDonKhachHang = new ArrayList<Object>();

					java.util.Date ngayLap = dcNgayLHD.getDate();

					// nếu ngày lập null
					if (ngayLap == null) {
						hoaDonKhachHang = hoaDonDao.getAllOrderBySDT(sdt, maNhanVien, null);
					} else {
						java.sql.Date ngayLapSql = new java.sql.Date(dcNgayLHD.getDate().getTime());

						hoaDonKhachHang = hoaDonDao.getAllOrderBySDT(sdt, maNhanVien, ngayLapSql);
					}
					tableModel1.getDataVector().removeAllElements();
					if (hoaDonKhachHang == null || hoaDonKhachHang.size() == 0) {

						xoaRongTableDanhSachHD();
						return;
					}

					hoaDonKhachHang.forEach((o) -> {
						tableModel1.addRow((Object[]) o);
					});
					return;
				}
//				Mặc định theo ngày
				xoaRongTableDanhSachHD();
				if (dcNgayLHD != null && txtMaHD.getText().equals("")) {
					xoaRongTableDanhSachHD();
					List<Object> hoaDonKhachHang = new ArrayList<Object>();
					java.sql.Date ngayLapSql = new java.sql.Date(dcNgayLHD.getDate().getTime());
					try {
						hoaDonKhachHang = hoaDonDao.getAllOrderByDate(ngayLapSql, maNhanVien);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
					if (hoaDonKhachHang == null) {
						xoaRongTableDanhSachHD();
						return;
					}
					tableModel1.getDataVector().removeAllElements();
					hoaDonKhachHang.forEach((o) -> {
						tableModel1.addRow((Object[]) o);
					});
					return;
				}
//				

			}

		});

		btnTimKiem.setForeground(Color.WHITE);
		btnXuatHD.setForeground(Color.WHITE);

		btnThanhToan.setForeground(Color.WHITE);
		btnThemSP.setForeground(Color.WHITE);
		btnXoaSPCTHD.setForeground(Color.WHITE);
		btnXoaTrang.setForeground(Color.WHITE);

	}

	public static void main(String[] args) {

	}

// KIEM TRA SAN PHAM DA TON TAI TRONG DSCTSP
	public boolean kiemTraCTHDSanPham(List<ChiTietHoaDonSach> dssp, Sach sach, int soLuongMua) {
		ChiTietHoaDonSach sachDaTonTai = null;
		for (ChiTietHoaDonSach chiTietHoaDonSach : dssp) {
			if (chiTietHoaDonSach.getSanPham().getMaSanPham().equals(sach.getMaSanPham())) {
				sachDaTonTai = chiTietHoaDonSach;
				break;
			}
		}
		if (sachDaTonTai == null)
			return false;
		int soLuongCongDon = sachDaTonTai.getSoLuong() + soLuongMua;
		sachDaTonTai.setSoLuong(soLuongCongDon);
		sachDaTonTai.setThanhTien(soLuongCongDon * sach.getGiaBan());
		this.lamMoiDanhSachSanPham();
		return true;
	}

	public boolean kiemTraCTHDSanPhamDCHT(List<ChiTietHoaDonDCHT> dssp, DungCuHocTap dcht, int soLuongMua) {
		ChiTietHoaDonDCHT dchtDaTonTai = null;
		for (ChiTietHoaDonDCHT chiTietHoaDonDCHT : dssp) {
			if (chiTietHoaDonDCHT.getSanPham().getMaSanPham().equals(dcht.getMaSanPham())) {
				dchtDaTonTai = chiTietHoaDonDCHT;
			}
		}
		if (dchtDaTonTai == null)
			return false;
		int soLuongCongDon = dchtDaTonTai.getSoLuong() + soLuongMua;
		dchtDaTonTai.setSoLuong(soLuongCongDon);

		dchtDaTonTai.setThanhTien(soLuongCongDon * dcht.getGiaBan());
		this.lamMoiDanhSachSanPham();
		return true;
	}

	public boolean kiemTraNhapLieu() {
		if (txtMaSP.getText().trim().length() == 0) {
			txtMaSP.requestFocus();
			JOptionPane.showMessageDialog(this, "Mã sản phẩm không đc trống");
			return false;
		} else if (txtSoLuong.getText().trim().length() == 0) {
			txtSoLuong.requestFocus();
			JOptionPane.showMessageDialog(this, "Số lượng không đc trống");
			return false;
		}

		return true;
	}

	private boolean kiemTraNhapSoNguyen(String nhap) {
		try {
			int a = Integer.parseInt(nhap);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	làm mới danh sách sản phẩm đang mua
	DecimalFormat formatter = new DecimalFormat("###,###,###");
	int stt = 0;

	private void lamMoiDanhSachSanPham() {
		this.xoaRongTableDanhSachHDDSCTSanPhamMua();
		this.dsChiTietHDSachMua.stream().forEach(chiTietHoaDonSach -> {
			Sach sach = (Sach) chiTietHoaDonSach.getSanPham();
			stt++;
			Object[] o = { stt, sach.getMaSanPham(), sach.getTenSanPham(), sach.getNhaXuatBan(), sach.getTacGia(),
					sach.getNamXuatBan(), "", chiTietHoaDonSach.getSoLuong(), formatter.format(sach.getGiaBan()),
					formatter.format(chiTietHoaDonSach.getThanhTien()) };
			tableModel.addRow(o);
		});
		this.dsChiTietHDDCHTMua.stream().forEach(chiTietHoaDonDCHT -> {
			DungCuHocTap dungcuhoctap = (DungCuHocTap) chiTietHoaDonDCHT.getSanPham();
			stt++;
			Object[] o = { stt, txtMaSP.getText(), dungcuhoctap.getTenSanPham(), "", "", "",
					dungcuhoctap.getThuongHieu(), chiTietHoaDonDCHT.getSoLuong(),
					formatter.format(dungcuhoctap.getGiaBan()), formatter.format(chiTietHoaDonDCHT.getThanhTien()) };
			tableModel.addRow(o);
		});
		stt = 0;
	}

	private void capNhapTongSLVaTongTien(int sl, double giaBan) {
		this.tongSoLuong = this.tongSoLuong + sl;
		this.tongTongTien = this.tongTongTien + sl * giaBan;
		txtTSoLuong.setText(String.valueOf(this.tongSoLuong));
		txtTongTien.setText(String.valueOf(formatter.format(this.tongTongTien)) + "VNĐ");
	}

	@SuppressWarnings("unused")
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if (obj.equals(btnThemSP)) {
			if (txtSoLuong.getText().length() == 0) {
				JOptionPane.showMessageDialog(this, "Số lượng phải lơn hơn hoặc bằng 1");
				txtSoLuong.requestFocus();
				return;
			} else if (!kiemTraNhapSoNguyen(txtSoLuong.getText())) {
				txtSoLuong.requestFocus();
				JOptionPane.showMessageDialog(this, "Số lượng là số nguyên");
				return;
			}
			int sL = Integer.valueOf(txtSoLuong.getText());
			if (sL >= 1) {
				DecimalFormat formatter = new DecimalFormat("###,###,###");
				String tenSP = "";
				double donGia = 0;
				double thanhTien = 0;
				int stt = tableModel.getRowCount();

				if (txtMaSP.getText().startsWith("SPS")) {
					// int selectedRow = tableDSSP.getSelectedRow();
					Sach sach = sachDao.laySachTheoMaSach(txtMaSP.getText());
					if (sach == null) {
						JOptionPane.showMessageDialog(this, "Sản phẩm không tìm thấy!");
						return;
					}
//						 DAY NE
					if (sach.getSoLuong() != 0) {
						int txtSL = Integer.valueOf(txtSoLuong.getText());
						boolean spDaTonTai = this.kiemTraCTHDSanPham(dsChiTietHDSachMua, sach, txtSL);
						if (spDaTonTai) { // Chi tiết hóa đơn đã tồn tại
							this.capNhapTongSLVaTongTien(txtSL, sach.getGiaBan());
							return;
						}

//									láy số lượng sách hiện tại trừ (số lượng sách mua + số lượng sách mua trước đó)
						int updateSLT = sach.getSoLuong() - txtSL;
						if (updateSLT >= 0) {

							this.dsChiTietHDSachMua.add(new ChiTietHoaDonSach(sach, txtSL));

							tenSP = sach.getTenSanPham();
							donGia = sach.getGiaBan();
							thanhTien = txtSL * donGia;
							Object[] o = { stt + 1, txtMaSP.getText(), tenSP, sach.getNhaXuatBan(), sach.getTacGia(),
									sach.getNamXuatBan(), "", txtSL, formatter.format(donGia),
									formatter.format(thanhTien) };
							tableModel.addRow(o);
							// set lại tiền
							this.tongSoLuong = this.tongSoLuong + txtSL;
							this.tongTongTien = this.tongTongTien + thanhTien;
							// set lại số lượng tồn của sách
							QuanLiDCHT.TaiSanPhamSach();
							txtTSoLuong.setText(String.valueOf(this.tongSoLuong));
							txtTongTien.setText(String.valueOf(formatter.format(this.tongTongTien)) + "VNĐ");
						} else {
							JOptionPane.showMessageDialog(null,
									"Hiện tại mặt hàng này chỉ còn " + sach.getSoLuong() + " sản phẩm");
						}

					} else {
						JOptionPane.showMessageDialog(null, "Mặt hàng này đã hết hàng");
					}

				}
				// truong hop else cho san pham la dung cu hoc tap
				else {
					DungCuHocTap dungcuhoctap = dhctDao.layDCHTTheoMaDCHT(txtMaSP.getText());
					int txtSL = Integer.valueOf(txtSoLuong.getText());
					if (dungcuhoctap == null) {
						JOptionPane.showMessageDialog(this, "Sản phẩm không tìm thấy!");
						return;
					}
					if (dungcuhoctap.getTinhTrang()) {
						if (dungcuhoctap.getSoLuong() != 0) {
							Boolean sanPhamDaTonTai = this.kiemTraCTHDSanPhamDCHT(dsChiTietHDDCHTMua, dungcuhoctap,
									txtSL);
							if (sanPhamDaTonTai == true) {
								this.capNhapTongSLVaTongTien(txtSL, dungcuhoctap.getGiaBan());
								return;
							} else {
								int updateSLT = dungcuhoctap.getSoLuong() - txtSL;
								if (updateSLT >= 0) {
									this.dsChiTietHDDCHTMua.add(new ChiTietHoaDonDCHT(dungcuhoctap, txtSL));
									tenSP = dungcuhoctap.getTenSanPham();
									donGia = dungcuhoctap.getGiaBan();
									thanhTien = txtSL * donGia;
									Object[] o = { stt + 1, txtMaSP.getText(), tenSP, "", "", "",
											dungcuhoctap.getThuongHieu(), txtSL, formatter.format(donGia),
											formatter.format(thanhTien) };
									tableModel.addRow(o);
									// set lại tiền
									this.tongSoLuong = this.tongSoLuong + txtSL;
									this.tongTongTien = this.tongTongTien + thanhTien;
									QuanLiDCHT.TaiSanPhamDCHT();
									txtTSoLuong.setText(String.valueOf(this.tongSoLuong));
									txtTongTien.setText(String.valueOf(formatter.format(this.tongTongTien)) + "VNĐ");
								} else {
									JOptionPane.showMessageDialog(null,
											"Hiện tại mặt hàng này chỉ còn " + dungcuhoctap.getSoLuong() + " sản phẩm");
								}
							}
						} else {
							JOptionPane.showMessageDialog(null, "Mặt hàng này đã hết hàng");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Sản phẩm này hiện đã ngừng kinh doanh");
					}
				}

			} else {
				JOptionPane.showMessageDialog(this, "Số lượng phải lơn hơn hoặc bằng 1");
				txtSoLuong.selectAll();
				txtSoLuong.requestFocus();
			}
		}

		if (obj.equals(btnXoaTrang)) {
			txtMaSP.setText("");
			txtMaSP.requestFocus();
			txtTenSanPham.setText("");
			txtTacGia.setText("");
			txtNXB.setText("");
			txtThuongHieu.setText("");
			txtSoLuong.setText("0");
		}

		if (obj.equals(btnXoaSPCTHD)) {
			int row = table.getSelectedRow();

			DecimalFormat formatter = new DecimalFormat("###,###,###.00");
			if (row < 0) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm để xóa");
				return;
			}
			int selectedRow = table.convertRowIndexToModel(table.getSelectedRow());
			int selectedSL = table.convertColumnIndexToModel(7);
			int selectedTT = table.convertColumnIndexToModel(9);
			int soLuong = Integer.valueOf(tableModel.getValueAt(selectedRow, selectedSL).toString());
			String tongTien = (String) tableModel.getValueAt(selectedRow, selectedTT);
			NumberFormat nf_in = NumberFormat.getNumberInstance(Locale.GERMANY);
			String maSP = (String) tableModel.getValueAt(selectedRow, 1);

			if (maSP.startsWith("SPS")) {
				List<ChiTietHoaDonSach> dsSPDuocXoa = new ArrayList<ChiTietHoaDonSach>();
				for (ChiTietHoaDonSach e1 : dsChiTietHDSachMua) {

					if (e1.getSanPham().getMaSanPham().equals(maSP)) {
						dsSPDuocXoa.add(e1);
					}
				}
				dsSPDuocXoa.forEach(sp -> {
					dsChiTietHDSachMua.remove(sp);
				});
			} else {
				List<ChiTietHoaDonDCHT> dsDCDuocXoa = new ArrayList<ChiTietHoaDonDCHT>();
				for (ChiTietHoaDonDCHT e2 : dsChiTietHDDCHTMua) {
					if (e2.getSanPham().getMaSanPham().equals(maSP)) {
						dsDCDuocXoa.add(e2);
					}
				}
				dsDCDuocXoa.forEach(sp -> {
					dsChiTietHDDCHTMua.remove(sp);
				});
			}
			try {
				double val = nf_in.parse(tongTien).doubleValue() * 1000;

				tongSoLuong = tongSoLuong - soLuong;
				tongTongTien = tongTongTien - val;
				this.lamMoiDanhSachSanPham();
				txtTSoLuong.setText(Integer.toString(tongSoLuong));
				if (tableModel.getRowCount() == 0) {
					txtTongTien.setText("0VNĐ");
					return;
				}
				txtTongTien.setText(formatter.format(tongTongTien) + "VNĐ");
				return;

			} catch (ParseException e1) {

				e1.printStackTrace();
			}

		}
		if (obj.equals(btnThanhToan)) {

			int row = table.getRowCount();
			if (txtSDT.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "Chưa có khách hàng");
				return;
			}
			if (row > 0) {
				new FormThanhToan(tongTongTien, tenNhanVien).setVisible(true);
				return;

			} else if (row == 0) {
				JOptionPane.showMessageDialog(null, "Danh sách phẩm trống");
				return;
			} else {
				JOptionPane.showMessageDialog(null, "Chưa có khách hàng");
				return;
			}
		}
	}

	public static void xoaRong() {
		tableModel.setRowCount(0);
		txtSDT.setText("");
		txtMaSP.setText("");
		txtSoLuong.setText("");
		tfMaKH.setText("");
		txtDiaChi.setText("");
		tfTenKH.setText("");
		tongSoLuong = 0;
		tongTongTien = 0;
		txtTSoLuong.setText("0");
		txtTongTien.setText("0VNĐ");
	}

	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {

	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {

	}

	@Override
	public void windowDeiconified(WindowEvent e) {

	}

	@Override
	public void windowActivated(WindowEvent e) {

	}

	@Override
	public void windowDeactivated(WindowEvent e) {

	}

	@Override
	public void focusGained(FocusEvent e) {

	}

	@SuppressWarnings("unused")
	@Override
	public void focusLost(FocusEvent e) {

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

	@Override
	public void valueChanged(ListSelectionEvent e) {

	}

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	public static void taoHoaDon() {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		HoaDon newHD = new HoaDon(date);
//		// lấy số lương theo từng mã sản phẩm 
		newHD.setChiTietHoaDonSachs(dsChiTietHDSachMua);
		newHD.setChiTietHoaDonDCHTs(dsChiTietHDDCHTMua);

		if (tfMaKH.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "Chưa có khách hàng");
			txtSDT.requestFocus();
			return;
		}
		newHD.setKhachHang(new KhachHang(tfMaKH.getText()));

		newHD.setNhanvien(new NhanVien(maNhanVien));
		newHD.setTongTien(tongTongTien);
		newHD.setTongSoLuong(tongSoLuong);

		try {
			// Tao hoa don

			String newMaHD = hoaDonDao.themHoaDon(newHD);

			// Tao chi tiet hoa don
			boolean rsCTHD1 = chiTietHoaDonSachDao.themChiTietHoaDon(dsChiTietHDSachMua, newMaHD);

			boolean rsCTHD2 = chiTietHoaDonDCHTDao.themChiTietHoaDon(dsChiTietHDDCHTMua, newMaHD);
//			xử lý tăng mã hóa đơn và trừ số lượng sp đã mua
			int currentLength = hoaDonDao.laySoHoaDon();
			String maHoaDonUpDate = prefixMaHoaDon + (++currentLength);
			tfMaHD.setText(maHoaDonUpDate);
			for (ChiTietHoaDonSach chiTietHoaDonSach : dsChiTietHDSachMua) {
				String maSP = chiTietHoaDonSach.getSanPham().getMaSanPham();
				int soLuongFromDB = sachDao.laySoLuongSachByMaSP(maSP);
				int soLuongMua = chiTietHoaDonSach.getSoLuong();
				int soLuongUpdate = soLuongFromDB - soLuongMua;
				sachDao.suaSLTSach(soLuongUpdate, maSP);
			}
			for (ChiTietHoaDonDCHT chiTietHoaDonDCHT : dsChiTietHDDCHTMua) {
				String maSP = chiTietHoaDonDCHT.getSanPham().getMaSanPham();
				int soLuongFromDB = dhctDao.laySoLuongDCHTByMaSanPham(maSP);
				int soLuongMua = chiTietHoaDonDCHT.getSoLuong();
				int soLuongUpdate = soLuongFromDB - soLuongMua;
				dhctDao.SuaSLTDungCuHocTap(soLuongUpdate, maSP);
			}

			DecimalFormat df = new DecimalFormat("#,###,###,###  VNĐ");
			double tienKhachDua = FormThanhToan.money;
			JOptionPane.showMessageDialog(null, "Tạo hóa đơn thành công");
			new FormThanhToan(tongTongTien, tenNhanVien).setVisible(false);
			dsChiTietHDSachMua.removeAll(dsChiTietHDSachMua);
			dsChiTietHDDCHTMua.removeAll(dsChiTietHDDCHTMua);
			int result = JOptionPane.showConfirmDialog(null, "Bạn có muốn in hóa đơn không ?", "In hóa đơn : ",
					JOptionPane.YES_NO_OPTION);
			if (result == JOptionPane.YES_OPTION) {
				new HoaDonSanPham(tenNhanVien, tienKhachDua);
				InPDF.printComponent(HoaDonSanPham.textArea);
				String cmds[] = new String[] { "cmd", "/c", "C:\\Users\\Dell\\OneDrive\\Desktop\\a.pdf" };
				try {
					Runtime.getRuntime().exec(cmds);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				try {
					Runtime.getRuntime().exec(cmds);
				} catch (Exception e1) {
					System.out.println(e1);
				}
			} else if (result == JOptionPane.NO_OPTION) {
				xoaRong();
				return;
			}

			xoaRong();

			newHD.setMaHoaDon(newMaHD);

		} catch (ClassNotFoundException e1) {

			e1.printStackTrace();
		} catch (SQLException e1) {

			e1.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
