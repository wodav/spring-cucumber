Feature: User Is Able To Sign In
  Scenario: User Is Able to Sign In with correct password
    Given database is clear
    Given "User" is signed up with "right password"
      When "User" signs in with "right password"
      Then Signin succeeds with "[ROLE_USER, CONFIRMED]"
      And receives "200 OK"
    Then tears down database

#  Scenario: User is not able to Sign In with wrong password
#    Given "User" is signed up with "right password"
#      When "User" signs in with "wrong password"
#      Then Signin fails
#    Then tears down database

  Scenario: Admin Is Able to Sign In with correct password
    When "Admin" signs in with "admins password"
      Then Signin succeeds with "[ROLE_ADMIN, CONFIRMED]"
      And receives "200 OK"
    Then tears down database
