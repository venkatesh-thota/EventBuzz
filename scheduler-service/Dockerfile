FROM openjdk:8-jdk

ADD ./target/scheduler-service-0.0.1-SNAPSHOT.jar /usr/app/scheduler-service-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "scheduler-service-0.0.1-SNAPSHOT.jar"]