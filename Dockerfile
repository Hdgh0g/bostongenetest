FROM 8-jre-alpine
COPY build/libs/bostongenetest-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]