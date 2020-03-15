FROM openjdk:11.0.4-jdk-slim AS BUILD_IMAGE
COPY . .
RUN ./gradlew --info build

FROM openjdk:11.0.4-jre-slim

RUN apt-get update && apt-get -y install netcat
EXPOSE 8080 5005
ENV HOME /home
WORKDIR $HOME
COPY run.sh .
COPY --from=BUILD_IMAGE build/libs/sw-portal-backend-1.0.0-SNAPSHOT.jar sw-portal-backend.jar

CMD ["bash", "./run.sh"]
