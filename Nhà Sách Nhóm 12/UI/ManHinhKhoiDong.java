package UI;


import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.JProgressBar;
/**
 * 
 * Lê Võ Hữu Thái
 *
 */
public class ManHinhKhoiDong extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JLabel lbLoad;
	public JProgressBar progressBar;
	JLabel lblBackground;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					ManHinhKhoiDong manHinhKhoiDong = new ManHinhKhoiDong();
					manHinhKhoiDong.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ManHinhKhoiDong() {
		setSize(750, 500);
		setUndecorated(true);
		progressBar = new JProgressBar();
		progressBar.setBackground(new Color(255, 255, 255));
		progressBar.setForeground(new Color(255, 153, 51));
		progressBar.setBorder(BorderFactory.createLineBorder(Color.black));
		progressBar.setPreferredSize(new Dimension(200, 35));
		progressBar.setStringPainted(true);
		add(progressBar, BorderLayout.SOUTH);
		
		lblBackground = new JLabel("");
		JLabel lblNoiDung = new JLabel("Cửa hàng sách Nhóm 12");
		String img ="C:\\Users\\dinh phong\\Desktop\\HOC\\phat trien ung dung\\do an\\demo2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\PTUD_N3_DeTai2_QuanLyNhaSach2\\src\\Icon\\pexels-pixabay-415071.jpg";
		lblBackground.setIcon(ResizeImage(img));
		lblBackground.setLayout(new BoxLayout(lblBackground, BoxLayout.Y_AXIS));
		lblNoiDung.setFont(new Font("Arial",Font.BOLD,30));
		lblNoiDung.setForeground(new Color(255, 153, 51));
		lblNoiDung.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblBackground.add(lblNoiDung);
		add(lblBackground);
		
		
	}
	public ImageIcon ResizeImage(String ImagePath)
	{
		ImageIcon MyImage = new ImageIcon(ImagePath);
		Image img = MyImage.getImage();
		Image newImg = img.getScaledInstance(750, 550, Image.SCALE_SMOOTH);
		ImageIcon image = new ImageIcon(newImg);
		return image;
	}
}
