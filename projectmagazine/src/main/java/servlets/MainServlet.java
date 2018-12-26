package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/main")
public class MainServlet extends HttpServlet {

    private LoginService loginService;
    private ShopService shopService;
    private UsersService usersService;
    private ProductService productService;
    private AuthService authService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        loginService = (LoginService) servletContext.getAttribute("loginService");
        shopService = (ShopService) servletContext.getAttribute("shopService");
        usersService = (UsersService) servletContext.getAttribute("usersService");
        productService = (ProductService) servletContext.getAttribute("productService");
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Product> products = productService.findAll();
        User user;
        try {
            user = usersService.find(usersService.getCurrentUser(req.getCookies()).getId());
            user.setRole(usersService.getRoleByUser(user));
        } catch (Exception e){
            user = null;
        }
        Map<String, Object> root = new HashMap<>();
        root.put("products", products);
        System.out.println(user);
        if(user != null){
            root.put("user", user);
        } else
            root.put("user",null);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setServletContextForTemplateLoading(req.getServletContext(), "/ftl");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        Template template = cfg.getTemplate("index.ftl");

        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        req.setCharacterEncoding("UTF-8");
        // request.getRequestDispatcher("ftl/index.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            cookies = new Cookie[0];
        }
        Basket basket;

        String action = request.getParameter("action");

        System.out.println(request.getParameter("action") + " " + request.getParameter("product_id"));
        if(action.equals("exit")){
            System.out.println("exit");
            authService.deleteCookieByUserId(usersService.find(usersService.getCurrentUser(request.getCookies()).getId()));
            response.setStatus(200);
        }
        if(action.equals("delete")){
            Long productId = Long.valueOf(request.getParameter("product_id"));
            basket = shopService.delete(productId,cookies,loginService);
            response.setStatus(200);
            response.setContentType("application/json");
            String resultJson = mapper.writeValueAsString(basket.getProducts());
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        if(action.equals("buy")) {
            Long productId = Long.valueOf(request.getParameter("product_id"));
            basket = shopService.buy(productId, cookies, loginService);
            String resultJson = mapper.writeValueAsString(basket.getProducts());
            System.out.println(resultJson);
            response.setStatus(200);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        if(action.equals("deleteAll")) {
            basket = shopService.deleteAll(cookies, loginService);
            String resultJson = mapper.writeValueAsString(basket.getProducts());
            response.setStatus(200);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        if(action.equals("addOrder")) {
            shopService.addOrder(cookies,loginService);
            basket = shopService.deleteAll(cookies, loginService);
            String resultJson = mapper.writeValueAsString(basket);
            response.setStatus(200);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        if(action.equals("deleteOrder")){
            Long orderId = Long.valueOf(request.getParameter("order_id"));
            shopService.deleteOrder(orderId);
            response.setStatus(200);
        }
    }
}