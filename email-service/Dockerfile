FROM openjdk:8-jdk

ADD ./target/email-service-0.0.1-SNAPSHOT.jar /usr/src/email-service-0.0.1-SNAPSHOT.jar
EXPOSE 8095
WORKDIR usr/src
ENTRYPOINT ["java","-jar","email-service-0.0.1-SNAPSHOT.jar"]

