package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Auth;
import models.Contact;
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

@WebServlet("/answers.json")
public class JsonDataAnswersServlet extends HttpServlet {
    private ObjectMapper mapper = new ObjectMapper();

    private AuthService authService;
    private UsersService usersService;
    private ContactService contactService;
    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        contactService = (ContactService) servletContext.getAttribute("contactService");
        authService = (AuthService) servletContext.getAttribute("authService");
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
        List<Contact> contacts = contactService.getAnswers(currentUser);

        String resultJson = mapper.writeValueAsString(contacts);
        response.setStatus(200);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(resultJson);
    }

}
