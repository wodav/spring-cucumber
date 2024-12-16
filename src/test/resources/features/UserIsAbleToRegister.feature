Feature: User Is Able To Register

  Scenario: User registers
    Given database is clear
    When "User" signs up with "password"
      Then receives "201 CREATED"
      And "User" receives username
    Then tears down database

  Scenario: User registers with existing username
    When "User" signs up with "password"
      And "User" signs up with "password"
      Then receives "409 CONFLICT"
    Then tears down database