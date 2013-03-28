import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;


public class imagenes {
    private byte[] cara;
    private byte[] perfil;
    private byte[] cuerpo;

    public imagenes()
    {
        this.cara = null;
        this.perfil = null;
        this.cuerpo = null;
    }
    
    public void setImagenes(String cedulaIdentidad)
    {
        try{
     	   this.cara = byteArrayImagen("BDimagenes/"+cedulaIdentidad+"_1.jpg");
    	 	   this.perfil = byteArrayImagen("BDimagenes/"+cedulaIdentidad+"_2.jpg");
	        this.cuerpo = byteArrayImagen("BDimagenes/"+cedulaIdentidad+"_3.jpg");        
        } catch (FileNotFoundException e) {
        		 System.out.println("\t\tNo se encuentran registros en el servidor para "+cedulaIdentidad);
        } catch (IOException e) {
        		 System.out.println("\t\tErrores de entrada/salida");
        }
    }

    public byte[] byteArrayImagen(String imagen) throws FileNotFoundException, IOException{
        byte[] bytes = null;
        try {

            File file = new File(imagen);
            
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int totalBytes=0;
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum); 
                    totalBytes+=readNum;
                }
            } catch (IOException ex) {
                Logger.getLogger(imagenes.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //bytes es el ByteArray que retornamos
            bytes = bos.toByteArray();
	   // Lanza los errores a setImagenes()
        } catch (FileNotFoundException e) {
        		 throw e;
        } catch (IOException e) {
        		 throw e;
        }
        
        return bytes;
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
    
    public void byteImagenArray(String cedula) {
        try{
            File newfile1= new File("BDimagenes/"+cedula+"_1.jpg");
            BufferedImage imag=ImageIO.read(new ByteArrayInputStream(this.cara));
            ImageIO.write(imag, "jpg", newfile1);
            File newfile2 = new File("BDimagenes/"+cedula+"_2.jpg");
            imag=ImageIO.read(new ByteArrayInputStream(this.perfil));
            ImageIO.write(imag, "jpg", newfile2);
            File newfile3 = new File("BDimagenes/"+cedula+"_3.jpg");
            imag=ImageIO.read(new ByteArrayInputStream(this.cuerpo));
            ImageIO.write(imag, "jpg", newfile3);
        } catch (FileNotFoundException e ){
        	  System.out.println("\t\tNo se encuentran registros en el servidor para "+cedula);
        } catch(IOException ex){
        	  System.out.println("\t\tErrores de entrada/salida");
            Logger.getLogger(imagenes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
