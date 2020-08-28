# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

# Copy war file
COPY shakespeareanPokemonTranslator-0.0.1-SNAPSHOT.jar /shakespeareanPokemonTranslator.war

# run the app
CMD ["/usr/bin/java", "-jar", "/shakespeareanPokemonTranslator.war"]
