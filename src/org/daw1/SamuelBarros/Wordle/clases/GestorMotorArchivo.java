/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author samuel505
 */
public class GestorMotorArchivo implements iMotor {

    private static final File f = new File(Paths.get(".") + File.separator + "data" + File.separator + "palabrasEspanol.txt");
    private static final File f2 = new File(Paths.get(".") + File.separator + "data" + File.separator + "palabrasIngles.txt");
    private final Set<String> palabras = new TreeSet<String>();

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

    public boolean cargarTextosEspanol() throws IOException {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            return false;
            //Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);

        }
        String texto = "";
        if (texto == null) {
            return false;
        }
        while (texto != null) {

            // System.out.println(texto);
            try {

                texto = br.readLine();
                if (texto != null) {
                    palabras.add(texto);
                }
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        //System.out.println(palabras);
        return true;

    }

    @Override
    public boolean anadir(String palabra) {
        if (!palabra.matches("[A-Za-z]{5}")) {
            return false;
        } else {
            if (!f.exists()) {
                f.getParentFile().mkdirs();
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));) {
                bw.write(palabra);
                bw.newLine();
                return true;
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    }

    @Override
    public boolean borrar(String p) {
        try {
            cargarTextosEspanol();
        } catch (IOException ex) {
            // Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se cargaron las palabras");
            return false;
        }

        if (!palabras.contains(p)) {
            return false;
        }
        System.out.println("antes de borrar" + palabras);
        Iterator<String> it = palabras.iterator();
        do {
            String next = it.next();
            if (p.equals(next)) {
                it.remove();
            }
        } while (it.hasNext());

        System.out.println("despues de borrar" + palabras);

        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        } else {
           
            if (f.delete()) {
                System.out.println("Se a borrado");
            }else{
                System.out.println("no se ha borrado");
            }
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }
        for (String palabra : palabras) {
            if (!palabras.isEmpty()) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));) {
                    bw.append(palabra + "\n");

                } catch (IOException ex) {
                    Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String palabraAleatoria() {

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

        return false;

    }

}
