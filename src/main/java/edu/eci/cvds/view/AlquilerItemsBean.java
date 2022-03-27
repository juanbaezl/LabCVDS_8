/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.cvds.view;

import com.google.inject.Inject;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import java.sql.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

/**
 *
 * @author Imac
 */

@ManagedBean(name = "Alquiler")
@ApplicationScoped
public class AlquilerItemsBean extends BasePageBean{

    @Inject
    private ServiciosAlquiler serviciosAlquiler;
    private List<Cliente> clientes;
    private List<Item> disponibles;
    private Cliente seleccionado;
    private List<ItemRentado> rentados;
    private int[] item = new int[2];
    private int itemCod=0;
    private int itemDate=0;
    private long multa;
    private long costo;


    public void inicio(){
        try{
            clientes = serviciosAlquiler.consultarClientes();

        }catch(ExcepcionServiciosAlquiler e){

        }

    }

    public void insertarCliente(String nombre, long documento, String telefono, String direccion, String email){
        try {
            serviciosAlquiler.registrarCliente(new Cliente(nombre,documento,telefono,direccion,email));
            clientes = serviciosAlquiler.consultarClientes();

        } catch (ExcepcionServiciosAlquiler ex) {
            Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private void consultarItemsRentados(){
        try {
            rentados = serviciosAlquiler.consultarItemsCliente(seleccionado.getDocumento());
        } catch (ExcepcionServiciosAlquiler ex) {
            Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void consultarMulta(){
        long total=0;
        try {
            if(rentados!=null){

                for(ItemRentado i: rentados){
                    total+=serviciosAlquiler.consultarMultaAlquiler(i.getItem().getId(),new Date(System.currentTimeMillis()));
                }
            }
        }catch (ExcepcionServiciosAlquiler ex) {
                Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        multa=total;
    }
    public void consultarCostoAlquiler(int itid, int numDias){
        try {
            System.out.println(itemCod);
            Item rentado=serviciosAlquiler.consultarItem(itemCod);
            System.out.println(rentado.getId()+" "+rentado.getTarifaxDia());
            costo = rentado.getTarifaxDia()*itemDate;
            System.out.println(costo);
            item[0]=itid;
            item[1]=numDias;
        } catch (ExcepcionServiciosAlquiler ex) {
            Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void rentarItem(){
        
        try {
            Item rentado=serviciosAlquiler.consultarItem(itemCod);
            System.out.println("Silas");
            serviciosAlquiler.registrarAlquilerCliente(new java.sql.Date(System.currentTimeMillis()), seleccionado.getDocumento(), rentado, itemDate);
            costo=serviciosAlquiler.consultarCostoAlquiler(itemCod,itemDate);
            System.out.println("Silas 2.0");
        } catch (ExcepcionServiciosAlquiler ex) {
            Logger.getLogger(AlquilerItemsBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public Cliente getSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(Cliente seleccionado) {
        this.seleccionado = seleccionado;
        try{
        disponibles = serviciosAlquiler.consultarItemsDisponibles();
        }catch(ExcepcionServiciosAlquiler ex){

        }
        consultarItemsRentados();
        consultarMulta();
    }
    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Item> getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(List<Item> disponibles) {
        this.disponibles = disponibles;
    }

    public List<ItemRentado> getRentados() {
        return rentados;
    }

    public void setRentados(List<ItemRentado> rentados) {
        this.rentados = rentados;
    }

    public long getMulta() {
        return multa;
    }

    public void setMulta(long multa) {
        this.multa = multa;
    }

    public long getCosto() {
        return costo;
    }

    public void setCosto(long costo) {
        this.costo = costo;
    }

    public int getItemCod() {
        return itemCod;
    }

    public void setItemCod(int itemCod) {
        this.itemCod = itemCod;
    }

    public int getItemDate() {
        return itemDate;
    }

    public void setItemDate(int itemDate) {
        this.itemDate = itemDate;
    }



}