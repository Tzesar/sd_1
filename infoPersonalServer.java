/*
 * Servidor de informacion personal UDP
 */
 
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

public class infoPersonalServer{
	public static void main( String[] args ) throws Exception{
		DBConnector db = new DBConnector("datos.bd");
		String busqueda;
		infoPersonal datosPersonales;
		
		busqueda = db.buscar(3968344);
		if ( busqueda != null ){
			datosPersonales = new infoPersonal(busqueda);
			System.out.println(datosPersonales.toString());
		}
		
		busqueda = db.buscar(3968345);
		if ( busqueda != null ){
			datosPersonales = new infoPersonal(busqueda);
			System.out.println(datosPersonales.toString());
		}
	}
}

