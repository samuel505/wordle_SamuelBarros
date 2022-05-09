/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

/**
 *
 * @author samuel505
 */
public class MotorTest implements iMotor{

    @Override
    public boolean anadir(String palabra) {
        
        return true;
    }

    @Override
    public boolean borrar(String palabra) {
        return true;
    }

    @Override
    public String palabraAleatoria() {
        return "ABCDE";
    }

    @Override
    public boolean existePalabra(String palabra) {
      return false;
    }
    
}
