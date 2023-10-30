
FROM eclipse-temurin:17-jdk-jammy AS base
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:resolve
COPY src ./src

FROM base AS test
CMD ["./mvnw", "test"]

FROM base AS development
CMD ["./mvnw", "spring-boot:run", "-Dspring-boot.run.profiles=mysql", "-Dspring-boot.run.jvmArguments='-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000'"]

FROM base AS build
RUN ./mvnw package


FROM eclipse-temurin:17-jre-jammy AS production
EXPOSE 8080
COPY --from=build /app/target/trendyol-bootcamp-*.jar /trendyol-checkout.jar
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/trendyol-checkout.jar"]