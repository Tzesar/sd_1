/**
*
* @author Andrés Pontt
*/
public class Persona {
    private String nombre;
    private String apellido;
    private int edad;
/**
* El constructor es obligatorio si queremos deserializar correctamente el response JSON
*/
    public Persona(){
        
    }
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public int getEdad() {
        return edad;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }

}