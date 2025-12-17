FROM elcipse-emurin:25-noble AS build
WORKDIR /src 
COPY ./ ./ 
RUN ./mvnw dependency:go-offline 
RUN ./mvnw package -DskipTests 



FROM eclipse-temurin:25-jre-alpine AS run
WORKDIR /app
COPY --from=build /app/target/ExpenseReport-0.0.1-SNAPSHOT.jar /app/
ENTRYPOINT ["java", "-jar", ]


EXPOSE 8080