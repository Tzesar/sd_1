/*
 * Clase que establece la estructura de la informacion de posicion
 * proveida por el servidor posicionServer
 */

import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
 
public class posicion{
	private int ci;
	private String fecha, hora, posicion;
	
	public posicion(){
		this.ci = 0;
		this.fecha = null;
		this.hora = null;
		this.posicion = null;
	}
	
	public posicion(String fecha, String hora, String posicion){
		this.fecha = fecha;
		this.hora = hora;
		this.posicion = posicion;
	}

	public String toJSON(){
		Gson gson = new Gson();
		return ( gson.toJson(this) );
	}
	public String getFecha(){
		return this.fecha;
	}
	
	public String getHora(){
		return this.hora;
	}
	
	public String getPosicion(){
		return this.posicion;
	}
	
	public void setFecha(String fecha){
		this.fecha = fecha;
	}
	
	public void setHora(String hora){
		this.hora = hora;
	}
	
	public void setPosicion(String dato){
		this.posicion = dato;
	}
	
	public String toString(){
		return (this.fecha+';'+this.hora+';'+this.posicion+';');
	}
}
