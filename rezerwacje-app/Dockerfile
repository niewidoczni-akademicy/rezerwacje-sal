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

FROM build as jar
RUN ./gradlew bootJar

FROM adoptopenjdk/openjdk11:alpine-jre as prod
COPY --from=jar /app/build/libs/rezerwacje-0.0.1-SNAPSHOT.jar/ rezerwacje-backend.jar
ENTRYPOINT [ "java", "-Xmx256m", "-jar", "rezerwacje-backend.jar" ]

