FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/app-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080

ENV SPRING_DATA_MONGODB_DATABASE=gymgoers
ENV SPRING_DATA_MONGODB_URI="mongodb+srv://gg-developer:pass@gymgoers.9eiuj0z.mongodb.net/gymgoers?retryWrites=true&w=majority&ssl=true"
ENV GYMGOER_APP_JWTSECRET="======================GG========================================"
ENV GYMGOER_APP_JWTEXPIRATIONMS=86400000

ENTRYPOINT ["java","-jar","app.jar"]