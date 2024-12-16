package com.spring.jwt.mapper;

import com.spring.jwt.data.SuggestionData;
import com.spring.jwt.data.UserData;
import com.spring.jwt.model.SuggestionEntity;
import com.spring.jwt.model.UserEntity;

public class SuggestionMapper {
    static public SuggestionData entityToData(SuggestionEntity suggestionEntity, UserEntity userEntity){
        SuggestionData suggestionData = new SuggestionData();
        UserData userData = new UserData();

        userData.setId(userEntity.getId());
        userData.setUserName(userEntity.getUsername());
        userData.setPassword("******");
        userData.setAuthorities(userEntity.getAuthorities());//TODO:move to user mapper

        suggestionData.setId(suggestionEntity.getId());
        suggestionData.setText(suggestionEntity.getText());
        suggestionData.setUser(userData);
        suggestionData.setValidated(suggestionEntity.getValidated());

        return suggestionData;
    }
}
