Feature: User Creates Suggestions
  Scenario: User Creates Suggestion
    Given database is clear
    Given "User" is signed up with "users password"
    Given "User" is signed in with "users password"
      When "User" creates new Suggestion "My suggestion 1"
      Then receives "201 CREATED"
      And receives suggestion "My suggestion 1"
    Then tears down database

#  Scenario: User tries to Validates Suggestion
#    Given "User" is signed up with "users password"
#    Given "User" is signed in with "users password"
#      When "User" validates "Suggestion 1"
#      Then receives "403 FORBIDDEN"
#    Then tears down database

  Scenario: Moderator Validates Suggestion
    Given database is clear
    Given "User" is signed up with "password"
    Given "User" is signed in with "password"
    Given "User" created new suggestion "Suggestion 1"
    Given "User" created new suggestion "Suggestion 2"
    Given "Illi" is signed in with "moderators password"
      When "Illi" validates "Suggestion 1"
      When "Illi" validates "Suggestion 2"
      Then receives "200 OK"
    Then tears down database
