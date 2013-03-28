/*
 * Servidor central - cumple la funcion de un proxy para los clientes
*/

import java.net.*;
import java.io.*;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class centralServerHilo extends Thread {

	private Socket socket = null;

	public centralServerHilo(Socket socket) {
		super("centralServerHilo");
		this.socket = socket;
	}

	public void run() {
		String inputLine, outputLine;
		
		System.out.println("\tHilo iniciado");
		
		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			
			inputLine = in.readLine();
			//while ( !(inputLine.equals("terminar")) ) {
				
				System.out.println("\t\tMensaje recibido: " + inputLine);
				Gson gson = new Gson();
				
				// Recuperar la informacion de los demas servidores
				// Servidor de informacion personal
				// Puerto del servidor infoPersonalServer es 9800
				String datosServer1 = conseguirInfo(inputLine, "127.0.0.1", 9800);
				infoPersonal InfoPersonal = new infoPersonal();
				Scanner scan;
				int estado;
				if ( datosServer1 != null ){
					datosServer1 = datosServer1.substring(0, datosServer1.length()-1);
					scan = new Scanner(datosServer1).useDelimiter(";");
					estado = scan.nextInt();
					if ( estado == 2 ){
						System.out.println("\t\tError en el Servidor de Informacion Personal");
					} else if ( estado == 1 ){
						System.out.println("\t\tNo existe registros en el Servidor de Informacion Personal sobre "+inputLine);
					} else{
						int longitud = scan.nextInt();
						String datos1 = scan.next();
						
						// Deserializa el string respuesta del servidor
						datos1 = datos1.substring(0, longitud);
	     	 			Type collectionType = new TypeToken<infoPersonal>(){}.getType();
	     	   			InfoPersonal = gson.fromJson(datos1, collectionType);
	     	   		}
	     	   	}
        			
        			// Servidor de imagenes
        			// Puerto del servidor de imagenes es 9700
        			// Deserializa el string respuesta del servidor
        			String datosServer2 = conseguirImagenes(inputLine);
    				imagenes Imagenes = new imagenes();
    				Type collectionType = new TypeToken<imagenes>(){}.getType();
        			if ( datosServer2 != null ){
        				Imagenes = gson.fromJson(datosServer2, collectionType);
        			} else {
        				System.out.println("\t\tNo existe registros en el Servidor de Identikit sobre "+inputLine);
        			}
        			
        			// Servidor de posiciones
				// Puerto del servidor infoPersonalServer es 9600
				String datosServer3 = conseguirInfo(inputLine, "127.0.0.1", 9600);
				posicion Posicion = new posicion();
				if ( datosServer3 != null ){
					datosServer3 = datosServer3.substring(0, datosServer3.length()-1);
					scan = new Scanner(datosServer3).useDelimiter(";");
					estado = scan.nextInt();
					if ( estado == 2 ){
						System.out.println("\t\tError en el Servidor de Posiciones");
					} else if ( estado == 1 ){
						System.out.println("\t\tNo existe registros en el Servidor de Posiciones sobre "+inputLine);
					} else{
						int longitud = scan.nextInt();
						String datos3 = scan.next();
				
						// Deserializa el string respuesta del servidor
						datos3 = datos3.substring(0, longitud);
	     	 			collectionType = new TypeToken<posicion>(){}.getType();
	     	   			Posicion = gson.fromJson(datos3, collectionType);
     		   		}
     		   	}
				
				// Unir las tres fuentes de datos
				persona persona = new persona(InfoPersonal, Imagenes, Posicion);
				
				String datosFinales = persona.toJson();
				
				// Devuelve la informacion al cliente
				// infoPersonalServer Response
				out.println(datosFinales);

				
				// Obtiene la sgte consulta del cliente			
				//inputLine = in.readLine();
				
				// Cierra los buferes y el socket
            		out.close();
            		in.close();
            		socket.close();
			//}
        	} catch (IOException e) {
          	e.printStackTrace();
        	}
        	finally{
            	System.out.println("\tFinalizando Hilo");
        	}
    	}
    	
    	// Crea una conexion UDP con el servidor Informacion Personal y devuelve
    	// los datos en formato JSON
    	private String conseguirInfo(String ci, String ipServer, int infoPort){
    		
    		// Datos del servidor infoPersonal
		String modifiedSentence = null;
    	
    		try {

			DatagramSocket clientSocket = new DatagramSocket();
			byte[] sendData = new byte[10];
          	byte[] receiveData = new byte[250];

	          InetAddress IPAddress = InetAddress.getByName(ipServer);
          	
          	// Crea el paquete que consulta al servidor infoPersonalServer
	          sendData = ci.getBytes();
     	     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, infoPort);
     	     clientSocket.send(sendPacket);
			
			// Se crea el paquete donde se almacenara la respuesta del servidor
     	     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			
     	     // Se establece el tiempo de espera del paquete respuesta
     	     clientSocket.setSoTimeout(5000);
			
			try {
				// Escucha por una respuesta
				clientSocket.receive(receivePacket);
	     	     modifiedSentence = new String(receivePacket.getData(), "UTF-8");
     	     }
     	     catch (SocketTimeoutException ste) {
			
          	     System.out.println("\t\tTimeOut: El paquete udp se asume perdido.");
          	}
          	
        		// Se cierra el socket ya que la consulta finalizo
            	clientSocket.close();
		}
		catch (UnknownHostException ex) {
	          System.err.println(ex);
	     }
	     catch (IOException ex) {
     	     System.err.println(ex);
        	}
        	
		return modifiedSentence;    	
    	}
    	
    	// Crea una conexion TCP con el servidor Identikit y devuelve las imagenes en JSON
    	private String conseguirImagenes( String ci ){
    	
    		// Datos del servidor identikitServer
		Socket unSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		String respuesta = null;
		try {
			// Se crea el socket que se conectara con el servidor ServidorIdentikit
          	unSocket = new Socket("127.0.0.1", 9700);
          	unSocket.setSoTimeout(10000);
            	// enviamos nosotros
            	out = new PrintWriter(unSocket.getOutputStream(), true);
            	//viene del servidor
            	in = new BufferedReader(new InputStreamReader(unSocket.getInputStream()));
        	} catch (UnknownHostException e) {
          	System.err.println("\t\tHost desconocido");	
        	} catch (IOException e) {
            	System.err.println("\t\tError de I/O en la conexion al host");
        	}
		
          //escribimos al servidor
        	String fromServer;
          out.println(ci);
          
          try{
          	
          	try{
          		// Esperamos la respuesta del servidor de imagenes
				respuesta = in.readLine();
          	}  catch (SocketException e){
        			System.out.println("\t\tTimeOut: se asume que la transmision se perdio");
        		} catch (IOException e){
        			System.out.println("\t\tError entrada/salida");
        		}
          	
			// Se cierran las conexiones
			out.close();
        		in.close();
        		unSocket.close();
          } catch (IOException e){
        		System.out.println("\t\tError entrada/salida");
        	}
		
        	return respuesta;
    	}
}
