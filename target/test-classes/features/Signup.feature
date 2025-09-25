Feature: Check Signup Functionality

Scenario: Successful signup test

Given User is on the signup Page
When User enters name "Aditya" email "ap1234@test.com" title "Mr." password "Aditya1" Day "10" Month "July" Year "1992" firstname "Aditya" lastname "P" company "ABC" Address "Ye hi hai address" Country "India" State "Maharashtra" City "Solapur" Zipcode "123456" MobileNo "9876543210"
And User clicks on create Account
Then User should see account creation success message
Then User clicks on the continue button
And sees home page
Then user logs out