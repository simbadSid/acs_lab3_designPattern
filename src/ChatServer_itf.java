




public interface ChatServer_itf
{
	public boolean register (Client_itf c, String pseudo);
	public void unregister (Client_itf c);
	public void sndMsg(Client_itf c, String msg);
}
