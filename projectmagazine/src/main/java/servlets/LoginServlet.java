package servlets;

import models.Auth;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import forms.LoginForm;
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
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UsersService usersService;
    private LoginService service;
    private AuthService authService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        service = (LoginService) servletContext.getAttribute("loginService");
        authService = (AuthService) servletContext.getAttribute("authService");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("ftl/login.ftl").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        LoginForm loginForm = LoginForm.builder()
                .name(name)
                .password(password)
                .build();

        Optional<String> optionalCookieValue = service.login(loginForm);


        if (optionalCookieValue.isPresent()) {
            Cookie cookie = new Cookie("auth", optionalCookieValue.get());
            resp.addCookie(cookie);
            Auth auth = authService.findByCookieValue(cookie.getValue());
            String role = "";
            try {
                role = usersService.getRoleByUser(usersService.find(auth.getUser().getId())).getName();
            }
            catch (Exception e){
                System.out.println("пользователя нет");
            }
            if(role.equals("admin")){
                resp.setStatus(201);
                resp.sendRedirect("/admin");
            } else {
                resp.setStatus(201);
                resp.sendRedirect("/main");
            }
        } else {
            resp.setStatus(200);
            resp.sendRedirect("/login");
        }
    }
}