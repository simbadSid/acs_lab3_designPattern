package socket;

import general.Client_itf;
import general.ExceptionUnknownUser;

import java.io.BufferedReader;
import java.io.PrintWriter;






public class RemoteSocketClient implements Client_itf
{
// ---------------------------------
// Attributs
// ---------------------------------
	private BufferedReader	in;
	private PrintWriter		out;

// ---------------------------------
// Builder
// ---------------------------------
	public RemoteSocketClient(BufferedReader in, PrintWriter out)
	{
		this.in		= in;
		this.out	= out;
	}

// ---------------------------------
// Local methods
// ---------------------------------
	@Override
	public void unregister() throws ExceptionUnknownUser
	{
		throw new RuntimeException("Un implemented method");
	}

	@Override
	public void sndMsg(String msg) throws ExceptionUnknownUser
	{
		throw new RuntimeException("Un implemented method");
	}

	@Override
	public void notifyForeignClientAction(String client, String action)
	{
		try
		{
			out.write(client);
			out.write(action);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}