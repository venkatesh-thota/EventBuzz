FROM openjdk:8-jdk

ADD ./target/stackroute-0.0.1-SNAPSHOT.jar /usr/app/stackroute-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "stackroute-0.0.1-SNAPSHOT.jar"]
