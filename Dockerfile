# Build stage
FROM gradle:8.14-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle build --no-daemon -x test

# Run stage  
FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

ENV JAVA_OPTS="-XX:+UseSerialGC -XX:MaxRAMPercentage=80.0 -Xss256k"
ENTRYPOINT sh -c "java $JAVA_OPTS -jar app.jar"

