package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import general.ServerImpl;
import general.Server_itf;







public class ServerSocketEntry implements Runnable
{
// ---------------------------------
// Attributs
// ---------------------------------
	public static final int		port				= 2222;
	public static final String	ACTION_REGISTER		= "register";
	public static final String	ACTION_UNREGISTER	= "unregister";
	public static final String	ACTION_SEND_MESSAGE	= "sendMessage";

	private Server_itf		server;
	private ServerSocket	serverSocket;

// ---------------------------------
// Builder
// ---------------------------------
	public ServerSocketEntry(Server_itf server) throws IOException
	{
		this.server			= new ServerImpl();
		this.serverSocket	= new ServerSocket(port);
	}

// ---------------------------------
// Local method
// ---------------------------------
	@Override
	public void run()
	{
		Socket clientSocket;
		PrintWriter out	= null;
		BufferedReader	in = null;

	    while(true)
		{
	    	try
	    	{
				clientSocket	= serverSocket.accept();
			    out				= new PrintWriter(clientSocket.getOutputStream(), true);
			    in				= new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	    	}
	    	catch(Exception e)
	    	{
	    		e.printStackTrace();
	    		System.exit(0);
	    	}
	    	Thread t = new ServeerCommunicationThread(in, out);
	    	t.start();
		}
	}

// ---------------------------------
// Class that manages a communication session.
// ---------------------------------
	private class ServeerCommunicationThread extends Thread
	{
		// Attributes
	    private PrintWriter		out;
	    private BufferedReader	in;

	    // Builder
		public ServeerCommunicationThread(BufferedReader in, PrintWriter out)
		{
			this.in		= in;
			this.out	= out;
		}

		// Local methods
		@Override
	    public void run()
		{
			String action;
			RemoteSocketClient c = new RemoteSocketClient(in, out);

			try
			{
				while((action = in.readLine()) != null)
				{
					if		(action.equals(ACTION_REGISTER))
					{
						String pseudo = in.readLine();
						boolean res = server.register(c, pseudo);
						out.write(""+res);
					}
					else if	(action.equals(ACTION_UNREGISTER))
					{
						server.unregister(c);
					}
					else if	(action.equals(ACTION_SEND_MESSAGE))
					{
						String msg = in.readLine();
						server.sndMsg(c, msg);
					}
					else
					{
						System.out.println("*** Unknown action " + action + " ***");
						return;
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
				return;
			}
		}
	}
}
