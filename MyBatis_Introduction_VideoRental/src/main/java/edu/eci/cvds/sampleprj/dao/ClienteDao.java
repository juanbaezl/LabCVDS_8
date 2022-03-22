package edu.eci.cvds.sampleprj.dao;

import java.util.Date;
import java.util.List;

import edu.eci.cvds.samples.entities.Cliente;


public interface ClienteDao {
    public Cliente consultarCliente (int id) throws PersistenceException; 

    public void agregarItemRentadoACliente(int id, int idit, Date fechainicio,Date fechafin) throws PersistenceException;

    public List<Cliente> consultarClientes() throws PersistenceException;
    
}

