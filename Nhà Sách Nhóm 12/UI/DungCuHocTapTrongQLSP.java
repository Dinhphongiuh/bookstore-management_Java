package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
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

public class DungCuHocTapTrongQLSP  extends JPanel implements ActionListener, MouseListener{
	private DAO_LoaiDCHT daoLoaiDCHT;
	private ArrayList<LoaiDungCuHocTap> listLoaiDCHT;
	private DAO_DungCuHocTap daoDCHT;
	private ArrayList<DungCuHocTap> listDCHT;
	
	private JPanel pnTT, pnHinhAnh;
	public  JLabel lblHinhAnh;
	private JLabel lblThongBao , lblTinhTrang;
	private JButton btnSua, btnNgungKinhDoanh;
	String path;
	public  static String chuoiTinhTrang;
	public static boolean tinhTrang;
	public DungCuHocTapTrongQLSP(DungCuHocTap dcht) {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		daoLoaiDCHT = new DAO_LoaiDCHT();
		daoDCHT = new DAO_DungCuHocTap();
		//Border borderSP = BorderFactory.createLineBorder(Color.GRAY);
		//setBackground(new Color(230, 230, 230));
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(620,185));

		//setBorder(borderSP);

		JPanel pnRight = new JPanel();
		pnRight.setLayout(new BoxLayout(pnRight, BoxLayout.Y_AXIS));
		pnRight.setBackground(new Color(230, 230, 230));

		JPanel pnSLT = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnSLT.setBackground(new Color(230, 230, 230));
		JLabel lblSoLuongTon = new JLabel("Số lượng tồn: " + dcht.getSoLuong());
		lblSoLuongTon.setFont(new Font("Arial", Font.BOLD, 14));
		lblSoLuongTon.setForeground(Color.BLACK);
		pnSLT.add(lblSoLuongTon);

		DecimalFormat formatter = new DecimalFormat("###,###,###");

		JPanel pnGiaBan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnGiaBan.setBackground(new Color(230, 230, 230));
		JLabel lblGiaBan = new JLabel("Giá bán: " + String.valueOf(formatter.format(dcht.getGiaBan())+" VNĐ"));
		lblGiaBan.setFont(new Font("Arial", Font.BOLD, 14));
		lblGiaBan.setForeground(Color.BLACK);
		pnGiaBan.add(lblGiaBan);


		JPanel pnSuaXoa = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnSuaXoa.setBackground(new Color(230, 230, 230));
		JLabel lblSua = new JLabel("Sửa");
		Font font = new Font("Arial",Font.BOLD , 14);
		lblSua.setFont(font);
		btnSua = new JButton("");
		btnSua.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSua.setBackground(new Color(255, 133, 51));
		lblSua.setForeground(Color.WHITE);
		btnSua.add(lblSua);
		JLabel lblNgungKinhDoanh = new JLabel("Ngừng kinh doanh");
		btnNgungKinhDoanh = new JButton("");
		btnNgungKinhDoanh.setBackground(new Color(255, 133, 51));
		btnNgungKinhDoanh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblNgungKinhDoanh.setForeground(Color.WHITE);
		lblNgungKinhDoanh.setFont(font);
		btnNgungKinhDoanh.add(lblNgungKinhDoanh);
		pnSuaXoa.add(btnSua);
		pnSuaXoa.add(btnNgungKinhDoanh);

