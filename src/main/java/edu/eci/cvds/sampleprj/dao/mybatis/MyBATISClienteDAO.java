package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.ClienteDao;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ClienteMapper;
import edu.eci.cvds.samples.entities.Cliente;
import java.util.Date;
import java.util.List;

public class MyBATISClienteDAO implements ClienteDao {
    @Inject
    private ClienteMapper clienteMapper;

    @Override
    public Cliente consultarCliente(long id) throws PersistenceException {
        try {
            return clienteMapper.consultarCliente(id);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar cliente " + id, e);
        }
    }

    @Override
    public void agregarItemRentadoACliente(long id, int idit, Date fechainicio, Date fechafin)
            throws PersistenceException {
        try {
            clienteMapper.agregarItemRentadoACliente(id, idit, fechainicio, fechafin);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al agregar item a " + id, e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws PersistenceException {
        try {
            return clienteMapper.consultarClientes();
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar los clientes", e);
        }
    }

    @Override
    public void save(Cliente cl) throws PersistenceException{
        try{
            clienteMapper.insertarCliente(cl);
        }
        catch(org.apache.ibatis.exceptions.PersistenceException e){
            throw new PersistenceException("Error al registrar el cliente",e);
        } 
    }

}
