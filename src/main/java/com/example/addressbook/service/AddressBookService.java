package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;

import java.util.List;

public interface AddressBookService {
    List<AddressBook> getAllContacts();
    AddressBook getContactById(int id);
    AddressBook addContact(AddressBookDTO contactDTO);
    AddressBook updateContact(int id, AddressBookDTO contactDTO);
    void deleteContact(int id);
}
