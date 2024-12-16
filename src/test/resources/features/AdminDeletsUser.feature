Feature: Admin Deletes User
  Scenario: Admin deletes existing User
    Given "User" is signed up with "users password"
    Given "Admin" is signed in with "admins password"
      When Admin deletes "User"
      Then receives "200 OK"
    Then tears down database

  Scenario: Admin deletes existing User
    Given "User" is signed up with "users password"
    Given "Admin" is signed in with "admins password"
      When Admin deletes "User"
      Then receives "200 OK"
    Then tears down database