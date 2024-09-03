FROM openjdk:17-jdk-slim
ADD ./target/food-donation-app-userservice-0.0.1-SNAPSHOT.jar /usr/src/food-donation-app-userservice-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "food-donation-app-userservice-0.0.1-SNAPSHOT.jar"]
