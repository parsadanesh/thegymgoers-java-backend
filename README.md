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
it provided me with an opportunity to understand the similarities and differences between the frameworks. Still, it would also give 
me a chance to improve on my previous implementation and reevaluate my previous decisions made due to inexperience.

## Technologies Used

Built using `Java 23` using `Spring` and `Maven` with `MongoDB` for the data persistence layer.

Built with the following dependencies: Spring Web, Rest Repositories, Spring Security, OAuth2 Resource Server

## Architecture

Draft - React Frontend, Spring Boot backend, MongoDB Database, using rest controllers HTTP request my from the frontend are handled. The controllers are different so depending on the type of request and the payload the request is routed to a specific controller method. That method talks to the service class which communicates with the repository interface which implements the mongoDB repository using spring boot dependence.

## Using the app

You can use my application in a couple of ways.

To begin with, I have deployed my application to the cloud, I used Netlify to deploy the frontend and, I used Docker to containerise my backend to deploy on Render. This is the link: [webapp](https://thegymgoerapp.netlify.app). I am using a free tier for the cloud services so the backend server spins down after a period of no use, so when accessing the website you will potentially have to wait a couple of minutes for the services to start functioning.

Another option is downloading this Git repo and running the projects manually. I use VSCode to develop the frontend and IntelliJ to develop the backend. 
To run the frontend (you must be in the same directory as the package.json when doing this):
### Install Dependencies
- Once in the correct directory type, `npm install`
### Run The Development Server
- Type: `npm run dev`
## Run Tests
- Type: `npm run coverage`

To run the backend you can either use an IDE and run the main method or use docker:
### Clone the Repository
- `git clone <repository-url>`
- `cd <repository-directory>`

## Using Docker 
### Build the Docker image
- Type: `docker build -t parsadanesh20/app:latest .`
### Run the Docker Container
- Type: `docker run -p 8080:8080 parsadanesh20/app:latest`

## Using Maven
### Build Project
- `mvn clean install`
### Run the Application
- `mvn spring-boot:run`
