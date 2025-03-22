package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    private final List<AddressBook> addressBookList = new ArrayList<>();
    private final AtomicInteger idCounter = new AtomicInteger(1);  // Thread-safe ID generator

    @Override
    public List<AddressBook> getAllContacts() {
        return addressBookList;
    }

    @Override
    public AddressBook getContactById(int id) {
        return addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public AddressBook addContact(AddressBookDTO contactDTO) {
        AddressBook newContact = new AddressBook(
                idCounter.getAndIncrement(),
                contactDTO.getName(),
                contactDTO.getPhoneNumber(),
                contactDTO.getEmail()
        );
        addressBookList.add(newContact);
        return newContact;
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO contactDTO) {
        Optional<AddressBook> existingContact = addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst();

        if (existingContact.isPresent()) {
            AddressBook updatedContact = new AddressBook(id, contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail());
            addressBookList.set(addressBookList.indexOf(existingContact.get()), updatedContact);
            return updatedContact;
        }
        return null;
    }

    @Override
    public void deleteContact(int id) {
        addressBookList.removeIf(contact -> contact.getId() == id);
    }
}
