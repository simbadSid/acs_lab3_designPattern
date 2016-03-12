package general;





public interface Client_itf
{
	public void unregister					()								throws ExceptionUnknownUser;
	public void sndMsg						(String msg)					throws ExceptionUnknownUser;
	public void notifyForeignClientAction	(String client, String action);

}
