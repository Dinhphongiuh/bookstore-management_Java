package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

import connectDB.ConnectDB;
import dao.DAO_NhanVien;
import entity.NhanVien;

public class TroGiup extends JPanel implements ActionListener,MouseListener{
	DefaultMutableTreeNode rootHDSD;
	JTree tree;
	DefaultMutableTreeNode nodeQLSP, nodeQLHD, nodeQLKH, nodeQLNV, nodeQLThongKe, nodeQLTaiKhoan, nodeDoiMatKhau,nodeLHD, nodeDSHD, nodeQMK;
	JPanel pnTree;
	JScrollPane sc;
	List<String> listDD;
	JTextPane txtArea;
	public static String maNhanVien = null;
	DAO_NhanVien nhanVien_dao;
	public TroGiup(String maNV) {
		setSize(1400,800);
		setLayout(new BorderLayout());

		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		nhanVien_dao = new DAO_NhanVien();

		JPanel pnNorth = new JPanel();
		pnNorth.setBackground(Color.WHITE);
		JLabel lblTaiLieu = new JLabel("Hướng dẫn sử dụng tài liệu");
		lblTaiLieu.setFont(new Font("Arial",Font.BOLD,20));
		pnNorth.add(lblTaiLieu);
		maNhanVien = maNV;
		rootHDSD = new DefaultMutableTreeNode("Hướng dẫn sử dụng");
		tree = new JTree(rootHDSD);
		tree.setFont(new Font("Arial",Font.BOLD,12));
		sc = new JScrollPane(tree);
		sc.setPreferredSize(new Dimension(200, 0));

		pnTree = new JPanel();
		pnTree.setLayout(new BorderLayout());
		//TaoHinh
		ImageIcon leafIcon = new ImageIcon("src//Icon//documents.png");
		ImageIcon openIcon = ResizeImageIcon("src//Icon//open-book.png");
		ImageIcon closedIcon = ResizeImageIcon("src//Icon//closed-book.png");

		if (leafIcon != null) {
			DefaultTreeCellRenderer renderer =  new DefaultTreeCellRenderer();
			renderer.setClosedIcon(closedIcon);
			renderer.setOpenIcon(openIcon);
			renderer.setLeafIcon(leafIcon);
			tree.setCellRenderer(renderer);
		}
		nodeQLSP = new DefaultMutableTreeNode("Quản lí Sản Phẩm");
		nodeQLHD = new DefaultMutableTreeNode("Quản lí Hóa Đơn");

		nodeLHD = new DefaultMutableTreeNode("Lập hóa đơn");
		nodeQLHD.add(nodeLHD);	
		nodeDSHD = new DefaultMutableTreeNode("Xem danh sách hóa đơn");
		nodeQLHD.add(nodeDSHD);	

		nodeQLKH = new DefaultMutableTreeNode("Quản lí Khách Hàng");
		nodeQLNV = new DefaultMutableTreeNode("Quản lí Nhân Viên");
		nodeQLThongKe = new DefaultMutableTreeNode("Quản lí Thống Kê");
		nodeQLTaiKhoan = new DefaultMutableTreeNode("Quản lí Tài Khoản");
		nodeDoiMatKhau = new DefaultMutableTreeNode("Đổi mật khẩu");
		nodeQMK = new DefaultMutableTreeNode("Quên mật khẩu");

		NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
		if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
			rootHDSD.add(nodeQLSP);	
			rootHDSD.add(nodeQLHD);	
			rootHDSD.add(nodeQLKH);
			rootHDSD.add(nodeQLNV);
			rootHDSD.add(nodeQLThongKe);
			rootHDSD.add(nodeQLTaiKhoan);
			rootHDSD.add(nodeDoiMatKhau);
			rootHDSD.add(nodeQMK);
		}
		else {
			rootHDSD.add(nodeQLSP);	
			rootHDSD.add(nodeQLHD);	
			rootHDSD.add(nodeQLKH);
			rootHDSD.add(nodeQLThongKe);
			rootHDSD.add(nodeDoiMatKhau);
			rootHDSD.add(nodeQMK);
		}

		tree.expandRow(0);
		pnTree.add(sc,BorderLayout.WEST);

