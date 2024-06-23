package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.DungCuHocTap;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;

public class DAO_DungCuHocTap {
	
	/**
	 * Lấy hết dụng cụ học tập
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> getAllTbDCHT(){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			//preparedStatement.setString(1, maTour1);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu= rs.getString(8);
				String chatLieu= rs.getString(9);
				LoaiDungCuHocTap loaiDungCuHocTap = new LoaiDungCuHocTap(rs.getString(12));

				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,
						loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDungCuHocTap);
				dsDCHT.add(dcht);
			}		
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;

	}
	
	/**
	 * Lấy mã DCHT lớn nhất để set mã tự động
	 * @return
	 */
	public int layMaDCHTLonNhat() {
		int maDCHTLonNhat = 0;
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maDCHT = rs.getString(1).substring(6);
				if(maDCHTLonNhat < Integer.parseInt(maDCHT)) {
					maDCHTLonNhat  = Integer.parseInt(maDCHT);
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return maDCHTLonNhat;
	}
	
	/**
	 * Thêm một dụng cụ học tập
	 * @param dcht
	 * @return
	 */
	public boolean themDCHT(DungCuHocTap dcht) {
		ConnectDB.getInstance();
		Connection con = ConnectDB.getConnection();
		String sql = "insert into DungCuHocTap values(?,?,?,?,?,?,?,?,?,?,?,?) ";
		int k = 0;
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, dcht.getMaSanPham());
			preparedStatement.setString(2, dcht.getTenSanPham());
			preparedStatement.setDouble(3, dcht.getGiaBan());
			preparedStatement.setString(4, dcht.getNhaCungCap());
			preparedStatement.setInt(5, dcht.getSoLuong());	
			preparedStatement.setString(6, dcht.getHinhAnh());
			preparedStatement.setString(7, dcht.getThuongHieu());
			preparedStatement.setString(8, dcht.getXuatXu());
			preparedStatement.setString(9, dcht.getChatLieu());
			preparedStatement.setBoolean(10, true);	//true là còn hàng, false là ngừng kinh doanh
			preparedStatement.setString(11, dcht.getLoaiSanPham());
			preparedStatement.setString(12, dcht.getLoaiDungCuHocTap().getMaLoaiDCHT());

			k = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k>0;
	}
	//Check tên DCHT coi tồn tại không
//	public DungCuHocTap LayDCHTTheoTenDCHT(String tenDCHT) {
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "select * from DungCuHocTap where tenSanPham = ?";
//			PreparedStatement preparedStatement = con.prepareStatement(sql);
//			preparedStatement.setString(1, tenDCHT);
//			ResultSet rs = preparedStatement.executeQuery();
//			while (rs.next()) {
//				String maSanPham = rs.getString(1);
//				String tenSanPham = rs.getString(2);
//				double giaBan = rs.getFloat(3);
//				String nhaCungCap= rs.getString(4);
//				int soLuong = rs.getInt(5);
//				String hinhAnh= rs.getString(6);
//				String loaiSanPham= rs.getString(11);
//				boolean tinhTrang = rs.getBoolean(10);
//				String thuongHieu= rs.getString(7);
//				String xuatXu= rs.getString(8);
//				String chatLieu= rs.getString(9);
//				LoaiDungCuHocTap loaiDungCuHocTap = new LoaiDungCuHocTap(rs.getString(12));
//
//				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,
//						loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDungCuHocTap);
//				return dcht;
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return null;
//	}

	/**
	 * Sửa lại số lượng tồn DCHT đã tồn tại
	 * @param soLuong
	 * @param maDCHT
	 * @return
	 */
	public boolean SuaSLTDungCuHocTap(int soLuong, String maDCHT) {
		Sach sach = new Sach();
		ConnectDB.getInstance();
		Connection con =  ConnectDB.getConnection();
		String sql = "update DungCuHocTap "
				+ "set soLuong=?"
				+ " where maSanPham = ?";
		int k =0;
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setInt(1, soLuong);
			preparedStatement.setString(2, maDCHT);
			k = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k>0; 
	}
	
