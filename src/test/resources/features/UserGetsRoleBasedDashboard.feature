Feature: User Gets Role Based Dashboard

  Scenario: Admin Gets Dashboard
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users/me/dashboard"
      Then he gets back My Dashboard: "Admin, ROLE_ADMIN,CONFIRMED"
    Then tears down database

  Scenario: User Gets Dashboard
    Given "User" is signed up with "users password"
    Given "User" is signed in with "users password"
      When "User" calls Endpoint of Dashboard with "/users/me/dashboard"
      Then he gets back My Dashboard: "User, ROLE_USER,CONFIRMED"
    Then tears down database

  Scenario: Moderator Gets Dashboard
    Given "Illi" is signed in with "moderators password"
      When "Illi" calls Endpoint of Dashboard with "/users/me/dashboard"
      Then he gets back My Dashboard: "Illi, ROLE_MODERATOR,CONFIRMED"
    Then tears down database

