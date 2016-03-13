package socket;

import general.Client_itf;
import general.ExceptionUnknownUser;







public class RemoteSocketClient implements Client_itf
{
// ---------------------------------
// Attributes
// ---------------------------------
	private SocketReaderWriter readerWriter;

// ---------------------------------
// Builder
// ---------------------------------
	public RemoteSocketClient(SocketReaderWriter readerWriter)
	{
		this.readerWriter = readerWriter;
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
//TODO		this.readerWriter.writeLine(client);
//TODO		this.readerWriter.writeLine(action);
	}
}