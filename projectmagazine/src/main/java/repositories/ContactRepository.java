package repositories;

import models.Contact;
import models.User;

import java.util.List;

public interface ContactRepository extends CrudRepository <Contact>
{
    List<Contact> findContactForUser(User user);
    void remove(Contact contact);
    List<Contact> getMessages();
    void addReply(Long id, String text);
}
