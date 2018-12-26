package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.*;
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
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/orders.json")
public class JsonDataUserOrdersServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();

    private AuthService authService;
    private UsersService usersService;
    private ShopService shopService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        authService = (AuthService) servletContext.getAttribute("authService");
        shopService = (ShopService) servletContext.getAttribute("shopService");
    }

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
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
        Basket basket = shopService.findByOwnerId(currentUser.getId());
        List<Order> orders = shopService.getUserOrders(currentUser);
        Basket[] baskets = new Basket[orders.size()];
        int i = 0;
        for(Order order: orders){
            baskets[i] = basket;
            baskets[i].setOrder_id(order.getId());
            baskets[i].setTrack(shopService.getTrack(order));
            baskets[i].setProducts(shopService.getProductsByOrder(basket,order));
            System.out.println(baskets[i]);
            i++;
        }

        String resultJson = mapper.writeValueAsString(baskets);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(resultJson);
    }

}
