FROM ghcr.io/graalvm/jdk-community:20.0.1-ol9 AS build
COPY . /build
WORKDIR /build
RUN pwd && ls . && ls /build
RUN /app/mvnw package -Pnative

FROM quay.io/quarkus/quarkus-micro-image:2.0
ARG PORT
ENV PORT=${PORT}
COPY --from=build target/*-runner /work/application

EXPOSE 8080
USER 1001

CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]