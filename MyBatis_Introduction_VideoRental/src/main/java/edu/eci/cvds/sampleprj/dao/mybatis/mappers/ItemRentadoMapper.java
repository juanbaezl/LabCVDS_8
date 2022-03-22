package edu.eci.cvds.sampleprj.dao.mybatis.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.ItemRentado;

public interface ItemRentadoMapper {
    public ItemRentado consultarItemRentado(@Param("idit")int iditem);
    public List<ItemRentado> consultarItemsCliente(@Param("idc")long idcliente);  
}