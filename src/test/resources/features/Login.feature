Feature: Login functionality

  Scenario: Successful login with valid credentials
    Given User is on the Login Page
    When User enters email "aditya@p.com" and password "AdityaPass123"
    And Clicks on the login button
    Then User should see the homepage
    Then User should log out

  Scenario: Unsuccessful login with invalid credentials
    Given User is on the Login Page
    When User enters email "invalid@p.com" and password "wrongPass"
    And Clicks on the login button
    Then User should see a login error message
