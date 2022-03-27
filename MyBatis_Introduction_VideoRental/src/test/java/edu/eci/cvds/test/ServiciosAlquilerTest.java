package edu.eci.cvds.test;

import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.google.inject.Inject;
import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.samples.entities.Cliente;
import edu.eci.cvds.samples.entities.ItemRentado;
import edu.eci.cvds.samples.entities.Item;
import edu.eci.cvds.samples.entities.TipoItem;
import edu.eci.cvds.samples.services.ExcepcionServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.ServiciosAlquilerFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class ServiciosAlquilerTest {

    @Inject
    private SqlSession sqlSession;

    ServiciosAlquiler serviciosAlquiler;

    public ServiciosAlquilerTest() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Before
    public void setUp() {
        serviciosAlquiler = ServiciosAlquilerFactory.getInstance().getServiciosAlquilerTesting();
    }

    @Test
    public void deberiaRegistrarNuevosClientes(){
        try{
            Cliente cl = new Cliente("Sebas", 12345678 ,"6874123", "Carrera 45 - 205 ", "sebas@gmail.com");
            serviciosAlquiler.registrarCliente(cl);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deberiaConsultarClientes(){
        try{
            Cliente cl=new Cliente("Sebas", 12345678 ,"6874123", "Carrera 45 - 205 ", "sebas@gmail.com");
            serviciosAlquiler.registrarCliente(cl);
            Cliente consulta = serviciosAlquiler.consultarCliente(cl.getDocumento());
            Assert.assertTrue(cl.getDocumento() == consulta.getDocumento());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void deberiaRegistrarAlquilerCliente(){
        try{
            Cliente cl = new Cliente("Sebas", 112345678 ,"6874123", "Carrera 45 - 205 ", "sebas@gmail.com");
            TipoItem ti = new TipoItem(1,"Deportes");
            Date fecha =  new Date(2019,10,11);
            Item it = new Item(ti, 1, "Item1", "Primer Item", fecha, 20000, "", "Masculino");
            serviciosAlquiler.registrarAlquilerCliente(fecha, cl.getDocumento(), it, 8);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Test
    public void deberiaObtenerItemsNoRegresados() {
    	try {
    		serviciosAlquiler.consultarItemsCliente(4);
    	}catch(Exception e) {
    		e.getMessage();
    	}
    }
    
    @Test
    public void deberiaCalcularLaMulta() {
    	try {
    		TipoItem ti = new TipoItem(1,"Deportes");
    		Date fecha =  new Date(2019,10,11);
    		Item it = new Item(ti, 1, "Item1", "Primer Item", fecha, 20000, "", "Masculino");
    		System.out.println(serviciosAlquiler.consultarMultaAlquiler(1, new Date(2019,10,11)));
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    }
}