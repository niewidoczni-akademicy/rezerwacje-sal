FROM adoptopenjdk/openjdk11:alpine
WORKDIR /app

RUN set -x && \
    addgroup -g 1000 appuser && \
    adduser -u 1000 -D -G appuser appuser
