package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Basket;
import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;
import services.LoginService;
import services.LoginServiceImpl;
import services.ShopService;
import services.ShopServiceImpl;

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

@WebServlet("/main.json")
public class JsonDataServlet extends HttpServlet {

    private LoginService loginService;
    private ShopService shopService;
    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        loginService = (LoginService) servletContext.getAttribute("loginService");
        shopService = (ShopService) servletContext.getAttribute("shopService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            cookies = new Cookie[0];
        }

        Basket basket = shopService.getUserBasket(loginService, cookies);
        String resultJson = mapper.writeValueAsString(basket.getProducts());
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(resultJson);
    }
}