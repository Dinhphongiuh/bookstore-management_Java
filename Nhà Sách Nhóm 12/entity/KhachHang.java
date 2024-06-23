package entity;

public class KhachHang {
	private String maKH;
	private String tenKH;
	private String soDT;
	private String diaChi;
	public String getMaKH() {
		return maKH;
	}
	public String getMaKH(String maKH) {
		return maKH;
	}
	public void setMaKH(String maKH) {
		this.maKH = maKH;
	}
	public String getTenKH() {
		return tenKH;
	}
	public void setTenKH(String tenKH) {
		this.tenKH = tenKH;
	}
	public String getSoDT() {
		return soDT;
	}
	public void setSoDT(String soDT) {
		this.soDT = soDT;
	}
	public String getDiaChi() {
		return diaChi;
	}
	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}
	/**
	 * 
	 * @param maKH
	 * @param tenKH
	 * @param soDT
	 * @param diaChi
	 */
	public KhachHang(String maKH, String tenKH, String soDT, String diaChi) 
	{
		super();
		this.maKH = maKH;
		this.tenKH = tenKH;
		this.soDT = soDT;
		this.diaChi = diaChi;
	}
	public KhachHang(String maKH) {
		super();
		this.maKH = maKH;
	}
	public KhachHang() {
		super();
	}
	@Override
	public String toString() {
		return "KhachHang [maKH=" + maKH + ", tenKH=" + tenKH + ", soDT=" + soDT + ", diaChi=" + diaChi + "]";
	}

}
