package namic.cad.server.processing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ProcessingWorker implements Runnable
{

	// the client socket for connection request
	public Socket communicationWorkerRequestSocket = null;

	public ProcessingWorker(Socket communicationWorkerRequestSocket)
	{
		this.communicationWorkerRequestSocket = communicationWorkerRequestSocket;
	}

	@Override
	public void run()
	{
		try
		{
			// Client socket output stream for sending message to client
			PrintStream out = new PrintStream(this.communicationWorkerRequestSocket.getOutputStream());
			// Client socket input stream for receiving message from client
			BufferedReader in = new BufferedReader(new InputStreamReader(this.communicationWorkerRequestSocket.getInputStream()));
			
			boolean flag = true;
			
			while (flag)
			{
				// Receiving the message from the client
				String str = in.readLine();
				
				// TODO processing the 'str' for obtaining the case ID for processing
				
			}
			
			// TODO sending the reply for the request (Accept or deny uploading)
			
			in.close();
			out.close();
			this.communicationWorkerRequestSocket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

}
