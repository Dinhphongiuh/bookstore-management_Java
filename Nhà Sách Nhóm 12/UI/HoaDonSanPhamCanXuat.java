package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

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

import javax.swing.JTextArea;
import java.awt.Font;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
/**
 * 
 * Đỗ Thị Tường Vi
 *
 */
public class HoaDonSanPhamCanXuat extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnHoaDon;
	private DAO_HoaDon hoaDonDao;
	private DAO_KhachHang khachHangDao;
	private DAO_NhanVien nhanVienDao;
	private DAO_ChiTietHoaDonSach chiTietHoaDonSachDao;
	private DAO_ChiTietHoaDonDCHT chiTietHoaDonDCHTDao;
	private DAO_Sach sachDao;
	private DAO_DungCuHocTap dchtDao;
	public static JTextArea textArea;
		

	public HoaDonSanPhamCanXuat(String maHoaDonDuocChon) {
		
		hoaDonDao=new DAO_HoaDon();
		khachHangDao=new DAO_KhachHang();
		nhanVienDao=new DAO_NhanVien();
		chiTietHoaDonSachDao=new DAO_ChiTietHoaDonSach();
		chiTietHoaDonDCHTDao=new DAO_ChiTietHoaDonDCHT();
		sachDao=new DAO_Sach();
		dchtDao=new DAO_DungCuHocTap();
		JScrollPane sc = new JScrollPane();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(425,600));
		setLocationRelativeTo(null);
		setResizable(false);

		pnHoaDon = new JPanel();
		pnHoaDon.setBorder(new EmptyBorder(5, 5, 5, 5));
		pnHoaDon.setLayout(null);
		textArea = new JTextArea();
		textArea.setEnabled(true);
		HoaDon hoaDon=hoaDonDao.timHoaDon(maHoaDonDuocChon);
		KhachHang khachHang=khachHangDao.layKhachHangTheoMa(hoaDon.getKhachHang().getMaKH());
		NhanVien nhanVien=nhanVienDao.layNhanVienTheoMa(hoaDon.getNhanvien().getMaNV());
		double tongTien=hoaDon.getTongTien();
		int tongSoLuong=hoaDon.getTongSoLuong();
		textArea.setFont(new Font("Arial", Font.BOLD, 11));
		textArea.setBounds(5, 5, 400, 535);
		textArea.append("\n 		Nhà sách Flames \n");
		textArea.append("\n 12 Nguyễn Văn Bảo, phường 12, quận Gò Vấp, Thành Phố Hồ Chí Minh \n");
		textArea.append("---------------------------------------------------------------------------------------------------\n");
		textArea.append("	 	HÓA ĐƠN \n");
		textArea.append("  Ngày lập hóa đơn: " + hoaDon.getNgayLapHoaDon() + "\n");	
		textArea.append("  Khách hàng:         " + khachHang.getTenKH() + "\n");
		textArea.append("  Điện thoại:           " + khachHang.getSoDT() + "\n");
		textArea.append("  Nhân viên:            " + nhanVien.getTenNV() + "\n");
		textArea.append("\n"+String.format("   |%-14s|%-16s|%-16s|\n", "    SL    ", "      ĐGiá    	  ", "	TTiền"));
		textArea.append(" --------------------------------------------------------------------------------------------------\n");

	
		
		List<ChiTietHoaDonSach> listSach=chiTietHoaDonSachDao.layCTHDSach(maHoaDonDuocChon);
		for (ChiTietHoaDonSach e : listSach) {
			Sach sach=sachDao.laySachTheoMaSach(e.getSanPham().getMaSanPham());
			String tenSP=sach.getTenSanPham();
			int sL=e.getSoLuong();
			double ttien=e.getThanhTien();
		
			double dg=sach.getGiaBan();
			tenSP = (tenSP.length() > 64) ? tenSP.substring(0,64) +"\n" +"  "+tenSP.substring(64,tenSP.length()) : tenSP ;
			textArea.append("  "+tenSP +"\n");
			textArea.append(String.format("   |%-14s|%-24s|%-16s|\n", "    "+sL+"    ", "      "+dg+"    ", "	"+ttien));
			textArea.append("  -------------------------------------------------------------------------------------------------\n");
		}
		
		List<ChiTietHoaDonDCHT> listDCHT=chiTietHoaDonDCHTDao.layCTHDDCHT(maHoaDonDuocChon);
		for (ChiTietHoaDonDCHT e1 : listDCHT) {
			DungCuHocTap dcht=dchtDao.layDCHTTheoMaDCHT(e1.getSanPham().getMaSanPham());
			String tenSP=dcht.getTenSanPham();
		
			int sL=e1.getSoLuong();
			double ttien=e1.getThanhTien();
			double dg=dcht.getGiaBan();
			tenSP = (tenSP.length() > 64) ? tenSP.substring(0,64) +"\n" +"  "+tenSP.substring(64,tenSP.length()) : tenSP ;
			textArea.append("  "+tenSP +"\n");
			textArea.append(String.format("   |%-14s|%-24s|%-16s|\n", "    "+sL+"    ", "      "+dg+"    ", "	"+ttien));
			textArea.append("  -------------------------------------------------------------------------------------------------\n");
		}
//		for(int i = 0; i<tableRowCount; i++) {
//			tenSanPham = QuanLiHoaDon.table.getValueAt(i, 2).toString();
//			soLuong = QuanLiHoaDon.table.getValueAt(i, 7).toString();
//			donGia = QuanLiHoaDon.table.getValueAt(i, 8).toString();
//			thanhTien = QuanLiHoaDon.table.getValueAt(i, 9).toString();
//			tenSanPham = (tenSanPham.length() > 64) ? tenSanPham.substring(0,64) +"\n" +"  "+tenSanPham.substring(64,tenSanPham.length()) : tenSanPham ;
//			textArea.append("  "+tenSanPham +"\n");
//			textArea.append(String.format("   |%-14s|%-24s|%-16s|\n", "    "+soLuong+"    ", "      "+donGia+"    ", "	"+thanhTien));
//			textArea.append("  -------------------------------------------------------------------------------------------------\n");
//		}
		DecimalFormat df = new DecimalFormat("#,###,###,###  VNĐ");
		textArea.append(String.format("\n  Tổng số lượng: %20s" , String.valueOf(tongSoLuong)));
		textArea.append(String.format("\n  Tổng cộng: %20s \n" ,df.format(tongTien).toString()));
		
		pnHoaDon.add(textArea);
		textArea.getRows();
		add(pnHoaDon);
		setVisible(true);

	}
}



