FROM openjdk:11
ADD . ./src/main/resources/static
ADD target/movie-app.jar movie-app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "movie-app.jar"]