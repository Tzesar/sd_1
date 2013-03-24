import java.io.*;

public class usoDBConnector{
	public static void main( String[] args ) throws Exception{
		DBConnector db;
		try{
			db = new DBConnector("nuevosDatos.bd");
		}
		catch(Exception e){
			throw e;
		}
		System.out.println( db.buscar(Integer.parseInt(args[0])) );
		System.out.println( db.getLine().length() );
		System.out.println( db.length());
		System.out.println(db.length()/(long)db.getLine().length());
		
		int contador = 0;
		db.seek(0);
		while(db.getLine() != null ){
			contador++;
		}
		
		System.out.println("Lineas="+contador);
	}
}
