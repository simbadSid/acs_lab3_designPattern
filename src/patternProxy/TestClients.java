package patternProxy;

import java.util.Random;


import general.ClientImpl;
import general.Client_itf;
import general.ExceptionServerRefused;
import general.ExceptionUnknownUser;








public class TestClients
{
// ---------------------------------
// Attributes
// ---------------------------------
	private static final int	nbrClient	= 2;
	private static final String	serverIP	= "127.0.0.1";
	private static final int	serverPort	= 2222;

// ---------------------------------
// Main methods
// ---------------------------------
	public static void main(String[] args) throws ExceptionUnknownUser, ExceptionServerRefused
	{
		Client_itf[] clientTab = new Client_itf[nbrClient];
		RemoteServer[] localServerRef = new RemoteServer[nbrClient];
		Random rnd = new Random();

		for (int i=0; i<nbrClient; i++)
		{
			localServerRef[i] = new RemoteServer(serverIP, serverPort);
			clientTab[i] = new ClientImpl(localServerRef[i], "client"+i);
			localServerRef[i].launchCallbackThread(clientTab[i]);
			clientTab[i].register();
		}

		for (int i=0; i<nbrClient; i++)
		{
			int nbrMsg = 1 + rnd.nextInt(3);
			for (int j=0; j<nbrMsg; j++)
				clientTab[i].sndMsg("Message from " + i);
			clientTab[i].unregister();
			localServerRef[i].stopCallBackThread();
		}

	}

}