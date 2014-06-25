package namic.cad.server.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InvokeCommunicationWorker
{
	ServerSocket communicationWorkerSocket = null;

	Socket clientRequestSocket = null;

	public void runWorker()
	{
		try
		{
			communicationWorkerSocket = new ServerSocket(8888);
			System.out.println("Running the server....");
			int numOfClients = 1;
			// Multiple servers
			while (true)
			{
				clientRequestSocket = communicationWorkerSocket.accept();
				System.out.println("Client " + numOfClients + " sent a request for uploading the data ");
				
				if (clientRequestSocket != null)
				{
					Thread thread = new Thread(new CommunicationWorker(clientRequestSocket));
					thread.setDaemon(true);
					thread.start();
					numOfClients++;
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
