/*
 * Clase que establece la estructura de la informacion personal
 * proveida por el servidor infoPersonalServer
 */
 
import java.util.Scanner;
 
public class infoPersonal{
	private int ci;
	private String nombre, apellido, estado, fNac, estCivil, ciuNatal, ciuActual, profesion;
	
	public infoPersonal( String datos ) throws NullPointerException{
		try{
			Scanner scan = new Scanner(datos).useDelimiter(";");
			ci = scan.nextInt();
			nombre = scan.next();
			apellido = scan.next();
			estado = scan.next();
			fNac = scan.next();
			estCivil = scan.next();
			ciuNatal = scan.next();
			ciuActual = scan.next();
			profesion = scan.next();
		}catch(NullPointerException e){
			System.out.println("Parametros vacios.");
			throw e;
		}
	}
	
	public int getCi(){
		return ci;
	}
	
	public String getNombre(){
		return nombre;
	}
	
	public String getApellido(){
		return apellido;
	}
	
	public String getEstado(){
		return estado;
	}
	
	public String getFNac(){
		return fNac;
	}
	
	public String getEstCivil(){
		return estCivil;
	}
	
	public String getCiuNatal(){
		return ciuNatal;
	}
	
	public String getCiuActual(){
		return ciuActual;
	}
	
	public String getProfesion(){
		return profesion;
	}
	
	public void setCi(int dato){
		ci = dato;
	}
	
	public void setNombre(String dato){
		nombre = dato;
	}
	
	public void setApellido(String dato){
		
	}
	
	public void setEstado(String dato){
		estado = dato;
	}
	
	public void setFNac(String dato){
		fNac = dato;
	}
	
	public void setEstCivil(String dato){
		estCivil = dato;
	}
	
	public void setCiuNatal(String dato){
		ciuNatal = dato;
	}
	
	public void setCiuActual(String dato){
		ciuActual = dato;
	}
	
	public void setProfesion(String dato){
		profesion = dato;
	}
	
	public String toString(){
		return (Integer.toString(ci)+';'+nombre+';'+apellido+';'+estado+';'+fNac+';'+estCivil+';'+ciuNatal+';'+ciuActual+';'+profesion);
	}
}
