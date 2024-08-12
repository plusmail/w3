package kroryi.w3.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.extern.log4j.Log4j2;


@WebListener
@Log4j2
public class AppListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {

        log.info("----------------------init------------------------");
        log.info("----------------------init------------------------");
        log.info("----------------------init------------------------");
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("appName", "웹개발 초기버전");

    }

    public void contextDestroyed(ServletContextEvent sce) {
        log.info("----------------------destory------------------------");
        log.info("----------------------destory------------------------");
        log.info("----------------------destory------------------------");

    }
}
