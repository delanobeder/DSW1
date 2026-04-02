FROM maven:3.8.5-openjdk-17-slim AS build-stage
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:11.0.4 AS production-stage
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build-stage /app/target/Livraria.war /usr/local/tomcat/webapps/Livraria.war
EXPOSE 8080
CMD ["catalina.sh", "run"]