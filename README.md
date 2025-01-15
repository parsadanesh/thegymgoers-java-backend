# Java Backend Implementation For The GymGoers
This is a Java and Spring boot implementation of The Gymgoers backend.

<details>
    <summary>Table of Content</summary>
    <ol>
        <li><a href="#about-the-project">About The Project</a></li>
        <li><a href="#technologies-used">Technologies Used</a></li>
        <li><a href="#architecture">Architecture</a></li>
    </ol>
</details>

---

## About The Project

For my first full-stack project I created an application called [The GymGoers](https://github.com/parsadanesh/TheGymGoers/blob/main/ProjectREADME.md) using the `MERN` stack (`MongoDB`, `ExpressJS`, `ReactJS`, `NodeJS`).

This project is a `Java` and `Spring` implementation of the backend for The GymGoers. I have been given the opportunity to create a project in Java and Spring Boot to showcase. 

As this was my first time using Spring Boot in general, I wanted to create a project that would really help solidify my 
knowledge and skills with Spring and Spring Boot. By performing a migration of the backend from the MERN stack to Spring Boot 
it provided me with an opportunity to understand the similarities and differences between the frameworks, but also it would give 
me the chance to improve on my previous implementation, and revaluate decision I made previously due to inexperience.

## Technologies Used

Built in `Java 23` using `Spring` and `Maven` with `MongoDB` for the data persistence layer.

Built with the following dependencies: Spring Web, Rest Repositories, Spring Security, OAuth2 Resource Server

## Architecture

Draft - React Frontend, Spring Boot backend, MongoDB Database, using rest controolers HTTP request my from the frontend are handled. The controllers are diffenied so depending on the type of request and the payload the request is routed to a specific controller method. That method talks to the service class which communicates with the repositorty interface which implements the mongoDB repository using spring boot dependence.