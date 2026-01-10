package com.iag.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iag.dao.CategoriaDAO;
import com.iag.models.Categoria;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/categorias")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CategoriaResource {
    
    private CategoriaDAO categoriaDAO = new CategoriaDAO();
    private Gson gson = new Gson();
    
    @GET
    public Response obtenerTodas() {
        try {
            List<Categoria> categorias = categoriaDAO.obtenerTodas();
            return Response.ok(gson.toJson(categorias)).build();
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al obtener categorías: " + e.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
    
    @GET
    @Path("/{id}")
    public Response obtenerPorId(@PathParam("id") int id) {
        try {
            Categoria categoria = categoriaDAO.obtenerPorId(id);
            
            if (categoria != null) {
                return Response.ok(gson.toJson(categoria)).build();
            } else {
                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Categoría no encontrada");
                
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(gson.toJson(error))
                        .build();
            }
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al obtener categoría: " + e.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
    
    @GET
    @Path("/count")
    public Response contarCategorias() {
        try {
            int total = categoriaDAO.contarCategorias();
            
            JsonObject response = new JsonObject();
            response.addProperty("total", total);
            
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al contar categorías: " + e.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
}
