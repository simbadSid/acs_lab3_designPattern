package patternSingleton;

import general.ExceptionServerRefused;
import general.ExceptionUnknownUser;
import general.Server_itf;
import general.ClientImpl;

import java.util.Random;







public class Test
{
// ---------------------------------
// Attributs
// ---------------------------------
	private static final int	nbrClient = 5;

// ---------------------------------
// Local methods
// ---------------------------------
	public static void main(String[] args) throws ExceptionUnknownUser, ExceptionServerRefused
	{
		ClientImpl clientTab[] = new ClientImpl[nbrClient];
		Random rnd = new Random();

		for (int i=0; i<nbrClient; i++)
		{
			Server_itf server = ServerSingleton.getInstance();	// The server is re implemented on purpose
			clientTab[i] = new ClientImpl(server, "client"+i);
			clientTab[i].register();
		}

		for (int i=0; i<nbrClient; i++)
		{
			int nbrMsg = 1 + rnd.nextInt(3);
			for (int j=0; j<nbrMsg; j++)
				clientTab[i].sndMsg("Message from " + i);
		}
	}
}