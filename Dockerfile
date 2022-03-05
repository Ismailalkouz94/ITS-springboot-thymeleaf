FROM openjdk:11
EXPOSE 8095
COPY target/*.jar app.jar
CMD [ "java", "-jar", "app.jar" ]