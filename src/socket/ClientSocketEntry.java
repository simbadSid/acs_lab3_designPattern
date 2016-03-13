package socket;

import java.net.Socket;
import general.Client_itf;
import general.ExceptionServerRefused;
import general.Server_itf;







public class ClientSocketEntry implements Server_itf
{
// ---------------------------------
// Attributes
// ---------------------------------
	private String				serverIP;
	private int					serverPort;
	private SocketReaderWriter	readerWriter;

// ---------------------------------
// Builder
// ---------------------------------
	public ClientSocketEntry()
	{
		this.serverIP	= ServerSocketEntry.DEFAULT_IP;
		this.serverPort	= ServerSocketEntry.DEFAULT_PORT;
	}
	public ClientSocketEntry(String serverIP, int serverPort)
	{
		this.serverIP	= new String(serverIP);
		this.serverPort	= serverPort;
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