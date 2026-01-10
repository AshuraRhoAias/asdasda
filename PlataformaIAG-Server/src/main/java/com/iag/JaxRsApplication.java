package com.iag;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class JaxRsApplication extends Application {
    
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        
        // Registrar recursos
        resources.add(com.iag.resources.AuthResource.class);
        resources.add(com.iag.resources.ContenidoResource.class);
        resources.add(com.iag.resources.CategoriaResource.class);
        resources.add(com.iag.resources.UsuarioResource.class);
        
        return resources;
    }
}