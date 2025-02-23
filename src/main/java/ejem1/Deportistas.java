package ejem1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/deportistas")
public class Deportistas {

    Connection mysqlConnection;
    ArrayList<Deportista> deportistas = new ArrayList<>();

    public void abrirConexionMySQL(String bd, String servidor, String usuario,
            String password) throws ClassNotFoundException {
        try {
            String url = String.format("jdbc:mariadb://%s:3306/%s", servidor, bd);
            // Establecemos la conexión con la BD
            Class.forName("org.mariadb.jdbc.Driver");
            this.mysqlConnection = DriverManager.getConnection(url, usuario, password);
            if (this.mysqlConnection != null) {
                System.out.println("Conectado a " + bd + " en " + servidor);
            } else {
                System.out.println("No conectado a " + bd + " en " + servidor);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getLocalizedMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("Código error: " + e.getErrorCode());
        }
    }

    public void fillArray() throws ClassNotFoundException {
        deportistas.clear();
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");
        try (Statement statement = this.mysqlConnection.createStatement()) {
            String query = "select * from deportistas";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {

                Deportista deportista = new Deportista(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(5), resultSet.getBoolean(3), resultSet.getString(3));
                deportistas.add(deportista);
            }

        } catch (

        SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Devuelve un listado con todos los deportistas del sistema
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/")
    public Response todos() throws ClassNotFoundException {
        fillArray();
        return Response.ok(new GenericEntity<List<Deportista>>(deportistas) {
        }).build();
    }

    /*
     * devuelve la informacion relativa al deportista id
     * 
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/{id}")
    public Response buscaJugador(@PathParam("id") int id) throws ClassNotFoundException {
        fillArray();

        for (Deportista deportista : deportistas) {
            if (deportista.getId() == id) {
                return Response.ok(deportista).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
    }

    /**
     * Lista los deportistas de un deporte
     * 
     * @throws ClassNotFoundException
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/deporte/{nombreDeporte}")
    public Response porDeporte(@PathParam("nombreDeporte") String nombreDeporte) throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasConDeporte = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (deportista.getDeporte().equalsIgnoreCase(nombreDeporte)) {
                deportistasConDeporte.add(deportista);
            }
        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasConDeporte) {
        }).build();
    }

    /*
     * Lista los deportistas activos
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/activos")
    public Response activos() throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasActivos = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (deportista.isActivo()) {
                deportistasActivos.add(deportista);
            }
        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasActivos) {
        }).build();
    }

    /*
     * Lista los deportistas retirados
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/retirados")
    public Response retirados() throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasRetirados = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (!deportista.isActivo()) {
                deportistasRetirados.add(deportista);
            }
        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasRetirados) {
        }).build();
    }

    /*
     * lista los deportistas masculinos
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/masculinos")
    public Response masculinos() throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasMasculinos = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (deportista.getGenero().equalsIgnoreCase("masculino")) {
                deportistasMasculinos.add(deportista);
            }

        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasMasculinos) {
        }).build();
    }

    /*
     * lista los deportistas femeninos
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/femeninos")
    public Response femeninos() throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasFemeninos = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (deportista.getGenero().equalsIgnoreCase("femeninos")) {
                deportistasFemeninos.add(deportista);
            }

        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasFemeninos) {
        }).build();
    }

    /*
     * Deportes por genero (/xg): Lista un array con dos elementos: uno con todos
     * los deportistas masculinos y otro con todos los deportistas femeninos
     * pendiente de hacer arrayy dentro de ptro
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/xg")
    public Response deportesPorGenero() throws ClassNotFoundException {

        fillArray();

        ArrayList<Deportista> deportistasFemeninos = new ArrayList<>();
        ArrayList<Deportista> deportistasMasculinos = new ArrayList<>();

        for (Deportista deportista : deportistas) {
            if (deportista.getGenero().equalsIgnoreCase("femenino")) {
                deportistasFemeninos.add(deportista);
            } else {
                deportistasMasculinos.add(deportista);
            }
        }
        deportistas.clear();
        deportistas = deportistasFemeninos;
        for (Deportista deportista : deportistasMasculinos) {
            deportistas.add(deportista);
        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistas) {
        }).build();
    }

    /**
     * lista los deportistaas activos de un deporte
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/deporte/{nombreDeporte}/activos")
    public Response activosDeUnDeporte(@PathParam("nombreDeporte") String nombreDeporte) throws ClassNotFoundException {
        fillArray();
        ArrayList<Deportista> deportistasActivos = new ArrayList<>();
        for (Deportista deportista : deportistas) {
            if (deportista.getDeporte().equalsIgnoreCase(nombreDeporte) && deportista.isActivo()) {
                deportistasActivos.add(deportista);
            }
        }
        return Response.ok(new GenericEntity<List<Deportista>>(deportistasActivos) {
        }).build();
    }

    /**
     * Cuenta el numero de deportistas distintos
     * 
     * @throws ClassNotFoundException
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/sdepor")
    public Response contarDeportistas() throws ClassNotFoundException {

        fillArray();

        return Response.ok(deportistas.size()).build();
    }

    /**
     * Listar los deportes existentes ordenados alfabeticamente sin repiticiones
     * 
     */
    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/deportes")
    public Response listarDeportes() throws ClassNotFoundException {

        ArrayList<String> deportes = new ArrayList<>();
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");

        try (Statement statement = mysqlConnection.createStatement()) {

            String query = "select distinct deporte from deportistas order by deporte asc";
            ResultSet result = statement.executeQuery(query);
            while (result.next()) {
                deportes.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Response.ok(new GenericEntity<List<String>>(deportes) {
        }).build();
    }

    /**
     * Añade un deportista en el sistema.
     * Se recibe el objeto deportista por el body de la petición y se inserta en la
     * BD.
     *
     * @throws SQLException
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/deportista/crear")
    public Response crearDeportista(Deportista deportista) throws ClassNotFoundException, SQLException {

        // Inicializa y abre la conexión (métodos propios)
        fillArray();
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");

        // Crea un statement para ejecutar la consulta SQL
        Statement statement = mysqlConnection.createStatement();

        // Construye la consulta INSERT.
        // Nota: Se agregan comillas simples para los valores tipo String y se convierte
        // el boolean a entero (1 o 0)
        String query = String.format(
                "INSERT INTO deportistas ( nombre, activo, genero, deporte) VALUES ( '%s', %d, '%s', '%s')",

                deportista.getNombre(),
                (deportista.isActivo() ? 1 : 0),
                deportista.getGenero(),
                deportista.getDeporte());

        // Ejecuta la consulta de actualización (para INSERT, UPDATE o DELETE se usa
        // executeUpdate)
        int rowsAffected = statement.executeUpdate(query);

        // Verifica si se insertó correctamente el deportista
        if (rowsAffected > 0) {
            deportistas.add(deportista); // Agrega el deportista a la lista local (si es que se utiliza)
            return Response.status(Response.Status.CREATED).entity(deportista).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error al insertar deportista\"}")
                    .build();
        }
    }

    /**
     * Añade un deportista mediante un formulario
     */
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/crear/form")
    public Response crearDeportistaConForm(@FormParam("id") int id, @FormParam("nombre") String nombre,
            @FormParam("deporte") String deporte, @FormParam("activo") boolean activo, @FormParam("sexo") String genero)
            throws ClassNotFoundException, SQLException {
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");
        fillArray();

        Deportista deportista = new Deportista(id, nombre, deporte, activo, genero);

        Statement statement = mysqlConnection.createStatement();
        String query = String.format(
                "INSERT INTO deportistas ( nombre, activo, genero, deporte) VALUES ( '%s', %d, '%s', '%s')",
                deportista.getNombre(),
                (deportista.isActivo() ? 1 : 0),
                deportista.getGenero(),
                deportista.getDeporte());
        int rowsAffected = statement.executeUpdate(query);

        deportistas.add(deportista);
        if (rowsAffected > 0) {
            deportistas.add(deportista);
            return Response.status(Response.Status.CREATED).entity(deportista).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error al insertar deportista\"}")
                    .build();
        }
    }

    /**
     * Crea deportistas en el sistema.
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("/adds")
    public Response crearDeportistas(List<Deportista> deportistas2) throws ClassNotFoundException, SQLException {
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");

        List<Deportista> deportistasInsertados = new ArrayList<>();

        for (Deportista deportista : deportistas2) {
            Statement statement = mysqlConnection.createStatement();
            String query = String.format(
                    "INSERT INTO deportistas (nombre, activo, genero, deporte) VALUES ('%s', %d, '%s', '%s')",
                    deportista.getNombre(),
                    (deportista.isActivo() ? 1 : 0),
                    deportista.getGenero(),
                    deportista.getDeporte());

            int rowsAffected = statement.executeUpdate(query);
            if (rowsAffected > 0) {
                deportistasInsertados.add(deportista);
            }
        }

        // Si se insertó al menos un deportista, se devuelve 201 (Created) con la lista
        // de deportistas insertados.
        if (!deportistasInsertados.isEmpty()) {
            return Response.status(Response.Status.CREATED)
                    .entity(deportistasInsertados)
                    .build();
        } else {
            // Si ninguno se insertó, se devuelve un error 500.
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"mensaje\": \"Error al insertar deportistas\"}")
                    .build();
        }
    }

    /**
     * Actualiza la informacion relativa a un deportista
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/actualizar")
    public Response actualizarDeportista(Deportista deportista) throws SQLException, ClassNotFoundException {
        // si ese id existe lo que hago es cmabiarle valor a ese dportista
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");
        // Se recomienda usar PreparedStatement para evitar inyección SQL y problemas
        // con comillas.
        String updateQuery = "UPDATE deportistas SET nombre = ?, activo = ?, genero = ?, deporte = ? WHERE id = ?";
        PreparedStatement preparedStatement = mysqlConnection.prepareStatement(updateQuery);
        preparedStatement.setString(1, deportista.getNombre());
        preparedStatement.setInt(2, deportista.isActivo() ? 1 : 0);
        preparedStatement.setString(3, deportista.getGenero());
        preparedStatement.setString(4, deportista.getDeporte());
        preparedStatement.setInt(5, deportista.getId());

        int rowsAffected = preparedStatement.executeUpdate();

        if (rowsAffected > 0) {
            return Response.ok(deportista).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"mensaje\": \"Deportista no encontrado o no actualizado\"}")
                    .build();
        }
    }

    /**
     * elimina la informacion relativa a un deportista dado su id
     * 
     */
    @DELETE
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Path("del/{id}")
    public Response eliminarDeportista(@PathParam("id") int id) throws SQLException, ClassNotFoundException {
        abrirConexionMySQL("ad_tema6", "localhost", "root", "");

        String queryPrepared = "DELETE FROM DEPORTISTAS WHERE id=?";
        PreparedStatement preparedStatement = mysqlConnection.prepareStatement(queryPrepared);
        preparedStatement.setInt(1, id);

        int rowsAffected = preparedStatement.executeUpdate();
        return Response.ok().build();
    }

    /**
     * 
     * muestra la imagen num del deportista id como image/jpg
     */
    @GET
    @Produces("image/jpg")
    @Path("img/{id}/{num}")
    public Response imagenDeportista(@PathParam("id") int id, @PathParam("num") int num)
            throws SQLException, ClassNotFoundException {

        abrirConexionMySQL("ad_tema6", "localhost", "root", "");

        String queryPrepared = "select * from imagenes where id=?";
        PreparedStatement preparedStatement = mysqlConnection.prepareStatement(queryPrepared);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            if (resultSet.getString("nombre").split("_")[1].equals(((Integer) num).toString())) {
                String imagePath = resultSet.getString("path").replace("./", "") + "/" + resultSet.getString("nombre");
                System.out.println(imagePath);
                File file = new File(imagePath);
                System.out.println(file.getAbsolutePath());
                try {
                    return Response.ok(new FileInputStream(file)).build();
                } catch (FileNotFoundException e) {
                    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                            .entity("Error al cargar la imagen").type(MediaType.TEXT_PLAIN).build();
                }
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    /**
     * Muestra el nombre y las imagenes del deportista como html
     * 
     */

    @GET
    @Produces("imagen/jpg")
    @Path("/img/{id}")
    public Response imagenesDeportistas(@PathParam("id") int id) {

        
        return Response.ok().build();
    }
}
