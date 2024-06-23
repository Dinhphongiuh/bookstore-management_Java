package UI;

import java.awt.BorderLayout;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.DAO_KhachHang;
import dao.DAO_NhanVien;
import entity.KhachHang;
import entity.NhanVien;
import connectDB.ConnectDB;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class QuanLiKhachHang extends JPanel implements ActionListener, MouseListener {
	JTextField txtTimMa, txtTimSDT, txtTim,txtTong;
	JButton btnLamMoi, btnThem, btnSua, btnLoad, btnThoat;
	DefaultTableModel model;
	private JTextField txtTimsdt;
	private String kt;
	int row;
	KhachHang khachHang;
	private DAO_NhanVien dao_kh;
	JTable table;
	private JPanel pnTongQuan;
	private JPanel pnMain;

	public QuanLiKhachHang() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dao_kh = new DAO_NhanVien();
		khachHang = new KhachHang();
		setLayout(new BorderLayout());

		JPanel pnNorth = new JPanel();
		JLabel lblTieuDe = new JLabel("Quản lý Khách Hàng");
		Font font = new Font("Arial", Font.BOLD, 25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(new Color(255, 113, 51));

		// pnNorth.setBackground(Color.WHITE);
		pnNorth.add(lblTieuDe);
		add(pnNorth, BorderLayout.NORTH);

		// Panel Chính
		pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1315, 750);

		JLabel lbTim = new JLabel("Tìm Kiếm: ");
		lbTim.setFont(new Font("Arial", Font.BOLD, 21));
		lbTim.setForeground(new Color(255, 113, 51));

		txtTim = new JTextField();

		btnThem = new JButton("Thêm Khách Hàng");
		btnThem.setFont(new Font("Arial", Font.BOLD, 14));
		btnThem.setBackground(new Color(255, 113, 51));
		btnThem.setOpaque(true);
		btnThem.setBorderPainted(false);
		btnThem.setForeground(Color.WHITE);
		btnThem.setIcon(ResizeImage(""));
		//C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\add_icon.png
		
		btnSua = new JButton("Sửa thông tin");
		btnSua.setBackground(new Color(255, 113, 51));
		btnSua.setOpaque(true);
		btnSua.setBorderPainted(false);
		btnSua.setForeground(Color.WHITE);
		btnSua.setFont(new Font("Arial", Font.BOLD, 14));
		btnSua.setIcon(ResizeImage(""));
		// C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\sua.png
		
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
		btnLamMoi.setBackground(new Color(255, 113, 51));
		btnLamMoi.setOpaque(true);
		btnLamMoi.setBorderPainted(false);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setIcon(ResizeImage(""));
		//C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\ref.png
		
		
		// Panel hiển thị danh sách
		JPanel pnDanhsach = new JPanel();
		pnDanhsach.setLayout(new BorderLayout());
		pnDanhsach.setBounds(10, 142, 1315, 430);
		pnDanhsach.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Danh sách khách hàng",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));

		
		table = new JTable(model) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column)
			{
				Component c = super.prepareRenderer(renderer, row, column);

				//  Alternate row color

				if (!isRowSelected(row))
					c.setBackground(row % 2 == 0 ? getBackground() : new Color(245, 245, 240));

				return c;
			}
		};
		table.setRowHeight(30);
		table.setFont(new Font("Arial", Font.PLAIN, 13));
		table.setShowGrid(false);
		table.getTableHeader().setBackground(new Color(255, 153, 51));
		table.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 14));
		table.getTableHeader().setForeground(Color.white);
		JScrollPane pane = new JScrollPane(table);
		pnDanhsach.add(pane);
		// Panel chức năng
		JPanel pnChucnang = new JPanel();
		pnChucnang.setLayout(null);
		pnChucnang.setBounds(0, 0, 1000, 100);

		lbTim.setBounds(450, 45, 150, 40);
		txtTim.setBounds(575, 47, 300, 30);
		TextPrompt tp7 = new TextPrompt("Nhập mã khách hàng hoặc số điện thoại", txtTim);
		tp7.setForeground(new Color(46, 46, 31));
		tp7.changeAlpha(0.5f);
		tp7.changeStyle(Font.ITALIC);
		btnLamMoi.setBounds(144, 20, 140, 50);
		btnSua.setBounds(562, 20, 180, 50);
		btnThem.setBounds(990, 20, 210, 50);

		pnTongQuan = new JPanel();
		pnTongQuan.setLayout(null);
		pnTongQuan.setBounds(1020, 20, 300, 110);
		pnTongQuan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Tổng quan",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));
		JLabel lblTong = new JLabel("Tổng số Khách hàng: ");
		txtTong = new JTextField();
		try {
			txtTong.setText(timTong());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		JPanel pnChucNangb = new JPanel();
		pnChucNangb.setLayout(null);
		pnChucNangb.setBounds(10, 600, 1315, 80);
		pnChucNangb.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Chức Năng",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));

		lblTong.setBounds(20, 30, 160, 50);
		txtTong.setBounds(160, 40, 30, 30);
		txtTong.setEditable(false);
		txtTong.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtTong.setBackground(null);
		pnChucnang.add(lbTim);
		pnChucnang.add(txtTim);
		pnChucNangb.add(btnSua);
		pnChucNangb.add(btnLamMoi);
		pnChucNangb.add(btnThem);
		pnTongQuan.add(lblTong);
		pnTongQuan.add(txtTong);

		JPanel pnLoc = new JPanel();
		pnLoc.setBackground(new Color(255, 255, 255));

		// Thêm vào Panel chính
		pnMain.add(pnDanhsach);
		pnMain.add(pnChucnang);
		pnMain.add(pnChucNangb);
		pnMain.add(pnTongQuan);

		add(pnMain);

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);
		table.addMouseListener(this);
		try {
			loadKH();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txtTim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtTim.getText().length() == 0) {
					try {
						loadKH();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (txtTim.getText().length() > 0) {
					if (txtTim.getText().charAt(0) == '0') {
						try {
							timSDT();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						try {
							timKH();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			new FormThemKhachHang().setVisible(true);
		} else if (o.equals(btnSua)) {
			if (kt == null) {
				JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng");
			} else {
				new FormCapNhatKhachHang(khachHang).setVisible(true);
				dispose();
			}
		} else if (o.equals(btnLamMoi)) {
			try {
				loadKH();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				txtTong.setText(timTong());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void dispose() {
		// TODO Auto-generated method stub

	}

	private void loadKH() throws SQLException {
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		model = dao_kh.getAllKH();
		table.setModel(model);
	}

	private String timTong() throws SQLException {
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		String t = dao_kh.tong();
		return t;
	}

	private void timSDT() throws SQLException 
	{
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		model = dao_kh.timKiemSDT("%" + txtTim.getText() + "%");
		table.setModel(model);
	}

	private void timKH() throws SQLException {
		DAO_KhachHang dao_kh = new DAO_KhachHang();
		model = dao_kh.timKiem(txtTim.getText() + "%");
		table.setModel(model);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLiKhachHang frame = new QuanLiKhachHang();
					frame.setVisible(true);
					frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	protected void setExtendedState(int maximizedBoth) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		row = table.getSelectedRow();
		kt = khachHang.getMaKH(table.getValueAt(row, 0).toString());
		khachHang.setMaKH(table.getValueAt(row, 0).toString());
		khachHang.setTenKH(table.getValueAt(row, 1).toString());
		khachHang.setSoDT(table.getValueAt(row, 2).toString());
		khachHang.setDiaChi(table.getValueAt(row, 3).toString());

		System.out.println(table.getValueAt(row, 0).toString());

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

}
