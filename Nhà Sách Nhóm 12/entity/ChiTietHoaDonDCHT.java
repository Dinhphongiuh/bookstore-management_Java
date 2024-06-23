package entity;

// Lộc Trường Luân

public class ChiTietHoaDonDCHT {
	private HoaDon hoaDon;
	private SanPham sanPham;
	private int soLuong;
	private double thanhTien;

	public ChiTietHoaDonDCHT(SanPham sanPham, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.thanhTien = sanPham.getGiaBan()*soLuong;
	}
	
	public ChiTietHoaDonDCHT() {
		
	}

	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	public SanPham getSanPham() {
		return sanPham;
	}

	public void setSanPham(SanPham sanPham) {
		this.sanPham = sanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}

	public double getThanhTien() {
		return thanhTien;
	}

	public void setThanhTien(double thanhTien) {
		this.thanhTien = thanhTien;
	}

	@Override
	public String toString() {
		return "ChiTietHoaDonDCHT [hoaDon=" + hoaDon.getMaHoaDon() + ", sanPham=" + sanPham.getMaSanPham() + ", soLuong=" + soLuong + ", thanhTien="
				+ thanhTien + "]";
	}

	
	
	
	
}

