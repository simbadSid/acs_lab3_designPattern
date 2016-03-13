package socket;

import java.io.IOException;
import javax.swing.SwingUtilities;
import general.ServerImpl;







public class TestServer
{
	public static void main(String[] args) throws IOException
	{
		ServerImpl			server		= new ServerImpl();
		ServerSocketEntry	serverEntry	= new ServerSocketEntry(server);
		SwingUtilities.invokeLater(serverEntry);

	
	}
}