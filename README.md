# truelayer-challenge
REST API that returns the shakespearean translation of the description related to a given pokemon name

/*
 * AUTHOR: Loutjie Joubert
 * DATE: 26 AUG 2020
 * PROJECT: Truelayer Italian Challenge
 * DESCRIPTION: Develop a REST API that, given a Pokemon name, returns its shakespearean translation.
 * GITHUB: PRIVATE - https://github.com/LouJou/truelayer-challenge
 */

DESCRIPTION:
- The API is provided as a Java application, with embedded Tomcat server, packaged in a Jar format that can be run on any machine with the Java 8 virtual machine installed
- A version without the embedded server is also provided to be used for deployment to Docker with the supplied DockerFile.
- The API was written in Java using the Eclipse IDE and Maven build.
- The API was originally conceptualized and developed as a Java servlet intended to be run on a Tomcat web server.
- In order to assist in deployment for testing and execution purposes it was modified slightly to use the Spring framework to embed a Tomcat web server.
  This way the application could be deployed as a standalone executable Java jar file without the need for a web server to be installed separately.
- A further modification was introduced for a Docker specific version that removed the embedded web server and deploys the application together with Openjdk-8-Alpine to Docker from where it can be run.
- Once installed the application waits for a GET request at the endpoint: http://localhost:8080/pokemon/{charizard}.
- It retrieves the pokemon name and makes a GET request to "https://pokeapi.co/api/v2/pokemon/{charizard}" for the pokemon description.
- It then makes a POST request to the "https://api.funtranslations.com/translate/shakespeare.json" API for the translation.
  NB: The shakespearean API limits the number of calls to 5 calls per hour.
- A JSON formatted response is returned with the name and translated description.


INSTRUCTIONS:

1. Software Prerequisites:
- Java Virtual Machine version 1.8
- Port 8080 available for web server
- Internet browser installed
- Install Docker for the Docker version

2. Running:

2.1. As standalone Java application with embedded Tomcat web server

- Install the Java 1.8 virtual machine if not already installed.
- Open a command shell window and navigate to the folder where the application files were saved.
- Select the version with the embedded Tomcat web server.
- Run the jar from the command line:
  Example: java -jar shakespeareanPokemonTranslation.jar
- The application should run and start the tomcat server on localhost port 8080.
  NB: Please stop any other application that might be using that port prior to launching the application.
- Open a browser window and navigate to the url: http://localhost:8080/pokemon/charizard - with the pokemon name, "charizard" in this case, inserted after "/pokemon/".
- To view a different Pokemon character's translation simply replace the character's name at the end of the url
- The browser should return a JSON formatted response with the pokemon name and the shakespearean translation.
  Example response:
  {
    "name":"charizard",
    "description":"Spits fire yond is hot enow to melt boulders. Known to cause forest fires unintentionally."
    }

2.2. In Docker as Java application with OpenJDK 1.8 Alpine

  - Install Docker
  - Open a command shell window and navigate to the folder where the application files were saved.
  - Select the Docker version of the application. A dockerFile should be listed in the folder.
  - Run the following docker commands:
    docker build -t shakespearean_pokemon_translator-api .
    - NB: Don't forget the "." at the end
    docker run -p 8080:8080 --name shakespeareanpokemontranslatorapi shakespearean_pokemon_translator-api
  - Open a browser window and navigate to the url: http://localhost:8080/pokemon/charizard - with the pokemon name, "charizard" in this case, inserted after "/pokemon/".
  - To view a different Pokemon character's translation simply replace the character's name at the end of the url
  - The browser should return a JSON formatted response with the pokemon name and the shakespearean translation.
    Example response:
    {
      "name":"charizard",
      "description":"Spits fire yond is hot enow to melt boulders. Known to cause forest fires unintentionally."
      }

TESTING:

1. Testing the REST API

1.1 POSTMAN:
1.1.1 A Postman collection file has been provided with excample GET requests that can be run to view the returned responses.

CREDITS:

Inspiration found and and selected code snippets used from the following sources.
1. Online:
1.1. Embedded Tomcat server and Docker examples:
https://www.captechconsulting.com/blogs/create-an-embedded-server-app-with-tomcat-8
https://medium.com/bb-tutorials-and-thoughts/how-to-dockerize-java-rest-api-3d55ad36b914
https://medium.com/holisticon-consultants/dont-build-fat-jars-for-docker-applications-6252a5571248
https://medium.com/bb-tutorials-and-thoughts/how-to-dockerize-java-rest-api-3d55ad36b914

