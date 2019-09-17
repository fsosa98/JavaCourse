package hr.fer.zemris.java.hw16.web.init;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.jsdemo.servlets.ImageDB;

/**
 * Class Initialization is initialization class.
 * 
 * 
 * @author Filip
 */
@WebListener
public class Initialization implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ImageDB.initialization(sce.getServletContext().getRealPath("/WEB-INF/opisnik.txt"),
				sce.getServletContext().getRealPath("/WEB-INF/slike"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
