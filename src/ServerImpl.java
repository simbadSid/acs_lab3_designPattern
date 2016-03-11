import java.util.HashMap;
import java.util.LinkedList;






public class ServerImpl implements ChatServer_itf
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
	public void unregister(Client_itf c)
	{
		String pseudo = this.getUserPseudo(c);
		if (pseudo == null) throw new RuntimeException("Unknown user");
		this.loggedUser.remove(pseudo);
	}

	@Override
	public void sndMsg(Client_itf c, String msg)
	{
		String pseudo = this.getUserPseudo(c);
		if (pseudo == null) throw new RuntimeException("Unknown user");

		System.out.println("User \"" + pseudo + "\" has sent the message: \"" + msg);
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
