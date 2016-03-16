package patternProxy;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import socket.SocketReaderWriter;
import general.Client_itf;







public class RemoteClient implements Client_itf
{
// ---------------------------------
// Attributes
// ---------------------------------
	private SocketReaderWriter readerWriter;

// ---------------------------------
// Local methods
// ---------------------------------
	public void connectToClientCallBack(String clientCallBackIP, Integer clientCallBackPort) throws UnknownHostException, IOException
	{
		Socket socket		= new Socket(clientCallBackIP, clientCallBackPort);
		this.readerWriter	= new SocketReaderWriter(socket);
	}

	public void closeconnectionToClientCallBack()
	{
		this.readerWriter.close();
	}

	@Override
	public void register()
	{
		throw new RuntimeException("Can't be called from local reference");
	}

	@Override
	public void unregister()
	{
		throw new RuntimeException("Can't be called from local reference");
	}

	@Override
	public void sndMsg(String msg)
	{
		throw new RuntimeException("Can't be called from local reference");
	}

	@Override
	public void notifyForeignClientAction(String client, String action)
	{
		this.readerWriter.writeLine(client);
		this.readerWriter.writeLine(action);
	}
}