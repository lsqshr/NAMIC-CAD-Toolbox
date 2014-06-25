package namic.cad.test;

import java.io.IOException;

import it.sauronsoftware.ftp4j.FTPClient;
import it.sauronsoftware.ftp4j.FTPException;
import it.sauronsoftware.ftp4j.FTPIllegalReplyException;

public class FTP4J_test
{

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		FTPClient client = new FTPClient();
		try
		{
			client.connect("127.0.0.1");
			client.login("zhangfanmark", "`");
			String dir = client.currentDirectory();
			System.out.println(dir);
		}
		catch (IllegalStateException | IOException | FTPIllegalReplyException
				| FTPException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
