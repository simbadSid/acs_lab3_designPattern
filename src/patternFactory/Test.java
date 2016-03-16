package patternFactory;

import general.Client_itf;
import general.ExceptionServerRefused;
import general.Server_itf;





public class Test
{
	private static final int	nbrClient	= 6;
	private static final String	serverIP	= "127.0.0.1";
	private static final int	serverPort	= 2222;
	

	public static void main(String[] args) throws ExceptionServerRefused, ClassNotFoundException
	{
		Client_itf[] clientTab = new Client_itf[nbrClient];
		
		Server_itf server = Factory.newServerInstance(Factory.serverType_local, serverIP, serverPort);
		for (int i=0; i<nbrClient; i++)
		{
			clientTab[i] = Factory.newClientInstance(Factory.clientType_local, server, "client_"+i, null, null);
		}
		for (int i=0; i<nbrClient; i++)
		{
			clientTab[i].register();
			clientTab[i].sndMsg("Blablba");
		}
		for (int i=0; i<nbrClient; i++)
		{
			clientTab[i].unregister();			
		}
	}

}
