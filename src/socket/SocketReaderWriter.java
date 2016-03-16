package socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;





public class SocketReaderWriter
{
// ---------------------------------
// Attributes
// ---------------------------------
	private Socket			socket;
	private PrintWriter		out;
	private BufferedReader	in;
	private boolean			printError = true;

// ---------------------------------
// Builder
// ---------------------------------
	public SocketReaderWriter(Socket socket) throws IOException
	{
		this.socket	= socket;
		this.out	= new PrintWriter(socket.getOutputStream(), true);
		this.in		= new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	public SocketReaderWriter(Socket socket, boolean printError) throws IOException
	{
		this(socket);
		this.printError = printError;
	}

// ---------------------------------
// Local method
// ---------------------------------
	public String readLine()
	{
		try
		{
			return this.in.readLine();
		}
		catch (Exception e)
		{
			if (printError) e.printStackTrace();
			return null;
		}
	}

	public void writeLine(String msg)
	{
		try
		{
			this.out.write(msg + "\n");
			this.out.flush();
		}
		catch (Exception e)
		{
			if (printError) e.printStackTrace();
		}
	}

	public void close()
	{
		try
		{
			this.in.close();
			this.out.close();
			this.socket.close();
		}
		catch (IOException e)
		{
			if (printError) e.printStackTrace();
		}
	}
}
