package entity;

public class TaiKhoan {
	private NhanVien nhanVien;
	private String matKhau;
	/**
	 * @param nhanVien
	 * @param matKhau
	 */
	public TaiKhoan(NhanVien nhanVien, String matKhau) {
		super();
		this.nhanVien = nhanVien;
		this.matKhau = matKhau;
	}
	/**
	 * 
	 */
	public TaiKhoan() {
		super();
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public String getMatKhau() {
		return matKhau;
	}
	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}
	@Override
	public String toString() {
		return "TaiKhoan [nhanVien=" + nhanVien.getMaNV() + ", matKhau=" + matKhau + "]";
	}

}
