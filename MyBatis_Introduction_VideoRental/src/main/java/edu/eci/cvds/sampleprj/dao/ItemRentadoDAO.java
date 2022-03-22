package edu.eci.cvds.sampleprj.dao;
import java.util.List;

import edu.eci.cvds.samples.entities.ItemRentado;

public interface ItemRentadoDAO {
    public List<ItemRentado> consultarItemsCliente(long idCliente) throws PersistenceException;
    public ItemRentado consultarItemRentado(int iditem) throws PersistenceException;
}
