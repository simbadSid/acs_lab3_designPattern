package socket;

import java.io.IOException;
import javax.swing.SwingUtilities;

import patternSingleton.ServerSingleton;
import general.ServerImpl;
import general.Server_itf;







public class TestServer
{
	public static void main(String[] args) throws IOException
	{
		Server_itf			server		= new ServerSingleton();
		ServerSocketEntry	serverEntry	= new ServerSocketEntry(server);
		SwingUtilities.invokeLater(serverEntry);

	
	}
}