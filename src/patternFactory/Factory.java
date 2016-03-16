package patternFactory;

import java.lang.reflect.Constructor;
import java.rmi.RemoteException;
import javax.swing.SwingUtilities;
import socket.ServerSocketEntry;
import general.Client_itf;
import general.Server_itf;
import patternProxy.RemoteServer;





public class Factory
{
// --------------------------------
// Attributes
// --------------------------------
	public static final String	clientType_local	= "general.ClientImpl";
	public static final String	clientType_remote	= "patternProxy.RemoteClient";
	public static final String	serverType_local	= "general.ServerImpl";
	public static final String	serverType_remote	= "patternProxy.RemoteServer";

// --------------------------------
// Local methods
// --------------------------------
	public static Client_itf newClientInstance(String clientType, Server_itf server, String pseudo,
												String clientCallcackIP, Integer clientCallbackPort) throws ClassNotFoundException
	{
		try
		{
			Class<?> clazz = Class.forName(clientType);
			Constructor<?> constructor = null;

			if (clientType.equals(clientType_local))
			{
				constructor = clazz.getConstructor(Server_itf.class, String.class);
				return (Client_itf) constructor.newInstance(server, pseudo);
			}
			else if(clientType.equals(clientType_remote))
			{
				constructor = clazz.getConstructor(String.class, Integer.class);
				Client_itf client = (Client_itf) constructor.newInstance(clientCallcackIP, clientCallbackPort);
				((RemoteServer) server).launchCallbackThread(client);
				return client;
			}
			else
				throw new RemoteException("Unkown client implementation: " + clientType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}

	public static Server_itf newServerInstance(String serverType, String serverIP, Integer serverPort) throws ClassNotFoundException
	{
		try
		{
			Class<?> clazz = Class.forName(serverType);
			Constructor<?> constructor = null;

			if (serverType.equals(serverType_local))
			{
				constructor = clazz.getConstructor();
				return (Server_itf) constructor.newInstance();
			}
			else if(serverType.equals(serverType_remote))
			{
				constructor = clazz.getConstructor(String.class, Integer.class);
				Server_itf server = (Server_itf) constructor.newInstance(serverIP, serverPort);
				ServerSocketEntry	serverEntry	= new ServerSocketEntry(server);
				SwingUtilities.invokeLater(serverEntry);
				return server;
			}
			else
				throw new RemoteException("Unkown server implementation: " + serverType);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new ClassNotFoundException();
		}
	}
}