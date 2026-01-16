package com.iag.models;

import java.sql.Timestamp;
import java.util.List;

public class Contenido {
    
    private int id;
    private String titulo;
    private String cuerpo;
    private String tipo;
    private String estado;
    private int vistas;
    private Timestamp fechaPublicacion;
    private int autorId;
    private String autorNombre;
    private List<Categoria> categorias;
    
    public Contenido() {
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getCuerpo() {
        return cuerpo;
    }
    
    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public int getVistas() {
        return vistas;
    }
    
    public void setVistas(int vistas) {
        this.vistas = vistas;
    }
    
    public Timestamp getFechaPublicacion() {
        return fechaPublicacion;
    }
    
    public void setFechaPublicacion(Timestamp fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public int getAutorId() {
        return autorId;
    }
    
    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }
    
    public String getAutorNombre() {
        return autorNombre;
    }
    
    public void setAutorNombre(String autorNombre) {
        this.autorNombre = autorNombre;
    }
    
    public List<Categoria> getCategorias() {
        return categorias;
    }
    
    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    public String getExtracto() {
    if (cuerpo == null || cuerpo.isEmpty()) {
        return "";
    }
    
    if (cuerpo.length() > 150) {
        return cuerpo.substring(0, 150) + "...";
    }
    
    return cuerpo;
}
}
