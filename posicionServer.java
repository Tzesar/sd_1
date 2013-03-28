/*
 * Servidor de informacion de posicion UDP
 */
 
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class posicionServer{
	public static void main( String[] args ) throws Exception{
		int puerto = 9600;
		
		try {
          	DatagramSocket serverSocket = new DatagramSocket(puerto);
          	System.out.println("Servidor de Posicion corriendo\nPuerto:"+puerto);

	          while (true){
				byte[] receiveData = new byte[7];
                	DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
                	serverSocket.receive(request);
                	
                	// Comprueba si el mensaje no es un comando para apagar el servidor
                	String mensaje = new String(request.getData());
                	if ( mensaje.equals("termina") ){
					serverSocket.close();
					System.out.println("Cerrando el servidor");
					break;
                	}
                	
                	new posicionHilo(serverSocket, request).start();
                	System.out.println("\tPedido en proceso");
            	}
        	}catch (SocketException ex){
            	System.out.println("Puerto UDP " + puerto +" esta ocupado.");
            	System.exit(1);
        	}
	}
}

