/*
 * centralServer - Servidor de informacion policial central, funciona como proxy
*/

import java.io.*;
import java.net.*;

public class centralServer{
	
	public static void main(String[] args) throws IOException {
		String inputLine, outputLine;
		ServerSocket serverSocket = null;
		Socket socket = null;
		boolean listening = true;
		
		try {
			serverSocket = new ServerSocket(9900);
			System.out.println("Servidor central proxy corriendo\nPuerto:9900");
		} catch (IOException e) {
			System.err.println("No se puede abrir el puerto: 9900.");
			System.exit(1);
		}
	
		while (listening) {
			new centralServerHilo(serverSocket.accept()).start();
		}
	
		serverSocket.close();
	}
	
}
