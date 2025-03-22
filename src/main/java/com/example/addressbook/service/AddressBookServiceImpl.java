package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.exception.ResourceNotFoundException;
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
        return addressBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with ID " + id + " not found"));
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO contactDTO) {
        AddressBook existingContact = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with ID " + id + " not found"));

        existingContact.setName(contactDTO.getName());
        existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
        existingContact.setEmail(contactDTO.getEmail());
        return addressBookRepository.save(existingContact);
    }

    @Override
    public void deleteContact(int id) {
        if (!addressBookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact with ID " + id + " not found");
        }
        addressBookRepository.deleteById(id);
    }
}
