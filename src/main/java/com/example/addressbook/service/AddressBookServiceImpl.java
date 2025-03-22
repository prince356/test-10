package com.example.addressbook.service;

import com.example.addressbook.dto.AddressBookDTO;
import com.example.addressbook.exception.ResourceNotFoundException;
import com.example.addressbook.model.AddressBook;
import com.example.addressbook.repository.AddressBookRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class AddressBookServiceImpl implements AddressBookService {

    private final AddressBookRepository addressBookRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private static final String CACHE_PREFIX = "Contact_";

    public AddressBookServiceImpl(AddressBookRepository addressBookRepository, RedisTemplate<String, Object> redisTemplate) {
        this.addressBookRepository = addressBookRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<AddressBook> getAllContacts() {
        return addressBookRepository.findAll();
    }

    @Override
    public AddressBook getContactById(int id) {
        String cacheKey = CACHE_PREFIX + id;
        AddressBook cachedContact = (AddressBook) redisTemplate.opsForValue().get(cacheKey);
        if (cachedContact != null) {
            return cachedContact;
        }
        AddressBook contact = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with ID " + id + " not found"));
        redisTemplate.opsForValue().set(cacheKey, contact, 10, TimeUnit.MINUTES); // Cache for 10 minutes
        return contact;
    }

    @Override
    public AddressBook addContact(AddressBookDTO contactDTO) {
        AddressBook newContact = new AddressBook();
        newContact.setName(contactDTO.getName());
        newContact.setPhoneNumber(contactDTO.getPhoneNumber());
        newContact.setEmail(contactDTO.getEmail());
        return addressBookRepository.save(newContact);
    }

    @Override
    public AddressBook updateContact(int id, AddressBookDTO contactDTO) {
        AddressBook existingContact = addressBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Contact with ID " + id + " not found"));

        existingContact.setName(contactDTO.getName());
        existingContact.setPhoneNumber(contactDTO.getPhoneNumber());
        existingContact.setEmail(contactDTO.getEmail());

        redisTemplate.delete(CACHE_PREFIX + id); // Remove old cached data
        return addressBookRepository.save(existingContact);
    }

    @Override
    public void deleteContact(int id) {
        if (!addressBookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Contact with ID " + id + " not found");
        }
        addressBookRepository.deleteById(id);
        redisTemplate.delete(CACHE_PREFIX + id);
    }
}
