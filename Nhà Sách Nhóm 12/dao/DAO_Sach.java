package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiSach;
import entity.Sach;

public class DAO_Sach {
	
	/**
	 * Lấy hết sách
	 * @return dsSach
	 */
	public ArrayList<Sach> getAllTbSach()
	{
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setString(1, maTour1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) 
			{
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);
				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;

	}
	
	/**
	 * Lấy sách theo mã sách
	 * @param maSach
	 * @return sach
	 */
	public Sach laySachTheoMaSach(String maSach) {
		Sach sach = null;
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where maSanPham = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maSach);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return sach;
	}

	/**
	 * Lấy mã sách lớn nhất để set mã tự động
	 * @return maSachLonNhat
	 */
//	public int layMaSachLonNhat() 
//	{
//		int maSachLonNhat = 0;
//		try {
//			ConnectDB.getInstance();
//			Connection con=ConnectDB.getConnection();
//			String sql = "select * from Sach";
//			Statement statement = con.createStatement();
//			ResultSet rs = statement.executeQuery(sql);
//			while (rs.next()) {
//				String maSach = rs.getString(1).substring(3);
//				if(maSachLonNhat < Integer.parseInt(maSach)) {
//					maSachLonNhat  = Integer.parseInt(maSach);
//				}
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//
//		return maSachLonNhat;
//	}

	/**
	 * Thêm 1 quyển sách
	 * @param sach
	 * @return 
	 */
	public boolean themSach(Sach sach) 
	{
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "insert into Sach (maSanPham, tenSanPham, giaBan, nhaCungCap, soLuong, tacGia, namXuatBan, nhaXuatBan, hinhAnh, soTrang"
				+ ", tinhTrang, loaiSanPham, maLoaiSach) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
		int k = 0;
		try 
		{
//			ConnectDB conDB = new ConnectDB();
//			Connection con = conDB.ConnectDataBase();
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, sach.getMaSanPham());
			preparedStatement.setString(2, sach.getTenSanPham());
			preparedStatement.setDouble(3, sach.getGiaBan());
			preparedStatement.setString(4, sach.getNhaCungCap());
			preparedStatement.setInt(5, sach.getSoLuong());
			preparedStatement.setString(6, sach.getTacGia());
			preparedStatement.setInt(7, sach.getNamXuatBan());
			preparedStatement.setString(8,sach.getNhaXuatBan());		
			preparedStatement.setString(9, sach.getHinhAnh());
			preparedStatement.setInt(10, sach.getSoTrang());
			preparedStatement.setBoolean(11, true);	//true là còn hàng, false là ngừng kinh doanh
			preparedStatement.setString(12, sach.getLoaiSanPham());
			preparedStatement.setString(13, sach.getLoaiSach().getMaLoaiSach());

			k = preparedStatement.executeUpdate();

		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k > 0;
	}
	/**
	 * Sửa lại số lượng tồn của sách khi biết mã sách và số lượng tồn hiện tại
	 * @param soLuong
	 * @param maSach
	 * @return
	 */
	public boolean suaSLTSach(int soLuong,String maSach) 
	{
//		Sach sach = new Sach();
		ConnectDB.getInstance();
		Connection con =  ConnectDB.getConnection();
		String sql = "update Sach "
				+ "set soLuong=?"
				+ " where maSanPham = ?";
		int k =0;
		try 
		{
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, soLuong);
			preparedStatement.setString(2, maSach);
			k = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k>0; 
	}

	/**
	 * Sửa lại tình trạng sách khi nhấn ngừng kinh doanh
	 * @param tinhTrang
	 * @param maSach
	 * @return
	 */
	public boolean suaTinhTrangSach(boolean tinhTrang, String maSach) {
		Sach sach = new Sach();
		ConnectDB.getInstance();
		Connection con =  ConnectDB.getConnection();
		String sql = "update Sach "
				+ "set tinhTrang=?"
				+ " where maSanPham = ?";
		int k =0;
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setBoolean(1, tinhTrang);
			preparedStatement.setString(2, maSach);
			k = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k>0; 
	}
	
	/**
	 * Lấy sách theo mã loại sách truyền vào
	 * @param maLoaiSach
	 * @return dsSach
	 */
	public ArrayList<Sach> getSachTheoMaLoaiSach(String maLoaiSach){
		ArrayList<Sach> dsSach = new ArrayList<Sach>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where maLoaiSach = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maLoaiSach);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);

			}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;

	}
	
	/**
	 * Tìm kiếm sách heo tên sách hoặc tác giả
	 * @param tuKhoa
	 * @return dsSach
	 */
	public ArrayList<Sach> timKiemtenSachTacGia(String tuKhoa){
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where tenSanPham LIKE CONCAT('%', ?, '%') or tacGia LIKE CONCAT('%', ?, '%')";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tuKhoa);
			preparedStatement.setString(2, tuKhoa);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	
	/**
	 * Lọc sách theo nhà xuất bản
	 * @param tenNhaXuatBan
	 * @return dsSach
	 */
	public ArrayList<Sach> locNhaXuatBan(String tenNhaXuatBan){
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where nhaXuatBan LIKE CONCAT('%', ?, '%') ";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tenNhaXuatBan);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	
	/**
	 * Lọc sách theo tình trạng sách
	 * @param tinhTrangTruyenVao
	 * @return dsSach
	 */
	public ArrayList<Sach> locTinhTrang(String tinhTrangTruyenVao){
		boolean chuyenDoiTinhTrang = false;
		if(tinhTrangTruyenVao.equals("Còn hàng") || tinhTrangTruyenVao.equals("Hết hàng")) {	
			chuyenDoiTinhTrang = true;
		}
		else if(tinhTrangTruyenVao.equals("Ngừng kinh doanh")) {
			chuyenDoiTinhTrang = false;
		}
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where tinhTrang  = ? ";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setBoolean(1, chuyenDoiTinhTrang);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}

	/**
	 * Lọc theo tình trạng hết hàng khi soluong = 0 và tinhTrang phải true để phân biệt với ngừng kinh doanh
	 * @return dsSach
	 */
	public ArrayList<Sach> locTinhTrangHetHang()
	{
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach where soLuong  = 0 and tinhTrang = 1";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}

	/**
	 * Sắp xếp sách theo giá hoặc số lượng tồn
	 * @param tangGiam
	 * @param giaTriSapXep
	 * @return dsSach
	 */
	public ArrayList<Sach> sapXepGiaSLT(String tangGiam,String giaTriSapXep){
		ArrayList<Sach> dsSach = new ArrayList<Sach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from Sach  order by "+ giaTriSapXep +" " + tangGiam;
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(9);
				String loaiSanPham= rs.getString(12);
				boolean tinhTrang = rs.getBoolean(11);
				String tacGia= rs.getString(6);
				int namXuatBan = rs.getInt(7);
				String nhaXuatBan= rs.getString(8);
				int soTrang = rs.getInt(10);

				LoaiSach loaisach = new LoaiSach(rs.getString(13));
				Sach sach = new Sach(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,tacGia,namXuatBan,nhaXuatBan,soTrang,loaisach);
				dsSach.add(sach);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsSach;	
	}
	
	/**
	 * Sửa một cuốn sách
	 * @param sach
	 * @return
	 */
	public boolean suaSach(Sach sach) {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement st=null;
		try {
			st=con.prepareStatement("update Sach set tenSanPham=?,donGia=?,nhaCungCap=?, soLuong=?, tacGia=?, namXuatBan=?,"
					+ "nhaXuatBan=?, hinhAnh=?, soTrang=?, maLoaiSach=? where maSanPham=?");
			st.setString(1, sach.getTenSanPham());
			st.setDouble(2, sach.getGiaBan());
			st.setString(3,sach.getNhaCungCap());
			st.setInt(4,sach.getSoLuong());
			st.setString(5,sach.getTacGia());
			st.setInt(6,sach.getNamXuatBan());
			st.setString(7,sach.getNhaXuatBan());
			st.setString(8,sach.getHinhAnh());
			st.setInt(9,sach.getSoTrang());
			st.setString(10,sach.getLoaiSach().getMaLoaiSach());
			st.setString(11,sach.getMaSanPham());
			int n=st.executeUpdate();
			if(n> 0)
				return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Lấy sách theo mã sản phẩm
	 * @param maSanPham
	 * @return soLuong
	 */
	public int laySoLuongSachByMaSP(String maSanPham) {
		int soLuong = 0;
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();
			String sql = " select soLuong from Sach\r\n"
					+ "  where maSanPham=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maSanPham);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				soLuong=rs.getInt(1);
			}
			System.out.println(soLuong);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return soLuong;
	}
	
	
	public int layMaSachLonNhat() 
	{
		int maSachLonNhat = 0;
		try 
		{
			ConnectDB conDB = new ConnectDB();
			Connection con = conDB.ConnectDataBase();
//			ConnectDB.getInstance();
//			Connection con=ConnectDB.getConnection();
			String sql = "select * from Sach";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) 
			{
				String maSach = rs.getString(1).substring(3).trim();
				if(maSachLonNhat < Integer.parseInt(maSach))
				{
					maSachLonNhat  = Integer.parseInt(maSach);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return maSachLonNhat;
	}
	
	public static void main(String[] args) {

		}
}
