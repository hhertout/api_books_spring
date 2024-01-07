FROM maven

RUN apt update && apt install -y inotify-tools

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN mvn install -DskipTests
RUN mvn compiler:compile

COPY run.sh run.sh
