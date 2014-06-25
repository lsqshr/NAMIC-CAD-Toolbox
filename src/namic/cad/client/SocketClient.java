package namic.cad.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SocketClient
{

	Socket socket = null;

	InputStreamReader input = null;
	InputStream in = null;

	byte[] b = new byte[1024];

	/**
	 * @param args
	 */
	public void socketStart()
	{
		try
		{
			socket = new Socket("127.0.0.1", 8888);
			OutputStream out = socket.getOutputStream();

			out.write("Client sending a message\n fsdfas".getBytes());

 			System.out.println("Sending from the client");

			// Receiving the server data

			BufferedReader in = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));

			while (!in.ready())
			{
				
			}
			String str = in.readLine();
			System.out.println(str);

		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			try
			{
				// Close all recoures
				in.close();
				socket.close();
			}
			catch (Exception e2)
			{
			}
		}
	}

	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		new SocketClient().socketStart();
	}

}
