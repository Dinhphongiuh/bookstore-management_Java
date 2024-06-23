package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import connectDB.ConnectDB;
import entity.LoaiSach;
import entity.NhanVien;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DAO_NhanVien {
	@SuppressWarnings("unused")
	private int n;
	@SuppressWarnings("unused")
	private ArrayList<entity.NhanVien> listNhanVien;
	private ArrayList<String> list_MaNV;


	private static ConnectDB conDB = null;
	private static Connection con = null;

	public DAO_NhanVien() {
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
	//Luân
	/**
	 * tìm kiếm theo mã
	 * @param ma
	 * @return NhanVien
	 */
	public DefaultTableModel timKiem(String ma) throws SQLException {
		String[] header = { "Mã Nhân Viên", "Tên Nhân Viên", "Email", "Địa Chỉ", "Số Điện Thoại", "Giới Tính",
		"Chức Vụ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from NhanVien  where maNhanVien like '" + ma + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7) };
			tableModel.addRow(o);
		}
		return tableModel;
	}
	
	public NhanVien layNhanVienTheoMa(String mnv) {
		try 
		{

			String sql = "select * from NhanVien where maNhanVien = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, mnv);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maNhanVien = rs.getString(1);
				String tenNhanVien = rs.getString(2);
				String diaChi = rs.getString(3);
				String email = rs.getString(4);
				String soDT = rs.getString(5);
				String gioiTinh = rs.getString(6);
				String chucVu = rs.getString(7);

				NhanVien nv = new NhanVien(maNhanVien,tenNhanVien,diaChi,email,soDT,gioiTinh,chucVu);


				return nv;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
/**
 * Lấy tất cả nhân viên
 * @return
 * @throws dsNhanVien
 */
	public DefaultTableModel getAllNV() throws SQLException {
		String[] header = { "Mã Nhân Viên", "Tên Nhân Viên", "Email", "Địa Chỉ", "Số Điện Thoại", "Giới Tính", "Chức Vụ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT *from NhanVien\r\n";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7) };
			tableModel.addRow(o);
		}
		return tableModel;
	}
	/**
	 * Lấy mã nhân viên lớn nhất để set mã tự động
	 * @return maNhanVienLonNhat
	 */
	public int layMaNVLonNhat() {
		int maNhanVienLonNhat = 0;
		try {
			ConnectDB.getInstance();
//			Connection con=ConnectDB.getConnection();
			ConnectDB conDB = new ConnectDB();
			Connection con = conDB.ConnectDataBase();
			String sql = "select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNhanVien = rs.getString(1).substring(2).trim();
				if(maNhanVienLonNhat < Integer.parseInt(maNhanVien)) {
					maNhanVienLonNhat  = Integer.parseInt(maNhanVien);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return maNhanVienLonNhat;
	}
	/**
	 * Thêm nhân viên
	 */
	public boolean themNV(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("insert into nhanVien values(?,?,?,?,?,?,?)");
			stmt.setString(1, nv.getMaNV());
			stmt.setString(2, nv.getTenNV());
			stmt.setString(3, nv.getEmail());
			stmt.setString(4, nv.getDiaChi());
			stmt.setString(5, nv.getSoDT());
			stmt.setString(6, nv.getGioiTinh());
			stmt.setString(7, nv.getChucVu());

			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return n > 0;
	}
/**
 * Cập nhật nhân viên
 * @param nv
 * @return nv
 */
	public boolean update(NhanVien nv) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {

			stmt = con.prepareStatement("update nhanVien set tenNhanVien=?,email=?,diaChi=?,soDienThoai=?,gioiTinh=?,chucVu=? where maNhanVien=?");

			stmt.setString(1, nv.getTenNV());
			stmt.setString(2, nv.getEmail());
			stmt.setString(3, nv.getDiaChi());
			stmt.setString(4, nv.getSoDT());
			stmt.setString(5, nv.getGioiTinh());
			stmt.setString(6, nv.getChucVu());
			stmt.setString(7, nv.getMaNV());
			n = stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();

		}
		return n > 0;
	}

	public ArrayList<String> getListMaNV() {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "Select maNV from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String CMND;
				CMND = rs.getString(1);
				list_MaNV.add(CMND);
			}
		} catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return list_MaNV;
	}

	public NhanVien getNhanVienbyHDId(String properties, String ma) {
		Connection con = ConnectDB.getConnection();
		String sql = "select * from NhanVien where " + properties + " = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, ma);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(6), rs.getString(3),
						rs.getString(5), rs.getString(4), rs.getString(7));
				return nv;
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		}
		return null;
	}

	public NhanVien getNhanVienById(String string, String string2) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Tim kiếm
	 * @param cmnd
	 * @param ma
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel timKiem(String cmnd, String ma) throws SQLException {
		String[] header = { "Mã Nhân Viên", "Tên Nhân Viên", "Giới Tính", "Email", "Địa Chỉ","Số Điện Thoại", "Ngày Vào Làm", "Tình Trạng" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from NhanVien  where maNV like '" + ma + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7)};
			tableModel.addRow(o);
		}
		return tableModel;
	}
	/**
	 * Tìm kiếm
	 * @param sdt
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel timKiemSDT(String sdt) throws SQLException {
		String[] header = { "Mã Nhân Viên", "Tên Nhân Viên", "Email", "Địa Chỉ", "Số Điện Thoại", "Giới Tính",
		"Chức Vụ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from NhanVien  where soDienThoai like '" + sdt + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7) };
			tableModel.addRow(o);
		}
		return tableModel;
	}

	/**
	 * Lọc theo giới tính
	 * @param gioiTinh
	 * @return
	 * @throws SQLException
	 */
	public DefaultTableModel timKiemGt(String gioiTinh) throws SQLException {
		String[] header = { "Mã Nhân Viên", "Tên Nhân Viên", "Email", "Địa Chỉ", "Số Điện Thoại", "Giới Tính",
		"Chức Vụ" };
		DefaultTableModel tableModel = new DefaultTableModel(header, 0);
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "SELECT * from NhanVien  where gioiTinh like N'" + gioiTinh + "'";

		Statement statement = con.createStatement();
		ResultSet rs = statement.executeQuery(sql);

		while (rs.next()) {
			Object[] o = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
					rs.getString(6), rs.getString(7) };
			tableModel.addRow(o);
		}
		return tableModel;
	}

	/**
	 * Lấy tổng nhân viên
	 * @return
	 * @throws SQLException
	 */
	public String tong() throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT COUNT(maNhanVien) AS 'Tong' FROM NhanVien";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			return rs.getString("Tong");
		}
		return null;
	}
