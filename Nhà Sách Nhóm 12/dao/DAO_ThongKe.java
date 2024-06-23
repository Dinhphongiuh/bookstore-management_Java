package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.ChiTietHoaDonDCHT;
import entity.ChiTietHoaDonSach;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.SanPham;

public class DAO_ThongKe {
	
	/**
	 * Thống kê số lượng sản phẩm ngày của chủ cửa hàng
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public int SoLuongSanPhamTheoNgayChon(String tuNgay, String denNgay) {
		int count = 0;
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT COALESCE(sum(soLuong),0) FROM ChiTietHoaDonSach \r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ?";
			String sqlDCHT = "SELECT COALESCE(sum(soLuong),0) FROM ChiTietHoaDonDungCuHT \r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ?";
			PreparedStatement pst = con.prepareStatement(sql);
			PreparedStatement pstDCHT = con.prepareStatement(sqlDCHT);
			pst.setString(1,dateBegin);
			pst.setString(2, dateEnd);
			pstDCHT.setString(1,dateBegin);
			pstDCHT.setString(2, dateEnd);
			ResultSet rs = pst.executeQuery();
			ResultSet rsDCHT = pstDCHT.executeQuery();
			if(rs.next()) {
				count+=Integer.valueOf(rs.getString(1));

			}
			System.out.println(count);
			if(rsDCHT.next()) {
				count+=Integer.valueOf(rsDCHT.getString(1));
			}
			System.out.println(count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê số lượng sản phẩm theo ngày của nhân viên
	 * @param tuNgay
	 * @param denNgay
	 * @param maNV
	 * @return
	 */
	public int SoLuongSanPhamTheoNgayChonNhanVien(String tuNgay, String denNgay, String maNV) {
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "SELECT COALESCE(sum(soLuong),0) FROM ChiTietHoaDonSach \r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ? and NhanVien.maNhanVien = ? ";
			String sqlDCHT = "SELECT COALESCE(sum(soLuong),0) FROM ChiTietHoaDonDungCuHT \r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ? and NhanVien.maNhanVien = ? ";
			PreparedStatement pst = con.prepareStatement(sql);
			PreparedStatement pstDCHT = con.prepareStatement(sqlDCHT);
			pst.setString(1,dateBegin);
			pst.setString(2, dateEnd);
			pst.setString(3, maNV);
			pstDCHT.setString(1,dateBegin);
			pstDCHT.setString(2, dateEnd);
			pstDCHT.setString(3, maNV);
			ResultSet rs = pst.executeQuery();
			ResultSet rsDCHT = pstDCHT.executeQuery();
			if(rs.next()) {
				count+=Integer.parseInt(rs.getString(1));
			}	
			if(rsDCHT.next()) {
				count+=Integer.parseInt(rsDCHT.getString(1));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê số lượng hóa đơn theo ngày chủ cửa hàng
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public int SoLuongHoaDonTheoNgayChon(String tuNgay, String denNgay) {
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select count(*) from HoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,dateBegin);
			pst.setString(2,dateEnd);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê số lượng hóa đơn ngày của nhân viên
	 * @param tuNgay
	 * @param denNgay
	 * @param maNhanVien
	 * @return
	 */
	public int SoLuongHoaDonTheoNgayChonNV(String tuNgay, String denNgay, String maNhanVien) {
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select count(*) from HoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ? and maNhanVien = ?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,dateBegin);
			pst.setString(2,dateEnd);
			pst.setString(3,maNhanVien);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê doanh thu theo ngày của chủ
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public double  DoanhThuTheoNgayChon(String tuNgay, String denNgay) {
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		double  tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongTien) from HoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,dateBegin);
			pst.setString(2, dateEnd);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				tongTien= rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tongTien);
		return tongTien;
	}
	/**
	 * Thống kê doanh thu theo ngày của nhân viên
	 * @param tuNgay
	 * @param denNgay
	 * @param maNhanVien
	 * @return
	 */
	public double  DoanhThuTheoNgayChonNV(String tuNgay, String denNgay, String maNhanVien) {
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		double  tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongTien) from HoaDon\r\n"
					+ "where ngayLapHoaDon >= ? and ngayLapHoaDon <= ? and maNhanVien =?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,dateBegin);
			pst.setString(2, dateEnd);
			pst.setString(3, maNhanVien);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				tongTien= rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tongTien);
		return tongTien;
	}
	/**
	 * Thống kê sản phẩm chưa được bán 
	 * @return
	 */
	public int SoLuongSPChuaBan() {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select count(*) from Sach \r\n"
					+ "where  not exists (select Sach.maSanPham\r\n"
					+ "					from  ChiTietHoaDonSach \r\n"
					+ "					where Sach.maSanPham = ChiTietHoaDonSach.maSach)";		
			PreparedStatement pst = con.prepareStatement(sql);		

			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Số lượng sản phẩm bán theo quý của chủ 
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public int  SoLuongSanPhamTheoQuy(String nam, String thang1, String thang2, String thang3) {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongSoLuong) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê số lượng sản phẩm bán theo quý của nhân viên
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public int  SoLuongSanPhamTheoQuyNV(String nam, String thang1, String thang2, String thang3, String maNhanVien) {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongSoLuong) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?) and maNhanVien =?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			pst.setString(5, maNhanVien);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê doanh thu theo quý của chủ
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public double  DoanhThuTheoQuy(String nam, String thang1, String thang2, String thang3) {
		double  tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongTien) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				tongTien= rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tongTien);
		return tongTien;
	}
	/**
	 * Thống kê doanh thu theo quý của nhân viên
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public double  DoanhThuTheoQuyNV(String nam, String thang1, String thang2, String thang3, String maNhanVien) {
		double  tongTien = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select SUM(tongTien) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)and maNhanVien =?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			pst.setString(5, maNhanVien);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				tongTien= rs.getDouble(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(tongTien);
		return tongTien;
	}
	/**
	 * Thống kê số lượng hóa đơn đã xuất theo quý của chủ 
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public int SoHoaDonTheoQuy(String nam, String thang1, String thang2, String thang3) {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select count(*) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * Thống kê Số hóa đơn đã xuất trong quý  của nhân viên
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public int SoHoaDonTheoQuyNV(String nam, String thang1, String thang2, String thang3, String maNhanVien) {
		int count = 0;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select count(*) from HoaDon\r\n"
					+ "where YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)and maNhanVien =?";		
			PreparedStatement pst = con.prepareStatement(sql);		
			pst.setString(1,nam);
			pst.setString(2, thang1);
			pst.setString(3, thang2);
			pst.setString(4, thang3);
			pst.setString(5, maNhanVien);
			ResultSet rs = pst.executeQuery();

			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	/**
	 * Danh sách chi tiết sách theo ngày của chủ cửa hàng
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public ArrayList<ChiTietHoaDonSach> thongKeSanPhamTheoNgay(String tuNgay,String denNgay){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<ChiTietHoaDonSach> dsSach = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maSach,sum(soLuong) FROM ChiTietHoaDonSach\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ?\r\n"
					+ "group by maSach\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maSach = rs.getString(1);
				int soLuong = rs.getInt(2);
				//				double thanhTien = rs.getInt(3);
				//				String maHD = rs.getString(4);
				ChiTietHoaDonSach CTHDSACH = new ChiTietHoaDonSach(new SanPham(maSach),
						soLuong);
				dsSach.add(CTHDSACH);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	/**
	 * Danh sách chi tiết dụng cụ học tập theo ngày của chủ cửa hàng
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public ArrayList<ChiTietHoaDonDCHT> thongKeDCHTTheoNgay(String tuNgay,String denNgay){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<ChiTietHoaDonDCHT> dsDCHT = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maDCHT,sum(soLuong) FROM ChiTietHoaDonDungCuHT\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ?\r\n"
					+ "group by maDCHT\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maDCHT = rs.getString(1);
				int soLuong = rs.getInt(2);
				ChiTietHoaDonDCHT CTHDDCHT = new ChiTietHoaDonDCHT(new SanPham(maDCHT),
						soLuong);
				dsDCHT.add(CTHDDCHT);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	/**
	 * Thống kê load Hóa đơn của chủ cửa hàng theo ngày
	 * @param tuNgay
	 * @param denNgay
	 * @return
	 */
	public ArrayList<HoaDon> thongKeHoaDonCCHTheoNgay(String tuNgay,String denNgay){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<HoaDon> dsHoaDon = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "select SUM(HoaDon.tongTien), maNhanVien from HoaDon "
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ?\r\n"
					+	"group by maNhanVien \r\n"
					+ "order by SUM(HoaDon.tongTien) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				double tongTien = rs.getDouble(1);
				NhanVien nv = new NhanVien(rs.getString(2));
				HoaDon hd = new HoaDon(tongTien,nv);
				dsHoaDon.add(hd);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;	
	}
	/**
	 * Thống kê load Hóa đơn của chủ cửa hàng theo quý
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public ArrayList<HoaDon> thongKeHoaDonCCHTheoQuy(String nam, String thang1, String thang2, String thang3){
		ArrayList<HoaDon> dsHoaDon = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "select SUM(HoaDon.tongTien), maNhanVien from HoaDon "
					+ "where  YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)\r\n"
					+	"group by maNhanVien \r\n"
					+ "order by SUM(HoaDon.tongTien) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				double tongTien = rs.getDouble(1);
				NhanVien nv = new NhanVien(rs.getString(2));
				HoaDon hd = new HoaDon(tongTien,nv);
				dsHoaDon.add(hd);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;	
	}
	/**
	 * Thống kê load Hóa đơn của nhân viên theo ngày
	 * @param tuNgay
	 * @param denNgay
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<HoaDon> thongKeHoaDonNVTheoNgay(String tuNgay,String denNgay, String maNhanVien){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<HoaDon> dsHoaDon = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "select * from HoaDon "
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ? and maNhanVien = ?\r\n"
					+ "order by tongTien desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			preparedStatement.setString(3, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maHoaDon = rs.getString(1);
				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				double tongTien = rs.getDouble(3);

				KhachHang kh = new KhachHang(rs.getString(4));
				NhanVien nv = new NhanVien(rs.getString(5));

				int tongSL = rs.getInt(6);
				HoaDon hd = new HoaDon(maHoaDon,ngayLapHoaDon,tongTien,tongSL,nv,kh);
				dsHoaDon.add(hd);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;	
	}
	/**
	 * Thống kê load Hóa đơn của nhân viên theo quý
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<HoaDon> thongKeHoaDonNVTheoQuy(String nam, String thang1, String thang2, String thang3, String maNhanVien){
		ArrayList<HoaDon> dsHoaDon = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "select * from HoaDon "
					+ "where  YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?) and maNhanVien = ?\r\n"
					+ "order by tongTien desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			preparedStatement.setString(5, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maHoaDon = rs.getString(1);
				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				double tongTien = rs.getDouble(3);

				KhachHang kh = new KhachHang(rs.getString(4));
				NhanVien nv = new NhanVien(rs.getString(5));

				int tongSL = rs.getInt(6);
				HoaDon hd = new HoaDon(maHoaDon,ngayLapHoaDon,tongTien,tongSL,nv,kh);
				dsHoaDon.add(hd);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsHoaDon;	
	}

	/**
	 * Danh sách chi tiết sách theo ngày của nhân viên
	 * @param tuNgay
	 * @param denNgay
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<ChiTietHoaDonSach> thongKeSanPhamTheoNgayNhanVien(String tuNgay,String denNgay, String maNhanVien){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<ChiTietHoaDonSach> dsSach = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maSach,sum(soLuong) FROM ChiTietHoaDonSach\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ?\r\n and NhanVien.maNhanVien = ?\r\n"
					+ "group by maSach\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			preparedStatement.setString(3, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maSach = rs.getString(1);
				int soLuong = rs.getInt(2);
				//				double thanhTien = rs.getInt(3);
				//				String maHD = rs.getString(4);
				ChiTietHoaDonSach CTHDSACH = new ChiTietHoaDonSach(new SanPham(maSach),
						soLuong);
				dsSach.add(CTHDSACH);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	/**
	 * Danh sách chi tiết dụng cụ học tập theo ngày của Nhân Viên
	 * @param tuNgay
	 * @param denNgay
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<ChiTietHoaDonDCHT> thongKeDCHTTheoNgayNhanVien(String tuNgay,String denNgay, String maNhanVien){
		String dateBegin = tuNgay.toString() + " 00:00:00";
		String dateEnd = denNgay.toString() + " 23:59:59";
		ArrayList<ChiTietHoaDonDCHT> dsDCHT = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maDCHT,sum(soLuong) FROM ChiTietHoaDonDungCuHT\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where ngayLapHoaDon >= ?  and ngayLapHoaDon <= ?\r\n and NhanVien.maNhanVien = ?\r\n"
					+ "group by maDCHT\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dateBegin); 
			preparedStatement.setString(2, dateEnd);
			preparedStatement.setString(3, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maDCHT = rs.getString(1);
				int soLuong = rs.getInt(2);
				ChiTietHoaDonDCHT CTHDDCHT = new ChiTietHoaDonDCHT(new SanPham(maDCHT),
						soLuong);
				dsDCHT.add(CTHDDCHT);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}

	/**
	 * Danh sách chi tiết sách theo quý  của chủ cửa hàng
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public ArrayList<ChiTietHoaDonSach> thongKeSanPhamTheoQuy4(String nam, String thang1, String thang2, String thang3){
		ArrayList<ChiTietHoaDonSach> dsSach = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maSach,sum(soLuong) FROM ChiTietHoaDonSach\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where  YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)\r\n"
					+ "group by maSach\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maSach = rs.getString(1);
				int soLuong = rs.getInt(2);
				//				double thanhTien = rs.getInt(3);
				//				String maHD = rs.getString(4);
				ChiTietHoaDonSach CTHDSACH = new ChiTietHoaDonSach(new SanPham(maSach),
						soLuong);
				dsSach.add(CTHDSACH);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	/**
	 * Danh sách chi tiết DCHT theo quý  của chủ cửa hàng
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @return
	 */
	public ArrayList<ChiTietHoaDonDCHT> thongKeDCHTTheoQuy(String nam, String thang1, String thang2, String thang3){
		ArrayList<ChiTietHoaDonDCHT> dsDCHT = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maDCHT,sum(soLuong) FROM ChiTietHoaDonDungCuHT\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+ "where  YEAR(ngayLapHoaDon) =? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?)\r\n"
					+ "group by maDCHT\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maDCHT = rs.getString(1);
				int soLuong = rs.getInt(2);
				ChiTietHoaDonDCHT CTHDDCHT = new ChiTietHoaDonDCHT(new SanPham(maDCHT),
						soLuong);
				dsDCHT.add(CTHDDCHT);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}

	/**
	 * Danh sách chi tiết sách theo quý  của Nhân viên
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<ChiTietHoaDonSach> thongKeSanPhamTheoQuy4NV(String nam, String thang1, String thang2, String thang3, String maNhanVien){
		ArrayList<ChiTietHoaDonSach> dsSach = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maSach,sum(soLuong) FROM ChiTietHoaDonSach\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonSach.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where  YEAR(ngayLapHoaDon) = ? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?) and NhanVien.maNhanVien = ?\r\n"
					+ "group by maSach\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			preparedStatement.setString(5, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maSach = rs.getString(1);
				int soLuong = rs.getInt(2);
				//				double thanhTien = rs.getInt(3);
				//				String maHD = rs.getString(4);
				ChiTietHoaDonSach CTHDSACH = new ChiTietHoaDonSach(new SanPham(maSach),
						soLuong);
				dsSach.add(CTHDSACH);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	/**
	 * Danh sách chi tiết DCHT theo quý  của nhân viên
	 * @param nam
	 * @param thang1
	 * @param thang2
	 * @param thang3
	 * @param maNhanVien
	 * @return
	 */
	public ArrayList<ChiTietHoaDonDCHT> thongKeDCHTTheoQuyNV(String nam, String thang1, String thang2, String thang3, String maNhanVien){
		ArrayList<ChiTietHoaDonDCHT> dsDCHT = new ArrayList();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();	
			String sql = "SELECT maDCHT,sum(soLuong) FROM ChiTietHoaDonDungCuHT\r\n"
					+ "JOIN  HoaDon ON ChiTietHoaDonDungCuHT.maHoaDon = HoaDon.maHoaDon\r\n"
					+"JOIN NhanVien on NhanVien.maNhanVien=HoaDon.maNhanVien\r\n"
					+ "where  YEAR(ngayLapHoaDon) =? AND ( MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ? OR MONTH(ngayLapHoaDon) = ?) and NhanVien.maNhanVien = ?\r\n"
					+ "group by maDCHT\r\n"
					+ "order by sum(soLuong) desc";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, nam); 
			preparedStatement.setString(2, thang1);
			preparedStatement.setString(3, thang2);
			preparedStatement.setString(4, thang3);
			preparedStatement.setString(5, maNhanVien);
			ResultSet rs = preparedStatement.executeQuery();	
			while (rs.next()) {
				String maDCHT = rs.getString(1);
				int soLuong = rs.getInt(2);
				ChiTietHoaDonDCHT CTHDDCHT = new ChiTietHoaDonDCHT(new SanPham(maDCHT),
						soLuong);
				dsDCHT.add(CTHDDCHT);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
}
