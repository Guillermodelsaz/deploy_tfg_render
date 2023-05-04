FROM amazoncorretto:11
COPY demo-0.0.1-SNAPSHOT.jar back.jar
EXPOSE 8887
ENTRYPOINT ["java","-jar", "/back.jar"]