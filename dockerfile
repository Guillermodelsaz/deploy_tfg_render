FROM amazoncorretto:11
COPY ./target/demo-0.0.1-SNAPSHOT.jar back.jar

ENTRYPOINT ["java","-jar", "/back.jar"]