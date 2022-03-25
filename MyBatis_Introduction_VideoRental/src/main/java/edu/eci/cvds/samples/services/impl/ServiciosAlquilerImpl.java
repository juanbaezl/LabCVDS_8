package edu.eci.cvds.samples.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import edu.eci.cvds.sampleprj.dao.ClienteDao;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;

import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Singleton
public class ServiciosAlquilerImpl implements ServiciosAlquiler {

   @Inject
   private ItemDAO itemDAO;
   @Inject
   private ClienteDao clienteDAO;
   @Inject
   private TipoItemDAO tipoItemDAO;
   @Inject
   private ItemRentadoDAO itemRentadoDAO;
   private static final int MULTA_DIARIA=5000;
   @Override
   public int valorMultaRetrasoxDia(int itemId) {
       throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public Cliente consultarCliente(long docu) throws ExcepcionServiciosAlquiler {
       try {
           return clienteDAO.consultarCliente(docu);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el cliente "+docu,ex);
       }
   }

   @Override
   public List<ItemRentado> consultarItemsCliente(long idcliente) throws ExcepcionServiciosAlquiler {
       try{
           return itemRentadoDAO.consultarItemsCliente(idcliente);
       }catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar los items rentados del cliente "+idcliente,ex);
       }
   }

   @Override
   public List<Cliente> consultarClientes() throws ExcepcionServiciosAlquiler {
       try {
           return clienteDAO.consultarClientes();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los clientes ",ex);
       }
   }

   @Override
   public Item consultarItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return itemDAO.load(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el item "+id,ex);
       }
   }

   @Override
   public List<Item> consultarItemsDisponibles() throws ExcepcionServiciosAlquiler{
       try {
           return itemDAO.consultarItemsDisponibles();
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar los items disponibles ",ex);
       }
   }

   @Override
   public long consultarMultaAlquiler(int iditem, Date fechaDevolucion) throws ExcepcionServiciosAlquiler {
       try{
           ItemRentado item=itemRentadoDAO.consultarItemRentado(iditem);
           LocalDate fechaMinimaEntrega=item.getFechafinrenta().toLocalDate();
           LocalDate fechaEntrega=fechaDevolucion.toLocalDate();
           long diasRetraso = ChronoUnit.DAYS.between(fechaMinimaEntrega, fechaEntrega);
           if(diasRetraso<0){
               diasRetraso=0;
           }
           return diasRetraso*MULTA_DIARIA;
           
       }catch (PersistenceException ex){
           throw new ExcepcionServiciosAlquiler("Error al consultar la multa del item "+iditem,ex);
       }
   }

   @Override
   public TipoItem consultarTipoItem(int id) throws ExcepcionServiciosAlquiler {
       try {
           return tipoItemDAO.getTipoItem(id);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al consultar el tipo item "+id,ex);
       }
   }

   @Override
   public List<TipoItem> consultarTiposItem() throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }


   @Override
   public void registrarAlquilerCliente(Date date, long docu, Item item, int numdias) throws ExcepcionServiciosAlquiler {
       try{
           clienteDAO.agregarItemRentadoACliente(docu,item.getId(),date,Date.valueOf(date.toLocalDate().plusDays(numdias)));
       }catch(PersistenceException ex){
            throw new ExcepcionServiciosAlquiler("Error al registrar item al cliente"+docu,ex);

       }
   }

   @Override
   public void registrarCliente(Cliente c) throws ExcepcionServiciosAlquiler {
       try {
           clienteDAO.save(c);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registrar cliente",ex);
       }
   }

   @Override
   public long consultarCostoAlquiler(int iditem, int numdias) throws ExcepcionServiciosAlquiler {
       try{
           List<Item> itemsDisponibles = itemDAO.consultarItemsDisponibles();
           Item item = itemDAO.load(iditem);
           boolean r=false;
           for(Item i: itemsDisponibles){
               if(i.getId()==iditem){
                   r=true;
               }
           }
           if(!r){
               throw new ExcepcionServiciosAlquiler("Item "+iditem+ "no disponible");
           }else{
               return item.getTarifaxDia()*numdias;
           }
               
        }catch(PersistenceException ex){
            throw new ExcepcionServiciosAlquiler("Error al consultar costo del item "+iditem,ex);
        }    
   }

   @Override
   public void actualizarTarifaItem(int id, long tarifa) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet.");
   }
   @Override
   public void registrarItem(Item i) throws ExcepcionServiciosAlquiler {
       try {
           itemDAO.save(i);
       } catch (PersistenceException ex) {
           throw new ExcepcionServiciosAlquiler("Error al registrar el item ",ex);
       }
   
   }

   @Override
   public void vetarCliente(long docu, boolean estado) throws ExcepcionServiciosAlquiler {
       throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
   }
}