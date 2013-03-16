
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author CJDM and MC
 */

public class pruebaJSON {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Persona persona1 = new Persona();
        persona1.setNombre("Andres");
        persona1.setApellido("Pontt");
        persona1.setEdad(24);
        
        Gson gson = new Gson();
        String jsonOutput = gson.toJson(persona1);
        
        System.out.println(jsonOutput);
        
        Persona persona2 = new Persona();
        
        Type collectionType = new TypeToken<Persona>(){}.getType();
        
        persona2 = gson.fromJson(jsonOutput, collectionType);
    }
}
