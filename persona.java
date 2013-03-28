/*
 * Clase que establece la estructura de la clase persona
 * proveida por el servidor centralServer
 */

import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
 
public class persona{
	private int ci;
	private String nombre, apellido, estado, fNac, estCivil, ciuNatal, ciuActual, profesion;
	private String fecha, hora, posicion;
	private byte[] cara;
	private byte[] perfil;
	private byte[] cuerpo;
	
	public persona(){
		this.ci = 0;
		this.nombre = null;
		this.apellido = null;
		this.estado = null;
		this.fNac = null;
		this.estCivil = null;
		this.ciuNatal = null;
		this.ciuActual = null;
		this.profesion = null;
		this.cara = null;
		this.perfil = null;
		this.cuerpo = null;
	}
	
	public persona(infoPersonal InfoPersonal, imagenes Imagenes, posicion Posicion){
	
		// Constructor primario
		this ();
	
		try{
			this.ci = InfoPersonal.getCi();
			this.nombre = InfoPersonal.getNombre();
			this.apellido = InfoPersonal.getApellido();
			this.estado = InfoPersonal.getApellido();
			this.fNac = InfoPersonal.getFNac();
			this.estCivil = InfoPersonal.getEstado();
			this.ciuNatal = InfoPersonal.getCiuNatal();
			this.ciuActual = InfoPersonal.getCiuActual();
			this.profesion = InfoPersonal.getProfesion();
			
			if ( Posicion.getFecha() != null ){
				this.fecha = Posicion.getFecha();
				this.hora = Posicion.getHora();
				this.posicion = Posicion.getPosicion();
			}			

			if ( Imagenes.getCara() != null ){
				this.cara = new byte[Imagenes.getCara().length];
				System.arraycopy( Imagenes.getCara(), 0, this.cara, 0, Imagenes.getCara().length );
			}
			if ( Imagenes.getPerfil() != null ){
				this.perfil = new byte[Imagenes.getPerfil().length];
				System.arraycopy( Imagenes.getPerfil(), 0, this.perfil, 0, Imagenes.getPerfil().length );
			}
			if ( Imagenes.getCuerpo() != null ){
				this.cuerpo = new byte[Imagenes.getCuerpo().length];
				System.arraycopy( Imagenes.getCuerpo(), 0, this.cuerpo, 0, Imagenes.getCuerpo().length );
			}
		} catch( NullPointerException e ){
			System.out.println("\t\tNullPointerException"+e.getMessage());
		}
	}
	

	
	public String toJson(){
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
	
     public byte[] getCara(){
		return cara;
     }
     
     public byte[] getPerfil(){
    		return perfil;
     }
     
     public byte[] getCuerpo(){
    		return cuerpo;
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
}
