package general;

import java.util.HashMap;






public class ServerImpl implements Server_itf
{
// ---------------------------------
// Attributs
// ---------------------------------
	private HashMap<String, Client_itf>	loggedUser;

// ---------------------------------
// Builder
// ---------------------------------
	public ServerImpl()
	{
		this.loggedUser = new HashMap<String, Client_itf>();
	}

// ---------------------------------
// Local methods
// ---------------------------------
	@Override
	public boolean register(Client_itf c, String pseudo)
	{
		if (this.loggedUser.containsKey(pseudo))	return false;

		this.loggedUser.put(pseudo, c);
		return true;
	}

	@Override
	public void unregister(Client_itf c) throws ExceptionUnknownUser
	{
		String pseudo = this.getUserPseudo(c);

		if (pseudo == null) throw new ExceptionUnknownUser();
		this.loggedUser.remove(pseudo);
	}

	@Override
	public void sndMsg(Client_itf c, String msg) throws ExceptionUnknownUser
	{
		String pseudo = this.getUserPseudo(c);
		if (pseudo == null) throw new ExceptionUnknownUser();

		for (String receiverClientName: this.loggedUser.keySet())
		{
			if (receiverClientName.equals(pseudo)) continue;
			Client_itf receiverClient = this.loggedUser.get(receiverClientName);
			receiverClient.notifyForeignClientAction(pseudo, "Send message: \"" + msg + "\"");
		}
//		System.out.println("User \"" + pseudo + "\" has sent the message: \"" + msg);
	}

// ---------------------------------
// Private methods
// ---------------------------------
	private String getUserPseudo(Client_itf c)
	{
		for (String pseudo : this.loggedUser.keySet())
		{
			if (this.loggedUser.get(pseudo) == c) return pseudo;
		}
		return null;
	}
}
