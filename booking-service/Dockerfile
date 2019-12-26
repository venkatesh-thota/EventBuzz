FROM openjdk:8-jdk

ADD ./target/booking-service-0.0.1-SNAPSHOT.jar /usr/src/booking-service-0.0.1-SNAPSHOT.jar
EXPOSE 10000
WORKDIR usr/src
ENTRYPOINT ["java","-jar","booking-service-0.0.1-SNAPSHOT.jar"]

