package socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import patternProxy.RemoteClient;
import general.ExceptionUnknownUser;
import general.ServerImpl;
import general.Server_itf;







public class ServerSocketEntry implements Runnable
{
// ---------------------------------
// Attributs
// ---------------------------------
	public static final int		DEFAULT_PORT			= 2222;
	public static final String	DEFAULT_IP				= "127.0.0.1";

	public static final String	ACTION_REGISTER			= "register";
	public static final String	ACTION_UNREGISTER		= "unregister";
	public static final String	ACTION_SEND_MESSAGE		= "sendMessage";
	public static final String	ACTION_RESULT_DONE		= "OK";
	public static final String	ACTION_RESULT_REFUSED	= "REFUSED";

	private Server_itf		server;
	private ServerSocket	serverSocket;

// ---------------------------------
// Builder
// ---------------------------------
	public ServerSocketEntry(Server_itf server) throws IOException
	{
		this.server			= new ServerImpl();
		this.serverSocket	= new ServerSocket(DEFAULT_PORT);
	}

// ---------------------------------
// Local method
// ---------------------------------
	@Override
	public void run()
	{
		SocketReaderWriter readerWritter = null;

		System.out.println("Server ready...");

		while(true)
		{
	    	try
	    	{
	    		Socket clientSocket	= serverSocket.accept();
				System.out.println("Server accepted new connection from \"" + clientSocket.getInetAddress() + "\" ...");
				readerWritter = new SocketReaderWriter(clientSocket);
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		continue;
	    	}
	    	Thread t = new ServeerCommunicationThread(readerWritter);
	    	t.start();
		}
	}

// ---------------------------------
// Class that manages a communication session.
// ---------------------------------
	private class ServeerCommunicationThread extends Thread
	{
		// Attributes
		private SocketReaderWriter readerWriter;

	    // Builder
		public ServeerCommunicationThread(SocketReaderWriter readerWriter)
		{
			this.readerWriter = readerWriter;
		}

		// Local methods
		@Override
	    public void run()
		{
			String action;
			RemoteClient c = new RemoteClient();
			boolean successfullyRegistered = false;
			boolean endSession = false;

			System.out.println("Server thread launched ...");
			while(true)
			{
				try
				{
					while((action = readerWriter.readLine()) != null)
					{
						System.out.println("Server received action \"" + action + "\" ...");
	
						if		(action.equals(ACTION_REGISTER))
						{
							String pseudo			= readerWriter.readLine();
							String clientCallBackIP	= readerWriter.readLine();
							int clientCallBackPort	= Integer.parseInt(readerWriter.readLine());
							c.connectToClientCallBack(clientCallBackIP, clientCallBackPort);
							boolean res = server.register(c, pseudo);
							readerWriter.writeLine(""+res);
							successfullyRegistered = true;
						}
						else if	(action.equals(ACTION_UNREGISTER))
						{
							if (successfullyRegistered == false)
								throw new ExceptionUnknownUser();
							server.unregister(c);
							c.closeconnectionToClientCallBack();
							successfullyRegistered = false;
							endSession = true;
						}
						else if	(action.equals(ACTION_SEND_MESSAGE))
						{
							if (successfullyRegistered == false)
								throw new ExceptionUnknownUser();
							String msg = readerWriter.readLine();
							System.out.println("\t" + msg);
							server.sndMsg(c, msg);
						}
						else
						{
							System.out.println("*** Unknown action " + action + " ***");
							throw new Exception();
						}
						readerWriter.writeLine(ACTION_RESULT_DONE);
						if (endSession == true)
							break;
					}
					System.out.println("Server thread removed...");
					return;
				}
				catch (Exception e)
				{
					e.printStackTrace();
					readerWriter.writeLine(ACTION_RESULT_REFUSED);
					if (successfullyRegistered)
					try
					{
						server.unregister(c);
					}
					catch (Exception e1)
					{
					}
					System.out.println("Server thread removed (exception raised)...");
					return;
				}
			}
		}
	}
}