package com.example.addressbook.controller;

import com.example.addressbook.model.AddressBook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private List<AddressBook> addressBookList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<AddressBook>> getAllContacts() {
        return ResponseEntity.ok(addressBookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBook> getContactById(@PathVariable int id) {
        return addressBookList.stream()
                .filter(contact -> contact.getId() == id)
                .findFirst()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AddressBook> addContact(@RequestBody AddressBook contact) {
        addressBookList.add(contact);
        return ResponseEntity.ok(contact);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBook> updateContact(@PathVariable int id, @RequestBody AddressBook updatedContact) {
        for (int i = 0; i < addressBookList.size(); i++) {
            if (addressBookList.get(i).getId() == id) {
                addressBookList.set(i, updatedContact);
                return ResponseEntity.ok(updatedContact);
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable int id) {
        addressBookList.removeIf(contact -> contact.getId() == id);
        return ResponseEntity.noContent().build();
    }
}
