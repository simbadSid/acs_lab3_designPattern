package patternFactory;


public class TestClients {
/*
	private static final int	nbrClient	= 6;
	private static final String	serverIP	= "127.0.0.1";
	private static final int	serverPort	= 2222;
	
	public static void main(String[] args) throws ExceptionServerRefused {
		Client_itf[] clientTab = new Client_itf[nbrClient];
		Server_itf[] localServerRef = new Server_itf[nbrClient];
		Random rnd = new Random();
		
		for (int i=0; i<nbrClient; i+=2) {
			localServerRef[i] = new ProxyServer(serverIP, serverPort);
			clientTab[i] = new ClientImpl(localServerRef[i], "client"+i);
			((ProxyServer) localServerRef[i]).launchCallbackThread(clientTab[i]);
			clientTab[i].register();
		}
		
		for (int i=1; i<nbrClient; i+=2) {
			clientTab[i] = new ClientImpl(ServerSingleton.getInstance(), "client"+i);
			clientTab[i].register();
		}
		
		
		for (int i=0; i<nbrClient; i+=2) {
			int nbrMsg = 1 + rnd.nextInt(3);
			for (int j=0; j<nbrMsg; j++)
				clientTab[i].sndMsg("Message from " + i);
			clientTab[i].unregister();
			((ProxyServer) localServerRef[i]).stopCallBackThread();
		}
		
		for (int i=1; i<nbrClient; i+=2) {
			int nbrMsg = 1 + rnd.nextInt(3);
			for (int j=0; j<nbrMsg; j++)
				clientTab[i].sndMsg("Message from " + i);
			clientTab[i].unregister();
		}
	}
*/
}
