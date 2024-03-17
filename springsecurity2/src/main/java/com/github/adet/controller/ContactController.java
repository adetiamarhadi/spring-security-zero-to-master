package com.github.adet.controller;

import com.github.adet.model.Contact;
import com.github.adet.repository.ContactRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping("/contact")
    @PreFilter("filterObject.contactName != 'Test'")
//    @PostFilter("filterObject.contactName != 'Test'") // only prevent to not perform the response
    public List<Contact> saveContactInquiryDetails(@RequestBody List<Contact> contacts) {

        Contact contact = contacts.get(0);
        contact.setContactId(getServiceReqNumber());
        contact.setCreateDt(new Date(System.currentTimeMillis()));

        Contact contactDb = contactRepository.save(contact);

        List<Contact> response = new ArrayList<>();
        response.add(contactDb);

        return response;
    }

    private String getServiceReqNumber() {
        Random random = new Random();
        int ranNum = random.nextInt(999999999 - 9999) + 9999;
        return "SR"+ranNum;
    }
}
