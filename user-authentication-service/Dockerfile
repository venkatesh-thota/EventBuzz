FROM openjdk:8-jdk

ADD ./target/user-authentication-service-0.0.1-SNAPSHOT.jar /usr/app/user-authentication-service-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "user-authentication-service-0.0.1-SNAPSHOT.jar"]
