/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel505
 */
public class GestorMotor implements iMotor {

    private static final File f = new File(Paths.get(".") + File.separator + "dat" + File.separator + "palabras.txt");
    Set<String> palabras = new TreeSet<String>();

    public boolean existe() {
        return f.exists();

    }

    private void leer() {

    }

    @Override
    public boolean anadir(String palabra) {

        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GestorMotor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f));) {
            bw.write(palabra);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(GestorMotor.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    @Override
    public boolean borrar(String palabra) {
        
        return false;
        
    }

    
    public void test() {
        palabras.add("samuel");
        palabras.add("lucas");
        palabras.add("pedro");
        palabras.add("juan");
    }
    
    
    
    @Override
    public String palabraAleatoria() {
        
        Random rm = new Random();
        int random =rm.nextInt(palabras.size()-1);
        String texto = "";
      
       Object array[] =palabras.toArray();
       
       return (String) array[random];
    }

    @Override
    public boolean existePalabra(String palabra) {

        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
