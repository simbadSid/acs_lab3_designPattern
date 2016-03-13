package general;





public interface Server_itf
{
	public boolean	register	(Client_itf c, String pseudo);
	public void		unregister	(Client_itf c)					 throws ExceptionUnknownUser, ExceptionServerRefused;
	public void		sndMsg		(Client_itf c, String msg)		 throws ExceptionUnknownUser, ExceptionServerRefused;
}
