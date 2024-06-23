package dao;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;
public class DAO_TaiKhoan {
	private List<TaiKhoan> listTaiKhoan;

	private static ConnectDB conDB = null;
	private static Connection con = null;

	public DAO_TaiKhoan() 
	{
		this.conDB = new ConnectDB();
		try 
		{
			this.conDB.connect();
		} catch (Exception e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = this.conDB.getConnection();
	}

	/**
	 * Lấy hết tài khoản của nhân viên
	 * @return listTaiKhoan
	 */
	public List<TaiKhoan> getAllTaiKhoan()
	{
		listTaiKhoan=new ArrayList<TaiKhoan>();
		try {
			ConnectDB conDB = new ConnectDB();
			Connection con = conDB.ConnectDataBase();
			String sql = "select * from TaiKhoan";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maNhanVien=rs.getString(1);
				String matKhau = rs.getString(2);

				NhanVien nhanVien=new NhanVien(maNhanVien);
				TaiKhoan taiKhoan = new TaiKhoan(nhanVien, matKhau);
				listTaiKhoan.add(taiKhoan);
			}
		}catch (SQLException e) { 
			// TODO: handle exception
			e.printStackTrace();
		}
		return listTaiKhoan;
	}

	/**
	 * Đăng nhập
	 * @param username
	 * @param password
	 * @return taiKhoan
	 */
//	public TaiKhoan login(String username, String password) 
//	{
//		String maHoa = maHoaMatKhau(password);
//		
//		TaiKhoan taiKhoan=null;
//		String sql = "select * from TaiKhoan where tenTaiKhoan = ? and matKhau = ?";
//		
//		try {
//			ConnectDB conDB = new ConnectDB();
//			Connection con = conDB.ConnectDataBase();
//			PreparedStatement pst = con.prepareStatement(sql);
//			pst.setString(1, username);
//			pst.setString(2, password);
//			ResultSet rs = pst.executeQuery();
//			if(rs.next()) {
//				TaiKhoan tk = new TaiKhoan(new NhanVien(rs.getString(1)), rs.getString(2));
//				return tk;
//			}
//		} catch (SQLException e1) {
//			JOptionPane.showMessageDialog(null, e1);
//			e1.printStackTrace();
//		}
//		return taiKhoan;
//	}
	
	public TaiKhoan login(String username, String password) 
	{
		String maHoa = maHoaMatKhau(password);
		
		TaiKhoan taiKhoan=null;
		String sql = "select * from TaiKhoan where maNhanVien = ? and matKhau = ?";
		
		try {
			ConnectDB conDB = new ConnectDB();
			Connection con = conDB.ConnectDataBase();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				TaiKhoan tk = new TaiKhoan(new NhanVien(rs.getString(1)), rs.getString(2));
				return tk;
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		}
		return taiKhoan;
	}
	
	/**
	 * Kiếm tra email nhân viên có đúng hay không
	 * @param email
	 * @return
	 */
	public NhanVien kiemTraEmail(String email) {
		NhanVien nhanVien=null;
		String sql = "select * from NhanVien\r\n"
				+ "where email = ?";
		
		try {
//			ConnectDB conDB  = new ConnectDB();
//			Connection con = conDB.ConnectDataBase();
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if(rs.next()) {
				NhanVien nv = new NhanVien(rs.getString(1), rs.getString(2), rs.getString(3)
						, rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7));
				return nv;
			}
		} catch (SQLException e1) {
			JOptionPane.showMessageDialog(null, e1);
			e1.printStackTrace();
		}
		return nhanVien;
	}
	
	/**
	 * Tạo tài khoản cho nhân viên
	 * @param tk
	 * @return
	 */
	public boolean taoTaiKhoan (TaiKhoan tk)
	{
		NhanVien nv = new NhanVien();
		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n= 0;
		try {
			ConnectDB coDB = new ConnectDB();
			Connection con = conDB.ConnectDataBase();
			
			stmt = con.prepareStatement("insert into TaiKhoan values (?,?)");
			stmt.setString(1, tk.getNhanVien().getMaNV());
			stmt.setString(2, "123456");			//16/11

			n=stmt.executeUpdate();
		}catch(SQLException e) 
		{
			//e.printStackTrace();
		}
		return n > 0;
	}
	

	public boolean updateMK (String maNV, String matKhau) {
		String maHoa = maHoaMatKhau(matKhau);
		
		ConnectDB.getInstance();
		Connection con= ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan set matKhau = ? where tenTaiKhoan = ?");
			stmt.setString(1, maHoa);
			stmt.setString(2, maNV);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n>0;
	}
	
	/**
	 * Cập nhật mật khẩu khi quên mật khẩu mới bằng email
	 * @param email
	 * @param matKhau
	 * @return
	 */
	public boolean capNhatMatKhauVoiEmail (String email, String matKhau) {
		String maHoa = maHoaMatKhau(matKhau);
		
		ConnectDB.getInstance();
		Connection con= ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan \r\n"
					+ "set matKhau = ? \r\n"
					+ "FROM TaiKhoan join NhanVien on TaiKhoan.tenTaiKhoan = NhanVien.maNhanVien\r\n"
					+ "where email = ?");
			stmt.setString(1, maHoa);
			stmt.setString(2, email);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n>0;
	}
	
	/**
	 * Cấp lại mật khẩu cho nhân viên. Chức năng dành cho chủ cửa hàng
	 * @param maNhanVien
	 * @return
	 */
	public boolean CapLaiMatKhau (String maNhanVien) {
		
		String maHoa = maHoaMatKhau("123456");
		
		ConnectDB.getInstance();
		Connection con= ConnectDB.getConnection();
		PreparedStatement stmt = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("update TaiKhoan set matKhau = '"+"123456"+"' where maNhanVien = ?");
			stmt.setString(1, maNhanVien);
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return n>0;
	}


	/**
	 * Hàm mã hóa mật khẩu
	 * @param password
	 * @return
	 */
	public static String maHoaMatKhau(String password) {
		byte[] salt = new byte[16];
		KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 127);
		SecretKeyFactory f = null;
		try {
			f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		} catch (NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		byte[] hash = null;
		try {
			hash = f.generateSecret(spec).getEncoded();
		} catch (InvalidKeySpecException e1) {
			e1.printStackTrace();
		}
		Base64.Encoder enc = Base64.getEncoder();
		return enc.encodeToString(hash);
	}

}
