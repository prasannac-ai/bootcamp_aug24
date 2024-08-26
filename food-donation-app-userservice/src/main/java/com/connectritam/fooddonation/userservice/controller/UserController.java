package com.connectritam.fooddonation.userservice.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.connectritam.fooddonation.userservice.model.Users;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<Users>> getUsers(

            @RequestParam(defaultValue = "10") int size) {
        Users user = new Users();
        user.setFirstName("Raja");
        user.setLastName("Ram");
        Users user1 = new Users();
        user1.setFirstName("Arun");
        user1.setLastName("Kumar");
        List<Users> userList = new ArrayList<>();

        userList.add(user);
        userList.add(user1);

        return ResponseEntity.ok(userList);
    }


    @PostMapping
    public ResponseEntity<Users> createUser(@RequestBody Users user) {

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(location).body(user);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {

        return ResponseEntity.noContent().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Users> updateUser(@PathVariable UUID id, @RequestBody Users user) {

        return ResponseEntity.ok(user);
    }

}
