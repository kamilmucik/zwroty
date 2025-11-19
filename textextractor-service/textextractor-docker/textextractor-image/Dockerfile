FROM  --platform=linux/amd64 eclipse-temurin:21-jre-alpine AS builder

ARG VERSION
ARG MODULE

COPY target/dependencies/${MODULE}-${VERSION}.jar /opt/build/${MODULE}.jar
COPY custom /opt/build/custom

####################################################
# Build image from builder image
FROM  --platform=linux/amd64 eclipse-temurin:21-jre-alpine

ARG VERSION
ARG MODULE

ENV APP_DIR=d3s
ENV APP_NAME="D3S"

COPY --from=builder /opt/build/${MODULE}.jar /opt/app/${APP_DIR}/
COPY --from=builder /opt/build/custom/ /opt/app/${APP_DIR}
# Set environment variables
ENV PATH=/opt/app/${APP_DIR}/entrypoint:$PATH

WORKDIR /opt/app/${APP_DIR}

#CMD ["prepareRun"]

#CMD ["tail", "-f", "/dev/null"]
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -XX:+ExitOnOutOfMemoryError -Djava.security.egd=file:/dev/./urandom"
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=75.0", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-XX:+ExitOnOutOfMemoryError", "-jar", "/opt/app/d3s/textextractor-starter.jar"]
