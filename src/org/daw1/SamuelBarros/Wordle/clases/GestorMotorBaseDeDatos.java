/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.daw1.SamuelBarros.Wordle.clases.conector.connect;

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
        this.idioma=idioma;
    }

    public boolean existe() {
        return f.exists();
    }

    
    public boolean comprobarTexto(String p) {
        String texto = "";
        for (String palabra : palabras) {
            texto += palabra + " ";
        }
        if (texto.contains(p)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean cargarTextos() throws IOException {
        palabras.clear();

        try ( Connection conn = DriverManager.getConnection(URL)) {
            // db parameters
            System.out.println(conn.getCatalog());
            Statement sentencia = conn.createStatement();
            try ( ResultSet rs = sentencia.executeQuery("SELECT * FROM palabras WHERE lang='"+idioma+"'")) {
                while (rs.next()) {
                    palabras.add(rs.getString("palabra").toUpperCase());
                }
            }
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
        
    }

    @Override
    public boolean anadir(String palabra) {
        return false;
      

    }

    @Override
    public boolean borrar(String palabra) {
        palabra = palabra.toUpperCase();
        try ( Connection conn = DriverManager.getConnection(URL)) {
            Statement sentencia = conn.createStatement();
            if (sentencia.executeUpdate("DELETE FROM palabras WHERE lang='"+idioma+"'"+"AND palabra = '"+palabra+"'")<1) {
                return false;
            }
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public String palabraAleatoria() {
        if (palabras.isEmpty()) {
            return null;
        }
        Random rm = new Random();
        int random = rm.nextInt(palabras.size());
        String texto = "";

        Object array[] = palabras.toArray();
        String palabra = (String) array[random];
        // System.out.println(palabra);
        return palabra;
    }

    @Override
    public boolean existePalabra(String palabra) {
        palabra = palabra.toUpperCase();
        Iterator it = palabras.iterator();
        String p = "";
        while (it.hasNext()) {
            p += it.next() + " ";
        }

        return p.contains(palabra);

    }

    @Override
    public String toString() {
        return "motorArchivo";
    }

}
