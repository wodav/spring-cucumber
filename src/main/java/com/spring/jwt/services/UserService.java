package com.spring.jwt.services;

import com.spring.jwt.data.UserData;
import com.spring.jwt.model.UserEntity;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public UserData createUser(UserData userData) throws Exception{

        if (userRepository.existsByUsername(userData.getUserName())) {
            throw  new IOException("Error: Username is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(userData.getUserName());
        user.setPassword(passwordEncoder.encode(userData.getPassword()));
        user.setAuthorities("ROLE_USER,CONFIRMED");
        userRepository.save(user);

        return userData;
    }

    @Transactional
    public UserEntity updateUser(long userId, UserEntity user) {
        UserEntity updateUser = userRepository.getReferenceById(userId);
        updateUser.setUsername(user.getUsername());
        updateUser.setPassword(passwordEncoder.encode(user.getPassword()));
        updateUser.setAuthorities(user.getAuthorities());
        userRepository.save(updateUser);
        return updateUser;
    }

    @Transactional
    public void createAdmin(String password) throws Exception{

        String userName="Admin";
        String authorities = "ROLE_ADMIN,CONFIRMED";
        if(userRepository.existsByUsername(userName)){
            throw  new IOException(userName + " already created with authorities " + authorities);
        }

        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthorities(authorities);
        userRepository.save(user);
    }

    @Transactional
    public void createModerator(String userName,String password) throws IOException {

        String authorities = "ROLE_MODERATOR,CONFIRMED";
        if(userRepository.existsByUsername(userName)){
            throw  new IOException(userName + " already created with authorities " + authorities);
        }

        UserEntity user = new UserEntity();
        user.setUsername(userName);
        user.setPassword(passwordEncoder.encode(password));
        user.setAuthorities(authorities);
        userRepository.save(user);
    }

    @Transactional
    public List<UserData> getUsers() {
        List<UserEntity> users =  userRepository.findAll();
        List<UserData> usersData = new ArrayList<>();

        for (UserEntity userEntity : users){

            UserData userData = new UserData();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            usersData.add(userData);
            }

        return usersData;
    }

    @Transactional
    public UserData getUser(Long id) throws NullPointerException{
        UserEntity userEntity;
        UserData userData = new UserData();

        Optional<UserEntity> user =userRepository.findById(id);
        if(user.isPresent()){
            userEntity = user.get();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            return userData;
        }
        else {
            throw new NullPointerException("User with id " + id + " not found");
        }
    }

    @Transactional
    public UserData getUserByUsername(String userName) {
        UserEntity userEntity;
        UserData userData = new UserData();

        Optional<UserEntity> user =userRepository.findByUsername(userName);
        if(user.isPresent()){
            userEntity = user.get();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            return userData;
        }
        else {
            throw new NullPointerException("User with name " + userName + " not found");
        }
    }

    @Transactional
    public UserEntity deleteUser(long id) {

        Optional<UserEntity> user =userRepository.findById(id);
        if(user.isPresent()){
            userRepository.delete(user.get());
            return user.get();
        }
        else {
            throw new NullPointerException("User with id " + id + " not found");
        }
    }

    @Transactional
    public UserData deleteUserByUserName(String userName) {
        Optional<UserEntity> user =userRepository.findByUsername(userName);
        if(user.isPresent()){
            UserEntity deletedUser = user.get();
            userRepository.delete(deletedUser);
            UserData userData = new UserData();
            userData.setId(deletedUser.getId());
            userData.setUserName(userData.getUserName());
            userData.setAuthorities(deletedUser.getAuthorities());
            return userData;
        }
        else {
            throw new NullPointerException("User with name " + userName + " not found");
        }
    }

    @Transactional
    public Optional<UserEntity> getUserByJwtToken(String jwtToken) {

        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        return userRepository.findByUsername(userName);
    }
}
