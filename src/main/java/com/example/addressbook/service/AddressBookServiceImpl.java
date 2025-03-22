package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.model.AddressBook;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    private final AddressBookRepository addressBookRepository;

    public AddressBookServiceImpl(AddressBookRepository addressBookRepository) {
        this.addressBookRepository = addressBookRepository;
    }

    @Override
    public List<AddressBook> getAllContacts() {
        return addressBookRepository.findAll();
    }

    @Override
    public AddressBook getContactById(int id) {
        return addressBookRepository.findById(id).orElse(null);
    }

    @Override
    public AddressBook addContact(AddressBookDTO contactDTO) {
        AddressBook newContact = new AddressBook(0, contactDTO.getName(), contactDTO.getPhoneNumber(), contactDTO.getEmail());
        return addressBookRepository.save(newContact);
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO contactDTO) {
        return addressBookRepository.findById(id)
                .map(existingContact -> {
                    existingContact.setName(contactDTO.getName());
                    existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
                    existingContact.setEmail(contactDTO.getEmail());
                    return addressBookRepository.save(existingContact);
                })
                .orElse(null);
    }

    @Override
    public void deleteContact(int id) {
        addressBookRepository.deleteById(id);
    }
}
