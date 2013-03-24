
import java.net.*;
import java.io.*;

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
			while ( !(inputLine.equals("terminar")) ) {
				
				System.out.println("\tMensaje recibido: " + inputLine);
				
				// Recuperar la informacion de los demas servidores
				String datosServer1 = conseguirInfoPersonal(inputLine);
				
				/*
				 * Falta implementar las consultas los demas servidores
				*/
				
				// Devuelve la informacion al cliente
				// infoPersonalServer Response
				out.println(datosServer1);
				
				// Obtiene la sgte consulta del cliente			
				inputLine = in.readLine();
			}
			
			// Cierra los buferes y el socket
            	out.close();
            	in.close();
            	socket.close();
            	System.out.println("\tFinalizando Hilo");

        	} catch (IOException e) {
          	e.printStackTrace();
        	}
    	}
    	
    	private String conseguirInfoPersonal(String ci){
    		
    		// Datos del servidor infoPersonal
		String ipInfoServer = "127.0.0.1";
		int infoServerPort = 9800;
		String modifiedSentence = null;
    	
    		try {

			DatagramSocket clientSocket = new DatagramSocket();
			byte[] sendData = new byte[10];
          	byte[] receiveData = new byte[250];

	          InetAddress IPAddress = InetAddress.getByName(ipInfoServer);
          	
          	// Crea el paquete que consulta al servidor infoPersonalServer
	          sendData = ci.getBytes();
     	     DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, infoServerPort);
     	     clientSocket.send(sendPacket);

			// Se crea el paquete donde se almacenara la respuesta del servidor
     	     DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

     	     // Se establece el tiempo de espera del paquete respuesta
     	     clientSocket.setSoTimeout(5000);

			try {
				// Escucha por una respuesta
				clientSocket.receive(receivePacket);
	     	     modifiedSentence = new String(receivePacket.getData());
     	     }
     	     catch (SocketTimeoutException ste) {

          	     System.out.println("\tTimeOut: El paquete udp se asume perdido.");
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
}
