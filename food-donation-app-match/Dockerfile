FROM openjdk:17-jdk-slim
ADD ./target/food-donation-app-match-0.0.1-SNAPSHOT.jar /usr/src/food-donation-app-match-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "food-donation-app-match-0.0.1-SNAPSHOT.jar"]
# ENTRYPOINT ["java", "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005", "-jar", "user-service-1.0-SNAPSHOT.jar"]
