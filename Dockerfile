# syntax=docker/dockerfile:1.24@sha256:87999aa3d42bdc6bea60565083ee17e86d1f3339802f543c0d03998580f9cb89

FROM cgr.dev/chainguard/jre:latest

WORKDIR /app

ARG BUILD_DATE
ARG BUILD_VERSION
ARG BUILD_REVISION

LABEL org.opencontainers.image.title="ipfix-generator" \
      org.opencontainers.image.description="Spring Boot application for generating IPFIX traffic" \
      org.opencontainers.image.url="https://github.com/JFWenisch/ipfix-generator" \
      org.opencontainers.image.source="https://github.com/JFWenisch/ipfix-generator" \
      org.opencontainers.image.documentation="https://github.com/JFWenisch/ipfix-generator/blob/main/README.md" \
      org.opencontainers.image.authors="JFWenisch" \
      org.opencontainers.image.licenses="GPL-3.0" \
      org.opencontainers.image.vendor="JFWenisch" \
      org.opencontainers.image.version="${BUILD_VERSION}" \
      org.opencontainers.image.revision="${BUILD_REVISION}" \
      org.opencontainers.image.created="${BUILD_DATE}"

ENV JAVA_TOOL_OPTIONS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:InitialRAMPercentage=20.0 -XX:+UseG1GC -Djava.security.egd=file:/dev/urandom"

COPY src/target/ipfix-generator-*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
