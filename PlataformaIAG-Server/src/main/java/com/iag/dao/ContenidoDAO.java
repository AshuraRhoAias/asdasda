package com.iag.dao;

import com.iag.config.DatabaseConfig;
import com.iag.models.Contenido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContenidoDAO {
    
    private CategoriaDAO categoriaDAO;
    
    public ContenidoDAO() {
        this.categoriaDAO = new CategoriaDAO();
    }
    
    public List<Contenido> obtenerTodos() {
        List<Contenido> contenidos = new ArrayList<>();
        String sql = "SELECT c.*, u.nombre as autor_nombre FROM contenidos c " +
                    "LEFT JOIN usuarios u ON c.autor_id = u.id " +
                    "ORDER BY c.fecha_publicacion DESC";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contenido contenido = mapResultSetToContenido(rs);
                contenido.setCategorias(categoriaDAO.obtenerPorContenido(contenido.getId()));
                contenidos.add(contenido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return contenidos;
    }
    
    public List<Contenido> obtenerPublicados() {
        List<Contenido> contenidos = new ArrayList<>();
        String sql = "SELECT c.*, u.nombre as autor_nombre FROM contenidos c " +
                    "LEFT JOIN usuarios u ON c.autor_id = u.id " +
                    "WHERE c.estado = 'publicado' " +
                    "ORDER BY c.fecha_publicacion DESC";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Contenido contenido = mapResultSetToContenido(rs);
                contenido.setCategorias(categoriaDAO.obtenerPorContenido(contenido.getId()));
                contenidos.add(contenido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return contenidos;
    }
    
    public List<Contenido> obtenerPorCategoria(int categoriaId) {
        List<Contenido> contenidos = new ArrayList<>();
        String sql = "SELECT c.*, u.nombre as autor_nombre FROM contenidos c " +
                    "LEFT JOIN usuarios u ON c.autor_id = u.id " +
                    "INNER JOIN contenido_categorias cc ON c.id = cc.contenido_id " +
                    "WHERE cc.categoria_id = ? AND c.estado = 'publicado' " +
                    "ORDER BY c.fecha_publicacion DESC";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, categoriaId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Contenido contenido = mapResultSetToContenido(rs);
                contenido.setCategorias(categoriaDAO.obtenerPorContenido(contenido.getId()));
                contenidos.add(contenido);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return contenidos;
    }
    
    public Contenido obtenerPorId(int id) {
        String sql = "SELECT c.*, u.nombre as autor_nombre FROM contenidos c " +
                    "LEFT JOIN usuarios u ON c.autor_id = u.id " +
                    "WHERE c.id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Contenido contenido = mapResultSetToContenido(rs);
                contenido.setCategorias(categoriaDAO.obtenerPorContenido(contenido.getId()));
                return contenido;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean crear(Contenido contenido, List<Integer> categoriasIds) {
        String sql = "INSERT INTO contenidos (titulo, cuerpo, tipo, estado, autor_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, contenido.getTitulo());
            stmt.setString(2, contenido.getCuerpo());
            stmt.setString(3, contenido.getTipo());
            stmt.setString(4, contenido.getEstado());
            stmt.setInt(5, contenido.getAutorId());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int contenidoId = rs.getInt(1);
                    return asignarCategorias(contenidoId, categoriasIds);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean actualizar(Contenido contenido, List<Integer> categoriasIds) {
        String sql = "UPDATE contenidos SET titulo = ?, cuerpo = ?, tipo = ?, estado = ? WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, contenido.getTitulo());
            stmt.setString(2, contenido.getCuerpo());
            stmt.setString(3, contenido.getTipo());
            stmt.setString(4, contenido.getEstado());
            stmt.setInt(5, contenido.getId());
            
            int rows = stmt.executeUpdate();
            
            if (rows > 0) {
                eliminarCategorias(contenido.getId());
                return asignarCategorias(contenido.getId(), categoriasIds);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM contenidos WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public void incrementarVistas(int id) {
        String sql = "UPDATE contenidos SET vistas = vistas + 1 WHERE id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public int contarContenidos() {
        String sql = "SELECT COUNT(*) FROM contenidos";
        
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
    
    private boolean asignarCategorias(int contenidoId, List<Integer> categoriasIds) {
        if (categoriasIds == null || categoriasIds.isEmpty()) {
            return true;
        }
        
        String sql = "INSERT INTO contenido_categorias (contenido_id, categoria_id) VALUES (?, ?)";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            for (Integer categoriaId : categoriasIds) {
                stmt.setInt(1, contenidoId);
                stmt.setInt(2, categoriaId);
                stmt.addBatch();
            }
            stmt.executeBatch();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private void eliminarCategorias(int contenidoId) {
        String sql = "DELETE FROM contenido_categorias WHERE contenido_id = ?";
        
        try (Connection conn = DatabaseConfig.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, contenidoId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private Contenido mapResultSetToContenido(ResultSet rs) throws SQLException {
        Contenido contenido = new Contenido();
        contenido.setId(rs.getInt("id"));
        contenido.setTitulo(rs.getString("titulo"));
        contenido.setCuerpo(rs.getString("cuerpo"));
        contenido.setTipo(rs.getString("tipo"));
        contenido.setEstado(rs.getString("estado"));
        contenido.setVistas(rs.getInt("vistas"));
        contenido.setFechaPublicacion(rs.getTimestamp("fecha_publicacion"));
        contenido.setAutorId(rs.getInt("autor_id"));
        contenido.setAutorNombre(rs.getString("autor_nombre"));
        return contenido;
    }
}