		txtArea = new JTextPane();
		txtArea.setEditable(false);
		txtArea.setBackground(new Color(255, 245, 230));
		JScrollPane sc1 = new JScrollPane(txtArea);
		sc1.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener(){
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				// TODO Auto-generated method stub
				txtArea.select(0,0);
			}});
		pnTree.add(sc1,BorderLayout.CENTER);
		tree.addMouseListener(this);

		add(pnNorth,BorderLayout.NORTH);
		add(pnTree);

	}
	//	public static void main(String[] args) {
	//		// TODO Auto-generated method stub
	//		Help help = new Help();
	//		help.setVisible(true);
	//	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	public ImageIcon ResizeImageIcon(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		DefaultMutableTreeNode nodeSelected  = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if(nodeSelected.equals(nodeQLSP)) {
			txtArea.setContentType("text/html");
			String html = "<html>"
					+ "<h1 style='text-align:center; color:orange'>Quản lí sản phẩm</h1>"
					+ "<div>"
					+ "<div style='margin-left: 45px'>"
					+ "<img  width='1000' height='550' style='text-align:center;margin-left: 45px' src='" + getClass().getResource("/image/quanlisanpham.png").toString()+"'/>"
					+ "</div>"
					+ "<p style='font-size:17px'>- Ở màn hình <b>Chính</b>, hệ thống sẽ có 2 button sách và dụng cụ học tập</p>"
					+ "<p style='font-size:17px;margin-left: 25px'>+ Với <b>button sách</b> sẽ hiển thị tất cả các loại sách, nhân viên có thể tìm sách theo thể loại, tên sách, tác giả, nhà xuất bản, ... và cũng có thể sắp xếp sách theo giá thấp nhất cao nhất hoặc sắp xếp theo số lượng tồn, lọc theo tình trạng của sản phẩm</p>"
					+ "<p style='font-size:17px;margin-left: 25px'>+ Với <b>dụng cụ học tập</b> sẽ hiển thị tất cả các loại dụng cụ học tập, nhân viên có thể tìm dụng cụ học tập theo thể loại, tên, ... và cũng có thể sắp xếp dụng cụ học tập theo giá thấp nhất cao nhất hoặc sắp xếp theo số lượng tồn, lọc theo tình trạng của sản phẩm</p>"
					+ "<p style='font-size:17px'>- Khi nhân viên chọn chức năng <b>Ngừng kinh doanh</b> thì sản phẩm sẽ hiện thông báo ngưng kinh doanh và không thể sửa được sản phẩm</p>"

					+ "<p style='font-size:17px'>- Khi nhân viên chọn chức năng <b>Thêm sản phẩm</b> hệ thống sẽ mở form thêm như bên dưới</p>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='700' height='500' style='text-align:center' src='"+getClass().getResource("/image/themsanpham.png").toString()+"'/>"
					+ "</div>"
					+ "<p style='font-size:17px;margin-left: 25px'>+ Mã sách sẽ tự động, nhân viên nhập theo các trường như ảnh. Với đơn giá, năm xuất bản, số trang số lượng tồn cần phải nhập kiểu số</p>"
					+ "<p style='font-size:17px;margin-left: 25px'>+ Nút <b>Làm mới</b> sẽ xóa hết các trường đã nhập</p>"
					+ "<p style='font-size:17px;margin-left: 25px'>+ Nút <b>Lưu</b> thực hiện lưu sản phẩm xuống database và hiển thị sản phẩm vừa thêm trên màn hình</p>"

					+ "<p style='font-size:17px'>- Khi nhân viên chọn chức năng <b>Nhập excel</b> hệ thống sẽ mở form và chọn đường dẫn tới file excel cần nhập vào, hệ thống sẽ nhập sách từ file excel vào hệ thống và hiển thị trên giao diện những cuốn sách vừa thêm</p>"			

					+ "<p style='font-size:17px'>- Khi nhân viên chọn chức năng <b>Sửa</b> hệ thống sẽ mở form và hiện thông tin như bên dưới, nhập giá trị cần sửa và chọn Lưu, sản phẩm sẽ được cập nhật lại vào hệ thống</p>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='700' height='500' style='text-align:center' src='"+getClass().getResource("/image/suasanpham.jpg").toString()+"'/>"
					+ "</div>"
					+ "</div>"
					+ "</html>";

			txtArea.setText(html);
		}
		else if(nodeSelected.equals(nodeQLKH)) {
			txtArea.setContentType("text/html");
			String html = "<html>"
					+ "<h1 style='text-align:center; color:orange'>Quản lí khách hàng</h1>"
					+ "<div>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='1000' height='550' style='text-align:center' src='"+getClass().getResource("/image/quanlikhachhang.png").toString()+"'/>"
					+ "</div>"
					+ "<h2>a. Tìm kiếm</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'>Người dùng nhập mã khách hàng hoặc số điện thoại của khách hàng tại ô tìm kiếm, kết quả sẽ được trả về ở bảng bên dưới</p>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='800' height='550' style='text-align:center' src='"+getClass().getResource("/image/timkiemkhachhang.png").toString()+"'/>"
					+ "</div>"
					//Sửa					
					+ "<h2>b. Sửa thông tin</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 1: </b>Người dùng chọn khách hàng để chỉnh sửa thông tin (có thể dùng kết quả của tìm kiếm trước đó, khách hàng được chọn sẽ được hiển thị màu khác với các khách hàng còn lại trên Danh sách Khách hàng)</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 2: </b>Chọn nút sửa thông tin</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 3: </b>Thực hiện chỉnh sửa thông tin trong giao diện Cập nhật Khách hàng</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 4: </b>Nhấn nút cập nhật. Nếu có lỗi, vui lòng kiểm tra lại thông tin đã nhập</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 5: </b>Trên giao diện thông báo Xác nhận cập nhật. Chọn Yes để xác nhận cập nhật hoặc chọn No để huỷ xác nhận cập nhật</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 6: </b>Nhấn nút làm mới để cập nhật lại thông tin trên giao diện</p>"
					//Thêm	
					+ "<h2>c. Thêm khách hàng</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 1: </b>Chọn nút Thêm khách hàng</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 2: </b>Điền thông tin khách hàng và chọn nút thêm khách hàng. Nếu có lỗi sẽ có thông báo hiển thị thông tin lỗi, vui lòng kiểm tra lại thông tin đã nhập theo thông báo lỗi. Nếu thêm thành công, nhấn nút Ok để hoàn thành</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 3: </b>Nhấn nút làm mới để cập nhật lại thông tin trên giao diện</p>"


					+ "</div>"
					+ "</html>";

			txtArea.setText(html);
		}
		else if(nodeSelected.equals(nodeQLNV)) {
			txtArea.setContentType("text/html");
			String html = "<html>"
					+ "<h1 style='text-align:center; color:orange'>Quản lí nhân viên</h1>"
					+ "<div>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='1000' height='550' style='text-align:center' src='"+getClass().getResource("/image/quanlinhanvien.png").toString()+"'/>"
					+ "</div>"
					//Tìm kiếm
					+ "<h2>a. Tìm nhân viên, Lọc theo giới tính</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'>- Tìm nhân viên: Người dùng nhập mã Nhân viên hoặc số điện thoại của Nhân viên tại ô tìm kiếm, kết quả sẽ được trả về ở bảng bên dưới</p>"
					+ "<p style='font-size:16px;margin-left: 25px'>- -	Lọc theo giới tính: người dùng chọn vào biểu tượng lựa chọn ở mục lọc, chọn giới tính để lọc và hệ thống sẽ xuất ra danh sách nhân viên theo giới tính</p>"

					//Sửa					
					+ "<h2>b. Sửa thông tin</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 1: </b>Người dùng chọn Nhân viên để chỉnh sửa thông tin (có thể dùng kết quả của tìm kiếm trước đó, Nhân viên được chọn sẽ được hiển thị màu khác với các Nhân viên còn lại trên Danh sách Nhân viên)</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 2: </b>Chọn nút sửa thông tin</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 3: </b>Thực hiện chỉnh sửa thông tin trong giao diện Cập nhật nhân viên</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 4: </b>Nhấn nút cập nhật. Nếu có lỗi, vui lòng kiểm tra lại thông tin đã nhập</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 5: </b>Trên giao diện thông báo Xác nhận cập nhật. Chọn Yes để xác nhận cập nhật hoặc chọn No để huỷ xác nhận cập nhật</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 6: </b>Nhấn nút làm mới để cập nhật lại thông tin trên giao diện</p>"
					//Thêm	
					+ "<h2>c. Thêm nhân viên</h2>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 1: </b>Chọn nút Thêm nhân viên</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 2: </b>Điền thông tin nhân viên và chọn nút thêm nhân viên. Nếu có lỗi sẽ có thông báo hiển thị thông tin lỗi, vui lòng kiểm tra lại thông tin đã nhập theo thông báo lỗi. Nếu thêm thành công, nhấn nút Ok để hoàn thành</p>"
					+ "<p style='font-size:16px;margin-left: 25px'><b>Bước 3: </b>Nhấn nút làm mới để cập nhật lại thông tin trên giao diện</p>"


					+ "</div>"
					+ "</html>";

			txtArea.setText(html);
		}
		else if(nodeSelected.equals(nodeQLThongKe)) {
			NhanVien nhanvienTheoMa = nhanVien_dao.layNhanVienTheoMa(maNhanVien);	
			if(nhanvienTheoMa.getChucVu().trim().equals("Chủ Cửa Hàng")) {
				txtArea.setContentType("text/html");
				String html = "<html>"
						+ "<h1 style='text-align:center; color:orange'>Quản lí thống kê</h1>"
						+ "<div>"
						+ "<div style='margin-left: 45px'>"
						+ "<img width='1000' height='550' style='text-align:center' src='"+getClass().getResource("/image/quanlithongke.png").toString()+"'/>"
						+ "</div>"
						+ "<p style='font-size:16px'>- Ở màn hình <b>Quản lí thống kê</b>, hệ thống sẽ có 2 table là top những sản phẩm đã bán và top doanh thu của tất cả nhân viên có trong cửa hàng</p>"
						+ "<p style='font-size:16px'>- Khi chọn <b>thống kê theo quý</b>, người dùng cần phải chọn năm của quý cần thông kê (mặc định hệ thống sẽ lấy năm hiện tại), hệ thống sẽ hiện ra tổng doanh thu của quý đó, tổng số lượng sản phẩm, "
						+ "hóa đơn đã bán trong quý đó và sẽ hiện top những tên sách đã bán nhiều nhất và top doanh thu của nhân viên bán cao nhất trong quý đó</p>"

					+ "<p style='font-size:16px'>- Khi chọn thống kê từ ngày này đến ngày khác, hệ thống sẽ hiện ra tổng doanh thu, tổng số lượng sản phẩm đã bán trong khoảng thời gian đó và sẽ hiện top những tên sách đã bán nhiều và doanh thu của tất cả nhân viên trong thời gian đó. Nếu người dùng không chọn ngày thống kê, hệ thống sẽ thông báo người dùng cần chọn ngày thống kê</p>"
					+ "<p style='font-size:16px'>- Khi chọn <b>Xuất báo cáo</b>, hệ thống sẽ xuất báo cáo thống kê ra file excel</p>"

					+ "</div>"
					+ "</html>";

				txtArea.setText(html);
			}
			else {
				txtArea.setContentType("text/html");
				String html = "<html>"
						+ "<h1 style='text-align:center; color:orange'>Quản lí thống kê</h1>"
						+ "<div>"
						+ "<div style='margin-left: 45px'>"
						+ "<img width='1000' height='550' style='text-align:center' src='"+getClass().getResource("/image/quanlithongkeNV.png").toString()+"'/>"
						+ "</div>"
						+ "<p style='font-size:16px'>- Ở màn hình <b>Quản lí thống kê</b>, hệ thống sẽ hiển thị hai table là top những sản phẩm đã bán và top hóa đơn có doanh thu cao nhất của chỉ nhân viên đăng nhập</p>"
						+ "<p style='font-size:16px'>- Khi chọn <b>thống kê theo quý</b>, người dùng cần phải chọn năm của quý cần thông kê (mặc định hệ thống sẽ lấy năm hiện tại), hệ thống sẽ hiện ra tổng doanh thu của quý đó, tổng số lượng sản phẩm, "
						+ "hóa đơn đã bán trong quý đó và sẽ hiện top những tên sách đã bán nhiều nhất và top hóa đơn có doanh thu cao nhất trong quý đó</p>"

					+ "<p style='font-size:16px'>- Khi chọn thống kê từ ngày này đến ngày khác, hệ thống sẽ hiện ra tổng doanh thu, tổng số lượng sản phẩm đã bán trong khoảng thời gian đó và sẽ hiện top những tên sách đã bán nhiều và top hóa đơn có doanh thu cao nhất. Nếu người dùng không chọn ngày thống kê, hệ thống sẽ thông báo người dùng cần chọn ngày thống kê</p>"
					+ "<p style='font-size:16px'>- Khi chọn <b>Xuất báo cáo</b>, hệ thống sẽ xuất báo cáo thống kê ra file excel</p>"

					+ "</div>"
					+ "</html>";

				txtArea.setText(html);
			}
		}
		else if(nodeSelected.equals(nodeQLTaiKhoan)) {
			txtArea.setContentType("text/html");
			String html = "<html>"
					+ "<h1 style='text-align:center; color:orange'>Quản lí tài khoản</h1>"
					+ "<div>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='1000' height='550' style='object-fit:cover' src='"+getClass().getResource("/image/quanlitaikhoan.png").toString()+"'/>"
					+ "</div>"
					+ "<p style='font-size:16px;'>- Để sử dụng được chức năng này thì chỉ có chủ cửa hàng mới được quyền sử dụng chức năng này</p>"
					+ "<p style='font-size:16px;'>- Nút <b>Tạo Tài Khoản</b>, chủ cửa hàng cần phải nhập mã nhân viên cần tạo tài khoản. Nếu mã nhân viên đó không tồn tại thì hệ thống sẽ thông báo lỗi, ngược lại sẽ tạo tài khoản thành công cho nhân viên</p>"
					+ "<p style='font-size:16px;'>- Nút <b>Đặt lại mật khẩu</b> thực hiện đặt lại mật khẩu về dạng mặt định 123456. Nếu chưa chọn tài khoản sẽ thông báo Vui lòng chọn tài khoản. Ngược lại thông báo đặt lại mật khẩu thành công.</p>"
					+ "<p style='font-size:16px;'>- Nút <b>Làm mới</b> thực hiện làm mới lại table quản lí tài khoản</p>"

					+ "</div>"
					+ "</html>";

			txtArea.setText(html);
		}
		else if(nodeSelected.equals(nodeDoiMatKhau)) {
			txtArea.setContentType("text/html");
			String html = "<html>"
					+ "<h1 style='text-align:center; color:orange'>Đổi mật khẩu</h1>"
					+ "<div>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='1000' height='550' style='object-fit:cover' src='"+getClass().getResource("/image/giaodiendoimk.png").toString()+"'/>"
					+ "</div>"
					+ "<p style='font-size:16px;'>- Người dùng chọn vào nút Đổi mật khẩu trên giao diện làm việc, sau đó nhập đúng thông tin tài khoản: Tài khoản, mật khẩu cũ, mật khẩu mới.</p>"
					+ "<p style='font-size:16px;'>- Nhấn nút Lưu thay đổi để xác nhận đổi mật khẩu:</p>"
					+ "<p style='font-size:16px;margin-left: 25px'>+ Nếu đúng thông tin, sẽ có thông báo đúng mật khẩu</p>"
					+ "<p style='font-size:16px;margin-left: 25px'>+ Nếu sai Thông tin sẽ có hiển thị thông báo</p>"
					+ "<div style='margin-left: 45px'>"
					+ "<img width='800' height='450' style='object-fit:cover' src='"+getClass().getResource("/image/formDMK1.png").toString()+"'/>"
					+ "</div>"
					+ "</div>"
					+ "</html>";

			txtArea.setText(html);
		}
		if(nodeSelected.getLevel() == 2) {
			if(nodeSelected.equals(nodeLHD)) {
				txtArea.setContentType("text/html");
				String html = "<html>"
						+ "<h1 style='text-align:center; color:orange'>Lập hóa đơn</h1>"
						+ "<div>"
						+ "<div style='margin-left: 45px'>"
						+ "<img width='1000' height='550' style='object-fit:cover' src='"+getClass().getResource("/image/laphoadon.png").toString()+"'/>"
						+ "</div>"
						+ "<p style='font-size:16px;'>- Để sử dụng được chức năng Lập hóa đơn thì người dùng phải đăng nhập thành công vào hệ thống, chọn chức năng Quản Lý Hóa Đơn và chọn tab Hóa đơn</p>"
						+ "<p style='font-size:16px;'>- Người dùng nhập số điện thoại ở phần thông tin khách hàng, nếu số điện thoại của khách hàng đã tồn tại trong danh sách khách hàng ở cơ sở dữ liệu thì các thông tin như: mã khách hàng, tên khách hàng, địa chỉ sẽ được hiển thị lên phần thông tin khách hàng. Ngược lại, nếu khách hàng không có trong cơ sở dữ liệu hay nói cách khác khách hàng chưa từng mua tại cửa hang thì các thông tin đó sẽ không được hiển thị lên phần thông tin khách hàng.</p>"
						+ "<p style='font-size:16px;'>- Sau đó, người dùng nhập mã sản phẩm ở Jtextfield mã sản phẩm trong phần Thông tin sản phẩm. Nếu mã sản phẩm hợp lệ thì các thông tin như: tác giả, thương hiệu, tên sản phẩm, năm xuất sẽ hiển thị lên phần thông tin sản phẩm. Tiếp tục người dùng nhập số lượng khách mua ở Jtextfield số lượng và người dùng nhấn nút Thêm sản phẩm, nếu số lượng hợp lệ thì sản phẩm đó sẽ được lưu vào danh sách mua hàng đồng thời hiển thị lên bảng Danh sách sản phẩm bán</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Thêm sản phẩm</b> thực hiện them sản phẩm vào bảng Danh sách sản phẩm bán. Nếu thông tin sản phẩm, số lượng đã có đây đủ thì sẽ them vào bảng Danh sách sản phẩm bán. Ngược lại thông báo “Số lượng phải lớn hơn hoặc bằng 1” hoặc thông báo “Không tìm thấy sản phẩm”.</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Xóa Trắng</b> dùng để xóa trỗng các thông tin phần Thông tin sản phẩm.</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Xóa SP CTHD</b> Thực hiện xóa sản phẩm ra khỏi bảng Danh sách sản phẩm bán. Nếu chưa chọn sản phẩm thì thông báo “Vui lòng chọn sản phẩm để xóa”. Ngược lại, xóa sản phẩm ra khỏi bảng Danh sách sản phẩm và xóa ra khỏi danh sách mua hàng.</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Thanh Toán</b> thực hiển mở cửa sổ thanh toán. Nếu chưa có thông tin khách hàng thì sẽ thông báo “Chưa có khách hàng!”, nếu bảng Danh sách sản phẩm bán rỗng sẽ thông báo “Chưa có sản phẩm”. Ngược lại mở cửa sổ thanh toán.</p>"

						+ "</div>"
						+ "</html>";

				txtArea.setText(html);
			}
			else if(nodeSelected.equals(nodeDSHD)) {
				txtArea.setContentType("text/html");
				String html = "<html>"
						+ "<h1 style='text-align:center; color:orange'>Danh sách hóa đơn</h1>"
						+ "<div>"
						+ "<div style='margin-left: 45px'>"
						+ "<img width='1000' height='550' style='object-fit:cover' src='"+getClass().getResource("/image/danhsachhoadon1.png").toString()+"'/>"
						+ "</div>"

						+ "<p style='font-size:16px;'>- Để sử dụng được chức năng này thì người dùng phải đăng nhập thành công vào hệ thống và chọn chức năng Quản Lý Hóa Đơn, sau đó chọn tab Danh sách hóa đơn.</p>"
						+ "<p style='font-size:16px;'>- Người dùng có thể <b>Tìm kiếm hóa đơn</b> bằng cách nhập mã hóa đơn hoặc số điện thoại hoặc chọn ngày lập hóa đơn tại phần Thông tin tìm kiếm. Người dùng chỉ có thể tìm kiếm Hóa đơn do mình lập.</p>"
						+ "<p style='font-size:16px;'>- Người dùng chọn bất kỳ hóa đơn trên bảng Thông tin hóa đơn, giao diện sẽ hiển thị danh sách chi tiết hóa đơn tưởng ứng tại bảng Thông tin chi tiết hóa đơn.</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Làm mới</b> thực hiện tải lại trang, tải lại hóa đơn mới nhất được lập, đồng thời ngày lập hóa đơn được chọn là ngày hiện tại.</p>"
						+ "<p style='font-size:16px;'>- Nút <b>Xuất Hóa Đơn</b> thực hiện chức năng xuất lại hóa đơn bất kỳ trước đó. Nếu bảng Thông tin hóa đơn chưa được chọn sẽ thông báo “Vui lòng chọn hóa đơn cần xuất”. Ngược lại, sẽ mở cửa sổ xuất hóa đơn.</p>"
						+ "<div style='margin-left: 45px'>"
						+ "<img width='1000' height='550' style='object-fit:cover' src='"+getClass().getResource("/image/giaodieninhoadon.png").toString()+"'/>"
						+ "</div>"
						+ "</div>"
						+ "</html>";

				txtArea.setText(html);
			}
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
}
