## Movie-App user document


To start application stack execute following command
docker-compose up --build -d


### Database 
(docker image name: sachiniw/movie-db)
Application required pre-defined table structure therefore custom mysql docker 
image has been created with based on ubuntu official docker image

Table name: movies
Coulumns: 
	id, title, original_title, description, studio, tag, genre
	
	
### Spring-boot application(docker image name: sachiniw/movie-app)

|===
|URL |Description
|`http://localhost:8080/movies`
|All movies 
|`http://localhost:8080/movies/add`
|Add movies 
|`http://localhost:8080/movies/edit/{id}`
|Edit movies 
|===

If application is not working then check mySql connection URI in application
properties(movie-app.jar\BOOT-INF\classes\application.properties). 
Application configured to run on 172.17.0.2 and uses default docker local bridge connection.
If require change URI host's IP as prefer. 


### Spring-boot documents(docker image name: sachiniw/movie-doc)
docker run -it -v <your dir>:/documents/ sachiniw/movie-doc





	
