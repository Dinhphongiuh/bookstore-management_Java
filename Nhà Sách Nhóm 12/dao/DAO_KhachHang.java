package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import connectDB.ConnectDB;
import entity.KhachHang;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DAO_KhachHang {
	@SuppressWarnings("unused")
	private int n;
	@SuppressWarnings("unused")
	private ArrayList<String> list_MaKH;

	private static ConnectDB conDB = null;
	private static Connection con = null;
	
	public DAO_KhachHang() {
		this.conDB = new ConnectDB();
		try {
			this.conDB.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = this.conDB.getConnection();
	}
	/**
	 * Lấy khách hãng theo mã
	 * @param mkh
	 * @return
	 */
	public KhachHang layKhachHangTheoMa(String mkh) 
	{
		try {
			
			String sql = "select * from KhachHang where maKhachHang = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, mkh);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) 
			{
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String diaChi = rs.getString(4);
				String soDT = rs.getString(3);

				KhachHang kh = new KhachHang(maKH, tenKH, soDT, diaChi);

				return kh;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	/**
	 * Lấy hết khách hàng
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel getAllKH() throws SQLException 
	{
		String[] header = { "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT *from KhachHang\r\n";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = {rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
			tableModel.addRow(o);
		}
		return tableModel;
	}
	/**
	 * Thêm khách hàng
	 * @param kh
	 * @return
	 */
	public boolean themKH(KhachHang kh) 
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try 
		{
			stmt = con.prepareStatement("insert into KhachHang (maKhachHang, tenKhachHang, soDT, diaChi) values(?,?,?,?)");
			stmt.setString(1, kh.getMaKH());
			stmt.setString(2, kh.getTenKH());
			stmt.setString(3, kh.getSoDT());
			stmt.setString(4, kh.getDiaChi());

			n = stmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				stmt.close();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		}
		return n > 0;
	}
	/**
	 * Cập nhật khách hàng
	 * @param kh
	 * @return
	 */
	public boolean update(KhachHang kh) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {

			stmt = con.prepareStatement("update KhachHang set tenKhachHang=?,soDT=?,diaChi=? where maKhachHang=?");

			stmt.setString(1, kh.getTenKH());
			stmt.setString(2, kh.getSoDT());
			stmt.setString(3, kh.getDiaChi());
			stmt.setString(4, kh.getMaKH());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return n > 0;
	}
	/**
	 * Lấy danh sách khách hàng
	 * @return
	 */
	public ArrayList<String> getListMaKH() {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select maKhachHang from KhachHang";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list_MaKH;
	}
	/**
	 * Lấy khách hàng theo thuộc tính
	 * @param properties
	 * @param ma
	 * @return
	 */
	public KhachHang getKhachHangbyHDId(String properties, String ma) {
		Connection con = ConnectDB.getConnection();
		String sql = "select * from KhachHang where " + properties + " = ?";
		try 
		{
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, ma);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				KhachHang kh = new KhachHang(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4));
				return kh;
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		}
		return null;
	}
	/**
	 * Lấy khách hàng theo số điện thoại
	 * @param soDienThoai
	 * @return
	 */
	public KhachHang getKhachHangBySDT(String soDienThoai) 
	{
		KhachHang khachHang = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang where soDT = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, soDienThoai);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maKH = rs.getString(1);
				String tenKH = rs.getString(2);
				String soDT = rs.getString(3);
				String diaChi = rs.getString(4);

				khachHang = new KhachHang(maKH, tenKH, soDT, diaChi);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return khachHang;
	}
	/**
	 * Tìm kiếm khách hàng
	 * @param ma
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel timKiem(String ma) throws SQLException {
		String[] header = { "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from KhachHang  where maKhachHang like '" + ma + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
			tableModel.addRow(o);
		}
		return tableModel;
	}
	/**
	 * Tìm tổng khách hàng
	 * @return
	 * @throws SQLException
	 */
	public String tong() throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT COUNT(maKhachHang) AS 'Tong' FROM KhachHang";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			return rs.getString("Tong");
		}
		return null;
	}
	/**
	 * Tìm khách hàng theo số điện thoại
	 * @param sdt
	 * @return
	 */
	public DefaultTableModel timKiemSDT(String sdt) throws SQLException 
	{
		String[] header = { "Mã Khách Hàng", "Tên Khách Hàng", "Số Điện Thoại", "Địa Chỉ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from KhachHang where soDT like '" + sdt + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) 
		{
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4) };
			tableModel.addRow(o);
		}
		return tableModel;
	}
	/**
	 * Lấy mã khách hàng lớn nhất
	 * @return
	 */
	public int layMaKHLonNhat() 
	{
		int maKhachHangLonNhat = 0;
		try 
		{
			ConnectDB conDB = new ConnectDB();
			ConnectDB.getInstance();
			Connection con = conDB.ConnectDataBase();
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
			String sql = "select * from KhachHang";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next())
			{
				String maKhachHang = rs.getString(1).substring(2).trim();
				if(maKhachHangLonNhat < Integer.parseInt(maKhachHang)) 
				{
					maKhachHangLonNhat = Integer.parseInt(maKhachHang);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return maKhachHangLonNhat;
	}
	//Thai
		public ArrayList<KhachHang> layHetKhachHang(){
			ArrayList<KhachHang> dsKhachHang = new ArrayList<KhachHang>();
			try {
				ConnectDB.getInstance();
				Connection con = ConnectDB.getConnection();
				String sql = "select * from KhachHang";
				Statement statement = con.createStatement();
				ResultSet rs = statement.executeQuery(sql);
				while (rs.next()) {
					String maKH = rs.getString(1);
					String tenKH = rs.getString(2);
					String soDT = rs.getString(3);
					String diaChi = rs.getString(4);

					KhachHang khachHang = new KhachHang(maKH, tenKH, soDT, diaChi);
					dsKhachHang.add(khachHang);
				}

			}
			catch(SQLException e) {
				e.printStackTrace();
			}
			return dsKhachHang;

		}
}
