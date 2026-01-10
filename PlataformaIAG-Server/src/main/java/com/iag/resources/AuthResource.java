package com.iag.resources;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iag.dao.UsuarioDAO;
import com.iag.models.Usuario;
import com.iag.utils.JWTUtil;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Gson gson = new Gson();

    // ========================= LOGIN =========================
    @POST
    @Path("/login")
    public Response login(String body) {

        System.out.println("======================================");
        System.out.println("📥 [LOGIN] Petición recibida");
        System.out.println("📦 [LOGIN] Body recibido: " + body);

        try {
            JsonObject json = gson.fromJson(body, JsonObject.class);

            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();

            System.out.println("👤 [LOGIN] Intento de login");
            System.out.println("📧 [LOGIN] Email: " + email);
            System.out.println("🔑 [LOGIN] Password recibida (length): " + password.length());

            Usuario usuario = usuarioDAO.login(email, password);

            if (usuario != null) {
                System.out.println("✅ [LOGIN] Usuario autenticado");
                System.out.println("🆔 [LOGIN] ID: " + usuario.getId());
                System.out.println("🎭 [LOGIN] Rol: " + usuario.getRol());

                String token = JWTUtil.generateToken(
                        usuario.getId(),
                        usuario.getEmail(),
                        usuario.getRol()
                );

                System.out.println("🔐 [LOGIN] JWT generado:");
                System.out.println(token);

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("token", token);
                response.addProperty("userId", usuario.getId());
                response.addProperty("nombre", usuario.getNombre());
                response.addProperty("email", usuario.getEmail());
                response.addProperty("rol", usuario.getRol());

                System.out.println("📤 [LOGIN] Respuesta enviada correctamente");
                System.out.println("======================================");

                return Response.ok(gson.toJson(response)).build();

            } else {
                System.out.println("❌ [LOGIN] Credenciales incorrectas para: " + email);

                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Email o contraseña incorrectos");

                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(gson.toJson(error))
                        .build();
            }

        } catch (Exception e) {
            System.out.println("🔥 [LOGIN] ERROR GRAVE");
            e.printStackTrace();

            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error en el servidor: " + e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }

    // ========================= REGISTRO =========================
    @POST
    @Path("/registro")
    public Response registro(String body) {

        System.out.println("======================================");
        System.out.println("📥 [REGISTRO] Petición recibida");
        System.out.println("📦 [REGISTRO] Body recibido: " + body);

        try {
            JsonObject json = gson.fromJson(body, JsonObject.class);

            String nombre = json.get("nombre").getAsString();
            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();

            System.out.println("👤 [REGISTRO] Nombre: " + nombre);
            System.out.println("📧 [REGISTRO] Email: " + email);
            System.out.println("🔑 [REGISTRO] Password length: " + password.length());

            if (usuarioDAO.existeEmail(email)) {
                System.out.println("⚠️ [REGISTRO] Email ya existe: " + email);

                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "El email ya está registrado");

                return Response.status(Response.Status.CONFLICT)
                        .entity(gson.toJson(error))
                        .build();
            }

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setPassword(password);

            boolean registrado = usuarioDAO.registrar(usuario);

            if (registrado) {
                System.out.println("✅ [REGISTRO] Usuario registrado correctamente");

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("message", "Usuario registrado exitosamente");

                return Response.status(Response.Status.CREATED)
                        .entity(gson.toJson(response))
                        .build();
            } else {
                System.out.println("❌ [REGISTRO] Error al registrar usuario");

                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Error al registrar usuario");

                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity(gson.toJson(error))
                        .build();
            }

        } catch (Exception e) {
            System.out.println("🔥 [REGISTRO] ERROR GRAVE");
            e.printStackTrace();

            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error en el servidor: " + e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }

    // ========================= VALIDAR TOKEN =========================
    @POST
    @Path("/validate")
    public Response validateToken(@HeaderParam("Authorization") String authHeader) {

        System.out.println("======================================");
        System.out.println("🔎 [VALIDATE] Petición de validación JWT");

        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("❌ [VALIDATE] Header Authorization inválido");

                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Token no proporcionado");

                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(gson.toJson(error))
                        .build();
            }

            String token = authHeader.substring(7);
            System.out.println("🔐 [VALIDATE] Token recibido:");
            System.out.println(token);

            boolean isValid = JWTUtil.isTokenValid(token);

            if (isValid) {
                Integer userId = JWTUtil.getUserIdFromToken(token);
                String rol = JWTUtil.getRolFromToken(token);

                System.out.println("✅ [VALIDATE] Token válido");
                System.out.println("🆔 [VALIDATE] UserID: " + userId);
                System.out.println("🎭 [VALIDATE] Rol: " + rol);

                JsonObject response = new JsonObject();
                response.addProperty("success", true);
                response.addProperty("userId", userId);
                response.addProperty("rol", rol);

                return Response.ok(gson.toJson(response)).build();
            } else {
                System.out.println("❌ [VALIDATE] Token inválido o expirado");

                JsonObject error = new JsonObject();
                error.addProperty("success", false);
                error.addProperty("message", "Token inválido o expirado");

                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(gson.toJson(error))
                        .build();
            }

        } catch (Exception e) {
            System.out.println("🔥 [VALIDATE] ERROR GRAVE");
            e.printStackTrace();

            JsonObject error = new JsonObject();
            error.addProperty("success", false);
            error.addProperty("message", "Error al validar token: " + e.getMessage());

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(gson.toJson(error))
                    .build();
        }
    }
}
