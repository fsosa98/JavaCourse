package hr.fer.zemris.java.hw13.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Class StartListener is implementation of servlet context listener.
 * 
 * @author Filip
 */
@WebListener
public class StartListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("startTime", System.currentTimeMillis());
		sce.getServletContext().setAttribute("path", sce.getServletContext().getContextPath());
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
