package ejem1;


import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType; // javax.ws.rs.core.MediaType;

@Path("/Persona") // la URI
public class GestionaPersona {

    public static Persona persona = new Persona(1, "nicole", true, "femenino");

    // Un método leer que retornará una persona en formato XML o JSON
    @GET
   @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Persona leer() { 
        return persona; 
    }

    // Un método guardar, en el atributo de clase persona, que guarda una persona
    // pasada como parámetro en formato XML o JSON
    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public void Guardar(Persona personaGuardar) {
        persona = personaGuardar;
    }

}
