/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ORMJPA;

import java.util.*;
import java.text.*;
import dao.*;
import entities.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.logging.*;
import javax.persistence.*;

/**
 *
 * @author camil
 */
public class ORMaero {

    private EntityManagerFactory factory;

    public ORMaero() {
        factory = Persistence.createEntityManagerFactory("AeropuertoPU");
    }

    public int login(String id, String clave) {
        UsuarioJpaController daouser = new UsuarioJpaController(factory);
        if (daouser.findUsuario(id) != null && daouser.findUsuario(id) != null && daouser.findUsuario(id) != null) {

            if (daouser.findUsuario(id).getUsuario().equals(id) && daouser.findUsuario(id).getContraseña().equals(clave) && daouser.findUsuario(id).getTipo().equals("general")) {
                return 1;
            }
            if (daouser.findUsuario(id).getUsuario().equals(id) && daouser.findUsuario(id).getContraseña().equals(clave) && daouser.findUsuario(id).getTipo().equals("usuario")) {
                return 2;
            }
            if (daouser.findUsuario(id).getUsuario().equals(id) && daouser.findUsuario(id).getContraseña().equals(clave) && daouser.findUsuario(id).getTipo().equals("aero")) {
                return 3;
            }
            if (daouser.findUsuario(id).getUsuario().equals(id) || daouser.findUsuario(id).getContraseña().equals(clave) || daouser.findUsuario(id).getTipo().equals("")) {
                return 0;
            }
        }
        return 0;
    }

    //Inician Metodos tabla Vuelos
    public List<Vuelo> obtenerVuelosDisponibles() {
        VueloJpaController objVuelo = new VueloJpaController(factory);
        List<Vuelo> vuelos = objVuelo.findVueloEntities();

        return vuelos;
    }

    public List<Reserva> obtenerReservas(){
        ReservaJpaController daoReser= new ReservaJpaController(factory);
        List<Reserva> reservas=daoReser.findReservaEntities();
        return reservas;
    }
    
    public List<Aerolinea> obtenerAerolienas() {
        AerolineaJpaController objAerolinea = new AerolineaJpaController(factory);
        List<Aerolinea> aerolineas = objAerolinea.findAerolineaEntities();

        return aerolineas;
    }

    public List<Avion> obteneraviones() {
        AvionJpaController daoavion = new AvionJpaController(factory);
        List<Avion> avion = daoavion.findAvionEntities();

        return avion;
    }

    public List<Reserva> obtenerReserva() {
        ReservaJpaController daoavion = new ReservaJpaController(factory);
        List<Reserva> reserva = daoavion.findReservaEntities();
        for (int i = 0; i < reserva.size(); i++) {
            System.out.println(reserva.get(i));
        }
        return reserva;
    }

    public List<Aeropuerto> obteneraeropuertos() {
        AeropuertoJpaController daoaeropuerto = new AeropuertoJpaController(factory);
        List<Aeropuerto> aeropuerto = daoaeropuerto.findAeropuertoEntities();

        return aeropuerto;
    }

    public List<Clase> obtenerClases() {
        ClaseJpaController daoClase = new ClaseJpaController(factory);
        List<Clase> clases = daoClase.findClaseEntities();

        return clases;
    }

