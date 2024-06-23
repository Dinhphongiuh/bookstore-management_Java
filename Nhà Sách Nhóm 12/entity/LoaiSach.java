package entity;

public class LoaiSach {
	private String maLoaiSach;
	private String tenLoaiSach;
	
	/**
	 * 
	 * @param maLoaiSach
	 * @param tenLoaiSach
	 */
	public LoaiSach(String maLoaiSach, String tenLoaiSach) {
		super();
		this.maLoaiSach = maLoaiSach;
		this.tenLoaiSach = tenLoaiSach;
	}
	
	/**
	 * 
	 * @param maLoaiSach
	 */
	public LoaiSach(String maLoaiSach) {
		super();
		this.maLoaiSach = maLoaiSach;
		
	}
	/**
	 * 
	 */
	public LoaiSach() {
		super();
	}
	public String getMaLoaiSach() {
		return maLoaiSach;
	}
	public void setMaLoaiSach(String maLoaiSach) {
		this.maLoaiSach = maLoaiSach;
	}
	public String getTenLoaiSach() {
		return tenLoaiSach;
	}
	public void setTenLoaiSach(String tenLoaiSach) {
		this.tenLoaiSach = tenLoaiSach;
	}
	@Override
	public String toString() {
		return "LoaiSach [maLoaiSach=" + maLoaiSach + ", tenLoaiSach=" + tenLoaiSach + "]";
	}
	
}
