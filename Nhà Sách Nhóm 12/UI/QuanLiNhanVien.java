package UI;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import dao.DAO_NhanVien;
import entity.NhanVien;
import entity.Sach;
import connectDB.ConnectDB;

public class QuanLiNhanVien extends JPanel implements ActionListener, MouseListener {
	public  JTable table;
	JTextField txtTim,txtTong,txtTongNam,txtTongNu;
	JButton btnThem, btnSua, btnLamMoi;
	FormThemNhanVien ui;
	public static QuanLiNhanVien qlnv;
	public static DAO_NhanVien dao_nv;
	int row;
	String kt;
	NhanVien nhanVien;
	public  DefaultTableModel model;
	public static JLabel lblTong = null;

	public static List<NhanVien> listNhanVien;
	
	public QuanLiNhanVien() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e1) {

			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		dao_nv = new DAO_NhanVien();
		nhanVien = new NhanVien();
		
		listNhanVien = new ArrayList<NhanVien>();
		
		setLayout(new BorderLayout());

		JPanel pnNorth = new JPanel();
		JLabel lblTieuDe = new JLabel("Quản lý nhân viên");
		Font font = new Font("Arial", Font.BOLD, 25);
		lblTieuDe.setFont(font);
		lblTieuDe.setForeground(new Color(255, 113, 51));
		// pnNorth.setBackground(Color.WHITE);
		pnNorth.add(lblTieuDe);
		add(pnNorth, BorderLayout.NORTH);

		// Panel Chính
		JPanel pnMain = new JPanel();
		pnMain.setLayout(null);
		pnMain.setBounds(0, 0, 1300, 750);

		// Khai báo Label, TextField, Button

		JLabel lbTim = new JLabel("Tìm Kiếm: ");
		lbTim.setFont(new Font("Arial", Font.BOLD, 21));
		lbTim.setForeground(new Color(255, 113, 51));
		txtTim = new JTextField();
		TextPrompt tp7 = new TextPrompt("Nhập mã nhân viên hoặc số điện thoại", txtTim);
		tp7.setForeground(new Color(46, 46, 31));
		tp7.changeAlpha(0.5f);
		tp7.changeStyle(Font.ITALIC);
		btnThem = new JButton("Thêm Nhân Viên");
		btnThem.setFont(new Font("Arial", Font.BOLD, 14));
		btnThem.setIcon(ResizeImage(""));
		btnThem.setBackground(new Color(255, 113, 51));
		btnThem.setOpaque(true);
		btnThem.setBorderPainted(false);
		btnThem.setForeground(Color.WHITE);
		btnSua = new JButton("Sửa thông tin");
		btnSua.setFont(new Font("Arial", Font.BOLD, 14));
		btnSua.setIcon(ResizeImage(""));
		btnSua.setBackground(new Color(255, 113, 51));
		btnSua.setOpaque(true);
		btnSua.setBorderPainted(false);
		btnSua.setForeground(Color.WHITE);
		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBackground(new Color(255, 113, 51));
		btnLamMoi.setOpaque(true);
		btnLamMoi.setBorderPainted(false);
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFont(new Font("Arial", Font.BOLD, 14));
		btnLamMoi.setIcon(ResizeImage(""));

		// Panel hiển thị danh sách
		JPanel pnDanhsach = new JPanel();
		pnDanhsach.setLayout(new BorderLayout());
		pnDanhsach.setBounds(10, 142, 1315, 430);
		pnDanhsach.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Danh sách Nhân Viên",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));

		String[] chuoi = { "Mã Nhân Viên", "Tên Nhân Viên", "Địa Chỉ", "Email", "Số Điện Thoại", "Giới Tính", "Chức Vụ" };

		model = new DefaultTableModel(chuoi,0);
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

		btnLamMoi.setBounds(472, 20, 140, 50);
		btnSua.setBounds(780, 20, 180, 50);
		btnThem.setBounds(1098, 20, 200, 50);

		JPanel pnTongQuan = new JPanel();
		pnTongQuan.setLayout(null);
		pnTongQuan.setBounds(1020, 20, 300, 115);
		pnTongQuan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Tổng quan",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));

		JPanel pnChucNangb = new JPanel();
		pnChucNangb.setLayout(null);
		pnChucNangb.setBounds(10, 600, 1315, 80);
		pnChucNangb.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(new Color(192, 192, 192), 2), "Chức Năng",
				TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, new Font("Arial", Font.BOLD, 14))));

		JLabel lblTong = new JLabel("Tổng số Nhân viên: ");
		JLabel lblTongNam = new JLabel("Tổng số Nhân viên Nam: ");
		JLabel lblTongNu = new JLabel("Tổng số Nhân viên Nữ: ");
		
		txtTong= new JTextField();
		try {
			txtTong.setText(timTong());
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtTong.setEditable(false);
		txtTong.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtTong.setBackground(null);
		
		txtTongNam= new JTextField();
		try {
			txtTongNam.setText(timTongGT("Nam"));
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtTongNam.setEditable(false);
		txtTongNam.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtTongNam.setBackground(null);
		
		txtTongNu= new JTextField();
		try {
			txtTongNu.setText(timTongGT("Nữ"));
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		txtTongNu.setEditable(false);
		txtTongNu.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		txtTongNu.setBackground(null);
		

		lblTong.setBounds(20, 10, 160, 50);
		txtTong.setBounds(180, 20, 30, 30);
		lblTongNam.setBounds(20, 40, 260, 50);
		txtTongNam.setBounds(180, 50, 30, 30);
		lblTongNu.setBounds(20, 70, 260, 50);
		txtTongNu.setBounds(180, 80, 30, 30);
		

		pnChucnang.add(lbTim);
		pnChucnang.add(txtTim);

		pnChucNangb.add(btnSua);
		pnChucNangb.add(btnThem);
		pnChucNangb.add(btnLamMoi);

		pnTongQuan.add(lblTong);
		pnTongQuan.add(lblTongNam);
		pnTongQuan.add(lblTongNu);
		pnTongQuan.add(txtTong);
		pnTongQuan.add(txtTongNam);
		pnTongQuan.add(txtTongNu);

		JLabel lblLocGioiTinh = new JLabel("Lọc theo giới tính: ");
		lblLocGioiTinh.setFont(new Font("Arial", Font.BOLD, 20));
		lblLocGioiTinh.setForeground(new Color(255, 113, 51));
		lblLocGioiTinh.setBounds(54, 20, 190, 40);
		JComboBox cbxLocGioiTinh = new JComboBox<String>();
		cbxLocGioiTinh.setBounds(244, 30, 120, 30);
		cbxLocGioiTinh.setBackground(Color.WHITE);
		cbxLocGioiTinh.addItem("");
		cbxLocGioiTinh.addItem("Nam");
		cbxLocGioiTinh.addItem("Nữ");

		cbxLocGioiTinh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (cbxLocGioiTinh.getSelectedItem().toString().length() == 0) {
					try {
						loadNV();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (cbxLocGioiTinh.getSelectedItem().toString().length() > 0) {
					try {
						dao_nv = new DAO_NhanVien();
						model = dao_nv.timKiemGt("%" + cbxLocGioiTinh.getSelectedItem().toString() + "%");
						table.setModel(model);
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}

			}
		});
		txtTim.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (txtTim.getText().length() == 0) {
					try {
						loadNV();
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
							timNV();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});

		pnChucNangb.add(lblLocGioiTinh);
		pnChucNangb.add(cbxLocGioiTinh);

		// Thêm vào Panel chính
		pnMain.add(pnDanhsach);
		pnMain.add(pnChucnang);
		pnMain.add(pnChucNangb);
		pnMain.add(pnTongQuan);
		add(pnMain);

		btnThem.addActionListener(this);
		btnSua.addActionListener(this);
		btnLamMoi.addActionListener(this);

		try {
			loadNV();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		table.addMouseListener(this);
		//ThemNV.btnThem.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			new FormThemNhanVien().setVisible(true);
			
		} else if (o.equals(btnSua)) {
			if (kt == null) {
				JOptionPane.showMessageDialog(null, "Chưa chọn Nhân viên");
			} else {
				new FormCapNhatNhanVien(nhanVien).setVisible(true);
				dispose();
			}
			
		} else if (o.equals(btnLamMoi)) {
			try {
				model = dao_nv.getAllNV();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			table.setModel(model);
			try {
				txtTong.setText(timTong());
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				txtTongNam.setText(timTongGT("Nam"));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				txtTongNu.setText(timTongGT("Nữ"));
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if (o.equals(FormThemNhanVien.btnThem)) {
			try {
				loadNV();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private void dispose() {
		// TODO Auto-generated method stub

	}

//	public void loadNV() throws SQLException {
//		model = dao_nv.getAllNV();
//		table.setModel(model);
//	}
	
	public void loadNV() throws SQLException {
		model = dao_nv.getAllNV();
		table.setModel(model);
	}

	private void timNV() throws SQLException {
		DAO_NhanVien dao_nv = new DAO_NhanVien();
		model = dao_nv.timKiem("%" + txtTim.getText() + "%");
		table.setModel(model);
	}

	private void timSDT() throws SQLException {
		DAO_NhanVien dao_nv = new DAO_NhanVien();
		model = dao_nv.timKiemSDT("%" + txtTim.getText() + "%");
		table.setModel(model);
	}

	public static String timTong() throws SQLException {
		dao_nv = new DAO_NhanVien();
		return dao_nv.tong();
	}

	public static String timTongGT(String GT) throws SQLException {
		dao_nv = new DAO_NhanVien();
		return dao_nv.tongGT(GT);
	}

	// private boolean xoaNV() throws SQLException {
	// DAO_KhachHang dao_kh= new DAO_KhachHang();
	// if(dao_kh.xoaNV(ui.txtMaNV.getText()));
	// return true;
	//
	// }
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuanLiNhanVien frame = new QuanLiNhanVien();
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

	public ImageIcon ResizeImage(String ImagePath) {
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		row = table.getSelectedRow();
		kt=nhanVien.getMaNV(table.getValueAt(row, 0).toString());
		nhanVien.setMaNV(table.getValueAt(row, 0).toString());
		nhanVien.setTenNV(table.getValueAt(row, 1).toString());
		nhanVien.setEmail(table.getValueAt(row, 2).toString());
		nhanVien.setDiaChi(table.getValueAt(row, 3).toString());
		nhanVien.setSoDT(table.getValueAt(row, 4).toString());
		nhanVien.setGioiTinh(table.getValueAt(row, 5).toString());
		nhanVien.setChucVu(table.getValueAt(row, 6).toString());

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
}
