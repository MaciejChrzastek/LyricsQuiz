package pl.edu.pwr.ztw.lyricsQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.lyricsQuiz.model.User;
import pl.edu.pwr.ztw.lyricsQuiz.repository.IUserRepository;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    IUserRepository userRepository;


    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userRepository.createUser(user);
        return new ResponseEntity<>("User account was created successfully", HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userRepository.getUsers();
    }


    @RequestMapping(value = "/get/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id){
        User requestedUser = userRepository.getUser(id);
        if (requestedUser == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(requestedUser, HttpStatus.OK);
    }


    @RequestMapping(value = "/update/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {

        if(userRepository.getUser(id) == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        userRepository.updateUser(id, user);
        return new ResponseEntity<>("User account was updated successsfully", HttpStatus.OK);
    }



    @RequestMapping(value = "/delete/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") int id) {
        User to_be_deleted_user = userRepository.getUser(id);
        if(to_be_deleted_user == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        userRepository.deleteUser(id);
        return new ResponseEntity<>("User account was deleted successsfully", HttpStatus.OK);
    }







}
