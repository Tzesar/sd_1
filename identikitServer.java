import java.net.*;
import java.io.*;


public class identikitServer {
	public static void main(String[] args) throws IOException {
		String unpitLine, outputLine;
		ServerSocket serverSocket = null;
		Socket clientSocket = null;
		boolean listening = true;
		
		try{
			serverSocket = new ServerSocket(9700);
		}catch (IOException e){
			System.err.println("No se puede abrir el puerto: 9700");
			System.exit(1);
		}
		System.out.println("Servidor Identikit corriendo");
		System.out.println("Puerto: 9700");
		
		while(listening){
			new identikitServerHilo(serverSocket.accept()).start();
		}
		
		serverSocket.close();
	}
}
