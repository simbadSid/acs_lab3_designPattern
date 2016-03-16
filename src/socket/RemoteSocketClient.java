package socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import general.Client_itf;
import general.ExceptionServerRefused;
import general.ExceptionUnknownUser;







public class RemoteSocketClient implements Client_itf
{
// ---------------------------------
// Attributes
// ---------------------------------
	private SocketReaderWriter readerWriter;


// ---------------------------------
// Local methods
// ---------------------------------
	public void connectToClientCallBack(String clientCallBackIP, int clientCallBackPort) throws UnknownHostException, IOException
	{
		Socket socket		= new Socket(clientCallBackIP, clientCallBackPort);
		this.readerWriter	= new SocketReaderWriter(socket);
	}

	public void closeconnectionToClientCallBack()
	{
		this.readerWriter.close();
	}

	@Override
	public void register() throws ExceptionServerRefused
	{
		throw new RuntimeException("Un implemented method");
	}

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
		this.readerWriter.writeLine(client);
		this.readerWriter.writeLine(action);
	}
}