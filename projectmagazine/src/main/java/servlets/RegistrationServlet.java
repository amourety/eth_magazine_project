package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import forms.LoginForm;
import forms.UserForm;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repositories.AuthRepository;
import repositories.AuthRepositoryImpl;
import repositories.UsersRepository;
import repositories.UsersRepositoryJdbcTemplateImpl;
import services.LoginService;
import services.LoginServiceImpl;
import services.UsersService;
import services.UsersServiceImpl;
import sun.rmi.runtime.Log;

import javax.servlet.RequestDispatcher;
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
import java.util.Optional;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private BCryptPasswordEncoder encoder;
    private UsersService usersService;
    private ObjectMapper mapper = new ObjectMapper();

    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        this.usersService = (UsersService) servletContext.getAttribute("usersService");
        encoder = new BCryptPasswordEncoder();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("ftl/registration.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String first_name = request.getParameter("first_name");
        String surname = request.getParameter("surname");
        String hashPassword = encoder.encode(password);

        UserForm userForm = UserForm.builder().
                name(username).
                hashPassword(hashPassword).
                email(email).
                first_name(first_name).
                surname(surname).
                build();
        if(usersService.findByName(username) == null){
            usersService.addUser(userForm);
            String resultJson = mapper.writeValueAsString(1);
            response.setStatus(200);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        else {
            String resultJson = mapper.writeValueAsString(0);
            response.setStatus(200);
            response.setContentType("application/json");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }

    }
}

