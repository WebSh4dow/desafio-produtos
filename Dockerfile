# Estágio de compilação
FROM maven:3.6.3-openjdk-8 AS build
COPY . .
RUN mvn clean package -DskipTests

# Estágio de pacote
FROM openjdk:8-jdk-slim
COPY --from=build target/produtos-0.0.1-SNAPSHOT.jar /app/produtos.jar
EXPOSE 8080
ENTRYPOINT ["java", "-Xmx512m", "-jar", "/app/produtos.jar", "--server.port=$PORT"]