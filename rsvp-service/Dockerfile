FROM openjdk:8-jdk

ADD ./target/rsvp-service-0.0.1-SNAPSHOT.jar /usr/src/rsvp-service-0.0.1-SNAPSHOT.jar
EXPOSE 8005
WORKDIR usr/src
ENTRYPOINT ["java","-jar","rsvp-service-0.0.1-SNAPSHOT.jar"]

