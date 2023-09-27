/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import dao.VueloJpaController;
import entities.Vuelo;
import java.util.List;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author cesit
 */
public class Controlador {
    
    VueloJpaController objVuelo;
    
    public static void main(String []cb){
        new Controlador();
    }
    
    public Controlador(){
        this.obtenerVuelosDisponibles();
    }
    
    //Inician Metodos tabla Vuelos
    public void obtenerVuelosDisponibles(){
        //objVuelo = new VueloJpaController(new EntityManagerFactory());
        List<Vuelo> vuelosDisp = objVuelo.findVueloEntities();
        System.out.println(vuelosDisp);
    }
    
    
    
    //Termina metodos tabla vuelos
    
}
