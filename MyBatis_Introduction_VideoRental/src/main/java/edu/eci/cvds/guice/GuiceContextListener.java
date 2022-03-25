package edu.eci.cvds.guice;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.mybatis.guice.XMLMyBatisModule;
import org.mybatis.guice.datasource.helper.JdbcHelper;
import com.google.inject.Guice;
import com.google.inject.Injector;
import edu.eci.cvds.sampleprj.dao.ClienteDao;
import edu.eci.cvds.sampleprj.dao.ItemDAO;
import edu.eci.cvds.sampleprj.dao.ItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISClienteDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISItemRentadoDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.MyBATISTipoItemDAO;
import edu.eci.cvds.samples.services.ServiciosAlquiler;
import edu.eci.cvds.samples.services.impl.ServiciosAlquilerImpl;
//import edu.eci.cvds.samples.services.impl.ServiciosAlquilerItemsStub;

public class GuiceContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        ServletContext servletContext = servletContextEvent.getServletContext();
        servletContext.removeAttribute(Injector.class.getName());
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Injector injector = Guice.createInjector(new XMLMyBatisModule() {
            @Override
            protected void initialize() {
                install(JdbcHelper.MySQL);
                setEnvironmentId("development");
                setClassPathResource("mybatis-config.xml");

                // TODO Add service class associated to Stub implementation
                bind(ItemDAO.class).to(MyBATISItemDAO.class);
                bind(ClienteDao.class).to(MyBATISClienteDAO.class);
                bind(TipoItemDAO.class).to(MyBATISTipoItemDAO.class);
                bind(ItemRentadoDAO.class).to(MyBATISItemRentadoDAO.class);
                bind(ServiciosAlquiler.class).to(ServiciosAlquilerImpl.class);

            }
        });

        servletContextEvent.getServletContext().setAttribute(Injector.class.getName(), injector);
    }
}
