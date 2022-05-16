/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.IOException;

/**
 *
 * @author samuel505
 */
public interface iMotor {
    public boolean anadir(String palabra);
    public boolean borrar(String palabra);
    public String palabraAleatoria();
    public boolean existePalabra(String palabra);
    public boolean cargarTextos()throws IOException;
    
    
}
