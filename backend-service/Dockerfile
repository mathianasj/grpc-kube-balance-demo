FROM ghcr.io/graalvm/jdk-community:20.0.1-ol9 AS build
WORKDIR /app
COPY . /app/
RUN ls /app
RUN ./mvnw package

FROM registry.access.redhat.com/ubi8/openjdk-17:1.16
ARG PORT
ENV PORT=${PORT}
ENV LANGUAGE='en_US:en'


# We make four distinct layers so if there are application changes the library layers can be re-used
COPY --from=build --chown=185 /app/target/quarkus-app/lib/ /deployments/lib/
COPY --from=build --chown=185 /app/target/quarkus-app/*.jar /deployments/
COPY --from=build --chown=185 /app/target/quarkus-app/app/ /deployments/app/
COPY --from=build --chown=185 /app/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185

ENTRYPOINT [ "java", "-Dquarkus.http.host=0.0.0.0", "-Dquarkus.http.port=${PORT}", "-Djava.util.logging.manager=org.jboss.logmanager.LogManager", "-XX:MaxRAMPercentage=50.0", "-XX:+UseParallelGC", "-XX:MinHeapFreeRatio=10", "-XX:MaxHeapFreeRatio=20", "-XX:GCTimeRatio=4", "-XX:AdaptiveSizePolicyWeight=90", "-XX:+ExitOnOutOfMemoryError", "-cp", ".", "-jar", "/deployments/quarkus-run.jar" ]