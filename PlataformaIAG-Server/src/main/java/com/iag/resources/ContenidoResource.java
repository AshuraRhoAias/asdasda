package com.iag.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.iag.dao.ContenidoDAO;
import com.iag.models.Contenido;
import com.iag.utils.JWTUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/contenidos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContenidoResource {

    private static final Logger LOG = Logger.getLogger(ContenidoResource.class.getName());

    private final ContenidoDAO contenidoDAO = new ContenidoDAO();
    private final Gson gson = new Gson();

    /* ===========================
       GET
       =========================== */
    @GET
    public Response obtenerTodos(@QueryParam("estado") String estado) {
        LOG.info("GET /contenidos | estado=" + estado);

        try {
            List<Contenido> contenidos
                    = "publicado".equalsIgnoreCase(estado)
                    ? contenidoDAO.obtenerPublicados()
                    : contenidoDAO.obtenerTodos();

            LOG.info("Contenidos encontrados: " + contenidos.size());
            return Response.ok(gson.toJson(contenidos)).build();

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR obtenerTodos", e);
            return error500("Error al obtener contenidos", e);
        }
    }

    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") int id) {
        LOG.info("GET /contenidos/" + id);

        try {
            Contenido contenido = contenidoDAO.obtenerPorId(id);

            if (contenido == null) {
                LOG.warning("Contenido NO encontrado | id=" + id);
                return error404("Contenido no encontrado");
            }

            contenidoDAO.incrementarVistas(id);
            contenido.setVistas(contenido.getVistas() + 1);

            LOG.info("Contenido entregado | id=" + id);
            return Response.ok(gson.toJson(contenido)).build();

        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR obtenerPorId", e);
            return error500("Error al obtener contenido", e);
        }
    }

    /* ===========================
       POST
       =========================== */
    @POST
    public Response crear(@Context HttpHeaders headers, String body) {
        LOG.info("POST /contenidos");
        LOG.info("BODY RECIBIDO: " + body);

        try {
            AuthData auth = validarAdmin(headers);

            LOG.info("AUTORIZADO | userId=" + auth.userId + " | rol=" + auth.rol);

            JsonObject json = gson.fromJson(body, JsonObject.class);

            Contenido contenido = new Contenido();
            contenido.setTitulo(json.get("titulo").getAsString());
            contenido.setCuerpo(json.get("cuerpo").getAsString());
            contenido.setTipo(json.get("tipo").getAsString());
            contenido.setEstado(json.get("estado").getAsString());
            contenido.setAutorId(auth.userId);

            List<Integer> categoriasIds = gson.fromJson(
                    json.get("categorias"),
                    new TypeToken<List<Integer>>() {
                    }.getType()
            );

            LOG.info("CREANDO CONTENIDO | titulo=" + contenido.getTitulo());
            LOG.info("CATEGORIAS: " + categoriasIds);

            boolean creado = contenidoDAO.crear(contenido, categoriasIds);

            LOG.info("RESULTADO DAO.crear = " + creado);

            if (!creado) {
                return error500("Error al crear contenido", null);
            }

            return Response.status(Response.Status.CREATED)
                    .entity("{\"success\":true}")
                    .build();

        } catch (WebApplicationException e) {
            LOG.warning("DENEGADO: " + e.getResponse().getStatus());
            return e.getResponse();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR crear contenido", e);
            return error500("Error en el servidor", e);
        }
    }

    /* ===========================
       PUT
       =========================== */
    @PUT
    @Path("/{id}")
    public Response actualizar(@Context HttpHeaders headers,
            @PathParam("id") int id,
            String body) {

        LOG.info("PUT /contenidos/" + id);
        LOG.info("BODY RECIBIDO: " + body);

        try {
            validarAdmin(headers);

            JsonObject json = gson.fromJson(body, JsonObject.class);

            Contenido contenido = new Contenido();
            contenido.setId(id);
            contenido.setTitulo(json.get("titulo").getAsString());
            contenido.setCuerpo(json.get("cuerpo").getAsString());
            contenido.setTipo(json.get("tipo").getAsString());
            contenido.setEstado(json.get("estado").getAsString());

            List<Integer> categoriasIds = gson.fromJson(
                    json.get("categorias"),
                    new TypeToken<List<Integer>>() {
                    }.getType()
            );

            LOG.info("ACTUALIZANDO | id=" + id);
            LOG.info("CATEGORIAS: " + categoriasIds);

            boolean actualizado = contenidoDAO.actualizar(contenido, categoriasIds);

            LOG.info("RESULTADO DAO.actualizar = " + actualizado);

            if (!actualizado) {
                return error500("Error al actualizar contenido", null);
            }

            return Response.ok("{\"success\":true}").build();

        } catch (WebApplicationException e) {
            LOG.warning("DENEGADO PUT | status=" + e.getResponse().getStatus());
            return e.getResponse();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR actualizar", e);
            return error500("Error en el servidor", e);
        }
    }

    @GET
    @Path("/count")
    public Response contar(@Context HttpHeaders headers) {

        LOG.info("GET /contenidos/count");

        try {
            // 🔐 valida token ADMIN
            AuthData auth = validarAdmin(headers);
            LOG.info("AUTORIZADO COUNT | userId=" + auth.userId);

            int total = contenidoDAO.contarContenidos();

            JsonObject response = new JsonObject();
            response.addProperty("success", true);
            response.addProperty("total", total);

            LOG.info("TOTAL CONTENIDOS = " + total);

            return Response.ok(gson.toJson(response)).build();

        } catch (WebApplicationException e) {
            LOG.warning("DENEGADO COUNT | status=" + e.getResponse().getStatus());
            return e.getResponse();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR contar contenidos", e);
            return error500("Error al contar contenidos", e);
        }
    }


    /* ===========================
       DELETE
       =========================== */
    @DELETE
    @Path("/{id}")
    public Response eliminar(@Context HttpHeaders headers,
            @PathParam("id") int id) {

        LOG.info("DELETE /contenidos/" + id);

        try {
            validarAdmin(headers);

            boolean eliminado = contenidoDAO.eliminar(id);

            LOG.info("RESULTADO DAO.eliminar = " + eliminado);

            if (!eliminado) {
                return error500("Error al eliminar contenido", null);
            }

            return Response.ok("{\"success\":true}").build();

        } catch (WebApplicationException e) {
            LOG.warning("DENEGADO DELETE | status=" + e.getResponse().getStatus());
            return e.getResponse();
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "ERROR eliminar", e);
            return error500("Error en el servidor", e);
        }
    }

    /* ===========================
       SEGURIDAD
       =========================== */
    private AuthData validarAdmin(HttpHeaders headers) {
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);

        LOG.info("AUTH HEADER RECIBIDO: " + authHeader);

        if (authHeader == null || !authHeader.toLowerCase().startsWith("bearer ")) {
            LOG.warning("TOKEN NO PROPORCIONADO");
            throw new WebApplicationException(error401("Token no proporcionado"));
        }

        String token = authHeader.substring(7);
        LOG.info("TOKEN EXTRAIDO: " + token);

        String rol = JWTUtil.getRolFromToken(token);
        Integer userId = JWTUtil.getUserIdFromToken(token);

        LOG.info("TOKEN DATA | userId=" + userId + " | rol=" + rol);

        if (rol == null || !rol.trim().equalsIgnoreCase("admin")) {
            LOG.warning("ROL NO AUTORIZADO: " + rol);
            throw new WebApplicationException(error403("No tienes permisos"));
        }

        return new AuthData(userId, rol);
    }

    /* ===========================
       ERRORES
       =========================== */
    private Response error401(String msg) {
        return error(Response.Status.UNAUTHORIZED, msg);
    }

    private Response error403(String msg) {
        return error(Response.Status.FORBIDDEN, msg);
    }

    private Response error404(String msg) {
        return error(Response.Status.NOT_FOUND, msg);
    }

    private Response error500(String msg, Exception e) {
        return error(Response.Status.INTERNAL_SERVER_ERROR,
                e == null ? msg : msg + ": " + e.getMessage());
    }

    private Response error(Response.Status status, String msg) {
        JsonObject error = new JsonObject();
        error.addProperty("success", false);
        error.addProperty("message", msg);

        return Response.status(status)
                .entity(gson.toJson(error))
                .build();
    }

    /* ===========================
       AUTH DATA
       =========================== */
    private static class AuthData {

        final Integer userId;
        final String rol;

        AuthData(Integer userId, String rol) {
            this.userId = userId;
            this.rol = rol;
        }
    }
}
