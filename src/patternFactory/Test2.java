package patternFactory;

import general.Client_itf;
import general.ExceptionServerRefused;
import general.Server_itf;
import java.util.Random;







public class Test2
{
// ---------------------------------
// Attributes
// ---------------------------------
	private static final int	nbrClient	= 5;
	private static final String	clientType	= Factory.clientType_local;
	private static final String	serverType	= Factory.serverType_remote;

// ---------------------------------
// Local methods
// ---------------------------------
	public static void main(String[] args) throws ClassNotFoundException, ExceptionServerRefused
	{
/*
		Client_itf clientTab[] = new Client_itf[nbrClient];
		Random rnd = new Random();

		Server_itf server = Factory.newServerInstance(serverType);
		for (int i=0; i<nbrClient; i++)
		{
			clientTab[i] = Factory.newClientInstance(clientType, server, "client_" + i);
			clientTab[i].register();
		}

		for (int i=0; i<nbrClient; i++)
		{
			int nbrMsg = 1 + rnd.nextInt(3);
			for (int j=0; j<nbrMsg; j++)
				clientTab[i].sndMsg("Message from " + i);
		}
*/
	}
}