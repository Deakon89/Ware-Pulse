# Stage 1: build del JAR con Maven
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml mvnw ./
COPY .mvn ./.mvn
COPY src ./src

# Rendi eseguibile lo wrapper Maven
RUN chmod +x mvnw

# Build senza test per velocizzare (ma puoi rimuovere -DskipTests)
RUN ./mvnw clean package -DskipTests

# Stage 2: esegui il JAR
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/ware-pulse-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS=""
ENV PORT=${PORT:-8080}
# espone la porta che Railway fornisce via $PORT
EXPOSE ${PORT}
ENTRYPOINT ["sh","-c","exec java $JAVA_OPTS -jar /app/app.jar --server.port=$PORT"]
