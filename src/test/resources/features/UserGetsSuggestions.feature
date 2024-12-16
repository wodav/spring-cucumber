Feature: User Gets Suggestions
Scenario: Admin Gets Suggestions of specific User
  Given database is clear
  Given "User" is signed up with "password"
  Given "User" is signed in with "password"
  Given "User" created new suggestion "Suggestion 1"
  Given "User" created new suggestion "Suggestion 2"
  Given "User2" is signed up with "password"
  Given "User2" is signed in with "password"
  Given "User2" created new suggestion "Suggestion 3"
  Given "User2" created new suggestion "Suggestion 4"
  Given "Admin" is signed in with "admins password"
    When "Admin" gets Suggestions of "User"
    Then receives "200 OK"
    And receives list of 2 suggestions

  Scenario: Admin Gets Validated Suggestions
    Given database is clear
    Given "User" is signed up with "password"
    Given "User" is signed in with "password"
    Given "User" created new suggestion "Suggestion 1"
    Given "User" created new suggestion "Suggestion 2"
    Given "User2" is signed up with "password"
    Given "User2" is signed in with "password"
    Given "User2" created new suggestion "Suggestion 3"
    Given "User2" created new suggestion "Suggestion 4"
    Given "User2" created new suggestion "Suggestion 5"
    Given "Illi" is signed in with "moderators password"
      When "Illi" validates "Suggestion 1"
      When "Illi" validates "Suggestion 4"
      When "Illi" validates "Suggestion 5"
    Given "Admin" is signed in with "admins password"
      When "Admin" gets non validated Suggestions
        Then receives "200 OK"
        And receives list of 2 suggestions
      When "Admin" gets validated Suggestions
        Then receives "200 OK"
        And receives list of 3 suggestions

  Scenario: User Gets Own Suggestions
    Given database is clear
    Given "User" is signed up with "password"
    Given "User" is signed in with "password"
    Given "User" created new suggestion "Suggestion 1"
    Given "User" created new suggestion "Suggestion 2"
    Given "User2" is signed up with "password"
    Given "User2" is signed in with "password"
    Given "User2" created new suggestion "Suggestion 3"
    Given "User2" created new suggestion "Suggestion 4"
  #  Given "User" is signed in with "password"
      When "User2" gets own Suggestions
      Then receives "200 OK"
      And receives list of 2 suggestions