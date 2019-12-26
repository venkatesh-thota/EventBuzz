FROM openjdk:8

ADD ./target/config-server-1.0-SNAPSHOT.jar /usr/src/config-server-1.0-SNAPSHOT.jar
HEALTHCHECK --interval=30s --timeout=30s CMD curl -f http://localhost:8092/actuator/health || exit 1
EXPOSE 8092
WORKDIR usr/src
ENTRYPOINT ["java","-jar","config-server-1.0-SNAPSHOT.jar"]
