package services;

import forms.ContactForm;
import models.Contact;
import models.User;

import java.util.List;

public interface ContactService  {
    void addContact(ContactForm contactForm);
    List<Contact> getAnswers(User user);
    void delete(Contact contact);
    List<Contact> getMessages();
    void addReply(Long id, String text);
}
