package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDao;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

    @Inject
    private ItemDAO itemDAO;

    @Inject
    private ClienteDao clienteDao;

    @Inject
    private ItemRentadoDAO itemRentadoDAO;
    
    @Inject
    private TipoItemDAO tipoItemDAO;

    @Override
    public int valorMultaRetrasoxDia() {
        return 20;
    }

    @Override
    public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
        try{
            return clienteDao.consultarCliente(docu);
        }catch(PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar el cliente " + docu, e);
        }
        
    }

    @Override
    public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
        try{
            return itemRentadoDAO.consultarItemsCliente(idcliente);
        }catch(PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar los items del cliente " + idcliente, e);
        }
    }

    @Override
    public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
        try{
            return clienteDao.consultarClientes();
        }catch(PersistenceException e){
            throw new ExcepcionServiciosAlquiler("Error al consultar  clientes", e);
        }
    }

    @Override
    public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.load(id);
        } catch (PersistenceException ex) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item " + id, ex);
        }
    }

    @Override
    public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler {
        try {
            return itemDAO.consultarItemsDisponibles();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el items ", e);
        }
    }

    @Override
    public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
        try {
            ItemRentado item = itemRentadoDAO.consultarItemRentado(iditem);
            Date fechaFinRenta = item.getFechafinrenta();
            long diasRetraso = fechaDevolucion.compareTo(fechaFinRenta);
            if (diasRetraso<0){
                diasRetraso=0;
            }
            long multaRetrasoxDia = valorMultaRetrasoxDia();
            return diasRetraso*multaRetrasoxDia;
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar multa del item rentado "+iditem, e);
        }
    }

    @Override
    public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.getTipoItem(id);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el tipo "+id, e);
        }
    }

    @Override
    public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
        try {
            return tipoItemDAO.getTiposItems();
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar los tipos ", e);
        }
    }

    @Override
    public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            clienteDao.agregarItemRentadoACliente(docu, item.getId(), date, Date.valueOf(date.toLocalDate().plusDays(numdias)));
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al consultar el item al cliente "+docu, e);
        }
    }

    @Override
    public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
        try {
            clienteDao.save(c);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al registrar al cliente", e);
        }
    }

    @Override
    public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
        try {
            Item item=itemDAO.load(iditem);
            long tarifa = item.getTarifaxDia();
            return tarifa*numdias;
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al calcular costo de alquiler del item"+iditem, e);
        }
    }

    @Override
    public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
        try {
            Item item=itemDAO.load(id);
            item.setTarifaxDia(tarifa);
            itemDAO.save(item);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al modificar item"+id, e);
        }
    }

    @Override
    public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
        try {
            itemDAO.save(i);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al guardar item"+i, e);
        }
    }

    @Override
    public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
        try {
            Cliente cliente=clienteDao.consultarCliente(docu);
            cliente.setVetado(estado);
            clienteDao.save(cliente);
        } catch (PersistenceException e) {
            throw new ExcepcionServiciosAlquiler("Error al vetar cliente"+docu, e);
        }
    }
}
