/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sqlitetutorial.net
 */
public class conector {    
    //https://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
    
   private static File f= new File(Paths.get(".") + File.separator + "data" + File.separator + "dbwordle.db");
    
    private static final String URL = "jdbc:sqlite:"+f.toString();
    
    public static void connect() {        
        try (Connection conn = DriverManager.getConnection(URL)){
            // db parameters
            System.out.println(conn.getCatalog());
            Statement sentencia = conn.createStatement();
            try(ResultSet rs = sentencia.executeQuery("SELECT * FROM palabras WHERE lang='gl'")){
                while(rs.next()){
                    System.out.println(rs.getString("palabra"));
                }
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } 
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        connect();
    }
}