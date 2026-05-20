FROM maven:3-eclipse-temurin-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -q

FROM tomcat:10-jdk17
COPY --from=builder /app/target/lugus-library.war /usr/local/tomcat/webapps/ROOT.war
RUN mkdir -p /usr/local/tomcat/uploads
EXPOSE 8080
CMD ["catalina.sh", "run"]
