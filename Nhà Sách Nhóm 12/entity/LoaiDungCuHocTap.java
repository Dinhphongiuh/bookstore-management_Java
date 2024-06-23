package entity;

public class LoaiDungCuHocTap {
	private String maLoaiDCHT;
	private String tenLoaiDCHT;
	
	/**
	 * 
	 * @param maLoaiDCHT
	 * @param tenLoaiDCHT
	 */
	public LoaiDungCuHocTap(String maLoaiDCHT, String tenLoaiDCHT) {
		super();
		this.maLoaiDCHT = maLoaiDCHT;
		this.tenLoaiDCHT = tenLoaiDCHT;
	}
	/**
	 * 
	 * @param maLoaiDCHT
	 */
	public LoaiDungCuHocTap(String maLoaiDCHT) {
		this.maLoaiDCHT = maLoaiDCHT;
	}
	
	/**
	 * 
	 */
	public LoaiDungCuHocTap() {
		super();
	}
	public String getMaLoaiDCHT() {
		return maLoaiDCHT;
	}
	public void setMaLoaiDCHT(String maLoaiDCHT) {
		this.maLoaiDCHT = maLoaiDCHT;
	}
	public String getTenLoaiDCHT() {
		return tenLoaiDCHT;
	}
	public void setTenLoaiDCHT(String tenLoaiDCHT) {
		this.tenLoaiDCHT = tenLoaiDCHT;
	}
	@Override
	public String toString() {
		return "LoaiDungCuHocTap [maLoaiDCHT=" + maLoaiDCHT + ", tenLoaiDCHT=" + tenLoaiDCHT + "]";
	}
	
}
