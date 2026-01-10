package com.iag.dao;

import com.iag.config.DatabaseConfig;
import com.iag.models.Categoria;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
    public List<Categoria> obtenerTodas() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias ORDER BY nombre";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                categorias.add(mapResultSetToCategoria(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categorias;
    }
    
    public Categoria obtenerPorId(int id) {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCategoria(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public List<Categoria> obtenerPorContenido(int contenidoId) {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT c.* FROM categorias c " +
                    "INNER JOIN contenido_categorias cc ON c.id = cc.categoria_id " +
                    "WHERE cc.contenido_id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, contenidoId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                categorias.add(mapResultSetToCategoria(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return categorias;
    }
    
    public int contarCategorias() {
        String sql = "SELECT COUNT(*) FROM categorias";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
    
    private Categoria mapResultSetToCategoria(ResultSet rs) throws SQLException {
        Categoria categoria = new Categoria();
        categoria.setId(rs.getInt("id"));
        categoria.setNombre(rs.getString("nombre"));
        categoria.setDescripcion(rs.getString("descripcion"));
        categoria.setIcono(rs.getString("icono"));
        categoria.setColor(rs.getString("color"));
        return categoria;
    }
}
