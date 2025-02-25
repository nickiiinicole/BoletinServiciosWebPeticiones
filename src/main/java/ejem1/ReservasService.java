package ejem1;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservasService {
    private static List<Reserva> reservas = new ArrayList<>();

    // 1️⃣ Obtener todas las reservas
    @GET
    public List<Reserva> getReservas() {
        return reservas;
    }

    // 2️⃣ Obtener una reserva por ID
    @GET
    @Path("/{id}")
    public Response getReserva(@PathParam("id") int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                return Response.ok(r).build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada").build();
    }

    // 3️⃣ Crear una nueva reserva
    @POST
    public Response agregarReserva(Reserva reserva) {
        reserva.setEstado("activa");
        reservas.add(reserva);
        return Response.status(Response.Status.CREATED).entity("Reserva agregada con éxito").build();
    }

    // 4️⃣ Actualizar reserva (PUT)
    @PUT
    @Path("/{id}")
    public Response actualizarReserva(@PathParam("id") int id, Reserva reservaActualizada) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                r.setCliente(reservaActualizada.getCliente());
                r.setHabitacion(reservaActualizada.getHabitacion());
                r.setFechaEntrada(reservaActualizada.getFechaEntrada());
                r.setFechaSalida(reservaActualizada.getFechaSalida());
                return Response.ok("Reserva actualizada con éxito").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada").build();
    }

    // 5️⃣ Cancelar una reserva (DELETE)
    @DELETE
    @Path("/{id}")
    public Response cancelarReserva(@PathParam("id") int id) {
        for (Reserva r : reservas) {
            if (r.getId() == id) {
                r.setEstado("cancelada");
                return Response.ok("Reserva cancelada con éxito").build();
            }
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Reserva no encontrada").build();
    }

    // 6️⃣ Buscar reservas por cliente
    @GET
    @Path("/cliente/{nombre}")
    public List<Reserva> buscarPorCliente(@PathParam("nombre") String nombre) {
        return reservas.stream()
                .filter(r -> r.getCliente().equalsIgnoreCase(nombre))
                .collect(Collectors.toList());
    }

    // 7️⃣ Buscar reservas por fecha de entrada
    @GET
    @Path("/fecha")
    public List<Reserva> buscarPorFecha(@QueryParam("fechaEntrada") Date fecha) {
        return reservas.stream()
                .filter(r -> r.getFechaEntrada().equals(fecha))
                .collect(Collectors.toList());
    }
}
