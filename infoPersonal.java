/*
 * Clase que establece la estructura de la informacion personal
 * proveida por el servidor infoPersonalServer
 */

import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
 
public class infoPersonal{
	private int ci;
	private String nombre, apellido, estado, fNac, estCivil, ciuNatal, ciuActual, profesion;
	
	public infoPersonal( ){
		this.ci = 0;
		this.nombre = null;
		this.apellido = null;
		this.estado = null;
		this.fNac = null;
		this.estCivil = null;
		this.ciuNatal = null;
		this.ciuActual = null;
		this.profesion = null;
	}
	
	public infoPersonal( String datos ) throws NullPointerException{
		try{
			Scanner scan = new Scanner(datos).useDelimiter(";");
			this.ci = scan.nextInt();
			this.nombre = scan.next();
			this.apellido = scan.next();
			this.estado = scan.next();
			this.fNac = scan.next();
			this.estCivil = scan.next();
			this.ciuNatal = scan.next();
			this.ciuActual = scan.next();
			this.profesion = scan.next();
		}catch(NullPointerException e){
			System.out.println("\t\tParametros vacios.");
			throw e;
		}
	}
	
	public String toJSON(){
		Gson gson = new Gson();
		return ( gson.toJson(this) );
	}
	
	public int getCi(){
		return this.ci;
	}
	
	public String getNombre(){
		return this.nombre;
	}
	
	public String getApellido(){
		return this.apellido;
	}
	
	public String getEstado(){
		return this.estado;
	}
	
	public String getFNac(){
		return this.fNac;
	}
	
	public String getEstCivil(){
		return this.estCivil;
	}
	
	public String getCiuNatal(){
		return this.ciuNatal;
	}
	
	public String getCiuActual(){
		return this.ciuActual;
	}
	
	public String getProfesion(){
		return this.profesion;
	}
	
	public void setCi(int dato){
		this.ci = dato;
	}
	
	public void setNombre(String dato){
		this.nombre = dato;
	}
	
	public void setApellido(String dato){
		this.apellido = dato;
	}
	
	public void setEstado(String dato){
		this.estado = dato;
	}
	
	public void setFNac(String dato){
		this.fNac = dato;
	}
	
	public void setEstCivil(String dato){
		this.estCivil = dato;
	}
	
	public void setCiuNatal(String dato){
		this.ciuNatal = dato;
	}
	
	public void setCiuActual(String dato){
		this.ciuActual = dato;
	}
	
	public void setProfesion(String dato){
		this.profesion = dato;
	}
	
	public String toString(){
		return (Integer.toString(this.ci)+';'+this.nombre+';'+this.apellido+';'+this.estado+';'+this.fNac+';'+this.estCivil+';'+this.ciuNatal+';'+this.ciuActual+';'+this.profesion);
	}
}
