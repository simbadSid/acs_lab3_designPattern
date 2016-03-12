package general;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;






public class ClientImpl implements Client_itf
{
// ---------------------------------
// Attributs
// ---------------------------------
	public static final String terminalDirectoryPath = "resource/output/";

	private Server_itf		server;
	private String			clientPseudo;
	private PrintStream		terminal;
	private static File		sharedTerminalDirectory = null;

// ---------------------------------
// Builder
// ---------------------------------
	public ClientImpl(Server_itf server, String clientPseudo) throws RuntimeException
	{
		this.server			= server;
		this.clientPseudo	= new String(clientPseudo);
		this.initClientTerminal();

		boolean test = server.register(this, clientPseudo);
		if (!test)
			throw new RuntimeException("Client " + clientPseudo + " can't register into the server");
	}

// ---------------------------------
// Local methods
// ---------------------------------
	@Override
	public void unregister() throws ExceptionUnknownUser
	{
		this.server.unregister(this);
	}

	@Override
	public void sndMsg(String msg) throws ExceptionUnknownUser
	{
		this.server.sndMsg(this, msg);
	}

	@Override
	public void notifyForeignClientAction(String client, String action)
	{
		this.terminal.println("The client \"" + client + "\" has accomplished the action \"" + action + "\"");
	}

// ---------------------------------
// Private methods
// ---------------------------------
	private void initClientTerminal()
	{
		if (sharedTerminalDirectory == null)
		{
			String dirPath = terminalDirectoryPath + "/" + Calendar.getInstance().getTime();
			sharedTerminalDirectory = new File(dirPath);
			sharedTerminalDirectory.mkdir();
		}

		try
		{
			this.terminal	= new PrintStream(new File(sharedTerminalDirectory.getAbsolutePath() + "/" + clientPseudo));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
	}
}