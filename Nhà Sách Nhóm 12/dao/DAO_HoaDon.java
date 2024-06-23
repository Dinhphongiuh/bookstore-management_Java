package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class DAO_HoaDon 
{
	/**
	 * 
	 */
	private ConnectDB conDB = null;
	private static Connection con = null;
	private String prefixMaHoaDon = "HD00";

	@SuppressWarnings("static-access")
	public DAO_HoaDon() 
	{
		this.conDB = new ConnectDB();
		try 
		{
			this.conDB.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = this.conDB.getConnection();
	}

	/**
	 * Lấy mã hóa đơn
	 * 
	 * @param hoaDon
	 * @return maHD
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public String themHoaDon(HoaDon hoaDon) throws ClassNotFoundException, SQLException 
	{
		PreparedStatement stmt = null;
		int currentLength = this.laySoHoaDon();
		String maHD = this.prefixMaHoaDon + (++currentLength);
		try 
		{
			stmt = con.prepareStatement("INSERT INTO HoaDon VALUES(?,?,?,?,?,?)");
			stmt.setString(1, maHD);
			stmt.setTimestamp(2, hoaDon.getNgayLapHoaDon());
			stmt.setDouble(3, hoaDon.getTongTien());
			stmt.setString(4, hoaDon.getKhachHang().getMaKH());
			stmt.setString(5, hoaDon.getNhanvien().getMaNV());
			stmt.setInt(6, hoaDon.getTongSoLuong());
			stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{

			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return maHD;
	}

	/**
	 * Lấy số hóa đơn
	 * 
	 * @return response
	 */
	public int laySoHoaDon() 
	{
		ConnectDB conDB = new ConnectDB();
		
		int response = 0;
		try {
			Connection con = conDB.ConnectDataBase();
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("select count(maHoaDon) from HoaDon");
			response = rs.getHoldability();
			while (rs.next())
				response = rs.getInt(1);
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Danh sách hóa đơn
	 * 
	 * @return list<HoaDon>
	 */
	public List<HoaDon> getAllHoaDon() 
	{
		List<HoaDon> list = new ArrayList<HoaDon>();
		Connection con = conDB.getConnection();
		Statement stmt;
		String maHoaDon;
		try 
		{
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("Select * from HoaDon"); 
			while (rs.next()) 
			{
				maHoaDon = rs.getString(1);

				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				Double tongTien = rs.getDouble(3);
				String maKH = rs.getString(4);
				String maNhanVien = rs.getString(5);
				int tongSoLuong = rs.getInt(6);
				HoaDon hoaDon = new HoaDon(ngayLapHoaDon);
				hoaDon.setMaHoaDon(maHoaDon);
				hoaDon.setKhachHang(new KhachHang(maKH));
				hoaDon.setNhanvien(new NhanVien(maNhanVien));
				hoaDon.setTongTien(tongTien);
				hoaDon.setTongSoLuong(tongSoLuong);
				
				list.add(hoaDon);
			}
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		list.sort(((o1, o2) -> Integer.compare(Integer.parseInt(o1.getMaHoaDon().substring(2)),
				Integer.parseInt(o2.getMaHoaDon().substring(2)))));
		return list;
	}

	public List<HoaDon> xemHoaDonMoiNhat() 
	{
		List<HoaDon> list = new ArrayList<HoaDon>();
		Connection con = conDB.getConnection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from HoaDon order by ngayLapHoaDon desc");
			while (rs.next()) 
			{
				String maHoaDon = rs.getString(1);
				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				Double tongTien = rs.getDouble(3);
				String maKH = rs.getString(4);
				String maNhanVien = rs.getString(5);
				int tongSoLuong = rs.getInt(6);
				HoaDon hoaDon = new HoaDon(ngayLapHoaDon);
				hoaDon.setMaHoaDon(maHoaDon);
				hoaDon.setKhachHang(new KhachHang(maKH));
				hoaDon.setNhanvien(new NhanVien(maNhanVien));
				hoaDon.setTongTien(tongTien);
				hoaDon.setTongSoLuong(tongSoLuong);
				list.add(hoaDon);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return list;
	}

	/**
	 * Danh sách hóa đơn của ngày hiện tại
	 * @param maNV
	 * @return
	 */
	public List<Object> getAllOrderByNow(String maNV) 
	{
		List<Object> response = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("#,###,###,### VNĐ");
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT HoaDon.maHoaDon,HoaDon.maKhachHang,KhachHang.tenKhachHang, KhachHang.soDT, HoaDon.maNhanVien,NhanVien.tenNhanVien,HoaDon.ngayLapHoaDon, HoaDon.tongTien\r\n"
				+ "FROM HoaDon INNER JOIN\r\n"
				+ "KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN\r\n"
				+ "NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien\r\n"
				+ "where ngayLapHoaDon >= FORMAT (getdate(), 'yyyy-MM-dd') and NhanVien.maNhanVien = ?";

		try 
		{
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, maNV);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) 
			{
				double tien = Double.parseDouble(rs.getString(8));
				Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), df.format(tien) };
				response.add(o);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return response;
	}

	/**
	 * Tìm kiếm hóa đơn theo mã hóa đơn
	 * @param maHoaDon
	 * @return HoaDon
	 */

	public HoaDon timHoaDon(String maHoaDon) {
		HoaDon hoaDon = null;
		String sql = "Select * from HoaDon where maHoaDon = ?";

		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maHoaDon);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String maHD = rs.getString(1);

				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				Double tongTien = rs.getDouble(3);
				String maKH = rs.getString(4);
				String maNhanVien = rs.getString(5);
				int tongSoLuong = rs.getInt(6);
				hoaDon = new HoaDon(ngayLapHoaDon);
				hoaDon.setMaHoaDon(maHoaDon);
				hoaDon.setKhachHang(new KhachHang(maKH));
				hoaDon.setNhanvien(new NhanVien(maNhanVien));
				hoaDon.setTongTien(tongTien);
				hoaDon.setTongSoLuong(tongSoLuong);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}
		return hoaDon;
	}

	/**
	 * Tìm kiếm hóa đơn theo mã hóa đơn và mã nhân viên lập hóa đơn
	 * @param maHD
	 * @param maNV
	 * @return
	 * @throws SQLException
	 */
	public Object[] timHoaDonById(String maHD, String maNV) throws SQLException {

		DecimalFormat df = new DecimalFormat("#,###,###,### VNĐ");
		DecimalFormat dt = new DecimalFormat("dd-mm-yyyy hh:mm:ss");
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT HoaDon.maHoaDon,HoaDon.maKhachHang,KhachHang.tenKhachHang, KhachHang.soDienThoai, HoaDon.maNhanVien,NhanVien.tenNhanVien,HoaDon.ngayLapHoaDon, HoaDon.tongTien\r\n"
				+ "FROM     HoaDon INNER JOIN\r\n"
				+ "                  KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN\r\n"
				+ "                  NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien\r\n"
				+ "where HoaDon.maHoaDon= ? and NhanVien.maNhanVien = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, maHD);
		stmt.setString(2, maNV);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			double tien = Double.parseDouble(rs.getString(8));
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7), df.format(tien) };

			return o;

		}
		return null;
	}

	/**
	 * Tìm hóa đơn theo mã khách hàng
	 * @param maKhachHang
	 * @return
	 */
	public HoaDon timHoaDonTheoMaKhachHang(String maKhachHang) {
		HoaDon hoaDon = null;
		String sql = "Select * from HoaDon where maKhachHang = ?";

		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, maKhachHang);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String maHD = rs.getString(1);

				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				Double tongTien = rs.getDouble(3);
				String maKH = rs.getString(4);
				String maNhanVien = rs.getString(5);
				int tongSoLuong = rs.getInt(6);
				hoaDon = new HoaDon(ngayLapHoaDon);
				hoaDon.setMaHoaDon(maHD);
				hoaDon.setKhachHang(new KhachHang(maKH));
				hoaDon.setNhanvien(new NhanVien(maNhanVien));
				hoaDon.setTongTien(tongTien);
				hoaDon.setTongSoLuong(tongSoLuong);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return hoaDon;
	}

	/**
	 * Tìm những hóa đơn theo số điện thoại khách hàng và ngày mua
	 * @param sdt
	 * @param maNV
	 * @param ngayLap
	 * @return
	 */
	public List<Object> getAllOrderBySDT(String sdt, String maNV, Date ngayLap) {
		List<Object> response = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("#,###,###,### VNĐ");
		DecimalFormat dt = new DecimalFormat("dd-mm-yyyy hh:mm:ss");
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT HoaDon.maHoaDon,HoaDon.maKhachHang,KhachHang.tenKhachHang, KhachHang.soDienThoai, HoaDon.maNhanVien,NhanVien.tenNhanVien,HoaDon.ngayLapHoaDon, HoaDon.tongTien\r\n"
				+ "FROM     HoaDon INNER JOIN\r\n"
				+ "                  KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN\r\n"
				+ "                  NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien\r\n"
				+ "where KhachHang.soDienThoai=? and NhanVien.maNhanVien=? ";
		if (ngayLap != null) {
			sql += "and ngayLapHoaDon>= ?";
		}
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, sdt);
			stmt.setString(2, maNV);
			if (ngayLap != null) {
				stmt.setDate(3, ngayLap);
			}
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				double tien = Double.parseDouble(rs.getString(8));
				Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), df.format(tien) };
				response.add(o);
			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return response;
	}

	/**
	 * 
	 * @param SDT
	 * @return
	 */
	public List<HoaDon> timHoaDonTheoSDT(String SDT) {
		List<HoaDon> listHD = new ArrayList<HoaDon>();
		String sql = "SELECT HoaDon.* FROM HoaDon INNER JOIN  KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang where KhachHang.soDienThoai=?";

		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, SDT);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				String maHD = rs.getString(1);

				Timestamp ngayLapHoaDon = rs.getTimestamp(2);
				Double tongTien = rs.getDouble(3);
				String maKH = rs.getString(4);
				String maNhanVien = rs.getString(5);
				int tongSoLuong = rs.getInt(6);
				HoaDon hoaDon = new HoaDon(ngayLapHoaDon);
				hoaDon.setMaHoaDon(maHD);
				hoaDon.setKhachHang(new KhachHang(maKH));
				hoaDon.setNhanvien(new NhanVien(maNhanVien));
				hoaDon.setTongTien(tongTien);
				hoaDon.setTongSoLuong(tongSoLuong);
				listHD.add(hoaDon);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return listHD;
	}

	/**
	 * Tìm những hóa đơn theo ngày lập của mỗi nhân viên
	 * @param ngayLap
	 * @param maNV
	 * @return list
	 * @throws ParseException
	 */
	public List<Object> getAllOrderByDate(Date ngayLap, String maNV) throws ParseException {

		List<Object> response = new ArrayList<Object>();
		DecimalFormat df = new DecimalFormat("#,###,###,### VNĐ");
		DecimalFormat dt = new DecimalFormat("dd-mm-yyyy hh:mm:ss");

		String dateBegin = ngayLap.toString() + " 00:00:00";
		String dateEnd = ngayLap.toString() + " 23:59:59";

		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT HoaDon.maHoaDon,HoaDon.maKhachHang,KhachHang.tenKhachHang, KhachHang.soDienThoai, HoaDon.maNhanVien,NhanVien.tenNhanVien,HoaDon.ngayLapHoaDon, HoaDon.tongTien\r\n"
				+ "FROM     HoaDon INNER JOIN\r\n"
				+ "                  KhachHang ON HoaDon.maKhachHang = KhachHang.maKhachHang INNER JOIN\r\n"
				+ "                  NhanVien ON HoaDon.maNhanVien = NhanVien.maNhanVien\r\n"
				+ "where ngayLapHoaDon BETWEEN ? AND ? and NhanVien.maNhanVien = ?";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, dateBegin);
			stmt.setString(2, dateEnd);
			stmt.setString(3, maNV);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				double tien = Double.parseDouble(rs.getString(8));
				Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), df.format(tien) };
				response.add(o);

			}
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return response;
	}

}
