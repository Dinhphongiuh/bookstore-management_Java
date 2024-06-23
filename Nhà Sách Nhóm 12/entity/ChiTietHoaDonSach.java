package entity;

public class ChiTietHoaDonSach {
	private HoaDon hoaDon;
	private SanPham sanPham;
	private int soLuong;
	private double thanhTien;
	/**
	 * @param sanPham
	 * @param soLuong
	 * 
	 */
	public ChiTietHoaDonSach(SanPham sanPham, int soLuong) {
		super();
		this.sanPham = sanPham;
		this.soLuong = soLuong;
		this.thanhTien = sanPham.getGiaBan()*soLuong;
	}
	
	public ChiTietHoaDonSach() {
		
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
	
	
	public HoaDon getHoaDon() {
		return hoaDon;
	}

	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}

	@Override
	public String toString() {
		return "ChiTietHoaDonSach [hoaDon=" + hoaDon.getMaHoaDon() + ", sanPham=" + sanPham.getMaSanPham() + ", soLuong=" + soLuong + ", thanhTien="
				+ thanhTien + "]";
	}

	
	
	
	
}
