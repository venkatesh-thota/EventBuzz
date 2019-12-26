FROM openjdk:8-jdk

ADD ./target/ZuulService-0.0.1-SNAPSHOT.jar /usr/src/ZuulService-0.0.1-SNAPSHOT.jar
EXPOSE 8099
WORKDIR usr/src
ENTRYPOINT ["java","-jar","ZuulService-0.0.1-SNAPSHOT.jar"]


