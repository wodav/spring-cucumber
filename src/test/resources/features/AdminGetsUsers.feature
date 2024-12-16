Feature: Admin Gets Users
  Scenario: Admin gets all Users
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users"
      Then receives list of users
      And receives "200 OK"
    Then tears down database

  Scenario: Admin gets one User
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users?userId=1"
      Then receives one user
      And receives "200 OK"
    Then tears down database

  Scenario: Admin gets non existing User
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users?userId=7"
      Then receives "204 NO_CONTENT"
    Then tears down database

  Scenario: Admin gets one User by username
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users?userName=Admin"
      Then receives one user
      And receives "200 OK"
    Then tears down database

  Scenario: Admin gets non existing User by username
    Given "Admin" is signed in with "admins password"
      When "Admin" calls Endpoint of Dashboard with "/users?userName=notexostonguser"
      Then receives "204 NO_CONTENT"
    Then tears down database

  #Scenario: User gets all Users
  #  Given "User" is signed up with "users password"
  #  Given "User" is signed in with "users password"
  #    When "User" calls Endpoint of Dashboard with "/users"
  #    Then receives "403 Forbidden"
  #  Then tears down database
