package namic.cad.server.db.entity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import namic.cad.server.db.DBCommon;

public class Diagnosis
{
	public int DS_id;
	public String type = null;
	public String file_name = null;
	public Blob file_data = null;
	public int PS_id;
	
	public Diagnosis(int DS_id, String type, String file_name, Blob file_data, int PS_id)
	{
		this.DS_id = DS_id;
		this.type = type;
		this.file_name = file_name;
		this.file_data = file_data;
		this.PS_id = PS_id;
	}
	
	/**
	 * Save Diagnosis
	 * @return the DS_id
	 */
	public static int saveDiagnosis(String type, String file_path, String file_name, int PS_id) throws Exception
	{
		DBCommon db = DBCommon.getInstance();

		String sql_insert_image = "insert into Diagnosis(type, file_name, file_data, PS_id) values (?, ?, ?, ?);";
		
		PreparedStatement pstat = null;
		
		try
		{
			pstat = db.getConnection().prepareStatement(sql_insert_image);
			
			pstat.setString(1, type);
			pstat.setString(2, file_name);
			pstat.setInt(4, PS_id);
			
			File file = new File(file_path + "/" + file_name); 
			InputStream file_data = new BufferedInputStream(new FileInputStream(file)); 
			pstat.setBlob(3, file_data);
			
			pstat.executeUpdate();
			
			return  Diagnosis.retrieveDiagnosis(file_name).DS_id;
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
	 * Retrieve Diagnosis information
	 * 
	 * @return the Diagnosis object
	 */
	public static Diagnosis retrieveDiagnosis(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_select_diagnosis = "select DS_id, type, file_data, PS_id from Diagnosis where file_name = ?;";
		
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try
		{
			pstat = db.getConnection().prepareStatement(sql_select_diagnosis);
			
			pstat.setString(1, file_name);

			rs = pstat.executeQuery();
			
			while(rs.next())
			{
				int DS_id = rs.getInt(1);
				String type = rs.getString(2);
				Blob file_data = rs.getBlob(3);
				int PS_id = rs.getInt(4);
	
				return new Diagnosis(DS_id, type,  file_name, file_data, PS_id );
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
	
	/**
	 * Save Diagnosis locally
	 * 
	 * @return the File object
	 */
	public static File localizeImage(Diagnosis diag, String folder_path) throws Exception
	{
		InputStream in = diag.file_data.getBinaryStream(); 
        File file = new File(folder_path+ "/" +diag.file_name); 
        OutputStream out = new BufferedOutputStream(new FileOutputStream(file)); 
        byte[] buff = new byte[1024]; 
        for (int j = 0; (j = in.read(buff)) > 0;) 
        { 
                out.write(buff, 0, j); 
        } 
        
        in.close();
        out.close();
        
        return file;
	}
	
	/**
	 * Retrieve Diagnosis information
	 * 
	 * @ the number of Diagnosis records deleted.
	 */
	public static int deleteDiagnosis(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_delete_diagnosis = "delete from Diagnosis where file_name = ?;";
		
		PreparedStatement pstat = null;

		try
		{
			pstat = db.getConnection().prepareStatement(sql_delete_diagnosis);
			
			pstat.setString(1, file_name);

			return pstat.executeUpdate();
			
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

	
}
