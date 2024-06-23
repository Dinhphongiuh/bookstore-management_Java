package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import connectDB.ConnectDB;
import entity.LoaiDungCuHocTap;

public class DAO_LoaiDCHT {
	
	/**
	 * Lấy hết loại dụng cụ học tập
	 * @return dsLoaiDCHT : trả về danh sách các loại dụng cụ học tập
	 */
	public ArrayList<LoaiDungCuHocTap> getAllLoaiDCHT(){
		ArrayList<LoaiDungCuHocTap> dsLoaiDCHT = new ArrayList<LoaiDungCuHocTap>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from LoaiDungCuHocTap";
			Statement statement = con.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				String maLoaiDCHT = rs.getString(1);	
				String tenLoaiDCHT = rs.getString(2);
				LoaiDungCuHocTap loaiDCHT = new LoaiDungCuHocTap(maLoaiDCHT,tenLoaiDCHT);
				dsLoaiDCHT.add(loaiDCHT);
			}	
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		return dsLoaiDCHT;
	}
}
