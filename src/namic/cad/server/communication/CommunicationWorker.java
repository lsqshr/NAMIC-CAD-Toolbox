package namic.cad.server.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class CommunicationWorker implements Runnable
{
	// the client socket for connection request
	public Socket clientRequestSocket = null;

	public CommunicationWorker(Socket clientRequestSocket)
	{
		this.clientRequestSocket = clientRequestSocket;
	}

	@Override
	public void run()
	{
		try
		{
			// Client socket output stream for sending message to client
			PrintStream out = new PrintStream(this.clientRequestSocket.getOutputStream());
			// Client socket input stream for receiving message from client
			BufferedReader in = new BufferedReader(new InputStreamReader(this.clientRequestSocket.getInputStream()));
			
			boolean flag = true;
			
			while (flag)
			{
				// Receiving the message from the client
				String str = in.readLine();
				
				// TODO processing the 'str' for authorization
				
			}
			
			// TODO sending the reply for the request (Accept or deny uploading)
			
			in.close();
			out.close();
			this.clientRequestSocket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
