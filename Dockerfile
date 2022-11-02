FROM adoptopenjdk/openjdk11:jre-11.0.6_10
COPY build/libs/test-bot-*.jar app.jar
CMD ["java","-jar","app.jar"]
