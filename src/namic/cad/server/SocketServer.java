package namic.cad.server;

import namic.cad.server.communication.InvokeCommunicationWorker;

public class SocketServer
{

	public static void main(String[] args)
	{
		new InvokeCommunicationWorker().runWorker();
	}
}
