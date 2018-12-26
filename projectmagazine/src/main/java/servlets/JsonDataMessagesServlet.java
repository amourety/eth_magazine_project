package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Auth;
import models.Contact;
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
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

@WebServlet("/messages.json")
public class JsonDataMessagesServlet extends HttpServlet {

    private ObjectMapper mapper = new ObjectMapper();


    private ContactService contactService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        contactService = (ContactService) servletContext.getAttribute("contactService");
    }
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        List<Contact> contacts = contactService.getMessages();
        String resultJson = mapper.writeValueAsString(contacts);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(resultJson);
    }

}
