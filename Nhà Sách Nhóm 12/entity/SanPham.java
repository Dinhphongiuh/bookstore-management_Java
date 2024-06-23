package entity;

public class SanPham {
	protected  String maSanPham;
	protected  String tenSanPham;
	protected  double giaBan;
	protected  String nhaCungCap;
	protected  int soLuong;
	protected  String hinhAnh;
	protected  String loaiSanPham;
	protected  Boolean tinhTrang;
	
	/**
	 * 
	 * @param maSanPham
	 * @param tenSanPham
	 * @param giaBan
	 * @param nhaCungCap
	 * @param soLuong
	 * @param hinhAnh
	 * @param loaiSanPham
	 * @param tinhTrang
	 */
	public SanPham(String maSanPham, String tenSanPham, double giaBan, String nhaCungCap, int soLuong, String hinhAnh,
			String loaiSanPham, Boolean tinhTrang) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.giaBan = giaBan;
		this.nhaCungCap = nhaCungCap;
		this.soLuong = soLuong;
		this.hinhAnh = hinhAnh;
		this.loaiSanPham = loaiSanPham;
		this.tinhTrang = tinhTrang;
	}
	/**
	 * 
	 * @param maSanPham
	 */
	public SanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	/**
	 * 
	 * @param maSanPham
	 * @param tenSanPham
	 * @param soLuong
	 */
	public SanPham(String maSanPham, String tenSanPham,int soLuong) {
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.soLuong = soLuong;
	}
	/**
	 * 
	 * @param maSanPham
	 * @param soLuong
	 */
	public SanPham(String maSanPham,int soLuong) {
		this.maSanPham = maSanPham;
		this.soLuong = soLuong;
	}
	
	/**
	 * 
	 */
	public SanPham() {
		super();
	}
	public String getMaSanPham() {
		return maSanPham;
	}
	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}
	public String getTenSanPham() {
		return tenSanPham;
	}
	public void setTenSanPham(String tenSanPham) {
		this.tenSanPham = tenSanPham;
	}
	public double getGiaBan() {
		return giaBan;
	}
	public void setGiaBan(double giaBan) {
		this.giaBan = giaBan;
	}
	public String getNhaCungCap() {
		return nhaCungCap;
	}
	public void setNhaCungCap(String nhaCungCap) {
		this.nhaCungCap = nhaCungCap;
	}
	public int getSoLuong() {
		return soLuong;
	}
	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}
	public String getHinhAnh() {
		return hinhAnh;
	}
	public void setHinhAnh(String hinhAnh) {
		this.hinhAnh = hinhAnh;
	}
	public String getLoaiSanPham() {
		return loaiSanPham;
	}
	public void setLoaiSanPham(String loaiSanPham) {
		this.loaiSanPham = loaiSanPham;
	}
	public Boolean getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(Boolean tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	@Override
	public String toString() {
		return "SanPham [maSanPham=" + maSanPham + ", tenSanPham=" + tenSanPham + ", giaBan=" + giaBan + ", nhaCungCap="
				+ nhaCungCap + ", soLuong=" + soLuong + ", hinhAnh=" + hinhAnh + ", loaiSanPham=" + loaiSanPham
				+ ", tinhTrang=" + tinhTrang + "]";
	}
	
	
}
