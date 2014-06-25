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

public class PreprocessedStats
{
	public int PS_id;
	public String pipeline = null;
	public String file_name = null;
	public Blob file_data = null;
	public int Img_id;
	
	public PreprocessedStats(int PS_id, String pipeline, String file_name, Blob file_data, int img_id)
	{
		this.PS_id = PS_id;
		this.pipeline = pipeline;
		this.file_name = file_name;
		this.file_data = file_data;
		this.Img_id = img_id;
	}
	
	/**
	 * Save PreprocessedStats
	 * 
	 * @return the PS_id
	 * 
	 */
	public static int savePreprocessedStats(String pipeline,  String file_path, String file_name, int Img_id) throws Exception
	{
		DBCommon db = DBCommon.getInstance();

		String sql_insert_PreprocessedStats = "insert into PreprocessedStats(pipeline, file_name, file_data, Img_id) values (?, ?, ?, ?);";
		
		PreparedStatement pstat = null;
		
		try
		{
			pstat = db.getConnection().prepareStatement(sql_insert_PreprocessedStats);
			
			pstat.setString(1, pipeline);
			pstat.setString(2, file_name);
			pstat.setInt(4, Img_id);
			
			File file = new File(file_path+"/"+file_name); 
			InputStream file_data = new BufferedInputStream(new FileInputStream(file)); 
			pstat.setBlob(3, file_data);
			
			pstat.executeUpdate();
			
			return PreprocessedStats.retrievePreprocessedStats(file_name).PS_id;
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
	 * Retrieve PreprocessedStats information
	 * 
	 * @return the PreprocessedStats object
	 */
	public static PreprocessedStats retrievePreprocessedStats(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_select_PreprocessedStats = "select PS_id, pipeline, file_data, Img_id from PreprocessedStats where file_name = ?;";
		
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try
		{
			pstat = db.getConnection().prepareStatement(sql_select_PreprocessedStats);
			
			pstat.setString(1, file_name);

			rs = pstat.executeQuery();
			
			while(rs.next())
			{
				int PS_id = rs.getInt(1);
				String pipeline = rs.getString(2);
				Blob file_data = rs.getBlob(3);
				int Img_id = rs.getInt(4);
	
				return new PreprocessedStats(PS_id, pipeline, file_name, file_data, Img_id);
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
	 * Save PreprocessedStats locally
	 * 
	 * @return the File object
	 */
	public static File localizePreprocessedStats(PreprocessedStats ps, String folder_path) throws Exception
	{
		InputStream in = ps.file_data.getBinaryStream(); 
        File file = new File(folder_path + "/" + ps.file_name); 
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
	 * Retrieve PreprocessedStats information
	 * 
	 * @ the number of PreprocessedStats records deleted.
	 */
	public static int deletePreprocessedStats(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_delete_PreprocessedStats = "delete from PreprocessedStats  where file_name = ?;";
		
		PreparedStatement pstat = null;

		try
		{
			pstat = db.getConnection().prepareStatement(sql_delete_PreprocessedStats);
			
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
