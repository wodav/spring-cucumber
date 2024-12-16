package com.spring.jwt.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.spring.jwt.data.SuggestionData;
import com.spring.jwt.data.Views;
import com.spring.jwt.model.SuggestionEntity;
import com.spring.jwt.services.SuggestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/suggestions")
public class SuggestionController {

    @Autowired
    SuggestionService suggestionService;

    @PostMapping("")
    @JsonView(Views.Get.class)
    public ResponseEntity createSuggestion(@RequestHeader HttpHeaders headers, @RequestBody @JsonView(Views.Post.class) SuggestionData suggestionData) {

        try {
            String headerAuthorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
            String jwtToken = headerAuthorization.split("Bearer ")[1];

            SuggestionData suggestionResponse = suggestionService.createSuggestion(suggestionData,jwtToken);

            return new ResponseEntity<>(suggestionResponse, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.CONFLICT);
        }

    }

    @GetMapping("")
    @PreAuthorize("hasRole('MODERATOR')")
    @JsonView(Views.GetForAdmin.class)
    public ResponseEntity getSuggestion(@RequestParam(name = "suggestionId", required = false) Long suggestionId,
                                        @RequestParam(name = "isValidated", required = false) Boolean isValidated){
        //TODO regex!!


        if (null!=suggestionId){
            try {
                SuggestionData suggestion = suggestionService.getSuggestion(suggestionId);
                return new ResponseEntity<>(suggestion, HttpStatus.OK);
            }catch (NullPointerException e){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } else if (null!=isValidated) {
            List<SuggestionData> suggestions;
            suggestions = suggestionService.getSuggestionsIsValidated(isValidated);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        } else{
            List<SuggestionData> suggestions;
            suggestions = suggestionService.getSuggestions();
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        }
    }

    @PutMapping("")
    @PreAuthorize("hasRole('MODERATOR')")
    @JsonView(Views.GetForAdmin.class)
    public ResponseEntity<SuggestionData> updateSuggestion(@RequestParam(name ="text") String text, @RequestBody @JsonView(Views.PatchForAdmin.class) SuggestionData suggestionData) {

        try {
            SuggestionData updatedSuggestion = suggestionService.updateSuggestion(text, suggestionData);
            return new ResponseEntity<>(updatedSuggestion, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<SuggestionEntity> deleteSuggestion(@RequestParam(name ="suggestionId") long suggestionId) {
        SuggestionEntity deletedSuggestion = suggestionService.deleteSuggestion(suggestionId);
        return new ResponseEntity<>(deletedSuggestion, HttpStatus.OK);
    }

    @GetMapping("/me")
    public ResponseEntity<List<SuggestionData>> getSuggestionOfThisUser(@RequestHeader HttpHeaders headers) {

        String headerAuthorization = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String jwtToken = headerAuthorization.split("Bearer ")[1];
        try {
            List<SuggestionData> suggestions = suggestionService.getSuggestionsOfUserByJwtToken(jwtToken);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        }catch (IOException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    @JsonView(Views.GetForAdmin.class)
    public ResponseEntity<List<SuggestionData>> getSuggestionOfUser(@RequestParam(name ="userId") long userId) {
        List<SuggestionData> suggestions = null;
        try {
            suggestions = suggestionService.getSuggestionsOfUserByUserId(userId);
            return new ResponseEntity<>(suggestions, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

