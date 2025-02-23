package ejem1;

import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/personas")
public class Personas {

    /**
     * ¿Cuándo usar GET?
     * Para consultar datos.
     * Ejemplo: Obtener un listado de usuarios,
     * obtener detalles de un producto, obtener
     * información de una categoría, etc.
     * 
     * ¿Cuándo usar POST?
     * Para crear un nuevo recurso.
     * Para enviar datos que van a ser procesados por el servidor.
     * Ejemplo: Crear un nuevo usuario, agregar un producto a una base de datos,
     * enviar un formulario de contacto, etc.
     *
     */

    /**
     * La clase Response se usa para devolver repsuesta HTTP personalizadas
     * en vez de devolver un arrayList con Response :
     * - especifica el codigo estado HTTP
     * - enviar un mensaje o contenido personalizado
     * - controlar mejor los errores en las respuestas
     * 
     */
    public static ArrayList<Persona> personas = new ArrayList<>();

    @POST
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/guardar")
    public Response guardar(Persona persona) {
        personas.add(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();
        // devolvemos un objeto jakarta.ws.rs.core.Response , todos los errores
        // disponibles de http
        // estan contenidos en ese objeto

    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/listar")
    public Response listar() {
        personas.add(new Persona(1, "Nicole", true, "femenino"));
    //Para el xml
        return Response.ok(new GenericEntity<List<Persona>>(personas) {
        }).build();
    }

    // Con path param -> me permite extraer paramtros de una uri
    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ver(@PathParam("name") String name) {
        personas.add(new Persona(1, "nicole", true, "femenino"));
        personas.add(new Persona(2, "javi", false, "masculino"));
        ArrayList<Persona> personasConNombre = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona.getNombre().equalsIgnoreCase(name)) {
                personasConNombre.add(persona);
            }
        }
        return Response.ok(personasConNombre).build();
    }

    // ejercicio 3 apartado 4
    // http://localhost:8080/tema5maven/rest/personas/buscar?nombre=ja
    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verConQueryParam(@DefaultValue("nic") @QueryParam("nombre") String nombre) {
        // muestre las personas q trgan
        personas.add(new Persona(1, "nicole", true, "femenino"));
        personas.add(new Persona(3, "nicole nicki", false, "femenino"));
        personas.add(new Persona(4, "sofi", true, "femenino"));
        personas.add(new Persona(2, "javi", false, "masculino"));
        ArrayList<Persona> personasConNombre = new ArrayList<>();
        for (Persona persona : personas) {
            if (persona.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                personasConNombre.add(persona);
            }
        }
        return Response.ok(personasConNombre).build();
    }

    /**
     * Crea un método que permita insertar personas en el ArrayList personas
     * mediante el formulario anterior
     * 
     * @param id
     * @param nombre
     * @param casado
     * @param sexo
     */
    @POST
    @Path("/insertar")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersona(@FormParam("id") int id, @FormParam("nombre") String nombre,
            @FormParam("casado") boolean casado, @FormParam("sexo") String sexo) {
        Persona persona = new Persona(id, nombre, casado, sexo);
        personas.add(persona);
        return Response.status(Response.Status.CREATED).entity(persona).build();

    }

    /**
     * Crea, en el path add, un método que permita insertar varias personas de forma
     * simultánea en el ArrayList personas
     * 
     * @param nuevasPersonas
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/add")
    public Response guardarPersonas(ArrayList<Persona> nuevasPersonas) {
        for (int i = 0; i < nuevasPersonas.size(); i++) {
            guardar(nuevasPersonas.get(i));
        }
        return Response.status(Response.Status.CREATED).entity(nuevasPersonas).build();
    }

    // Crea un método, que pasándole en el path id una persona permita borrarla del
    // ArrayList personas
    @GET
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Path("/{id}")

    public Response eliminarPersona(@PathParam("id") int id) {
        for (int i = personas.size() - 1; i == 0; i--) {
            if (personas.get(i).getId() == id) {
                personas.remove(i);
            }
        }
        return Response.status(Response.Status.NO_CONTENT).build();

    }

    // ejercicio 3, apartado 9 Modifica el ejercicio del punto 4 para que los
    // parámetros de la query tengan
    // valores por defecto
    // con @DefaultValue

    /**
     * Ejercicio 3 , apartado 10 ,
     * Crea un método, en el path XML, que devuelva los datos de una persona
     * indicada en el path en XML y en JSON siendo el id un atributo de persona.
     * ¿Qué
     * diferencias encuentras entre la representación obtenida en XML y en JSON?
     */

    // param
    @GET
    @Path("/xml")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response obtenerPersona(@QueryParam("id") int id) {
        Persona persona = null;
        personas.add(new Persona(1, "nicole", true, "femenino"));
        personas.add(new Persona(3, "nicole nicki", false, "femenino"));
        // Buscar persona por id
        for (Persona p : personas) {
            if (p.getId() == id) {
                return Response.ok(p).build();
            }
        }

        return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();

    }

    // los atributos devueltos deben estar en gallego
    // crear un metodo en el path galego que realice esta accion+
    /**
     * para realizarlo, vamos a la clase persona y ponemos la
     * anotacion @xmlAttribute
     */

    @POST
    @Path("/galego")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response devolverPeronaGallego(Persona persona) {

        PersonaGallego persona2 = new PersonaGallego(persona.getId(), persona.getNombre(), persona.isCasado(),
                persona.getSexo());
        return Response.ok(persona2).build();
    }
    // apartado 12, modifica los ejercicios anteriores para que devuelvan el
    // Response adecuado

}
