package com.customergroup.application.serivce;

import com.customergroup.domain.Contact;
import com.customergroup.infrastructure.repository.ContactRespository;
import com.customergroup.exception.BadRequestException;
import com.customergroup.exception.ContactNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class ContactService {

    private final ContactRespository contactRespository;

    @Autowired
    public ContactService(ContactRespository contactRespository){
        this.contactRespository = contactRespository;
    }

    public List<Contact> getContacts(){
        return contactRespository.findAll();
    }

    public Contact getContact(long contactId){
        return contactRespository.findById(contactId)
                .orElseThrow(() -> new RuntimeException("Cannot find a contact by the id: " + contactId));
    }

    public void addContact(Contact contact){

        Boolean contactEmail = contactRespository.selectExistsEmail(contact.getEmail());
        Boolean contactPhone = contactRespository.selectExistsPhone(contact.getPhone());

        if(contactEmail){
            throw new BadRequestException("Email " + contact.getEmail() + " already exists!");
        }

        if(contactPhone){
            throw new BadRequestException("Phone number " + contact.getPhone() + " already exists!");
        }

        contactRespository.save(contact);
    }

    public void deleteContact(Long contactId) {
        boolean exists = contactRespository.existsById(contactId);
        if(!exists){
            throw new ContactNotFoundException("Contact with id: " + contactId + " does not exist!");
        }
        contactRespository.deleteById(contactId);
    }

    // Moves entity into managed state
    @Transactional
    public void updateContact(long contactId, String name, String phone, String email, String position) {
        Contact contact = contactRespository.findById(contactId).orElseThrow(
                () -> new ContactNotFoundException("Customer with id " + contactId + " does not exist!")
        );

        if (name != null && name.length() > 0){
            contact.setName(name);
        }

        boolean emailExists = contactRespository.selectExistsEmail(email);

        if(emailExists){
            throw new BadRequestException("Email '" + email + "' is already taken!");
        }

        if (email != null && email.length() > 0){
            contact.setEmail(email);
        }

        if (phone != null && phone.length() > 0){
            contact.setPhone(phone);
        }

        if (position != null && position.length() > 0){
            contact.setPosition(position);
        }

    }

    @Transactional
    public void updateContactByRaw(Contact contact){
        Contact resposContact = contactRespository.findById(contact.getId())
                .orElseThrow(
                        () -> new ContactNotFoundException("Customer with id " + contact.getId() + " does not exist!")
                );
        if (contact.getName() != null && contact.getName().length() > 0 && !Objects.equals(resposContact.getName(), contact.getName())){
            resposContact.setName(contact.getName());
        }
        boolean emailExists = contactRespository.selectExistsEmail(contact.getEmail());
        System.out.println("Email you're trying to insert: " + contact.getEmail());                                               // Debug
        System.out.println("Email of the current person you're inserting: " + resposContact.getEmail());                      // Debug
        System.out.println("Looking for any users with the same email: " + emailExists);                                // Debug
        if(emailExists){
            throw new BadRequestException("Email '" + contact.getEmail() + "' is already taken!");
        }

        if (contact.getEmail() != null && contact.getEmail().length() > 0){
            resposContact.setEmail(contact.getEmail());
        }

        if (contact.getPhone() != null && contact.getPhone().length() > 0){
            resposContact.setPhone(contact.getPhone());
        }

        if (contact.getPosition() != null && contact.getPosition().length() > 0){
            resposContact.setPosition(contact.getPosition());
        }
    }


}