//	public int tong() throws SQLException {
//		int count = 0;
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//
//		String sql = "SELECT COUNT(maNhanVien)  FROM NhanVien";
//		PreparedStatement pst = con.prepareStatement(sql);		
//
//		ResultSet rs = pst.executeQuery();
//
//		while (rs.next()) {
//			return rs.getInt(1);
//		}
//		System.out.println(count);
//		return count;
	//}
	/**
	 * Lấy tổng nhân viên theo giới tính
	 * @param gioiTinh
	 * @return
	 * @throws SQLException
	 */
	public String tongGT(String gioiTinh) throws SQLException {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();

		String sql = "SELECT COUNT(maNhanVien) AS 'TongGT' FROM NhanVien where gioiTinh like N'" + gioiTinh + "'";
		Statement st = con.createStatement();
		ResultSet rs = st.executeQuery(sql);
		while (rs.next()) {
			return rs.getString("TongGT");
		}
		return null;
	}
	
	//Thai
	public ArrayList<NhanVien> layHetNhanVien(){
		ArrayList<NhanVien> dsNhanVien = new ArrayList<NhanVien>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from NhanVien";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNhanVien = rs.getString(1);
				String tenNhanVien = rs.getString(2);
				String diaChi = rs.getString(3);
				String email = rs.getString(4);
				String soDT = rs.getString(5);
				String gioiTinh = rs.getString(6);
				String chucVu = rs.getString(7);

				NhanVien nv = new NhanVien(maNhanVien,tenNhanVien,diaChi,email,soDT,gioiTinh,chucVu);
				dsNhanVien.add(nv);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsNhanVien;
		
	}
}
