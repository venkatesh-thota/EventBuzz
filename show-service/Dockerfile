FROM openjdk:8-jdk

ADD ./target/show-service-0.0.1-SNAPSHOT.jar /usr/app/show-service-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "show-service-0.0.1-SNAPSHOT.jar"]
