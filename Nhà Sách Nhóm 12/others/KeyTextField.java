package others;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import connectDB.ConnectDB;
import dao.DAO_Sach;

public class KeyTextField extends KeyAdapter{
	private JTextField txtField;
	
	@SuppressWarnings("rawtypes")
	private List listSach;
	private List listDCHT;
	private List listSDT;
	@SuppressWarnings("rawtypes")
	public KeyTextField(JTextField txtTenSP, int loai)	//1 là cho Tìm sách , 2 là cho tìm DCHT,3 là Tìm SDT trong form lập hóa đơn
	{
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		txtField = txtTenSP;
		listSach = new ArrayList();
		listDCHT = new ArrayList();
		listSDT = new ArrayList();
		if(loai == 1) {
			databaseTim();
		}
		if(loai == 2) {
			databaseTimTenDCHT();
		}
		if(loai == 3) {
			databaseTimSDT();
		}
	}

	public void keyPressed(KeyEvent key)
	{
		switch(key.getKeyCode())
		{
		case KeyEvent.VK_BACK_SPACE : 
			break;
		case KeyEvent.VK_ENTER : 
			txtField.setText(txtField.getText());
			break;
		default : 
			EventQueue.invokeLater(new Runnable() 
			{
				@Override
				public void run() 
				{
					// TODO Auto-generated method stub
					String kt = txtField.getText();
					autoComplete(kt);
					autoCompleteDCHT(kt);
					autoCompleteSDT(kt);
				}
			});
		}

	}

	public void autoComplete(String kt)
	{
		String complete = "";
		int start = kt.length();
		int last = kt.length();
		int a;

		for(a=0;a<listSach.size();a++)
		{
			if(listSach.get(a).toString().startsWith(kt))
			{
				complete = listSach.get(a).toString();
				last = complete.length();
				break;
			}
		}
		
		if(last>start)
		{
			
			txtField.setText(complete);
			txtField.setCaretPosition(last);
			txtField.moveCaretPosition(start);
		}
	}
	public void autoCompleteDCHT(String kt)
	{
		String complete = "";
		int start = kt.length();
		int last = kt.length();
		int a;

		for(a=0;a<listDCHT.size();a++)
		{
			if(listDCHT.get(a).toString().startsWith(kt))
			{
				complete = listDCHT.get(a).toString();
				last = complete.length();
				break;
			}
		}
		
		if(last>start)
		{
			
			txtField.setText(complete);
			txtField.setCaretPosition(last);
			txtField.moveCaretPosition(start);
		}
	}

	public void autoCompleteSDT(String kt)
	{
		String complete = "";
		int start = kt.length();
		int last = kt.length();
		int a;

		for(a=0;a<listSDT.size();a++)
		{
			if(listSDT.get(a).toString().startsWith(kt))
			{
				complete = listSDT.get(a).toString();
				last = complete.length();
				break;
			}
		}
		
		if(last>start)
		{
			
			txtField.setText(complete);
			txtField.setCaretPosition(last);
			txtField.moveCaretPosition(start);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void databaseTim()
	{
		try
		{
			Connection konek = ConnectDB.getConnection();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM Sach";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{	
				listSach.add(rs.getString(2));
				listSach.add(rs.getString(6));
			}
			rs.close();
			state.close();
		}
		
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void databaseTimTenDCHT()
	{
		try
		{
			Connection konek = ConnectDB.getConnection();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM DungCuHocTap";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{	
				listDCHT.add(rs.getString(2));
			}
			rs.close();
			state.close();
		}
		
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void databaseTimSDT()
	{
		try
		{
			Connection konek = ConnectDB.getConnection();
			Statement state = konek.createStatement();
			String query = "SELECT * FROM KhachHang";
			ResultSet rs = state.executeQuery(query);
			while(rs.next())
			{	
				listSDT.add(rs.getString(3));
			}
			rs.close();
			state.close();
		}
		
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	}
}
