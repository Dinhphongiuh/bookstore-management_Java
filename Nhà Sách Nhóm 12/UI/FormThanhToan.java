package UI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet.ColorAttribute;

import dao.DAO_ChiTietHoaDonDCHT;
import dao.DAO_ChiTietHoaDonSach;
import dao.DAO_DungCuHocTap;
import dao.DAO_HoaDon;
import dao.DAO_KhachHang;
import dao.DAO_NhanVien;
import dao.DAO_Sach;
import entity.ChiTietHoaDonDCHT;
import entity.ChiTietHoaDonSach;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

import others.PrintSupport;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.TextField;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;

public class FormThanhToan extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prefixMaHoaDon = "HD00";
	private JPanel contentPane;
	public static JFormattedTextField txtTienKhachDua;
	public static TextField txtRefunds;
	private double tongTien;
	private List<ChiTietHoaDonSach> dsChiTietHDSachMua;
	private List<ChiTietHoaDonDCHT> dsChiTietHDDCHTMua;
	private String maKH;
	private String tenNhanVien;
	private int tongSoLuong;
	private String soDT;
	private String maHD;
	public static double money;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {

	}

	/**
	 * Create the frame.
	 */
	public FormThanhToan(double tongTien, String tenNhanVien) {
		this.tongTien = tongTien;
		this.tenNhanVien = tenNhanVien;

		DecimalFormat df = new DecimalFormat("###,###,### VNĐ");
		DAO_HoaDon hoaDonDao = new DAO_HoaDon();
		DAO_KhachHang khachHangDao = new DAO_KhachHang();
		DAO_NhanVien nhanVienDao = new DAO_NhanVien();
		DAO_ChiTietHoaDonSach chiTietHoaDonSachDao = new DAO_ChiTietHoaDonSach();
		DAO_ChiTietHoaDonDCHT chiTietHoaDonDCHTDao = new DAO_ChiTietHoaDonDCHT();
		DAO_Sach sachDao = new DAO_Sach();
		DAO_DungCuHocTap dungCuHocTapDao = new DAO_DungCuHocTap();
		setForeground(new Color(255, 69, 0));
		setFont(new Font("Dialog", Font.BOLD, 12));
		// setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setBounds(500, 120, 712, 470);
		setLocationRelativeTo(null);
		setBackground(SystemColor.controlShadow);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(159, 182, 205));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 153, 51));
		panel.setBounds(0, 0, 712, 361);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Thông tin thanh toán");
		lblNewLabel.setForeground(Color.white);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 26));
		lblNewLabel.setBounds(15, 8, 297, 43);
		panel.add(lblNewLabel);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 248, 255));
		panel_1.setBounds(0, 54, 712, 306);
		panel.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblSumTotal = new JLabel("Tổng thanh toán");
		lblSumTotal.setForeground(new Color(255, 153, 51));
		lblSumTotal.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblSumTotal.setBounds(43, 12, 220, 30);
		panel_1.add(lblSumTotal);

		TextField txtSumTotal = new TextField();
		txtSumTotal.setEditable(false);
		txtSumTotal.setBackground(Color.WHITE);
		txtSumTotal.setForeground(new Color(255, 153, 51));
		txtSumTotal.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtSumTotal.setBounds(354, 10, 322, 34);
		panel_1.add(txtSumTotal);
		txtSumTotal.setText(df.format(tongTien));

		JLabel lblPaymentsType = new JLabel("Hình thức TT");

		lblPaymentsType.setForeground(new Color(255, 153, 51));
		
		lblPaymentsType.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblPaymentsType.setBounds(43, 65, 144, 30);
		panel_1.add(lblPaymentsType);

		JComboBox cbbPaymentsType = new JComboBox();
		cbbPaymentsType.setForeground(new Color(128, 0, 128));
		cbbPaymentsType.setModel(new DefaultComboBoxModel(new String[] { "Tiền mặt" }));
		cbbPaymentsType.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbbPaymentsType.setBackground(Color.WHITE);
		cbbPaymentsType.setBounds(354, 63, 176, 34);
		panel_1.add(cbbPaymentsType);

		JLabel lblCurrency = new JLabel("Loại tiền");
		lblCurrency.setForeground(new Color(255, 153, 51));
		lblCurrency.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCurrency.setBounds(43, 118, 102, 30);
		panel_1.add(lblCurrency);

		JComboBox cbbCurrency = new JComboBox();
		cbbCurrency.setFont(new Font("Tahoma", Font.PLAIN, 20));
		cbbCurrency.setModel(new DefaultComboBoxModel(new String[] { "VND" }));
		cbbCurrency.setBounds(354, 116, 176, 34);
		panel_1.add(cbbCurrency);

		JLabel lblCusMoney = new JLabel("Tiền khách đưa");
		lblCusMoney.setForeground(new Color(255, 153, 51));
		lblCusMoney.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblCusMoney.setBounds(43, 171, 193, 25);
		panel_1.add(lblCusMoney);

		// txtTienKhachDua = new TextField();
		/*
		 * txtTienKhachDua.requestFocus(); txtTienKhachDua.setForeground(new Color(0, 0,
		 * 255)); txtTienKhachDua.setFont(new Font("Tahoma", Font.PLAIN, 20));
		 * txtTienKhachDua.setBounds(354, 169, 322, 34); panel_1.add(txtTienKhachDua);
		 */
		NumberFormat num = NumberFormat.getNumberInstance();
		txtTienKhachDua = new JFormattedTextField(num);
		txtTienKhachDua.requestFocus();
		txtTienKhachDua.setForeground(new Color(0, 0, 255));
		txtTienKhachDua.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtTienKhachDua.setBounds(354, 169, 322, 34);
		panel_1.add(txtTienKhachDua);

		JLabel lblRegex = new JLabel("");
		lblRegex.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRegex.setForeground(new Color(255, 0, 0));
		lblRegex.setBounds(354, 205, 322, 25);
		panel_1.add(lblRegex);

		JLabel lblRefunds = new JLabel("Tiền trả lại");
		lblRefunds.setForeground(new Color(255, 69, 0));
		lblRefunds.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblRefunds.setBounds(43, 253, 136, 25);
		panel_1.add(lblRefunds);

		txtRefunds = new TextField();
		txtRefunds.setBackground(Color.WHITE);
		txtRefunds.setForeground(new Color(255, 69, 0));
		txtRefunds.setFont(new Font("Tahoma", Font.PLAIN, 20));
		txtRefunds.setEditable(false);
		txtRefunds.setBounds(354, 251, 322, 34);
		panel_1.add(txtRefunds);

		JButton btnClose = new JButton("Đóng");
		btnClose.setForeground(Color.WHITE);
		btnClose.setIcon(ResizeImage("src//Icon//Button-exit-icon.png"));
		btnClose.setBackground(new Color(255, 153, 51));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
		btnClose.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnClose.setBounds(538, 371, 136, 48);
		contentPane.add(btnClose);

		JButton btnCompleted = new JButton("Hoàn tất");
		btnCompleted.setBackground(new Color(255, 153, 51));
		btnCompleted.setForeground(Color.WHITE);
		btnCompleted.setIcon(ResizeImage("src//Icon//Save-icon.png"));
		btnCompleted.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnCompleted.setBounds(352, 371, 169, 48);
		contentPane.add(btnCompleted);

		txtTienKhachDua.addKeyListener(new KeyAdapter() {
			

			public boolean isNumeric(String str) {
				return str.matches("\\d"); // match a number with optional '-' and decimal. "-?\\d+(\\.\\d+)?"
			}

			@Override
			public void keyReleased(KeyEvent e) {
				 
				NumberFormat num = NumberFormat.getNumberInstance();
				if (txtTienKhachDua.getText().equalsIgnoreCase("")) {
					lblRegex.setText("");
				} else if (isNumeric(
						txtTienKhachDua.getText().substring(txtTienKhachDua.getText().length() - 1)) == true)
					try {
						money = num.parse(txtTienKhachDua.getText()).doubleValue();
						txtTienKhachDua.setText(num.format(money));
						lblRegex.setText("");
						double cusMoney = 0;
						if (!txtTienKhachDua.getText().equalsIgnoreCase(""))
							cusMoney = num.parse(txtTienKhachDua.getText()).doubleValue();
						double total;

						total = df.parse(txtSumTotal.getText()).doubleValue();
						double tamp = 0;
						if (cusMoney >= total) {
							tamp = cusMoney - total;
							txtRefunds.setText(String.valueOf(df.format(tamp)));
						} else
							txtRefunds.setText("");
					} catch (NumberFormatException | ParseException e2) {

						e2.printStackTrace();
						lblRegex.setText("Vui lòng nhập tiền khách đưa!");
					}
				else
					lblRegex.setText("Vui lòng nhập tiền khách đưa!");
			}
		});

		btnCompleted.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// Thông tin hoàn tất
				if (txtRefunds.getText().equalsIgnoreCase("")) {
					JOptionPane.showMessageDialog(null, "Số tiền khách đưa không hợp lệ!");
					return;
				}
				QuanLiHoaDon.taoHoaDon();
				setVisible(false);
			}
		});

	}

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(25, 25, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
