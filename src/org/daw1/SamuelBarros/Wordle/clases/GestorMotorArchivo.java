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
import java.util.Iterator;
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

    private static  File f = null;
    private final Set<String> palabras = new TreeSet<>();

    public GestorMotorArchivo(String idioma) {
        comprobarIdioma(idioma);
    }

    
    public boolean existe() {
        return f.exists();
    }

    private void comprobarIdioma(String idioma){
        
        if (idioma.equals("es")) {
            f= new File(Paths.get(".") + File.separator + "data" + File.separator + "palabrasEspanol.txt");
        }else if (idioma.equals("gl")){
            f= new File(Paths.get(".") + File.separator + "data" + File.separator + "palabrasGalego.txt");
        }else{
            throw new IllegalArgumentException("Solo validos es y gl");
        }
        
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
        palabras.clear();
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
                    palabras.add(texto.toUpperCase());
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
        try {
            cargarTextos();
        } catch (IOException ex) {
            Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (!palabra.matches("[A-Za-z]{5}") || existePalabra(palabra)) {
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
                if (palabras.contains(palabra.toLowerCase())) {
                    return false;
                }
                bw.append(palabra.toLowerCase()+"\n");
               
                return true;
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        }

    }

    @Override
    public boolean borrar(String p) {
        p=p.toUpperCase();
        try {
            cargarTextos();
        } catch (IOException ex) {
            // Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("no se cargaron las palabras");
            return false;
        }

        if (!palabras.contains(p.toUpperCase())) {
            return false;
        }
        //System.out.println("antes de borrar " + palabras);
        Iterator<String> it = palabras.iterator();

        while (it.hasNext()) {
            String next = it.next();
            if (p.equals(next)) {
                it.remove();
            }
        }

        //System.out.println("despues de borrar" + palabras);
        
        
        StringBuilder sb1 = new StringBuilder();
        
        for (String palabra1 : palabras) {
            sb1.append(palabra1).append("\n");
        }
        
        
        
        if (!f.exists()) {
                f.getParentFile().mkdirs();
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f));) {
               
                bw.append(sb1.toString().toLowerCase());
               
                return true;
            } catch (IOException ex) {
                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }
        
        

        
//        Iterator<String> i = palabras.iterator();
//        while (it.hasNext()) {
//
//            if (!f.exists()) {
//                return false;
//            }
//
//            try (BufferedWriter bw = new BufferedWriter(new FileWriter(f));) {
//                bw.write(i.next());
//                bw.newLine();
//
//            } catch (IOException ex) {
//                Logger.getLogger(GestorMotorArchivo.class.getName()).log(Level.SEVERE, null, ex);
//                return false;
//            }
//
//        }
        
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
         try {
            cargarTextos();
        } catch (IOException ex) {
             System.out.println(ex);
        }
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
