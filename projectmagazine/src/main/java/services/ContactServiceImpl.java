package services;

import forms.ContactForm;
import models.Contact;
import models.User;
import repositories.ContactRepository;

import java.util.List;

public class ContactServiceImpl implements ContactService {
    private ContactRepository contactRepository;
    public ContactServiceImpl(ContactRepository contactRepository){ this.contactRepository = contactRepository;}
    @Override
    public void addContact(ContactForm contactForm) {
        Contact contact = Contact.builder().email(contactForm.getEmail()).
                name(contactForm.getName()).surname(contactForm.getSurname()).letter(contactForm.getLetter()).userid(contactForm.getUserid()).build();
        contactRepository.save(contact);

    }
    @Override
    public List<Contact> getAnswers(User user) {
        return contactRepository.findContactForUser(user);
    }

    @Override
    public void delete(Contact contact) {
        contactRepository.remove(contact);
    }

    @Override
    public List<Contact> getMessages() {
        return contactRepository.getMessages();
    }

    @Override
    public void addReply(Long id, String text) {
        contactRepository.addReply(id,text);
    }
}
