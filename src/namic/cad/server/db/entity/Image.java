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

public class Image
{
	public int Img_id;
	public String file_name = null;
	public String modality = null;
	public Blob file_data = null;
	public int P_id;
	
	public Image(int img_id, String file_name,  String modality, Blob file_data, int P_id )
	{
		this.Img_id = img_id;
		this.file_name = file_name;
		this.modality = modality;
		this.file_data = file_data;
		this.P_id = P_id;
	}
	
	
	/**
	 * Save Image
	 * @return the Img_id
	 */
	public static int saveImage(String file_name, String modality, String file_path, int P_id) throws Exception
	{
		DBCommon db = DBCommon.getInstance();

		String sql_insert_image = "insert into Image(file_name, modality, file_data, P_id) values (?, ?, ?, ?);";
		
		PreparedStatement pstat = null;
		
		try
		{
			pstat = db.getConnection().prepareStatement(sql_insert_image);
			
			pstat.setString(1, file_name);
			pstat.setString(2, modality);
			pstat.setInt(4, P_id);
			
			File file = new File(file_path + "/" + file_name); 
			InputStream file_data = new BufferedInputStream(new FileInputStream(file)); 
			pstat.setBlob(3, file_data);
			
			pstat.executeUpdate();
			
			return  Image.retrieveImage(file_name).Img_id;
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
	 * Retrieve Image information
	 * 
	 * @return the Image object
	 */
	public static Image retrieveImage(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_select_image = "select Img_id, modality, file_data, P_id from Image where file_name = ?;";
		
		PreparedStatement pstat = null;
		ResultSet rs = null;
		try
		{
			pstat = db.getConnection().prepareStatement(sql_select_image);
			
			pstat.setString(1, file_name);

			rs = pstat.executeQuery();
			
			while(rs.next())
			{
				int img_id = rs.getInt(1);
				String modality = rs.getString(2);
				Blob file_data = rs.getBlob(3);
				int P_id = rs.getInt(4);
	
				return new Image(img_id, file_name,  modality, file_data, P_id );
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
	 * Save Image locally
	 * 
	 * @return the File object
	 */
	public static File localizeImage(Image img, String folder_path) throws Exception
	{
		InputStream in = img.file_data.getBinaryStream(); 
        File file = new File(folder_path+ "/" +img.file_name); 
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
	 * Retrieve Image information
	 * 
	 * @ the number of Image records deleted.
	 */
	public static int deleteImage(String file_name) throws Exception
	{
		
		DBCommon db = DBCommon.getInstance();

		String sql_delete_image = "delete from Image where file_name = ?;";
		
		PreparedStatement pstat = null;

		try
		{
			pstat = db.getConnection().prepareStatement(sql_delete_image);
			
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
