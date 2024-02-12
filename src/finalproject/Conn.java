package finalproject;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.fxml.FXML;

public class Conn {

    public static void main(String[] args)  {

        Connection con = null;
         Statement s;
         

        try {
            con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/movie_schema", "root", "Root@Passward123#");
            s =con.createStatement(); 
            ResultSet resultSet =s.executeQuery("SELECT * FROM movie_schema.fav;");
            while(resultSet.next()){
           System.out.println(resultSet.getString("title"));
       }
            
//             String sql = "INSERT INTO fav (title) VALUES (?)";
//try (PreparedStatement stmt = conn.prepareStatement(sql)) {
//    stmt.setString(1, "life");  // Replace with actual data
//    stmt.executeUpdate();
//} catch (SQLException e) {
//    System.out.println("Error executing SQL statement: " + e.getMessage());
//}

            if (con != null) {
                System.out.println("database is connected");

            }

        }
        catch(Exception e){
            System.out.println("not connected");
        }

    }

}
