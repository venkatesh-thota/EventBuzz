FROM openjdk:10

ADD ./target/eureka-registry-1.0-SNAPSHOT.jar /usr/src/eureka-registry-1.0-SNAPSHOT.jar
EXPOSE 8091
WORKDIR usr/src
ENTRYPOINT ["java","-jar","eureka-registry-1.0-SNAPSHOT.jar"]
