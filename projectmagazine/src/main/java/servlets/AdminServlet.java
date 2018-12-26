package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Auth;
import models.Basket;
import models.Product;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;
import services.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet {

    private ProductService productService;
    private AuthService authService;
    private UsersService usersService;
    private ContactService contactService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        contactService = (ContactService) servletContext.getAttribute("contactService");
        authService = (AuthService) servletContext.getAttribute("authService");
        productService = (ProductService) servletContext.getAttribute("productService");
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            cookies = new Cookie[0];
        }
        User currentUser = new User();
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("auth")) {
                Auth auth = authService.findByCookieValue(cookie.getValue());
                currentUser = usersService.find(auth.getUser().getId());
            }
        }
        if(usersService.getRoleByUser(currentUser).getName().equals("admin")){
            request.getRequestDispatcher("ftl/adminpanel.ftl").forward(request, response);
        } else {
            response.sendRedirect("/main");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            cookies = new Cookie[0];
        }

        String action = request.getParameter("action");

        System.out.println(request.getParameter("action") + " " + request.getParameter("product_id"));
        if(action.equals("deleteProduct")){
            Long productId = Long.valueOf(request.getParameter("product_id"));
            productService.deleteById(productId);
            response.setStatus(200);
        }
        if(action.equals("addProduct")) {
            Product product = Product.builder().name(request.getParameter("name")).price(request.getParameter("price")).img(request.getParameter("img")).build();
            productService.addProduct(product);
            response.setStatus(200);
        }
        if(action.equals("replyMessage")){
            Long messageId = Long.valueOf(request.getParameter("id_message"));
            contactService.addReply(messageId, request.getParameter("text"));
        }
    }
}

