FROM adoptopenjdk/openjdk11:alpine as base
WORKDIR /app

RUN set -x && \
    addgroup -g 1000 appuser && \
    adduser -u 1000 -D -G appuser appuser

USER appuser

COPY gradlew ./
COPY gradle ./gradle/
RUN ./gradlew --version
COPY *.gradle gradle.* ./
RUN ./gradlew build || return 0

FROM base as build
COPY . .
RUN ./gradlew classes
