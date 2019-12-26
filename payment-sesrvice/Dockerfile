
FROM openjdk:8-jdk

ADD ./target/payment-sesrvice-0.0.1-SNAPSHOT.jar /usr/app/payment-sesrvice-0.0.1-SNAPSHOT.jar

WORKDIR /usr/app

ENTRYPOINT ["java","-jar", "payment-sesrvice-0.0.1-SNAPSHOT.jar"]

