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
public class ScoreManager {

    public static void addPlayerScore(String playerName, int score) {
        String query = "INSERT INTO player_scores (player_name, score) VALUES (?, ?)";
        MyConnection con = new MyConnection();
        PreparedStatement ps;
        ResultSet rs;
        try {
            ps = con.getConnection().prepareStatement(query);
            ps.setString(1, playerName);
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
