package socket;

import general.Client_itf;
import general.ExceptionUnknownUser;
import general.Server_itf;






public class RemoteSocketServer implements Server_itf
{
// ---------------------------------
// Attributs
// ---------------------------------

// ---------------------------------
// Builder
// ---------------------------------
	public RemoteSocketServer()
	{
		
	}

	@Override
	public boolean register(Client_itf c, String pseudo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void unregister(Client_itf c) throws ExceptionUnknownUser {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sndMsg(Client_itf c, String msg) throws ExceptionUnknownUser {
		// TODO Auto-generated method stub
		
	}

// ---------------------------------
// Local methods
// ---------------------------------
}
