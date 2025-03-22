package com.example.addressbook.controller;

import com.example.addressbook.model.AddressBook;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private final List<AddressBook> addressBookList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<AddressBook>> getAllContacts() {
        log.info("Fetching all contacts");
        return ResponseEntity.ok(addressBookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getContactById(@PathVariable int id) {
        log.info("Fetching contact with ID: {}", id);
        return addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Contact with ID {} not found", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<AddressBook> addContact(@RequestBody AddressBook contact) {
        addressBookList.add(contact);
        log.info("Added new contact: {}", contact);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBook> updateContact(@PathVariable int id, @RequestBody AddressBook updatedContact) {
        log.info("Updating contact with ID: {}", id);
        for (int i = 0; i < addressBookList.size(); i++) {
            if (addressBookList.get(i).getId() == id) {
                addressBookList.set(i, updatedContact);
                log.info("Updated contact: {}", updatedContact);
                return ResponseEntity.ok(updatedContact);
            }
        }
        log.warn("Contact with ID {} not found for update", id);
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        log.info("Deleting contact with ID: {}", id);
        boolean removed = addressBookList.removeIf(contact -> contact.getId() == id);
        if (removed) {
            log.info("Deleted contact with ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            log.warn("Contact with ID {} not found for deletion", id);
            return ResponseEntity.notFound().build();
        }
    }
}
