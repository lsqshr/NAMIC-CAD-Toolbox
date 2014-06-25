package namic.cad.server.processing;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InvokeProcessingWorker
{
	private final int MAXWORKERS = 2;
	
	ServerSocket processingWorkerSocket = null;

	Socket communicationWorkerRequestSocket = null;

	public void runWorker()
	{
		try
		{
			processingWorkerSocket = new ServerSocket(8888);
			System.out.println("Running the server....");
			int numOfProcessingWorkers = 0;
			// Multiple servers
			while (true)
			{
				communicationWorkerRequestSocket = processingWorkerSocket.accept();
								
				if (communicationWorkerRequestSocket != null)
				{
					numOfProcessingWorkers++;
					
					System.out.println("Communication Worker " + numOfProcessingWorkers + " sent a processing job!");
					
					if (numOfProcessingWorkers >= MAXWORKERS)
					{
						// TODO response to communication worker to pending
					}
					else
					{
						Thread thread = new Thread(new ProcessingWorker(communicationWorkerRequestSocket));
						thread.setDaemon(true);
						thread.start();
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
