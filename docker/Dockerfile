FROM openjdk:11.0.4-jre-slim

RUN apt-get update && apt-get -y install netcat

EXPOSE 8080 5005

ENV HOME /home
WORKDIR $HOME

COPY run.sh .

COPY sw-portal-backend*.jar sw-portal-backend.jar

CMD ["bash", "./run.sh"]
