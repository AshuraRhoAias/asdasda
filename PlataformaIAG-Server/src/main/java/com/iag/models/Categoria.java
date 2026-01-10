package com.iag.models;

public class Categoria {
    
    private int id;
    private String nombre;
    private String descripcion;
    private String icono;
    private String color;
    
    public Categoria() {
    }
    
    public Categoria(int id, String nombre, String descripcion, String icono, String color) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.icono = icono;
        this.color = color;
    }
    
    // Getters y Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public String getIcono() {
        return icono;
    }
    
    public void setIcono(String icono) {
        this.icono = icono;
    }
    
    public String getColor() {
        return color;
    }
    
    public void setColor(String color) {
        this.color = color;
    }
}
