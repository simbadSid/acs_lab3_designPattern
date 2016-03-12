package patternSingleton;

import general.Server_itf;
import general.Client_itf;
import general.ExceptionUnknownUser;
import general.ServerImpl;



public class ServerSingleton  implements Server_itf
{
// ---------------------------------
// Attributs
// ---------------------------------
	private static ServerImpl uniqueServerInstance;

// ---------------------------------
// Builder
// ---------------------------------
	public static Server_itf getInstance()
	{
		if (ServerSingleton.uniqueServerInstance == null) 
		{
			ServerSingleton.uniqueServerInstance = new ServerImpl();
		}
		return ServerSingleton.uniqueServerInstance;
	}

// ---------------------------------
// Local method
// ---------------------------------
	@Override
	public boolean register(Client_itf c, String pseudo)
	{
		return ServerSingleton.uniqueServerInstance.register(c, pseudo);
	}
	@Override
	public void unregister(Client_itf c) throws ExceptionUnknownUser
	{
		ServerSingleton.uniqueServerInstance.unregister(c);		
	}
	@Override
	public void sndMsg(Client_itf c, String msg) throws ExceptionUnknownUser
	{
		ServerSingleton.uniqueServerInstance.sndMsg(c, msg);		
	}

}
