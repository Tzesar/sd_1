
import java.io.*;
import java.net.*;

public class cliente {

	public static void main(String[] args) throws Exception {

		Socket clientSocket = null;
		PrintWriter out = null;
	     BufferedReader in = null;
     	int TimeOutConexion = 7000; //milisegundos
      	long ini = 0;
       	long fin = 0;

        	BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        	String fromServer;
        	String fromUser;

		// Imprime mensajes iniciales del servidor
		System.out.println("Servidor de informacion Policial");
		System.out.println("Ingrese CI para la consulta");
		
    		fromUser = stdIn.readLine();
   	     while ( !(fromUser.equals("terminar")) ) {
          
          	// Verifica que el mensaje a enviar no este vacio
          	if (fromUser != null) {
          	
          		try {

					SocketAddress sockaddr = new InetSocketAddress("localhost", 9900);
		            	//SocketAddress sockaddr = new InetSocketAddress("200.10.229.161", 8080);
		            	clientSocket = new Socket();

		   	    		ini = System.currentTimeMillis();
		            	clientSocket.connect(sockaddr, TimeOutConexion);
            
		            	// enviamos nosotros
		            	out = new PrintWriter(clientSocket.getOutputStream(), true);

					//viene del servidor
			          in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		     	} catch (SocketTimeoutException e){
          			fin = System.currentTimeMillis();
		          	System.err.println("Fallo de Timeout de conexion en " + TimeOutConexion);
         				System.err.println("Duracion " + (fin-ini));
		            	System.exit(1);
		        	} catch (UnknownHostException e) {
		          	System.err.println("Host desconocido");
     		     	System.exit(1);
     		   	} catch (IOException e) {
     		     	System.err.println("Error de I/O en la conexion al servidor");
     		     	System.exit(1);
    		    		}

				//escribimos al servidor
	               out.println(fromUser);
	               
		          fromServer = in.readLine();
				System.out.println("Server:"+fromServer);
				
				out.close();
         			in.close();
				clientSocket.close();
     	     }
            
     		fromUser = stdIn.readLine();
	    }
	    
//	    out.println("terminar");
         stdIn.close();
    }
}
