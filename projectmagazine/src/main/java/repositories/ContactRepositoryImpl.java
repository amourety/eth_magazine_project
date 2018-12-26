package repositories;

import models.Contact;
import models.Product;
import models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.util.List;

public class ContactRepositoryImpl implements ContactRepository {
    private JdbcTemplate jdbcTemplate;
    private ContactRepository contactRepository;
    //language=SQL
    private static final String SQL_INSERT_CONTACT =
            "insert into contacts(user_id,first_name,surname,email,letter) values (?,?,?,?,?)";
    //language=SQL
    private static final String SQL_FIND_READING_WITH_ANSWER =
            "select * from contacts where (contacts.answer is not null and contacts.isreading is Null or False) and contacts.user_id = ?";

    public ContactRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private RowMapper<Contact> contactRowMapper = (resultSet, i) -> Contact.builder()
            .id(resultSet.getLong("id"))
            .userid(resultSet.getLong("user_id"))
            .name(resultSet.getString("first_name"))
            .surname(resultSet.getString("surname"))
            .email(resultSet.getString("email"))
            .answer(resultSet.getString("answer"))
            .letter(resultSet.getString("letter"))
            .isReading(resultSet.getBoolean("isreading"))
            .build();

    @Override
    public List<Contact> findAll() {
        return null;
    }

    @Override
    public Contact find(Long id) {
        return null;
    }

    @Override
    public List<Contact> findContactForUser(User user) {
        try {
            return jdbcTemplate.query(SQL_FIND_READING_WITH_ANSWER, contactRowMapper, user.getId());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Contact> getMessages() {
        //language=SQL
        String SQL_SELECT_MESSAGES = "select * from contacts where (contacts.answer is null)";
        return jdbcTemplate.query(SQL_SELECT_MESSAGES, contactRowMapper);
    }

    @Override
    public void remove(Contact contact) {
        //language=SQL
        String qDeleteItem = "UPDATE contacts SET isreading = true WHERE id = ?";
        jdbcTemplate.update(qDeleteItem, contact.getId());
    }

    @Override
    public void addReply(Long id, String text) {
        //language=SQL
        String qDeleteItem = "UPDATE contacts SET answer = ? where id = ?";
        jdbcTemplate.update(qDeleteItem, text, id);
    }

    @Override
    public void save(Contact model) {

        jdbcTemplate.update(SQL_INSERT_CONTACT,model.getUserid(),model.getName(), model.getSurname(),model.getEmail(),model.getLetter());
    }

    @Override
    public void update(Contact model) {


    }
}
