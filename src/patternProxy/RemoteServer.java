package patternProxy;

import java.net.ServerSocket;
import java.net.Socket;

import socket.ServerSocketEntry;
import socket.SocketReaderWriter;
import general.Client_itf;
import general.ExceptionServerRefused;
import general.Server_itf;







public class RemoteServer implements Server_itf, Runnable
{
// ---------------------------------
// Attributes
// ---------------------------------
	private String				serverIP;					//Used by the client thread
	private int					serverPort;
	private SocketReaderWriter	readerWriter;
	private Client_itf			localClient;

	private static final int	defaultcallBackPort = 3333;	// Used by the call back thread: react to server calls
	private static int			nbrCreatedClient	= 0;
	private String				callBackIP;
	private int					callBackPort;
	private SocketReaderWriter	callBackReaderWriter;
	private boolean				callBackInitialized	= false;
	private final Object		callBackLock		= new Object();

// ---------------------------------
// Builder
// ---------------------------------
	public RemoteServer()
	{
		this(ServerSocketEntry.DEFAULT_IP, ServerSocketEntry.DEFAULT_PORT);
	}

	public RemoteServer(String serverIP, Integer serverPort)
	{
		this.serverIP		= new String(serverIP);
		this.serverPort		= serverPort;
		this.callBackIP		= "localhost";		// TODO
		this.callBackPort	= RemoteServer.defaultcallBackPort + RemoteServer.nbrCreatedClient;
		RemoteServer.nbrCreatedClient ++;
	}

// ---------------------------------
// Call back methods: Reacts each time the server
// ---------------------------------
	@Override
	public void run()
	{
		ServerSocket serverSocket = null;
		Socket callBackSocket;
		String pseudo	= "Internal agent";
		String msg1		= "Initialized client call back listening socket...";
		String msg2		= "Initialized client call back connection with server...";

    	try
    	{
			serverSocket = new ServerSocket(callBackPort);					// Initialize the call back connection
			this.localClient.notifyForeignClientAction(pseudo, msg1);
			synchronized(this.callBackLock)
			{
				this.callBackInitialized = true;
				this.callBackLock.notifyAll();								// Tell the client that it can start
			}

			callBackSocket = serverSocket.accept();							//		with the server
			serverSocket.close();
			this.localClient.notifyForeignClientAction(pseudo, msg2);
			this.callBackReaderWriter = new SocketReaderWriter(callBackSocket);

			while(true)														// React to each action of the server
			{
				String client	= this.callBackReaderWriter.readLine();
				String action	= this.callBackReaderWriter.readLine();
				if ((client == null) || (action == null))
					return;
				this.localClient.notifyForeignClientAction(client, action);
			}
    	}
		catch(Exception e)
		{
			e.printStackTrace();
			return;
		}
	}

// ---------------------------------
// Callback thread management
// ---------------------------------
	public void launchCallbackThread(Client_itf localClient)
	{
		this.localClient	= localClient;

		Thread t = new Thread(this);
		t.start();

		try												// Wait for the call back thread to be initialized
		{												//		and its waiting port open
			synchronized(this.callBackLock)
			{
				while(this.callBackInitialized == false)
				{
					this.callBackLock.wait();
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("");
		}
	}

	public void stopCallBackThread()
	{
		this.callBackReaderWriter.close();
	}

// ---------------------------------
// Local methods
// ---------------------------------
	@Override
	public boolean register(Client_itf c, String pseudo)
	{
		System.out.println("Client register (begining) ...");
		Socket socket = null;
		Boolean res = null;
		String serverAnswer;

		try
		{
			socket				= new Socket(this.serverIP, this.serverPort);
			this.readerWriter	= new SocketReaderWriter(socket);
			this.readerWriter.writeLine(ServerSocketEntry.ACTION_REGISTER);
			this.readerWriter.writeLine(pseudo);
			this.readerWriter.writeLine(this.callBackIP);
			this.readerWriter.writeLine(""+this.callBackPort);
			serverAnswer		= this.readerWriter.readLine();
			if (serverAnswer == null) throw new ExceptionServerRefused();
			res = Boolean.parseBoolean(serverAnswer);
			serverAnswer = this.readerWriter.readLine();
			if ((serverAnswer == null) || (!serverAnswer.equals(ServerSocketEntry.ACTION_RESULT_DONE)))
			{
				throw new ExceptionServerRefused();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}

		System.out.println("Client register (end) ...");
		return res;
	}

	@Override
	public void unregister(Client_itf c) throws ExceptionServerRefused
	{
		System.out.println("Client unregister (begining) ...");
		try
		{
			this.readerWriter.writeLine(ServerSocketEntry.ACTION_UNREGISTER);
			String serverAnswer = this.readerWriter.readLine();
			if ((serverAnswer == null) || (!serverAnswer.equals(ServerSocketEntry.ACTION_RESULT_DONE)))
			{
				throw new ExceptionServerRefused();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ExceptionServerRefused();
		}
		System.out.println("Client unregister (end) ...");
	}

	@Override
	public void sndMsg(Client_itf c, String msg) throws ExceptionServerRefused
	{
		System.out.println("Client send message (begining) ...");
		try
		{
			this.readerWriter.writeLine(ServerSocketEntry.ACTION_SEND_MESSAGE);
			this.readerWriter.writeLine(msg);
			String serverAnswer = this.readerWriter.readLine();
			if ((serverAnswer == null) || (!serverAnswer.equals(ServerSocketEntry.ACTION_RESULT_DONE)))
			{
				throw new ExceptionServerRefused();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ExceptionServerRefused();
		}
		System.out.println("Client send message (end) ...");
	}
}