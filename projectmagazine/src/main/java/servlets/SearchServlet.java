package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;
import services.LoginServiceImpl;
import services.SearchService;
import services.SearchServiceImpl;
import services.ShopServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet("/search")
public class SearchServlet extends HttpServlet {

    private SearchService service;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        service = (SearchService) servletContext.getAttribute("searchService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String query = req.getParameter("q");
        if (query != null) {
            List<Product> result = service.search(query);
            String resultJson = mapper.writeValueAsString(result);
            resp.setStatus(200);
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(resultJson);
        }


    }
}
