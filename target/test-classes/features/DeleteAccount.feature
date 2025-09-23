Feature: Verify Delete Account Functionality

Scenario: Successful account deletion test
Given User lands on the signup Page
When User enters name "A1" email "a1@z.com" title "Mr." password "Test1" Day "10" Month "July" Year "1992" firstname "Aditya" lastname "P" company "ABC" Address "Ye hi hai address" Country "India" State "Maharashtra" City "Solapur" Zipcode "123456" MobileNo "9876543210"
Then User clicks on create Account
And User should see account creation success message
Then User clicks on the continue button
When just click on delete account
Then verify account delete message
And User clicks on the continue button