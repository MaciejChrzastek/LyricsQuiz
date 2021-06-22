FROM openjdk:8
EXPOSE 8080
ADD target/lyrics-quiz-docker.jar lyrics-quiz-docker.jar
ENTRYPOINT ["java","-jar","/lyrics-quiz-docker.jar"]