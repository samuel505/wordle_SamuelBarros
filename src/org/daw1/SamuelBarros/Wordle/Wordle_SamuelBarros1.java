/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.daw1.SamuelBarros.Wordle;

import java.util.Scanner;
import org.daw1.SamuelBarros.Wordle.clases.GestorMotor;

/**
 *
 * @author alumno
 */
public class Wordle_SamuelBarros1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        GestorMotor gm = new GestorMotor();
        Scanner sc = new Scanner(System.in);
       String texto= "";
        System.out.println("aa");
        texto = sc.nextLine();
        gm.anadir(texto);
    }
    
}
