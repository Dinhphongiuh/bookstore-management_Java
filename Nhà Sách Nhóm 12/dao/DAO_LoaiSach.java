package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiDungCuHocTap;
import entity.LoaiSach;
import entity.Sach;

public class DAO_LoaiSach {
	
	/**
	 * Lấy hết loại sách
	 * @return dsLoaiSach
	 */
	public ArrayList<LoaiSach> getAllLoaiSach(){
		ArrayList<LoaiSach> dsLoaiSach = new ArrayList<LoaiSach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from LoaiSach";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maLoaiSach = rs.getString(1);
				String tenLoaiSach = rs.getString(2);
				LoaiSach loaiSach = new LoaiSach(maLoaiSach,tenLoaiSach);
				dsLoaiSach.add(loaiSach);
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsLoaiSach;
		
	}
	
	/**
	 * Lấy sách theo tên sách
	 * @param tenLoaiSachTruyenVao: Tên sách cần truyền
	 * @return loaisach
	 */
	public LoaiSach LaySachTheoTenSach(String tenLoaiSachTruyenVao) {
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from LoaiSach where tenLoaiSach = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, tenLoaiSachTruyenVao);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				String maLoaiSach = rs.getString(1);
				String tenLoaiSach = rs.getString(2);
				
				LoaiSach loaisach = new LoaiSach(maLoaiSach,tenLoaiSach);
				return loaisach;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
}
