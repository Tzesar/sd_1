/*
 * Hilo que atiende los pedidos de busqueda de datos personales
*/

//package infoPersonal;

import java.io.*;
import java.net.*;
import java.lang.Integer;
import java.util.Scanner;

public class infoPersonalHilo extends Thread{
	private DatagramPacket request = null;
	private DatagramSocket socket;
	
	public infoPersonalHilo(DatagramSocket socket, DatagramPacket request){
		super("infoPersonalHilo");
		this.socket = socket;
		this.request = request;
	}
	
	// Codigo que ejecuta el hilo
	public void run(){
		byte[] sendData = new byte[250];
		DatagramPacket response;
	
		// Obtiene el dato recibido y verifica que sea un numero
		String datoRecibido = new String(request.getData());
		// Remueve caracteres innecesarios al final de la fila
		datoRecibido = resolverPedido(datoRecibido);
		
		// Obtiene los datos del cliente
		// ya que siempre manda una respuesta
		InetAddress IPAddress = request.getAddress();
		int port = request.getPort();
		
		// Si los datos recibidos no son correctos devuelve un codigo de error 2
		// Este mensaje sera modificado con datos verdaderos
		response = new DatagramPacket("2".getBytes(), 1, IPAddress, port);
		
		try{
			int ci = Integer.parseInt(datoRecibido);
			
			// Crea la conexion con la base de datos
			DBConnector db = new DBConnector("nuevosDatos.bd");
			String busqueda;
			infoPersonal datosPersonales;
			
			// Busca la persona por CI
			busqueda = db.buscar(ci);
			
			// Si existen datos sobre la persona
			if ( busqueda != null ){
				// Crea el objeto infoPersonal
				datosPersonales = new infoPersonal(busqueda);
				
				// Guarda los datos correctos en sendData y prepara el paquete de respuesta
				sendData = ("0" + datosPersonales.toJSON() ).getBytes();
				response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			}else{
				// Si no se encuentran los datos se devuelve un codigo de error 1
				response = new DatagramPacket("1".getBytes(), 1, IPAddress, port);
			}	
		}
		catch(NumberFormatException ex){
			System.out.println("Dato Recibido erroneo ["+datoRecibido+"]");
		}
		catch(Exception e){
			System.out.println("Error inesperado");
		}
		
		// Envia la respuesta, que puede ser positiva o negativa
		try{
			socket.send(response);
		}
		catch(IOException e){
			System.out.println("Error al enviar la respuesta");
		}
	}
	
	private String resolverPedido( String pedido ){
		String nros = "0123456789";
		String salida = "";
		for ( int i = 0; i < pedido.length(); i++ ){
			if ( nros.indexOf(pedido.substring(i,i+1)) >= 0 ){
				salida = salida.concat(pedido.substring(i,i+1));
			}
		}
		return salida;
	}
}

