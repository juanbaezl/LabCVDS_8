package edu.eci.cvds.samples.services;

public class ExcepcionServiciosAlquiler extends Exception {
    public ExcepcionServiciosAlquiler(String a, Exception e){
        super(a,e);
    }
    public ExcepcionServiciosAlquiler(String m){
        super(m);
    }
}
