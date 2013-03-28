/*
 * Hilo que atiende los pedidos de busqueda de Posiciones
*/

// Formato de paquete respuesta
// <TIPO_DE_PAQUETE>;<LONGITUD_DE_LOS_DATOS>;<DATOS_EN_FORMATO_JSON>
// TIPO_DE_PAQUETE puede tomar los valores:
//  0 = paquete normal
//  1 = paquete sin datos (no se encontro el registro correspondiente al pedido recibido)
//  2 = paquete informativo (ocurrio un error inesperado)


import java.io.*;
import java.net.*;
import java.lang.Integer;
import java.util.Scanner;
import java.awt.TextField;
import java.util.*;
import java.text.*;

public class posicionHilo extends Thread{
	private DatagramPacket request = null;
	private DatagramSocket socket;

	public posicionHilo(DatagramSocket socket, DatagramPacket request){
		super("posicionHilo");
		this.socket = socket;
		this.request = request;
	}
	
	// Codigo que ejecuta el hilo
	public void run(){
		System.out.println("\tHilo iniciado");
		
		byte[] sendData = new byte[250];
		DatagramPacket response;
		Scanner scan1, scan2;

		// Obtiene el dato recibido y verifica que sea un numero
		String datoRecibido = new String(request.getData());
		// Remueve caracteres innecesarios al final de la fila
		datoRecibido = resolverPedido(datoRecibido);
		System.out.println ("\t\tMensaje: "+datoRecibido);
		// Obtiene los datos del cliente
		// ya que siempre manda una respuesta
		InetAddress IPAddress = request.getAddress();
		int port = request.getPort();
		
		// Si los datos recibidos no son correctos devuelve un codigo de error 2
		// Este mensaje sera modificado con datos verdaderos
		sendData = ("2;0;").getBytes();
		response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
		posicion datosPosicion;

		try{
			int ci = Integer.parseInt(datoRecibido), encontro=0;
			
			// Crea la conexion con la base de datos
			DBConnector db = new DBConnector("BD/Telefonica1.bd");
			DBConnector db2 = new DBConnector ("BD/Telefonica2.bd");
			
			String busqueda1, busqueda2;
			posicion persona;
			persona = new posicion();
						
			// Busca la persona por CI
			busqueda1 = db.buscar(ci);
			busqueda2 = db2.buscar(ci);
				

			if (busqueda1!="null" && busqueda2!="null"){
				// Si existen datos sobre la persona
			
				String f1, f2, fecha, hora1, hora2, hora;

				if ( busqueda1 != null && busqueda2!=null){
					
					/* * * * * *
					aca compara los datos recibidos de las telefonicas
					* * * * * */
					scan1 = new Scanner(busqueda1).useDelimiter(";");
					scan2 = new Scanner(busqueda2).useDelimiter(";");
					int buscado1 = scan1.nextInt();
					int buscado2 = scan2.nextInt();
					
					f1 = scan1.next();
					f2 = scan2.next();
					hora1 = scan1.next();
					hora2 = scan2.next();
					fecha = ComparaFecha(f1, f2);
					hora = ComparaHora (hora1, hora2);
	
					if (fecha.compareTo(f1)==0 && fecha.compareTo(f2)!=0)
						persona= new posicion(f1,hora1,scan1.next());
					else if (fecha.compareTo(f2)==0 && fecha.compareTo(f1)!=0)
						persona=new posicion (f2,hora2,scan2.next());
					else{
						if (hora.compareTo(hora1)==0)		
							persona=new posicion(f1,hora1,scan1.next());
						else 
							persona=new posicion (f2,hora2,scan2.next());
	
					}				
	
				}else if ( busqueda1 != null){
					scan1 = new Scanner(busqueda1).useDelimiter(";");
					int buscado1 = scan1.nextInt();
					f1 = scan1.next();
					hora1 = scan1.next();
					persona=new posicion(f1,hora1,scan1.next());
				}
				else if ( busqueda2 != null){
					scan2 = new Scanner(busqueda2).useDelimiter(";");
					int buscado2 = scan2.nextInt();	
					f2 = scan2.next();
					hora2 = scan2.next();
					persona=new posicion (f2,hora2,scan2.next());
				}
				else{
					// Si no se encuentran los datos se devuelve un codigo de error 1
					sendData = ("1;0;").getBytes();
					encontro=1;
				}
			
				if (encontro==0){
					String datosEnJSON = persona.toJSON();
					sendData = ("0;"+datosEnJSON.length()+";"+ datosEnJSON ).getBytes();
				}
				response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			}
		} catch(NumberFormatException ex){
			System.out.println("\t\tDato Recibido erroneo ["+datoRecibido+"]");
		} catch(Exception e){
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

	private String ComparaFecha(String f1, String f2) throws ParseException {

		String DATE_FORMAT = "dd/MM/yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		Date c1= sdf.parse(f1);
		Date c2= sdf.parse(f2);

		if (c1.before(c2)) {
			return (f2);
		}
		if (c1.after(c2)) {
			return (f1);
		}
		if (c1.equals(c2)) {
			return (f1);
		}
		return ("error de fechas // posicionHilo // ComparaFecha");
	}


	private String ComparaHora(String horaInicial, String horaFinal) {
      
        	try {
                	DateFormat dateFormat = new SimpleDateFormat("HH:mm");
                	Date horaIni;
                	Date horaFin;
                	horaIni = dateFormat.parse(horaInicial);
                	horaFin = dateFormat.parse(horaFinal);
                	if (horaIni.compareTo(horaFin) < 0)
                	    return horaFinal;
                	else if (horaIni.compareTo(horaFin)>0)
                	    return horaInicial;
			else
				return horaInicial;
        	} catch (ParseException ex) {
            	System.out.println("\t\tPosee errores");
            	return "error de horas // posicionHilo // ComparaHora";	
		}
        }
}
