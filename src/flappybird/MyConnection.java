/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flappybird;
import java.sql.*;

/**
 *
 * @author acer
 */
public class MyConnection {
    public Connection getConnection(){
        Connection con = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/flappybird","root","root");
        }
        catch(Exception e){
            System.out.println("Error connect with MySql" + e);
        }
        return con;
    }
}
