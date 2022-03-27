package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;

public class MyBATISTipoItemDAO implements TipoItemDAO {

    private TipoItemMapper TIM;

    @Override
    public List<TipoItem> getTiposItems() throws PersistenceException {
        try {
            return TIM.getTiposItems();
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar tipos de items ", e);
        }
    }

    @Override
    public TipoItem getTipoItem(int id) throws PersistenceException {
        try {
            return TIM.getTipoItem(id);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al consultar tipo de item con id " + id, e);
        }
    }

    @Override
    public void addTipoItem(String des) throws PersistenceException {
        try {
            TIM.addTipoItem(des);
        } catch (org.apache.ibatis.exceptions.PersistenceException e) {
            throw new PersistenceException("Error al añadir tipo de item con id" + des, e);
        }

    }

}
