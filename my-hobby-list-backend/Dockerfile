FROM eclipse-temurin:17-jdk-alpine as builder
WORKDIR /app-backend
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -Dmaven.test.skip=true
 
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app-backend
COPY --from=builder /app-backend/target/*.jar /app-backend/*.jar
ENTRYPOINT ["java", "-jar", "/app-backend/*.jar" ]
