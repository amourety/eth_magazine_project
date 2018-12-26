package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import forms.ContactForm;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@WebServlet("/contacts")
public class ContactsServlet extends HttpServlet {

    private ContactService contactService;
    private UsersService usersService;
    private AuthService authService;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        usersService = (UsersService) servletContext.getAttribute("usersService");
        contactService = (ContactService) servletContext.getAttribute("contactService");
        authService = (AuthService) servletContext.getAttribute("authService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user;

        try {
            user = usersService.find(usersService.getCurrentUser(req.getCookies()).getId());
            user.setRole(usersService.getRoleByUser(user));
        } catch (Exception e){
            user = null;
        }
        List<Contact> contacts = contactService.getAnswers(user);
        Map<String, Object> root = new HashMap<>();

        root.put("answers", contacts);
        System.out.println(user);
        if(user != null){
            root.put("user", user);
        } else
            root.put("user",null);

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_22);
        cfg.setServletContextForTemplateLoading(req.getServletContext(), "/ftl");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        Template template = cfg.getTemplate("contacts.ftl");

        try {
            template.process(root, resp.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        req.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie[] cookies = request.getCookies();
        boolean exists = false;

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

        String action = request.getParameter("action");
        System.out.println(action);

        if (action.equals("delete")){
            String answer_id = request.getParameter("answer_id");
            Contact contact = Contact.builder().id(Long.valueOf(answer_id)).build();
            contactService.delete(contact);
            List<Contact> contacts = contactService.getAnswers(currentUser);
            String resultJson = mapper.writeValueAsString(contacts);
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
        if(action.equals("sending")){
            String name = request.getParameter("name");
            String surname = request.getParameter("surname");
            String email = request.getParameter("email");
            String text = request.getParameter("text");

            ContactForm contactForm = ContactForm.builder().
                    name(name).
                    surname(surname).
                    email(email).
                    letter(text).
                    userid(currentUser.getId()).
                    build();
            contactService.addContact(contactForm);
            String resultJson = mapper.writeValueAsString(1);

            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            writer.write(resultJson);
        }
    }
}