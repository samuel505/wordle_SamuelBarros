/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.daw1.SamuelBarros.Wordle.clases;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author samuel505
 */
public interface iMotor {
    public boolean anadir(String palabra)throws IOException,SQLException;
    public boolean borrar(String palabra)throws IOException,SQLException;
    public String palabraAleatoria()throws IOException,SQLException;
    public boolean existePalabra(String palabra)throws IOException,SQLException;
    public boolean cargarTextos()throws IOException,SQLException;
    
    
}
