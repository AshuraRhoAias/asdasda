package com.iag.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iag.dao.UsuarioDAO;
import com.iag.models.Usuario;
import com.iag.utils.JWTUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();
    
    @GET
    public Response obtenerTodos(@HeaderParam("Authorization") String authHeader) {
        try {
            // Validar token
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Token no proporcionado");
                
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(gson.toJson(error))
                        .build();
            }
            
            String token = authHeader.substring(7);
            String rol = JWTUtil.getRolFromToken(token);
            
            if (!"admin".equals(rol)) {
                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "No tienes permisos para ver usuarios");
                
                return Response.status(Response.Status.FORBIDDEN)
                        .entity(gson.toJson(error))
                        .build();
            }
            
            List<Usuario> usuarios = usuarioDAO.obtenerTodos();
            return Response.ok(gson.toJson(usuarios)).build();
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al obtener usuarios: " + e.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
    
    @GET
    @Path("/count")
    public Response contarUsuarios() {
        
        try {
            int total = usuarioDAO.contarUsuarios();
            
            JsonObject response = new JsonObject();
            response.addProperty("total", total);
            
            return Response.ok(gson.toJson(response)).build();
        } catch (Exception e) {
            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al contar usuarios: " + e.getMessage());
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
}
