package com.spring.jwt.services;

import com.spring.jwt.data.SuggestionData;
import com.spring.jwt.data.UserData;
import com.spring.jwt.mapper.SuggestionMapper;
import com.spring.jwt.model.SuggestionEntity;
import com.spring.jwt.model.UserEntity;
import com.spring.jwt.repository.SuggestionRepository;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SuggestionService {

    @Autowired
    private SuggestionRepository suggestionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtils jwtUtils;

    @Transactional
    public SuggestionData createSuggestion(SuggestionData suggestionData, String jwtToken) throws IOException {

        String text = suggestionData.getText();

        SuggestionEntity createdSuggestion = new SuggestionEntity();
        SuggestionData suggestionResponse = new SuggestionData();
        UserEntity userEntity;
        UserData userData = new UserData();

        if (suggestionRepository.existsByText(text)) {
            throw new IOException("Error: Suggestion already exists!");
        }

        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        Optional<UserEntity> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isPresent()) {
            userEntity = userOptional.get();

            createdSuggestion.setText(text);
            createdSuggestion.setUser(userEntity);

            createdSuggestion = suggestionRepository.save(createdSuggestion);

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            suggestionResponse.setText(createdSuggestion.getText());
            suggestionResponse.setUser(userData);
            suggestionResponse.setValidated(createdSuggestion.getValidated());

            return suggestionResponse;
        } else {
            throw new IOException("Error User not found with this jwt token");
        }

    }

    @Transactional
    public SuggestionData getSuggestion(Long suggestionId) {

        SuggestionEntity suggestionEntity;
        SuggestionData suggestionData = new SuggestionData();
        UserEntity userEntity;
        UserData userData = new UserData();

        Optional<SuggestionEntity> suggestion = suggestionRepository.findById(suggestionId);
        if (suggestion.isPresent()) {
            suggestionEntity = suggestion.get();
            userEntity = suggestionEntity.getUser();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            suggestionData.setId(suggestionEntity.getId());
            suggestionData.setText(suggestionEntity.getText());
            suggestionData.setUser(userData);
            suggestionData.setValidated(suggestionEntity.getValidated());

            return suggestionData;
        } else {
            throw new NullPointerException("Suggestion with id " + suggestionId + " not found");
        }
    }

    @Transactional
    public List<SuggestionData> getSuggestions() {
        List<SuggestionData> suggestionDataList = new ArrayList<>();
        List<SuggestionEntity> suggestionEntityList = suggestionRepository.findAll();

        for (SuggestionEntity suggestionEntity : suggestionEntityList) {

            SuggestionData suggestionData = new SuggestionData();
            UserEntity userEntity = suggestionEntity.getUser();
            UserData userData = new UserData();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            suggestionData.setId(suggestionEntity.getId());
            suggestionData.setText(suggestionEntity.getText());
            suggestionData.setUser(userData);
            suggestionData.setValidated(suggestionEntity.getValidated());

            suggestionDataList.add(suggestionData);
        }
        return suggestionDataList;
    }

    @Transactional
    public SuggestionData updateSuggestion(String text, SuggestionData suggestionData) throws IOException {

        SuggestionEntity updatedSuggestionEntity;
        UserEntity userEntity;
        UserData userData = new UserData();


        Optional<SuggestionEntity> optionalSuggestion = suggestionRepository.findByText(text);
        if (optionalSuggestion.isPresent()) {

            Long suggestionId = optionalSuggestion.get().getId();
            updatedSuggestionEntity = suggestionRepository.getReferenceById(suggestionId);

            updatedSuggestionEntity.setText(suggestionData.getText());
            updatedSuggestionEntity.setValidated(suggestionData.getValidated());

            updatedSuggestionEntity = suggestionRepository.save(updatedSuggestionEntity);

            userEntity = updatedSuggestionEntity.getUser();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            suggestionData.setId(updatedSuggestionEntity.getId());
            suggestionData.setText(updatedSuggestionEntity.getText());
            suggestionData.setUser(userData);
            suggestionData.setValidated(updatedSuggestionEntity.getValidated());

            return suggestionData;
        } else {
            throw new IOException("Error Suggestion not found: " + text);
        }

    }

    @Transactional//TODO CHANGE TO SUGESSTIONDATA
    public SuggestionEntity deleteSuggestion(long suggestionId) {
        Optional<SuggestionEntity> suggestion = suggestionRepository.findById(suggestionId);
        if (suggestion.isPresent()) {
            suggestionRepository.delete(suggestion.get());
            return suggestion.get();
        } else {
            throw new NullPointerException("User with id " + suggestionId + " not found");
        }
    }

    @Transactional
    public List<SuggestionData> getSuggestionsOfUserByJwtToken(String jwtToken) throws IOException {

        String userName = jwtUtils.getUserNameFromJwtToken(jwtToken);
        Optional<UserEntity> userOptional = userRepository.findByUsername(userName);

        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            return getSuggestionsOfUserByUserId(user.getId());
        } else {
            throw new IOException("Error User not found with this jwt token");
        }

    }

    @Transactional
    public List<SuggestionData> getSuggestionsOfUserByUserId(Long userId) throws IOException {

        UserEntity userEntity;
        List<SuggestionData> suggestionDataList = new ArrayList<>();
        List<SuggestionEntity> suggestionEntityList;

        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {

            suggestionEntityList = suggestionRepository.findAllByUserId(userId);
            for (SuggestionEntity suggestionEntity : suggestionEntityList) {

                userEntity = suggestionEntity.getUser();
                SuggestionData suggestionData = SuggestionMapper.entityToData(suggestionEntity,userEntity);
                suggestionDataList.add(suggestionData);
            }
            return suggestionDataList;
        } else {
            throw new IOException("User with id " + userId + " not found");
        }


    }

    @Transactional
    public List<SuggestionData> getSuggestionsIsValidated(boolean isValidated) {

        UserEntity userEntity;
        List<SuggestionData> suggestionDataList = new ArrayList<>();
        List<SuggestionEntity> suggestionEntityList;

        suggestionEntityList = suggestionRepository.findAllByIsValidated(isValidated);
        for (SuggestionEntity suggestionEntity : suggestionEntityList) {

            SuggestionData suggestionData = new SuggestionData();
            userEntity = suggestionEntity.getUser();
            UserData userData = new UserData();

            userData.setId(userEntity.getId());
            userData.setUserName(userEntity.getUsername());
            userData.setPassword("******");
            userData.setAuthorities(userEntity.getAuthorities());

            suggestionData.setId(suggestionEntity.getId());
            suggestionData.setText(suggestionEntity.getText());
            suggestionData.setUser(userData);
            suggestionData.setValidated(suggestionEntity.getValidated());

            suggestionDataList.add(suggestionData);
        }
        return suggestionDataList;
    }
}
