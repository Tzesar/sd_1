/*
 * Simula una conexion con una base de datos, realmente trabaja con un archivo
 * de acceso aleatorio.
*/


//package infoPersonal;

import java.io.*;
import java.util.Scanner;

public class DBConnector{

	private RandomAccessFile in;
	
	public DBConnector ( String filename ) throws Exception{
		try{
			// Abre el archivo llamado filename
			in = new RandomAccessFile(filename,"r");
		}
		catch(FileNotFoundException e){
			// Excepciones de apertura del archivo
			System.out.println("\t\tNo se pudo abrir crear la conexion con " + filename);
		}
		catch(SecurityException e){
			// Excepciones no contempladas
			System.out.println("\t\tNo se tienen permisos para leer el archivo " + filename);
		}
		catch(Exception e){
			throw e;
		}
		
	}
	
	public String getLine(){
		String s;
		try{
			s = in.readLine();
		}
		catch(IOException e){
			throw new RuntimeException("\t\tError al leer la linea.");
		}
		return s;
	}
	
	public void seek(long pos){
		/* pos es la cantidad de bits que se va a mover desde el inicio
		 * del archivo
		 */
		try{
			in.seek(pos);
		}catch(IOException e){
			throw new RuntimeException("\t\tError en seek().");
		}
	}
	
	public String buscar(int ci){
		String match, s;
		Scanner scan;
		int buscado;
		match = null;
		s = null;
		
		// Mueve el puntero del archivo al comienzo y lee la primera linea
		seek(0);
		s = this.getLine();
		
		while( match == null && s != null ){
			// Crea el objeto scanner que obtiene los tokens del string s
			scan = new Scanner(s).useDelimiter(";");
			buscado = scan.nextInt();
			// Verifica la ci leida con la buscada
			if ( buscado == ci ){
				match = s;
			}
			// Lee la sgte linea
			s = this.getLine();
			scan.close();
		}
		
		return match;
	}
	
	public String buscar(int ci, int lineSize){
		String match, s;
		Scanner scan;
		int buscado;
		long fileSize;
		//match = null;
		s = null;
		
		try{
			fileSize = this.in.length()-1;
			//System.out.println("archivo:"+fileSize);
		}
		catch( IOException e){
			e.printStackTrace();
		}
		
		// Mueve el puntero del archivo al comienzo y lee la primera linea
		this.seek(0);
		
		int inicio = 0;
		int fin = 200;
		int pos;
		while (inicio <= fin) {
			pos = (inicio+fin) / 2;
			//System.out.println("pos:"+pos);
			this.seek(pos*lineSize);
			
			s = this.getLine();
			System.out.println("linea:"+s);
			scan = new Scanner(s).useDelimiter(";");
			buscado = scan.nextInt();
			
			System.out.println("Buscado:"+buscado);
			

			if ( buscado == ci )
				return s;
			else if ( buscado < ci ) {
				inicio = pos+1;
			} else {
				fin = pos-1;
			}
		}
		return "";
		
		/*while( match == null && s != null ){
			// Crea el objeto scanner que obtiene los tokens del string s
			scan = new Scanner(s).useDelimiter(";");
			buscado = scan.nextInt();
			// Verifica la ci leida con la buscada
			if ( buscado == ci ){
				match = s;
			}
			// Lee la sgte linea
			s = this.getLine();
			scan.close();
		}
		return match;
		*/
		

	}
	
	public void close(){
		try{
			in.close();
			System.out.println("\tConexion terminada.");
		}
		catch(IOException e){
			throw new RuntimeException("\tError al cerrar la conexion.");
		}
	}
	
	public long length(){
		try{
			return in.length();
		}
		catch(IOException e){
			throw new RuntimeException("\tError al acceder al archivo");
		}
	}
}
