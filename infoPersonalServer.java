/*
 * Servidor de informacion personal UDP
 */
 
//package infoPersonal;
 
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class infoPersonalServer{
	public static void main( String[] args ) throws Exception{
		int puerto = 9800;
		
		try {
          	DatagramSocket serverSocket = new DatagramSocket(puerto);
          	System.out.println("Servidor en ejecucion");

	          while (true){
				byte[] receiveData = new byte[7];
                	DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
                	serverSocket.receive(request);
                	
                	// Comprueba si el mensaje no es un comando para apagar el servidor
                	String mensaje = new String(request.getData());
                	//mensaje = mensaje.substring(0,mensaje.length()-2);
                	if ( mensaje.equals("termina") ){
					serverSocket.close();
					System.out.println("Cerrando el servidor");
					break;
                	}
                	
                	new infoPersonalHilo(serverSocket, request).start();
                	System.out.println("Pedido en proceso");
            	}
        	}catch (SocketException ex){
            	System.out.println("Puerto UDP " + puerto +" esta ocupado.");
            	System.exit(1);
        	}
	}
}

