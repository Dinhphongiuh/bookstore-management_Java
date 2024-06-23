package entity;

public class DungCuHocTap extends SanPham{
	private String thuongHieu;
	private String xuatXu;
	private String chatLieu;
	private LoaiDungCuHocTap loaiDungCuHocTap;
	/**
	 * 
	 */
	public DungCuHocTap() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param maSanPham
	 * @param tenSanPham
	 * @param giaBan
	 * @param nhaCungCap
	 * @param soLuong
	 * @param hinhAnh
	 * @param loaiSanPham
	 * @param tinhTrang
	 */
	public DungCuHocTap(String maSanPham, String tenSanPham, double giaBan, String nhaCungCap, int soLuong,
			String hinhAnh, String loaiSanPham, Boolean tinhTrang, String thuongHieu, String xuatXu, String chatLieu, LoaiDungCuHocTap loaiDungCuHocTap) {
		super(maSanPham, tenSanPham, giaBan, nhaCungCap, soLuong, hinhAnh, loaiSanPham, tinhTrang);
		this.thuongHieu = thuongHieu;
		this.xuatXu = xuatXu;
		this.chatLieu = chatLieu;
		this.loaiDungCuHocTap = loaiDungCuHocTap;
	}
	public String getThuongHieu() {
		return thuongHieu;
	}
	public void setThuongHieu(String thuongHieu) {
		this.thuongHieu = thuongHieu;
	}
	public String getXuatXu() {
		return xuatXu;
	}
	public void setXuatXu(String xuatXu) {
		this.xuatXu = xuatXu;
	}
	public String getChatLieu() {
		return chatLieu;
	}
	public void setChatLieu(String chatLieu) {
		this.chatLieu = chatLieu;
	}
	public LoaiDungCuHocTap getLoaiDungCuHocTap() {
		return loaiDungCuHocTap;
	}
	public void setLoaiDungCuHocTap(LoaiDungCuHocTap loaiDungCuHocTap) {
		this.loaiDungCuHocTap = loaiDungCuHocTap;
	}
	
	
	
	@Override
	public String toString() {
		return "DungCuHocTap [thuongHieu=" + thuongHieu + ", xuatXu=" + xuatXu + ", chatLieu=" + chatLieu
				+ ", loaiDungCuHocTap=" + loaiDungCuHocTap + "]";
	}
	
	
}
