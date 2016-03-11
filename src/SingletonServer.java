



public class SingletonServer  implements ChatServer_itf
{
// ---------------------------------
// Attributs
// ---------------------------------
	private static SingletonServer	uniqueServerInstance = null;

// ---------------------------------
// Builder
// ---------------------------------
	private SingletonServer()
	{
	}
	public static SingletonServer getInstance()
	{
		if (SingletonServer.uniqueServerInstance == null) 
		{
			SingletonServer.uniqueServerInstance = new SingletonServer();
		}
		return SingletonServer.uniqueServerInstance;
	}

// ---------------------------------
// Local method
// ---------------------------------
	@Override
	public boolean register(Client_itf c, String pseudo)
	{
		return SingletonServer.uniqueServerInstance.register(c, pseudo);
	}
	@Override
	public void unregister(Client_itf c)
	{
		SingletonServer.uniqueServerInstance.unregister(c);		
	}
	@Override
	public void sndMsg(Client_itf c, String msg)
	{
		SingletonServer.uniqueServerInstance.sndMsg(c, msg);		
	}

}
