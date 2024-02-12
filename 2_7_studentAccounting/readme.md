# Student accounting
![Java](https://img.shields.io/badge/-Java-0a0a0a?style=for-the-badge&logo=Java) ![Spring](https://img.shields.io/badge/-Spring-0a0a0a?style=for-the-badge&logo=Spring)
<br/>

>The project was created for educational purposes

## Table of contents
* [General info](#General info)
* [Technologies](#Technologies)
* [Status](#status)

## General info
The application allows you to create and edit a list of students. 
The application is launched using Docker by the command:
* docker build -t <app-name> .
* docker run -it <app-name>
</br>

To manage the application, the following commands are used:
* list - display a list of students;
* add - add a new students. The command takes three arguments:
  * Student's name;
  * Student's last name;
  * Student's age;
* del - delete (by id);
* clr - clear the list of students;
* quit, exit - exit the app.


## Technologies
* Java - version 17.0.2
* Spring Boot - version 3.1.5
* Docker

## Status
Project is: _finished_