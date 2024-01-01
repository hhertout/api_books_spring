FROM maven

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN mvn install -DskipTests
RUN mvn compiler:compile

CMD ["mvn", "spring-boot:run"]