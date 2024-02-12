package com.example.contacts.service;

import com.example.contacts.model.Contact;

import java.util.List;


public interface ContactService {

    List<Contact> findAll();

    Contact findById(Long id);

    Contact save(Contact contact);

    Contact update(Contact contact);

    void deleteById(Long id);

    void bathInsert(List<Contact> contactList);
}