    public void registrarPago(String id, java.sql.Date fecha, double precio) {
        PagoJpaController objpago = new PagoJpaController(factory);
        Pago pago = new Pago();
        pago.setIdPago(id);
        pago.setFgenerado(fecha);
        pago.setPrecioTotal(precio);
        try {
            objpago.create(pago);
        } catch (Exception ex) {
            Logger.getLogger(ORMaero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Destino> obtenerdestino() {
        DestinoJpaController daoDestino = new DestinoJpaController(factory);
        List<Destino> destino = daoDestino.findDestinoEntities();

        return destino;
    }

    public List<Origen> obtenerorigen() {
        OrigenJpaController daoOrigen = new OrigenJpaController(factory);
        List<Origen> origen = daoOrigen.findOrigenEntities();

        return origen;
    }

    public void agregarpersonaypasajero(String nombres, String apellidos, String identificacion, java.util.Date fechaNacimiento, String sexo, String email, Integer telefono, String direccion, Usuario us) {
        PasajeroJpaController daopas = new PasajeroJpaController(factory);
        PersonaJpaController daoper = new PersonaJpaController(factory);
        Pasajero pas = new Pasajero();
        Persona per = new Persona();

        per.setIdPersona(identificacion);
        per.setNombre(nombres);
        per.setDireccion(direccion);
        per.setTelefono(telefono);
        per.setFnac(fechaNacimiento);
        per.setSexo(sexo);

        pas.setIdPasajero(identificacion);
        pas.setIdPersona(per);
        pas.setUsuarioPas(us);

        try {
            daoper.create(per);
            daopas.create(pas);

        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void realizarReserva(String idReserva, String idPago, String codVuelo, String clase, String nombres, String apellidos, String identificacion, java.util.Date fechaNacimiento, String sexo, String email, Integer telefono, String direccion, Usuario us) {
        PasajeroJpaController daopas = new PasajeroJpaController(factory);
        PersonaJpaController daoper = new PersonaJpaController(factory);
        ReservaJpaController daoreserva = new ReservaJpaController(factory);
        VueloJpaController daovuelo = new VueloJpaController(factory);
        ClaseJpaController daoClase = new ClaseJpaController(factory);
        PagoJpaController daoPago = new PagoJpaController(factory);

        Pasajero pas = new Pasajero();
        Persona per = new Persona();
        Reserva reser = new Reserva();
        Clase objClase = daoClase.findClase(clase);
        Vuelo vuelo = daovuelo.findVuelo(codVuelo);
        Pago pago = daoPago.findPago(idPago);

        per.setIdPersona(identificacion);
        per.setNombre(nombres);
        per.setDireccion(direccion);
        per.setTelefono(telefono);
        per.setFnac(fechaNacimiento);
        per.setSexo(sexo);

        pas.setIdPasajero(identificacion);
        pas.setIdPersona(per);
        pas.setUsuarioPas(us);
        try {
            daoper.create(per);

        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            daopas.create(pas);
        } catch (Exception ex) {
            Logger.getLogger(ORMaero.class.getName()).log(Level.SEVERE, null, ex);
        }

        reser.setVuelo(vuelo);
        reser.setCodClase(objClase);
        reser.setPago(pago);
        reser.setPasajero(pas);
        ReservaPK respk= new ReservaPK();
        respk.setCodVuelo(codVuelo);
        respk.setIdPasajero(identificacion);
        respk.setClave(idReserva);
        reser.setReservaPK(respk); 
        
        try {
            daoreserva.create(reser);
        } catch (Exception ex) {
            Logger.getLogger(ORMaero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarusuario(String nombres, String apellidos, String identificacion, java.util.Date fechaNacimiento, String sexo, String email, Integer telefono, String usuario, String contraseña, String direccion) throws ParseException {
        UsuarioJpaController daouser = new UsuarioJpaController(factory);
        Usuario us = new Usuario();

        java.util.Date fechaCreacion = new java.util.Date();
        us.setUsuario(usuario);
        us.setContraseña(contraseña);
        us.setFcreacion(fechaCreacion);
        us.setTipo("usuario");

        ORMaero ormaero = new ORMaero();
        ormaero.agregarpersonaypasajero(nombres, apellidos, identificacion, fechaNacimiento, sexo, email, telefono, direccion, us);

        try {
            daouser.create(us);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void agregaravion(String matricula, String modelo, Integer capacidad, String nombreae) {
        AerolineaJpaController daoaero = new AerolineaJpaController(factory);
        AvionJpaController daoavion = new AvionJpaController(factory);
        Avion av = new Avion();
        Aerolinea aero = new Aerolinea();

        List<Aerolinea> aerolineas = daoaero.findAerolineaEntities();
        for (int j = 0; j < aerolineas.size(); j++) {
            if (aerolineas.get(j).getNombre().equals(nombreae.toUpperCase())) {
                String nitaero = aerolineas.get(j).getNit();
                aero.setNit(nitaero);
            }
        }

        aero.setNombre(nombreae);

        av.setMatricula(matricula);
        av.setModelo(modelo);
        av.setCapacidad(capacidad);
        av.setAerolinea(aero);

        try {
            daoavion.create(av);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminaravion(String matricula) {
        AvionJpaController daoavion = new AvionJpaController(factory);

        try {
            daoavion.destroy(matricula);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String compararav(String nombreae) {
        ORMaero ormaero = new ORMaero();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AeropuertoPU");
        AerolineaJpaController daoaero = new AerolineaJpaController(factory);
        AvionJpaController daoavion = new AvionJpaController(factory);
        List<Aerolinea> aerolineas = daoaero.findAerolineaEntities();
        List<Avion> avion = daoavion.findAvionEntities();
        for (int j = 0; j < aerolineas.size(); j++) {
            if (aerolineas.get(j).getNombre().equals(nombreae.toUpperCase())) {
                return aerolineas.get(j).getNit();
            }
        }
        return null;
    }

    public void crearvuelo(java.util.Date fechaVuelo, java.sql.Time horaVuelo, String codVuelo, String iatadestino, String matriculaAvion, String puerta) {
        VueloJpaController daovuelo = new VueloJpaController(factory);
        OrigenJpaController daoorigen = new OrigenJpaController(factory);
        DestinoJpaController daodestino = new DestinoJpaController(factory);

        Aeropuerto aeropuertoorigen = new Aeropuerto();
        Aeropuerto aeropuertodestino = new Aeropuerto();
        Avion avion = new Avion();
        Vuelo vuelo = new Vuelo();
        Origen origen = new Origen();
        Destino destino = new Destino();

        avion.setMatricula(matriculaAvion);

        vuelo.setCodVuelo(codVuelo);
        vuelo.setPuertaEmbarque(puerta);
        vuelo.setEstado("en curso");
        vuelo.setAvion(avion);

        aeropuertoorigen.setIata("vvc");

        origen.setVuelo(vuelo);
        origen.setAeropuerto(aeropuertoorigen);
        origen.setFecha(fechaVuelo);
        origen.setHora(horaVuelo);

        aeropuertodestino.setIata(iatadestino);

        destino.setVuelo(vuelo);
        destino.setAeropuerto(aeropuertodestino);

        try {
            daovuelo.create(vuelo);
            daoorigen.create(origen);
            daodestino.create(destino);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cancelarvuelo(String codvuelo, String matravion, String puerta) {
        VueloJpaController daovuelo = new VueloJpaController(factory);
        AvionJpaController daoavion = new AvionJpaController(factory);
        Avion avion2 = new Avion();
        Vuelo vuelo = new Vuelo();
        ORMaero ormaero = new ORMaero();
        avion2.setMatricula(matravion);

        vuelo.setDestinoList(ormaero.obtenerdestino());
        vuelo.setOrigenList(ormaero.obtenerorigen());
        vuelo.setReservaList(ormaero.obtenerReserva());
        vuelo.setCodVuelo(codvuelo);
        vuelo.setPuertaEmbarque(puerta);
        vuelo.setAvion(avion2);
        vuelo.setEstado("cancelado");
        try {
            daovuelo.edit(vuelo);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    public static void main(String[]args){
        ORMaero ormaero=new ORMaero();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AeropuertoPU");
        AerolineaJpaController daoaero = new AerolineaJpaController(factory);
        AvionJpaController daoavion = new AvionJpaController(factory);
        List<Aerolinea> aerolineas = daoaero.findAerolineaEntities();
        
       for (int j = 0; j < aerolineas.size(); j++){
           if(aerolineas.get(j).getNombre().equals("AVIANCA")){
         String nitaero=aerolineas.get(j).getNit();
         System.out.println(nitaero);
           }
       }
        
    }
     */
 /* public static void main(String[]args){
                ORMaero ormaero=new ORMaero();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AeropuertoPU");
        AerolineaJpaController daoaero = new AerolineaJpaController(factory);
        AvionJpaController daoavion = new AvionJpaController(factory);
        List<Aerolinea> aerolineas = daoaero.findAerolineaEntities();
        List<Avion> avion = daoavion.findAvionEntities();
       for (int j = 0; j < aerolineas.size(); j++){
           if(aerolineas.get(j).getNombre().equals("AVIANCA")){
               System.out.println(aerolineas.get(j));
           }
       }
          }*/
 /* public static void main(String[]args){
         ORMaero ormaero=new ORMaero();
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("AeropuertoPU");
        VueloJpaController daovuelo=new VueloJpaController(factory);
        AvionJpaController daoavion=new AvionJpaController(factory);
        AeropuertoJpaController daoaero=new AeropuertoJpaController(factory);
        Avion avion2=new Avion();
        Vuelo vuelo=new Vuelo();
        
        avion2.setMatricula("AVION 4");
        
       vuelo.setDestinoList(ormaero.obtenerdestino());
       vuelo.setOrigenList(ormaero.obtenerorigen());
       vuelo.setReservaList(ormaero.obtenerReserva());
        vuelo.setCodVuelo("11011902");
        vuelo.setPuertaEmbarque("1");
        vuelo.setAvion(avion2);
        vuelo.setEstado("cancelado");
         try {
            daovuelo.edit(vuelo);
        } catch (Exception ex) {
            Logger.getLogger(Aeropuerto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
}
