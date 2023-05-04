FROM amazoncorretto:11
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar back.jar
EXPOSE 8887
ENTRYPOINT ["java","-jar", "/back.jar"]