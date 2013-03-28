/*
 * Hilo que atiende los pedidos de busqueda de datos personales
*/

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
		System.out.println("\tHilo iniciado");
		
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
		sendData = ("2;0;").getBytes();
		response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		
		try{
			int ci = Integer.parseInt(datoRecibido);
			
			// Crea la conexion con la base de datos
			DBConnector db = new DBConnector("BD/Datos.bd");
			String busqueda;
			infoPersonal datosPersonales;
			
			// Busca la persona por CI
			busqueda = db.buscar(ci);
			
			// Si existen datos sobre la persona
			if ( busqueda != null ){
				// Crea el objeto infoPersonal
				datosPersonales = new infoPersonal(busqueda);
				
				// Guarda los datos correctos en sendData y prepara el paquete de respuesta
				String datosEnJSON = datosPersonales.toJSON();
				// Formato de paquete respuesta
				// <TIPO_DE_PAQUETE>;<LONGITUD_DE_LOS_DATOS>;<DATOS_EN_FORMATO_JSON>
				// TIPO_DE_PAQUETE puede tomar los valores:
				//  0 = paquete normal
				//  1 = paquete sin datos (no se encontro el registro correspondiente al pedido recibido)
				//  2 = paquete informativo (ocurrio un error inesperado)
				sendData = ("0;"+datosEnJSON.length()+";"+ datosEnJSON ).getBytes();
				response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			}else{
				// Si no se encuentran los datos se devuelve un codigo de error 1
				sendData = ("1;0;").getBytes();
				response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			}	
		}
		catch(NumberFormatException ex){
			System.out.println("\t\tDato Recibido erroneo ["+datoRecibido+"]");
		}
		catch(Exception e){
			System.out.println("\t\tError inesperado");
		}
		
		// Envia la respuesta, que puede ser positiva o negativa
		try{
			socket.send(response);
		}
		catch(IOException e){
			System.out.println("\t\tError al enviar la respuesta");
		}
		System.out.println("\tFinalizando hilo");
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
