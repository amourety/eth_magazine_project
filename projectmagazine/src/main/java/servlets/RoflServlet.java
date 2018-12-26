package servlets;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import models.Product;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.ProductsRepository;
import repositories.ProductsRepositoryJdbcTemplateImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/rofl")
public class RoflServlet extends HttpServlet {

    ProductsRepository productsRepository;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> products = productsRepository.findAll();
        Map<String, Object> root = new HashMap<>();
        root.put("products", products);
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setServletContextForTemplateLoading(req.getServletContext(), "/ftl");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        Template template =cfg.getTemplate("fff.ftl");
        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void init() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUsername("postgres");
        dataSource.setPassword("di9cbdy4");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/db_11_702");
        productsRepository = new ProductsRepositoryJdbcTemplateImpl(dataSource);
    }
}
