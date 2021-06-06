package pl.edu.pwr.ztw.lyricsQuiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pwr.ztw.lyricsQuiz.model.User;
import pl.edu.pwr.ztw.lyricsQuiz.repository.IUserRepository;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    IUserRepository userRepository;

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/add/user", method = RequestMethod.POST)
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        userRepository.createUser(user);
        return new ResponseEntity<>("User account was created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/addOrNotByData/user/{email}", method = RequestMethod.POST)
    public ResponseEntity<?> createUserOrNot(@PathVariable("email") String email) {

        try{
            User requestedUser = userRepository.getUserByData(email);
            return new ResponseEntity<>("User already exists", HttpStatus.OK);

        }catch (Exception e){
            ArrayList<User> list_of_users = (ArrayList<User>) userRepository.getUsers();
            int current_max_id = list_of_users.get(0).getId();
            for(int i=1;i<list_of_users.size();i++){
                if(list_of_users.get(i).getId()>current_max_id) current_max_id = list_of_users.get(i).getId();
            }
            int new_id = current_max_id +1;
            User newUser = new User(new_id, email);
            userRepository.createUser(newUser);
            return new ResponseEntity<>("User account was created successfully", HttpStatus.CREATED);

        }
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/users", method = RequestMethod.GET)
    public List<User> getUsers(){
        return userRepository.getUsers();
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") int id){
        User requestedUser = userRepository.getUser(id);
        if (requestedUser == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(requestedUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/get/userByData/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserByData(@PathVariable("email") String email){
        User requestedUser = userRepository.getUserByData(email);
        if (requestedUser == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(requestedUser, HttpStatus.OK);
    }

    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
    @RequestMapping(value = "/update/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") int id, @RequestBody User user) {

        if(userRepository.getUser(id) == null){
            return new ResponseEntity<String>("No user account with this id was found",HttpStatus.NOT_FOUND);
        }
        userRepository.updateUser(id, user);
        return new ResponseEntity<>("User account was updated successsfully", HttpStatus.OK);
    }


    @CrossOrigin(origins = {"http://127.0.0.1:5500", "http://localhost:5500"})
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
