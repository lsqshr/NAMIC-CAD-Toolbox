package namic.cad.server.db.entity;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import namic.cad.server.db.DBCommon;

public class Patient
{

	public int P_id;
	public String email = null;
	public String SerialNO = null;
	
	
	public Patient(int P_id, String email, String SerialNO)
	{
		this.P_id = P_id;
		this.email = email;
		this.SerialNO = SerialNO;
	}
	/**
	 * Save patient information
	 * 
	 * @return the P_id
	 */
	public static int savePatient(String email, String SerialNO) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_insert_patient = "insert into Patient(email, SerialNO) values (?, ?);";
		
		PreparedStatement pstat = null;
		
		try
		{
			pstat = db.getConnection().prepareStatement(sql_insert_patient);
			
			pstat.setString(1, email);
			pstat.setString(2, SerialNO);
			
			pstat.executeUpdate();
			
			return Patient.retrievePatient(SerialNO).P_id;
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pstat.close();
			db.closeDBResource();
		}
		
		return -1;
	}
	
		
	/**
	 * Delete patient information
	 * 
	 * @return the number of patient records deleted
	 */
	public static boolean deletePatient(String SerialNO) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_delete_patient = "delete from Patient where SerialNO = ?;";
		
		PreparedStatement pstat = null;
		
		try
		{
			pstat = db.getConnection().prepareStatement(sql_delete_patient);
			
			pstat.setString(1, SerialNO);
			
			pstat.executeUpdate();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pstat.close();
			db.closeDBResource();
		}
		
		return true;
	}
	
	
	/**
	 * Delete patient information
	 * 
	 * @return the Patient object
	 */
	public static Patient retrievePatient(String SerialNO) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_select_patient = "select * from Patient where SerialNO = ?;";
		
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try
		{
			pstat = db.getConnection().prepareStatement(sql_select_patient);
			
			pstat.setString(1, SerialNO);
			
			rs = pstat.executeQuery();
			
			while(rs.next())
			{
				int P_id = rs.getInt(1);
				String email = rs.getString(2);
				return new Patient(P_id, email, SerialNO);
			}
			
			return null;
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			rs.close();
			pstat.close();
			db.closeDBResource();
		}
		
		return null;
	}

}
