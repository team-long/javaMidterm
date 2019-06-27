# Trouble Maker

## Description

This application is a trouble ticket management system. You are allow to create, edit, comment, archive and resolve a trouble ticket.


## Team Long Members

- William Fritts
- Doug Klemp
- Vinh Nyguen
- Anthony Berry

## Problem Domain
- Keeping track of our trouble ticket history.
- not allowed to edit trouble tickets after being created.
- admin do not complete tickets in a timely manner.
- admin do not know which order to complete tickets.

## Solution
- Application allow users to create tickets.
- Application allow users to edit tickets.
- Application allow admin to store all tickets in an archive.
- Application allows users to see open tickets.
- Application notify admin by email when a ticket has been created.
- Application allow users to select severity level of the ticket.


## DEMO
- [Trouble Maker](http://troublemaker.us-west-2.elasticbeanstalk.com/login)

## Test
- [TEST](./src/test/java/com/teamLong/java401d/midterm/troublemaker/TroublemakerApplicationTests.java)

## Work Flow and Process
-

## Success
-

## Growth
-

## API
- ```@RequestMapping(value = "/", method = RequestMethod.GET)```
    - Give you the main page.
- ```@RequestMapping(value = "/user", method = RequestMethod.GET)```
    - Give you the user page.
- ```@RequestMapping(value = "/admin", method = RequestMethod.GET)```
    - Give you the admin page.
- ```@GetMapping("/tickets/all")```
    - Give you a list of all Tickets.
- ```@GetMapping("/ticket/{ticketId}")```
    - Give you a detailed page for one ticket.
- ```@PostMapping("/create/ticket")```
    - Give you the create a ticket page.
- ```@GetMapping("/edit/{id}")```
    - Give you the edit ticket page.
- ```@DeleteMapping("delete/ticket/{id}")```
    - Deletes a ticket from Ticket page.
- ```@RequestMapping(value = "/register", method = RequestMethod.GET)```
    - Give you the Register page.

## Directions
- from Intellij
    - open application
    - run Troublemakerapplication
- from command line
    - TEST
        - ```./gradlew test```
    - start server
        - ``` ./gradlew bootRun```
- deployed
    - [Trouble Maker](http://troublemaker.us-west-2.elasticbeanstalk.com/login)

## Acknowledgments
- Geekforgeeks.com
- bootstrap.com
- stackoverflow.com
- W3schools.com






