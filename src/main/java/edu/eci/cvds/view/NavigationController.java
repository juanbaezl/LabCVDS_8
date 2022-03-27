package edu.eci.cvds.view;

import java.io.Serializable;

import javax.faces.bean.ManagedBean; 
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "navigationController", eager = true)
@RequestScoped

public class NavigationController implements Serializable {
    public String paginaAlquiler() {
        return "registroalquiler";
    }
    public String paginaCliente() {
        return "registrocliente";
    }
    public String paginaInicio(){
        return "index";
    }
}