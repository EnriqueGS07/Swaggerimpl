package org.adaschool.api.controller.user;

import org.adaschool.api.exception.UserNotFoundException;
import org.adaschool.api.repository.user.User;
import org.adaschool.api.repository.user.UserDto;
import org.adaschool.api.service.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users/")
public class UsersController {

    private final UsersService usersService;

    public UsersController(@Autowired UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping
    public ResponseEntity<User> createUser( @RequestBody UserDto user) {
        User user1 = new User(user);
        usersService.save(new User(user));
        URI createdUserUri = URI.create("");
        return ResponseEntity.created(createdUserUri).body(user1);
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        //TODO implement this method
        return ResponseEntity.ok(null);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable("id") String id) {
        return (ResponseEntity.ok(usersService.findById(id).orElseThrow(() -> new UserNotFoundException((id)))));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody UserDto newUSer) {
       usersService.findById(id).orElseThrow(() -> new UserNotFoundException(id)).update(newUSer);
        return ResponseEntity.ok( usersService.save(usersService.findById(id).get()));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (usersService.findById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }else {
            usersService.deleteById(id);
        }
        return ResponseEntity.ok().build();
    }
}
