package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


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

import javax.swing.JScrollPane;
import javax.swing.JTable;


public class HoaDonSanPham extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pnHoaDon;
	public static JTextArea textArea;
		

	public HoaDonSanPham(String tenNV, double tienKhachDua) {
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

		textArea.setFont(new Font("Arial", Font.BOLD, 11));
		textArea.setBounds(5, 5, 400, 535);
		textArea.append("\n 		Nhà sách Flames \n");
		textArea.append("\n 12 Nguyễn Văn Bảo, phường 12, quận Gò Vấp, Thành Phố Hồ Chí Minh \n");
		textArea.append("---------------------------------------------------------------------------------------------------\n");
		textArea.append("	 	HÓA ĐƠN \n");
		textArea.append("  Ngày lập hóa đơn: " + QuanLiHoaDon.txtNgayLHD.getText() + "\n");	
		textArea.append("  Khách hàng:         " + QuanLiHoaDon.tfTenKH.getText() + "\n");
		textArea.append("  Điện thoại:           " + QuanLiHoaDon.txtSDT.getText() + "\n");
		textArea.append("  Nhân viên:            " + tenNV + "\n");
		textArea.append("\n"+String.format("   |%-14s|%-16s|%-16s|\n", "    SL    ", "      ĐGiá    	  ", "	TTiền"));
		textArea.append(" --------------------------------------------------------------------------------------------------\n");

		int tableRowCount  = QuanLiHoaDon.table.getRowCount();
		String tenSanPham = null, soLuong, donGia, thanhTien;
//		String[] header = { "STT", "Mã sản phẩm", "Tên sản phẩm", "Nhà xuất bản", "Tác giả", "Năm xuất bản",
//				"Thương hiệu", "Số lượng", "Đơn giá", "Thành tiền" };
		for(int i = 0; i<tableRowCount; i++) {
			tenSanPham = QuanLiHoaDon.table.getValueAt(i, 2).toString();
			soLuong = QuanLiHoaDon.table.getValueAt(i, 7).toString();
			donGia = QuanLiHoaDon.table.getValueAt(i, 8).toString();
			thanhTien = QuanLiHoaDon.table.getValueAt(i, 9).toString();
			tenSanPham = (tenSanPham.length() > 64) ? tenSanPham.substring(0,64) +"\n" +"  "+tenSanPham.substring(64,tenSanPham.length()) : tenSanPham ;
			textArea.append("  "+tenSanPham +"\n");
			textArea.append(String.format("   |%-14s|%-24s|%-16s|\n", "    "+soLuong+"    ", "      "+donGia+"    ", "	"+thanhTien));
			textArea.append("  -------------------------------------------------------------------------------------------------\n");
		}
		DecimalFormat df = new DecimalFormat("#,###,###,###  VNĐ");
		double tongTien=QuanLiHoaDon.tongTongTien;
		double tienTraLai=tienKhachDua-tongTien;
		textArea.append(String.format("\n  Tổng số lượng:  %20s" , QuanLiHoaDon.txtTSoLuong.getText()));
		textArea.append(String.format("\n  Tổng cộng:      %20s" , QuanLiHoaDon.txtTongTien.getText()));
		textArea.append(String.format("\n  Tiền khách đưa:%20s",df.format(tienKhachDua)));
		textArea.append(String.format("\n  Tiền trả lại:   %20s\n",df.format(tienTraLai)));
		
	
		textArea.append("\n\n\n 	Cảm ơn quý khách và hẹn gặp lại! \n");
		pnHoaDon.add(textArea);
		textArea.getRows();
		add(pnHoaDon);
		setVisible(true);
	}
}
