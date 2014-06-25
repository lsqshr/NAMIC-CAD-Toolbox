package namic.cad.test;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import namic.cad.server.db.entity.Diagnosis;
import namic.cad.server.db.entity.Image;
import namic.cad.server.db.entity.Patient;
import namic.cad.server.db.entity.PreprocessedStats;

public class Sample
{
	public static void main(String[] args) throws ClassNotFoundException
	{

		try
		{
			//String file_name, String modality, String file_path, int P_id
//			int img_id = Image.saveImage("005S1224MR.nii", "MRI", "/Users/zhangfanmark/Documents/Namic/MRITestingData", 1);
//		
//			
//			Image i = Image.retrieveImage("005S1224MR.nii");
//			
//			System.out.println(i.modality.toCharArray());
//			
//			InputStream in = i.file_data.getBinaryStream(); 
//            File file = new File(i.file_name); 
//            OutputStream out = new BufferedOutputStream(new FileOutputStream(file)); 
//            byte[] buff = new byte[1024]; 
//            for (int j = 0; (j = in.read(buff)) > 0;) { 
//                    out.write(buff, 0, j); 
//            } 
//            
//           System.out.println( Image.deleteImage("005S1224MR.nii"));
			
			
			//PreprocessedStats.savePreprocessedStats("pipeline", "/Users/zhangfanmark/Documents/NAMIC-CAD-Toolbox", "Readme.txt", 2);
			
			//System.out.println(PreprocessedStats.retrievePreprocessedStats("Readme.txt"));
			
			//PreprocessedStats.localizePreprocessedStats(PreprocessedStats.retrievePreprocessedStats("Readme.txt"), "/Users/zhangfanmark/Documents");
			
			//Diagnosis.saveDiagnosis("type", "/Users/zhangfanmark/Documents/NAMIC-CAD-Toolbox", "Readme.txt", 2);
			
			System.out.println(Diagnosis.deleteDiagnosis("Readme.txt"));
			
		//	System.out.println(PreprocessedStats.deletePreprocessedStats("Readme.txt"));
			//Patient p = Patient.retrievePatient("NrST34S");
			
			//System.out.println(p.email);
		}
		catch (Exception e)
		{
			// if the error message is "out of memory",
			// it probably means no database file is found
			e.printStackTrace();
			System.err.println(e.getMessage());
		}
		finally
		{

		}
	}
}
