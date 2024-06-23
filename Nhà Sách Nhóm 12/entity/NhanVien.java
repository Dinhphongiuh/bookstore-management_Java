package entity;


public class NhanVien {
	private String maNV,tenNV,email,diaChi,soDT,gioiTinh, chucVu;

	public String getMaNV() {
		return maNV;
	}
	public String getMaNV(String maNV) {
		return maNV;
	}
	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}
	public String getTenNV() {
		return tenNV;
	}
	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public String getChucVu() {
		return chucVu;
	}
	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}
	public NhanVien getNhanVien() {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 
	 * @param maNV
	 * @param tenNV
	 * @param email
	 * @param diaChi
	 * @param soDT
	 * @param gioiTinh
	 * @param chucVu
	 */

	public NhanVien(String maNV, String tenNV, String email, String diaChi, String soDT, String gioiTinh,
			String chucVu) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.email = email;
		this.diaChi = diaChi;
		this.soDT = soDT;
		this.gioiTinh = gioiTinh;
		this.chucVu = chucVu;
	}
	public NhanVien(String maNV) {
		super();
		this.maNV = maNV;
	}
	public NhanVien() {
		super();
		
	}
	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", email=" + email + ", diaChi=" + diaChi + ", gioiTinh="
				+ gioiTinh + ", chucVu=" + chucVu + "]";
	}

}