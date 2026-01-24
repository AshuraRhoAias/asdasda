import java.sql.Connection;
import java.sql.DriverManager;

public class TestConexion {
    public static void main(String[] args) {
        // ¡EXACTAMENTE la misma URL que en DatabaseConfig!
        String url = "jdbc:mysql://localhost:3306/bryan?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "bryan"; // o "root123" si tiene
        
        System.out.println("URL: " + url);
        System.out.println("User: " + user);
        
        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ ¡CONEXIÓN EXITOSA!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}