FROM openjdk:8-jdk

ADD ./target/recommendation-service-0.0.1-SNAPSHOT.jar /usr/src/recommendation-service-0.0.1-SNAPSHOT.jar
EXPOSE 8099
WORKDIR usr/src
ENTRYPOINT ["java","-jar","recommendation-service-0.0.1-SNAPSHOT.jar"]
