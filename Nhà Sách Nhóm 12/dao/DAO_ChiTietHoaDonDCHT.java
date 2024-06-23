package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.ConnectDB;
import entity.ChiTietHoaDonDCHT;
import entity.ChiTietHoaDonSach;
import entity.DungCuHocTap;
import entity.HoaDon;
import entity.LoaiDungCuHocTap;
import entity.SanPham;

// Đặng Mãi Chí Đức

public class DAO_ChiTietHoaDonDCHT {
	private static ConnectDB conDB = null;
	private static Connection con = null;
	private String prefixMaHoaDon = "HD00";

	public DAO_ChiTietHoaDonDCHT() {
		this.conDB = new ConnectDB();
		try {
			this.conDB.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = this.conDB.getConnection();
	}

	public boolean themChiTietHoaDon(List<ChiTietHoaDonDCHT> cthds, String maHD)
			throws ClassNotFoundException, SQLException {
		cthds.forEach((e) -> {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO ChiTietHoaDonDungCuHT VALUES(?,?,?,?)");
				stmt.setString(1, e.getSanPham().getMaSanPham());
				stmt.setInt(2, e.getSoLuong());
				stmt.setDouble(3, e.getThanhTien());
				stmt.setString(4, maHD);
				stmt.executeUpdate();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (Exception ec) {
					ec.printStackTrace();
				}
			}

		});

		return true;

	}

	
	public List<ChiTietHoaDonDCHT> layCTHDDCHT(String maHD) {
	
		List<ChiTietHoaDonDCHT> chiTietHoaDonDCHTs=new ArrayList<ChiTietHoaDonDCHT>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from ChiTietHoaDonDungCuHT where maHoaDon = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maHD);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				String maSanPham = rs.getString(1);
				int soLuong = rs.getInt(2);
				double thanhTien = rs.getDouble(3);
				String maHoaDon = rs.getString(4);

				ChiTietHoaDonDCHT	cthdDCHT = new ChiTietHoaDonDCHT(new SanPham(maSanPham), soLuong);
				cthdDCHT.setThanhTien(thanhTien);
				cthdDCHT.setHoaDon(new HoaDon(maHoaDon));
				chiTietHoaDonDCHTs.add(cthdDCHT);
				
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return chiTietHoaDonDCHTs;
	}
}
