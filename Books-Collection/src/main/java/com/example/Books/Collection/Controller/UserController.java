package com.example.Books.Collection.Controller;

import com.example.Books.Collection.Entity.User;
import com.example.Books.Collection.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody User user){
        return ResponseEntity.ok(userService.save(user));
    }

    @GetMapping("/one")
    public ResponseEntity<Optional> oneUser(@RequestParam int id){
        return ResponseEntity.ok(userService.findById(id));
    }
}
