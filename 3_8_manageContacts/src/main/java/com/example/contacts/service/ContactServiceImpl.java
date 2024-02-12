package com.example.contacts.service;

import com.example.contacts.model.Contact;
import com.example.contacts.repository.ContactRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public List<Contact> findAll() {
        log.debug("Call findAll in ContactServiceImpl");

        return contactRepository.findAll();
    }

    @Override
    public Contact findById(Long id) {
        log.debug("Call findById in ContactServiceImpl");
        return contactRepository.findById(id).orElseThrow();
    }

    @Override
    public Contact save(Contact contact) {
        log.debug("Call save in ContactServiceImpl");
        return contactRepository.save(contact);
    }

    @Override
    public Contact update(Contact contact) {
        log.debug("Call update in ContactServiceImpl");
        return contactRepository.update(contact);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Call deleteById in ContactServiceImpl");
        contactRepository.deleteById(id);
    }

    @Override
    public void bathInsert(List<Contact> contactList) {
        log.debug("Call bathInsert in ContactServiceImpl");
        contactRepository.batchInsert(contactList);
    }
}