	/**
	 * Sửa lại tình trạng DCHT khi nhấn ngừng kinh doanh
	 * @param tinhTrang
	 * @param maDCHT
	 * @return
	 */
	public boolean SuaTinhTrangDCHT(boolean tinhTrang, String maDCHT) {
		Sach sach = new Sach();
		ConnectDB.getInstance();
		Connection con =  ConnectDB.getConnection();
		String sql = "update DungCuHocTap "
				+ "set tinhTrang=?"
				+ " where maSanPham = ?";
		int k =0;
		try {
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setBoolean(1, tinhTrang);
			preparedStatement.setString(2, maDCHT);
			k = preparedStatement.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return k>0; 
	}
	
	/**
	 * lấy DCHT theo thể loại
	 * @param maLoaiDCHT
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> layDCHTTheoMaLoaiDCHT(String maLoaiDCHT){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();

		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where maLoaiDungCuHocTap = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maLoaiDCHT);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);

				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);

			}

		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;

	}
	
	/**
	 * Tìm kiếm theo tên dụng cụ học tập
	 * @param tuKhoa
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> timKiemtenDCHT(String tuKhoa){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where tenSanPham LIKE CONCAT('%', ?, '%')";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tuKhoa);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);

				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);

			}		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Sắp xếp DCHT theo giá VÀ SLT
	 * @param tangGiam
	 * @param giaTriSapXep
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> sapXepGiaSLT(String tangGiam,String giaTriSapXep){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap  order by "+ giaTriSapXep +" " + tangGiam;
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);

				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Lọc DCHT theo thương hiệu
	 * @param tenThuongHieu
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> locThuongHieu(String tenThuongHieu){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where thuongHieu LIKE CONCAT('%', ?, '%') ";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tenThuongHieu);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);

				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Lọc DCHT theo xuất xứ
	 * @param noiXuatXu
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> locXuatXu(String noiXuatXu){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where xuatXu LIKE CONCAT('%', ?, '%') ";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, noiXuatXu);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);
				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Lọc theo tình trạng còn hàng - ngừng kinh doanh
	 * @param tinhTrangTruyenVao
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> locTinhTrang(String tinhTrangTruyenVao){
		boolean chuyenDoiTinhTrang = false;
		if(tinhTrangTruyenVao.equals("Còn hàng") || tinhTrangTruyenVao.equals("Hết hàng")) {	
			chuyenDoiTinhTrang = true;
		}
		else if(tinhTrangTruyenVao.equals("Ngừng kinh doanh")) {
			chuyenDoiTinhTrang = false;
		}
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where tinhTrang  = ? ";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setBoolean(1, chuyenDoiTinhTrang);
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);
				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Lọc theo tình trạng hết hàng khi soluong = 0 và tinhTrang phải true để phân biệt với ngừng kinh doanh
	 * @return dsDCHT
	 */
	public ArrayList<DungCuHocTap> locTinhTrangHetHang(){
		ArrayList<DungCuHocTap> dsDCHT = new ArrayList<DungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from DungCuHocTap where soLuong  = 0 and tinhTrang = 1";
			PreparedStatement preparedStatement = con.prepareStatement(sql);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap= rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh= rs.getString(6);
				String loaiSanPham= rs.getString(11);
				boolean tinhTrang = rs.getBoolean(10);
				String thuongHieu= rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu= rs.getString(9);
				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(rs.getString(12));
				DungCuHocTap dcht = new DungCuHocTap(maSanPham,tenSanPham,giaBan,nhaCungCap,soLuong,hinhAnh,loaiSanPham,tinhTrang,thuongHieu,xuatXu,chatLieu,loaiDCHT);
				dsDCHT.add(dcht);
			}
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsDCHT;	
	}
	
	/**
	 * Sửa 1 DCHT
	 * @param dcht
	 * @return
	 */
	public boolean suaDCHT(DungCuHocTap dcht) {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		PreparedStatement st=null;
		try {
			st=con.prepareStatement("update DungCuHocTap set tenSanPham=?,donGia=?,nhaCungCap=?, soLuong=?, hinhAnh=?, thuongHieu=?, xuatXu=?,"
					+ "chatLieu=?, maLoaiDungCuHocTap=? where maSanPham=?");
			st.setString(1, dcht.getTenSanPham());
			st.setDouble(2, dcht.getGiaBan());
			st.setString(3,dcht.getNhaCungCap());
			st.setInt(4,dcht.getSoLuong());
			st.setString(5,dcht.getHinhAnh());
			st.setString(6,dcht.getThuongHieu());
			st.setString(7,dcht.getXuatXu());
			st.setString(8,dcht.getChatLieu());
			st.setString(9,dcht.getLoaiDungCuHocTap().getMaLoaiDCHT());
			st.setString(10,dcht.getMaSanPham());
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
	 * Lấy dụng cụ học tập theo mã DCHT
	 * @param maDCHT
	 * @return dungCuHT
	 */
	public DungCuHocTap layDCHTTheoMaDCHT(String maDCHT) {
		ConnectDB.getInstance();
		Connection con=ConnectDB.getConnection();
		DungCuHocTap dungCuHT = null;
		try {
			String sql = "select * from DungCuHocTap where maSanPham = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maDCHT);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maSanPham = rs.getString(1);
				String tenSanPham = rs.getString(2);
				double giaBan = rs.getFloat(3);
				String nhaCungCap = rs.getString(4);
				int soLuong = rs.getInt(5);
				String hinhAnh = rs.getString(6);
				String thuongHieu = rs.getString(7);
				String xuatXu = rs.getString(8);
				String chatLieu = rs.getString(9);
				boolean tinhTrang = rs.getBoolean(10);
				String loaiSanPham = rs.getString(11);
				String maLoaiDCHT = rs.getString(12);
				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(maLoaiDCHT);

				dungCuHT = new DungCuHocTap(maSanPham, tenSanPham, giaBan, nhaCungCap, soLuong, hinhAnh, loaiSanPham,
						tinhTrang, thuongHieu, xuatXu, chatLieu, loaiDCHT);
			}
		} catch (Exception e) {
		
			System.out.println(e);
		}
		return dungCuHT;
	}
	
	/**
	 * Lấy số lượng DCHT theo mã sản phẩm
	 * @param maSanPham
	 * @return soLuong
	 */
	public int laySoLuongDCHTByMaSanPham(String maSanPham) {
		int soLuong = 0;
		try {
			ConnectDB.getInstance();
			Connection con=ConnectDB.getConnection();
			String sql = " select soLuong from DungCuHocTap\r\n"
					+ "  where maSanPham=?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maSanPham);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				soLuong=rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return soLuong;
	}
}
