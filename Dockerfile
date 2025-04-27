FROM alpine/java:21-jdk


EXPOSE 8083
ARG JAR_FILE=target/pingpong-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]