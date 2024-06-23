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
import entity.HoaDon;
import entity.SanPham;

public class DAO_ChiTietHoaDonSach {
	private static ConnectDB conDB = null;
	private static Connection con = null;
	private String prefixMaHoaDon = "HD00";

	public DAO_ChiTietHoaDonSach() {
		this.conDB = new ConnectDB();
		try {
			this.conDB.connect();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.con = this.conDB.getConnection();
	}

	public boolean themChiTietHoaDon(List<ChiTietHoaDonSach> cthds, String maHD) throws ClassNotFoundException, SQLException {
		cthds.forEach((e) -> {
			PreparedStatement stmt = null;
			try {
				stmt = con.prepareStatement("INSERT INTO ChiTietHoaDonSach VALUES(?,?,?,?)");
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
	public List<ChiTietHoaDonSach> layCTHDSach(String maHD) {
		List<ChiTietHoaDonSach> chiTietHoaDonSachs = new ArrayList<ChiTietHoaDonSach>();
		try {
			ConnectDB.getInstance();
			Connection con = ConnectDB.getConnection();
			String sql = "select * from ChiTietHoaDonSach where maHoaDon = ?";
			PreparedStatement preparedStatement = con.prepareStatement(sql);
			preparedStatement.setString(1, maHD);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				
				String maSanPham = rs.getString(1);
				int soLuong = rs.getInt(2);
				double thanhTien = rs.getDouble(3);
				String maHoaDon = rs.getString(4);

				ChiTietHoaDonSach cthdSach = new ChiTietHoaDonSach(new SanPham(maSanPham), soLuong);
				cthdSach.setThanhTien(thanhTien);
				cthdSach.setHoaDon(new HoaDon(maHoaDon));
				chiTietHoaDonSachs.add(cthdSach);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return chiTietHoaDonSachs;
	}
}
