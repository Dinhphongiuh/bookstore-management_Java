package entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.search.DateTerm;

public class HoaDon {

	private String maHoaDon;
	private Timestamp ngayLapHoaDon;
	private double tongTien;
	private int tongSoLuong;
	private List<ChiTietHoaDonSach> chiTietHoaDonSachs;
	private List<ChiTietHoaDonDCHT> chiTietHoaDonDCHTs;
	private NhanVien nhanvien;
	private KhachHang khachHang;

	public HoaDon() {

	}

	public HoaDon(String maHoaDon) {

		this.maHoaDon = maHoaDon;
	}

	/**
	 * @param maHoaDon
	 * @param ngayLapHoaDon
	 * @param tongTien
	 * @param tongSoLuong
	 * @param nhanvien
	 * @param khachHang
	 */
	public HoaDon(String maHoaDon, Timestamp ngayLapHoaDon, double tongTien, int tongSoLuong, NhanVien nhanvien,
			KhachHang khachHang) {
		
		this.maHoaDon = maHoaDon;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.tongTien = tongTien;
		this.tongSoLuong = tongSoLuong;
		this.nhanvien = nhanvien;
		this.khachHang = khachHang;
	}

	public HoaDon(double tongTien, NhanVien nhanvien) {
		
		this.tongTien = tongTien;
		this.nhanvien = nhanvien;
	}

	public HoaDon(Timestamp ngayLapHoaDon) {

		this.ngayLapHoaDon = ngayLapHoaDon;
		chiTietHoaDonSachs = new ArrayList<ChiTietHoaDonSach>();
		chiTietHoaDonDCHTs = new ArrayList<ChiTietHoaDonDCHT>();
		this.tongTien = getTongTien();
		this.tongSoLuong = getTongSoLuong();
	}

	public String getMaHoaDon() {
		return maHoaDon;
	}

	public void setMaHoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}

	public Timestamp getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}

	public void setNgayLapHoaDon(Timestamp ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}

	public double getTongTien() {

		return this.tongTien;
	}

	public void setTongTien(double tongTien) {
		this.tongTien = tongTien;
	}

	public int getTongSoLuong() {

		return this.tongSoLuong;
	}

	public void setTongSoLuong(int tongSoLuong) {
		this.tongSoLuong = tongSoLuong;
	}

	public NhanVien getNhanvien() {
		return nhanvien;
	}

	public void setNhanvien(NhanVien nhanvien) {
		this.nhanvien = nhanvien;
	}

	public KhachHang getKhachHang() {
		return khachHang;
	}

	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}

	public List<ChiTietHoaDonSach> getChiTietHoaDonSachs() {
		return chiTietHoaDonSachs;
	}

	public void setChiTietHoaDonSachs(List<ChiTietHoaDonSach> chiTietHoaDonSachs) {
		this.chiTietHoaDonSachs = chiTietHoaDonSachs;
	}

	public List<ChiTietHoaDonDCHT> getChiTietHoaDonDCHTs() {
		return chiTietHoaDonDCHTs;
	}

	public void setChiTietHoaDonDCHTs(List<ChiTietHoaDonDCHT> chiTietHoaDonDCHTs) {
		this.chiTietHoaDonDCHTs = chiTietHoaDonDCHTs;
	}

	@Override
	public String toString() {
		return "HoaDon [maHoaDon=" + maHoaDon + ", ngayLapHoaDon=" + ngayLapHoaDon + ", tongTien=" + tongTien
				+ ", tongSoLuong=" + tongSoLuong + ", chiTietHoaDonSachs=" + chiTietHoaDonSachs
				+ ", chiTietHoaDonDCHTs=" + chiTietHoaDonDCHTs + ", nhanvien=" + nhanvien.getMaNV() + ", khachHang="
				+ khachHang.getMaKH() + "]";
	}

}
