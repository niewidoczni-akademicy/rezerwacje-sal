FROM adoptopenjdk/openjdk11:alpine as base
RUN set -x && \
    addgroup -g 1000 appuser && \
    adduser -u 1000 -D -G appuser appuser

WORKDIR /app
RUN chown appuser:appuser $(pwd)

USER appuser

COPY --chown=appuser gradlew ./
COPY --chown=appuser gradle ./gradle/
RUN ./gradlew --version
COPY --chown=appuser *.gradle gradle.* ./
RUN ./gradlew build || return 0

FROM base as build
COPY --chown=appuser . .
RUN ./gradlew classes
