import java.io.*;
import java.util.Scanner;

public class DBConnector{

	private RandomAccessFile in;
	
	public DBConnector ( String filename ) throws Exception {
		try{
			// Abre el archivo llamado filename
			in = new RandomAccessFile(filename,"r");
		}catch(FileNotFoundException e){
			// Excepciones de apertura del archivo
			System.out.println("No se pudo abrir crear la conexion con " + filename);
			throw e;
		}catch(SecurityException e){
			// Excepciones no contempladas
			System.out.println("No se tienen permisos para leer el archivo " + filename);
			throw e;
		}catch(Exception e){
			throw e;
		}
		
	}
	
	public String getLine(){
		String s;
		try{
			s = in.readLine();
		}
		catch(IOException e){
			throw new RuntimeException("Error al leer la linea.");
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
			throw new RuntimeException("Error en seek().");
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
		s = getLine();
		
		while( match == null && s != null ){
			// Crea el objeto scanner que obtiene los tokens del string s
			scan = new Scanner(s).useDelimiter(";");
			buscado = scan.nextInt();
			// Verifica la ci leida con la buscada
			if ( buscado == ci ){
				match = s;
			}
			// Lee la sgte linea
			s = getLine();
			scan.close();
		}
		
		return match;
	}
	
	public void close(){
		try{
			in.close();
			System.out.println("Conexion terminada.");
		}
		catch(IOException e){
			throw new RuntimeException("Error al cerrar la conexion.");
		}
	}
}
