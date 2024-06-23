package entity;

public class Sach extends SanPham{
	private String tacGia;
	private int namXuatBan;
	private String nhaXuatBan;
	private int soTrang;
	private LoaiSach loaiSach;
	
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
	 * @param tacGia
	 * @param namXuatBan
	 * @param nhaXuatBan
	 * @param soTrang
	 * @param loaiSach
	 */
	public Sach(String maSanPham, String tenSanPham, double giaBan, String nhaCungCap, int soLuong, String hinhAnh,
			String loaiSanPham, Boolean tinhTrang, String tacGia, int namXuatBan, String nhaXuatBan, int soTrang,
			LoaiSach loaiSach) {
		super(maSanPham, tenSanPham, giaBan, nhaCungCap, soLuong, hinhAnh, loaiSanPham, tinhTrang);
		this.tacGia = tacGia;
		this.namXuatBan = namXuatBan;
		this.nhaXuatBan = nhaXuatBan;
		this.soTrang = soTrang;
		this.loaiSach = loaiSach;
	}
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
	public Sach(String maSanPham, String tenSanPham, double giaBan, String nhaCungCap, int soLuong, String hinhAnh,
			String loaiSanPham, Boolean tinhTrang) {
		super(maSanPham, tenSanPham, giaBan, nhaCungCap, soLuong, hinhAnh, loaiSanPham, tinhTrang);
	}
	/**
	 * 
	 * @param soLuong
	 */
	public Sach(int soLuong) {
		this.soLuong = soLuong;
	}
	
	/**
	 * 
	 */
	public Sach() {
		super();
	}
	
	
	public String getTacGia() {
		return tacGia;
	}
	public void setTacGia(String tacGia) {
		this.tacGia = tacGia;
	}
	public int getNamXuatBan() {
		return namXuatBan;
	}
	public void setNamXuatBan(int namXuatBan) {
		this.namXuatBan = namXuatBan;
	}
	public String getNhaXuatBan() {
		return nhaXuatBan;
	}
	public void setNhaXuatBan(String nhaXuatBan) {
		this.nhaXuatBan = nhaXuatBan;
	}
	public int getSoTrang() {
		return soTrang;
	}
	public void setSoTrang(int soTrang) {
		this.soTrang = soTrang;
	}
	public LoaiSach getLoaiSach() {
		return loaiSach;
	}
	public void setLoaiSach(LoaiSach loaiSach) {
		this.loaiSach = loaiSach;
	}
	@Override
	public String toString() {
		return "Sach [tacGia=" + tacGia + ", namXuatBan=" + namXuatBan + ", nhaXuatBan=" + nhaXuatBan + ", soTrang="
				+ soTrang + ", loaiSach=" + loaiSach + "]";
	}
	
	


	
	
}
