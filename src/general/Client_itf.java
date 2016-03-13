package general;





public interface Client_itf
{
	public void unregister					()								throws ExceptionUnknownUser, ExceptionServerRefused;
	public void sndMsg						(String msg)					throws ExceptionUnknownUser, ExceptionServerRefused;
	public void notifyForeignClientAction	(String client, String action);

}
