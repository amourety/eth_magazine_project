package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import models.Image;
import models.User;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;
import services.UsersService;
import services.UsersServiceImpl;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/img")
public class GetImgServlet extends HttpServlet {
    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }
    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        User user;
        try {
            user = usersService.find(usersService.getCurrentUser(req.getCookies()).getId());
            user.setRole(usersService.getRoleByUser(user));
        } catch (Exception e) {
            user = null;
        }
        Image image = usersService.getLogo(user);

        String contentType = this.getServletContext().getMimeType(image.getImageFileName());

        resp.setHeader("Content-Type", contentType);

        resp.setHeader("Content-Length", String.valueOf(image.getImageData().length));

        resp.setHeader("Content-Disposition", "inline; filename=\"" + image.getImageFileName() + "\"");

        // Write image data to Response.
        resp.getOutputStream().write(image.getImageData());
    }
}
