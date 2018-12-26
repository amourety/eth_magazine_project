import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class MyListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("context.xml");
        UsersService usersService = classPathXmlApplicationContext.getBean(UsersServiceImpl.class);
        ShopServiceImpl shopService = classPathXmlApplicationContext.getBean(ShopServiceImpl.class);
        SearchService searchService = classPathXmlApplicationContext.getBean(SearchServiceImpl.class);
        ProductService productService = classPathXmlApplicationContext.getBean(ProductServiceImpl.class);
        LoginService loginService = classPathXmlApplicationContext.getBean(LoginServiceImpl.class);
        ContactService contactService = classPathXmlApplicationContext.getBean(ContactServiceImpl.class);
        AuthService authService = classPathXmlApplicationContext.getBean(AuthServiceImpl.class);
        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("usersService",usersService);
        servletContext.setAttribute("searchService",searchService);
        servletContext.setAttribute("productService",productService);
        servletContext.setAttribute("loginService",loginService);
        servletContext.setAttribute("contactService",contactService);
        servletContext.setAttribute("shopService",shopService);
        servletContext.setAttribute("authService",authService);


    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
