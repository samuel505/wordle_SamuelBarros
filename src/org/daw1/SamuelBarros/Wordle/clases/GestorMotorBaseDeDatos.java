/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel505
 */
public class GestorMotorBaseDeDatos implements iMotor {

    private static File f = new File(Paths.get(".") + File.separator + "data" + File.separator + "dbwordle.db");

    private String idioma;
    private static final String URL = "jdbc:sqlite:" + f.toString();

    private final Set<String> palabras = new TreeSet<>();

    public GestorMotorBaseDeDatos(String idioma) {

        if (idioma.equals("es") || idioma.equals("gl")) {
            this.idioma = idioma;
        } else {
            throw new IllegalArgumentException("Solo validos es y gl");
        }

    }

    public boolean existe() {
        return f.exists();
    }

    public boolean comprobarTexto(String p) {
        StringBuilder sb = new StringBuilder();
        for (String palabra : palabras) {
            sb.append(palabra).append(" ");
        }
        if (sb.toString().contains(p)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean cargarTextos() throws IOException {
       return true;

    }

    @Override
    public boolean anadir(String palabra) throws SQLException, IOException {
        if (existePalabra(palabra)) {
           return false;
        }
        
        
        palabra = palabra.toUpperCase();
        try (Connection conn = DriverManager.getConnection(URL)) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO palabras(lang,palabra) VALUES (?,?)");

            ps.setString(1, idioma);
            ps.setString(2, palabra);
            int insertado = ps.executeUpdate();
            if (insertado > 0) {
                return true;
            }
            return false;

        }

    }

    @Override
    public boolean borrar(String palabra) throws SQLException, IOException {
        palabra = palabra.toUpperCase();
        if (!existePalabra(palabra)) {
            return false;
        }
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement("DELETE FROM palabras WHERE lang = ? AND palabra = ? ")) {

            ps.setString(1, idioma);
            ps.setString(2, palabra);
            int eliminadas = ps.executeUpdate();
            if (eliminadas > 0) {
                return true;
            }

        }
        return false;
    }

//select palabras from palabras where land = ? limit n
    private int cantidadPalabras() throws SQLException {
        int count = 0;

        String sql = "SELECT COUNT(*) as total FROM palabras WHERE lang = ?";
        try (Connection conn = DriverManager.getConnection(URL);
                PreparedStatement ps = conn.prepareStatement(sql);) {
            ps.setString(1, "es");
            try (ResultSet rs = ps.executeQuery();) {
                count = rs.getInt("total");
            }

        }

        return count;
    }

    @Override
    public String palabraAleatoria() throws SQLException {
        String s = "";
        Random rd = new Random();
        int random = rd.nextInt(cantidadPalabras());

        try (Connection conn = DriverManager.getConnection(URL)) {

            try (PreparedStatement ps = conn.prepareStatement("select palabra from palabras where lang = ? LIMIT ?,1");) {
                ps.setString(1, idioma);
                ps.setInt(2, random);

                try (ResultSet rs = ps.executeQuery();) {
                    rs.next();
                    s = rs.getString("palabra");
                }

            }

            return s;

        }
    }

    @Override
    public boolean existePalabra(String palabra)throws IOException,SQLException {
       palabra= palabra.toUpperCase();
       try(Connection conn = DriverManager.getConnection(URL);
               PreparedStatement statement = conn.prepareStatement("SELECT palabra FROM palabras WHERE palabra = ? and lang = ?")){
           statement.setString(1, palabra);
           statement.setString(2, idioma);
           try(ResultSet rs = statement.executeQuery()){
               if (rs.next()) {
                   return true;
               }
               return false;
           }
       }
        
    }

    @Override
    public String toString() {
        return "motorArchivo";
    }

}
