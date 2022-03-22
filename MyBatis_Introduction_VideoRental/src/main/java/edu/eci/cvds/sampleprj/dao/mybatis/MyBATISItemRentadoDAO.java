package edu.eci.cvds.sampleprj.dao.mybatis;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.ItemRentadoMapper;
import java.util.List;

public class MyBATISItemRentadoDAO implements ItemRentadoDAO {

    @Inject
    private ItemRentadoMapper itemRentadoMapper;

    @Override
    public List<ItemRentado> consultarItemsCliente(long idCliente) throws PersistenceException {
        try {
            return itemRentadoMapper.consultarItemsCliente(idCliente);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar los items rentados del cliente " + idCliente, e);
        }
    }

    @Override
    public ItemRentado consultarItemRentado(int iditem) throws PersistenceException {
        try {
            return itemRentadoMapper.consultarItemRentado(iditem);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar el item rentado " + iditem, e);
        }
    }

}
