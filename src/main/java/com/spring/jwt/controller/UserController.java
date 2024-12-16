package com.spring.jwt.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.jwt.data.UserData;
import com.spring.jwt.data.Views;
import com.spring.jwt.model.UserEntity;
import com.spring.jwt.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(Views.GetForAdmin.class)
    public ResponseEntity createUser(@RequestBody @JsonView(Views.PostForAdmin.class)UserData userData) {
        try {
            UserData createdUser = userService.createUser(userData);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(Views.GetForAdmin.class)
    public ResponseEntity getUser(@RequestParam(name = "userId", required = false) Long userId,
                                  @RequestParam(name = "userName", required = false) String userName) {
        //TODO regex!!


        if (null!=userId){
            try {
                UserData user = userService.getUser(userId);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (NullPointerException e){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } else if (null!=userName) {
            try {
                UserData user = userService.getUserByUsername(userName);
                return new ResponseEntity<>(user, HttpStatus.OK);
            }catch (NullPointerException e){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } else{
            List<UserData> users;
            users = userService.getUsers();
            return new ResponseEntity<>(users, HttpStatus.OK);
        }
    }

    @PatchMapping("")
    public ResponseEntity<UserEntity> updateUser(@RequestParam(name ="userId") long userId, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(userId, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("")
    @JsonView(Views.Get.class)
    public ResponseEntity<UserData> deleteUser(@RequestParam(name ="userName") String userName) {
        UserData deletedUser = userService.deleteUserByUserName(userName);
        return new ResponseEntity<>(deletedUser, HttpStatus.OK);
    }

    @RequestMapping("/me/dashboard")
    public String getUserDashboard(@RequestHeader HttpHeaders headers) {

        String headerAuthorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String jwtToken = headerAuthorization.split("Bearer ")[1];

        Optional<UserEntity> userOptional = userService.getUserByJwtToken(jwtToken);

        UserEntity user = userOptional.get();
        return "My Dashboard: " + user.getUsername() + ", " + user.getAuthorities();
    }

    @RequestMapping("/{id}/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String getUserDashboardById(@PathVariable String id) {
        return "My User Dashboard";
    }

}
