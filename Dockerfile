# For Java 11, try this
FROM adoptopenjdk/openjdk11:alpine-jre

WORKDIR /opt/app

ARG JAR_FILE=target/prueba-tecnica-0.0.1-SNAPSHOT.jar

# cp prueba-tecnica-0.0.1-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

EXPOSE 8080/tcp

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]