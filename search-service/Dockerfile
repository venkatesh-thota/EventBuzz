FROM openjdk:8-jdk

ADD ./target/search-service-0.0.1-SNAPSHOT.jar /usr/app/search-service-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "search-service-0.0.1-SNAPSHOT.jar"]

