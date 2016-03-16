package general;





public interface Client_itf
{
	public void		register					()								throws ExceptionServerRefused;
	public void		unregister					()								throws ExceptionUnknownUser, ExceptionServerRefused;
	public void		sndMsg						(String msg)					throws ExceptionUnknownUser, ExceptionServerRefused;
	public void		notifyForeignClientAction	(String client, String action);
}