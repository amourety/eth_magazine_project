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
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/profile")
@MultipartConfig
public class ProfileServlet extends HttpServlet {

    private UsersService usersService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user;
        try {
            user = usersService.find(usersService.getCurrentUser(req.getCookies()).getId());
            user.setRole(usersService.getRoleByUser(user));
        } catch (Exception e) {
            user = null;
        }
        Map<String, Object> root = new HashMap<>();
        if (user != null) {
            root.put("user", user);
        } else
            root.put("user", null);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setServletContextForTemplateLoading(req.getServletContext(), "/ftl");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        Template template = cfg.getTemplate("profile.ftl");

        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        req.setCharacterEncoding("UTF-8");
        // request.getRequestDispatcher("ftl/index.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("file");
        String fileName = getSubmittedFileName(filePart);
        InputStream fileContent = filePart.getInputStream();
        User user = usersService.find(usersService.getCurrentUser(req.getCookies()).getId());
        try {
            usersService.setLogo(user, fileName, fileContent);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static String getSubmittedFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String fileName = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return fileName.substring(fileName.lastIndexOf('/') + 1).substring(fileName.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}