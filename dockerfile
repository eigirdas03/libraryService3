FROM maven:3.8-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

 
FROM openjdk:17
WORKDIR /app
EXPOSE 80
COPY --from=builder /app/target/*.jar /app/libraryService.jar
ENTRYPOINT ["java","-jar","/app/libraryService.jar"]