		JPanel pnTinhTrang = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		pnTinhTrang.setBackground(new Color(230, 230, 230));
		chuoiTinhTrang = null;
		lblTinhTrang = new JLabel();
		if(dcht.getTinhTrang()) {
			if(dcht.getSoLuong() == 0) {
				chuoiTinhTrang = "Hết hàng";
				lblTinhTrang = new JLabel("Tình trạng: " + chuoiTinhTrang);
				lblTinhTrang.setForeground(Color.RED);
				lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 14));
			}
			else {
				chuoiTinhTrang = "Còn hàng";
				lblTinhTrang = new JLabel("Tình trạng: " + chuoiTinhTrang);
				lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 14));
			}
		}
		else {
			//if(s.getSoLuong() == 0) {
			chuoiTinhTrang = "Ngừng kinh doanh";
			lblTinhTrang.setText("Tình trạng: " + chuoiTinhTrang);
			lblTinhTrang.setFont(new Font("Arial", Font.BOLD, 14));
			lblTinhTrang.setForeground(Color.RED);
			btnSua.setEnabled(false);
			btnNgungKinhDoanh.setEnabled(false);
			btnSua.setBackground(new Color(255, 179, 128));
			btnNgungKinhDoanh.setBackground(new Color(255, 179, 128));
		}
		pnTinhTrang.add(lblTinhTrang);



		pnRight.add(pnSLT);
		pnRight.add(pnGiaBan);
		pnRight.add(pnTinhTrang);
		pnRight.add(pnSuaXoa);

		pnTT = new JPanel();
		pnTT.setLayout(new BoxLayout(pnTT, BoxLayout.Y_AXIS));
		pnTT.setBackground(new Color(230, 230, 230));

		JPanel pnTenDCHT = new JPanel();
		JLabel lblTenDCHT = new JLabel("Tên dụng cụ học tập: " + dcht.getTenSanPham());
		lblTenDCHT.setFont(new Font("Arial", Font.BOLD, 16));
		pnTenDCHT.setForeground(Color.BLACK);
		pnTenDCHT.setBackground(new Color(230, 230, 230));
		pnTenDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnTenDCHT.add(lblTenDCHT);
		pnTT.add(pnTenDCHT);

		JPanel pnThuongHieu = new JPanel();
		pnThuongHieu.setBackground(new Color(230, 230, 230));
		JLabel lblThuongHieu = new JLabel("Thương hiệu: " + dcht.getThuongHieu());
		lblThuongHieu.setFont(new Font("Arial", Font.BOLD, 14));
		lblThuongHieu.setForeground(Color.BLACK);
		pnThuongHieu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnThuongHieu.add(lblThuongHieu);
		pnTT.add(pnThuongHieu);


		JPanel pnChatLieu = new JPanel();
		pnChatLieu.setBackground(new Color(230, 230, 230));
		JLabel lblChatLieu = new JLabel("Chất liệu: "+ dcht.getChatLieu());
		lblChatLieu.setFont(new Font("Arial", Font.BOLD, 14));
		lblChatLieu.setForeground(Color.BLACK);
		pnChatLieu.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnChatLieu.add(lblChatLieu);
		pnTT.add(pnChatLieu);

		String tenLoaiDCHT = null;
		listLoaiDCHT = daoLoaiDCHT.getAllLoaiDCHT();
		for(LoaiDungCuHocTap loaiDCHT : listLoaiDCHT) {
			if(dcht.getLoaiDungCuHocTap().getMaLoaiDCHT().equals(loaiDCHT.getMaLoaiDCHT())) {
				tenLoaiDCHT = loaiDCHT.getTenLoaiDCHT();
			}
		}
		JLabel lblLoaiDCHT = new JLabel("Loại dụng cụ học tập: " + tenLoaiDCHT);
		lblLoaiDCHT.setFont(new Font("Arial", Font.BOLD, 14));
		lblLoaiDCHT.setForeground(Color.BLACK);
		JPanel pnLoaiDCHT = new JPanel();
		pnLoaiDCHT.setBackground(new Color(230, 230, 230));
		pnLoaiDCHT.setLayout(new FlowLayout(FlowLayout.LEFT));
		pnLoaiDCHT.add(lblLoaiDCHT);
		pnTT.add(pnLoaiDCHT);


		lblHinhAnh = new JLabel("");

		JPanel pnLeft = new JPanel(new BorderLayout());
		pnLeft.setBackground(new Color(230, 230, 230));
		pnHinhAnh = new JPanel();
		//pnHinhAnh.setPreferredSize(new Dimension(170,170));
		pnHinhAnh.setBackground(new Color(230, 230, 230));
		path = dcht.getHinhAnh().trim();
		if(dcht.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
			JPanel pnKhungHinhAnh = new JPanel();
			pnKhungHinhAnh.setPreferredSize(new Dimension(170,170));
			pnKhungHinhAnh.setLayout(new BoxLayout(pnKhungHinhAnh, BoxLayout.X_AXIS));
			pnKhungHinhAnh.add(Box.createHorizontalStrut(40));
			lblHinhAnh.setText("Chưa có hình ảnh");
			pnKhungHinhAnh.add(lblHinhAnh);
			pnHinhAnh.add(pnKhungHinhAnh);
		}
		else {
			lblHinhAnh.setPreferredSize(new Dimension(170,170));
			lblHinhAnh.setIcon(ResizeImage(path));
			pnHinhAnh.add(lblHinhAnh);
		}
		lblHinhAnh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		pnHinhAnh.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		pnLeft.add(pnHinhAnh, BorderLayout.CENTER);
		pnLeft.add(pnTT, BorderLayout.EAST);

		Border borderSP = BorderFactory.createLineBorder(Color.GRAY);
		JPanel pnMain = new JPanel();
		pnMain.setLayout(new BorderLayout());

		pnMain.setBackground(new Color(230, 230, 230));
		pnMain.add(pnLeft, BorderLayout.WEST);
		pnMain.add(pnRight, BorderLayout.EAST);
		pnMain.setBorder(borderSP);
		pnLeft.setBorder(new EmptyBorder(6, 10, 6, 10));
		pnRight.setBorder(new EmptyBorder(6, 10, 6, 10));

		//		add(pnLeft, BorderLayout.WEST);
		//		add(pnRight, BorderLayout.EAST);
		add(pnMain);

		btnSua.addActionListener(new ActionListener() {	
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new FormSuaDCHT(dcht);
			}
		});
		lblHinhAnh.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				new FormChiTietDCHT(dcht);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if(dcht.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
					lblHinhAnh.setText("Chưa có hình ảnh");
				}
				else {
					lblHinhAnh.setIcon(ResizeImage(path));
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				if(dcht.getHinhAnh().trim().equals("Chưa có hình ảnh")) {
					lblHinhAnh.setText("Chưa có hình ảnh");
				}
				else {
					lblHinhAnh.setIcon(ResizeImageHover(path));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		pnHinhAnh.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				new FormChiTietDCHT(dcht);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btnNgungKinhDoanh.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(dcht.getTinhTrang()) {
					int hoi=JOptionPane.showConfirmDialog(null, "Dụng cụ học tập này sẽ ngừng kinh doanh","Chú ý",JOptionPane.YES_NO_OPTION);
					if(hoi==JOptionPane.YES_OPTION) { 
						lblTinhTrang.setText("Tình trạng: " + "Ngừng kinh doanh");
						daoDCHT.SuaTinhTrangDCHT(false, dcht.getMaSanPham());
						btnSua.setEnabled(false);
					}
				}
//				else {
//					JOptionPane.showMessageDialog(
//							null, 
//							"Số lượng tồn phải về 0", 
//							"Thông báo", 
//							JOptionPane.INFORMATION_MESSAGE);
//				}
			}
		});
	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(160, 160, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public ImageIcon ResizeImageHover(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(170, 170, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
