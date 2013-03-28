
import java.net.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class identikitServerHilo extends Thread {
	
	private Socket socket = null;

	public identikitServerHilo(Socket socket){
		super("identikitServerHilo");
		this.socket = socket;
	}
	
	public void run(){
		System.out.println("\tHilo iniciado");
		String inputLine, jsonOutput=null;
		try{		
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			Gson gson = new Gson();
			
			try {
				// Obtiene el pedido
				inputLine = in.readLine();
				System.out.println("\t\tMensaje:"+inputLine);
				imagenes Imagenes = new imagenes();
				Imagenes.setImagenes(inputLine);
				jsonOutput = gson.toJson(Imagenes);
			} catch(FileNotFoundException e){
				System.out.println("\t\tNo existen datos para esta consulta en este servidor");
			}

			// Devuelve el resuldato de la busqueda en el servidor
			out.println(jsonOutput);
			
			// Cierra las conexiones
			out.close();
			in.close();
			socket.close();
		} catch (IOException e){
			System.out.println("\t\tErrores de entrada/salida");
		} finally{
			System.out.println("\tHilo finalizado");
		}
	}
}
