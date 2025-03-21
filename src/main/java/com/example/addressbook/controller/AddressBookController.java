package com.addressbook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    private final List<String> addressBookList = new ArrayList<>();

    @GetMapping
    public ResponseEntity<List<String>> getAllContacts() {
        return ResponseEntity.ok(addressBookList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getContactById(@PathVariable int id) {
        if (id >= 0 && id < addressBookList.size()) {
            return ResponseEntity.ok(addressBookList.get(id));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }

    @PostMapping
    public ResponseEntity<String> addContact(@RequestParam String name) {
        addressBookList.add(name);
        return ResponseEntity.status(HttpStatus.CREATED).body("Entry added successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateContact(@PathVariable int id, @RequestParam String name) {
        if (id >= 0 && id < addressBookList.size()) {
            addressBookList.set(id, name);
            return ResponseEntity.ok("Entry updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable int id) {
        if (id >= 0 && id < addressBookList.size()) {
            addressBookList.remove(id);
            return ResponseEntity.ok("Entry deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Entry not found");
        }
    }
}
