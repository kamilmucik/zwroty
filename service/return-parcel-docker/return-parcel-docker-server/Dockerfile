FROM tomcat:9.0.95-jdk8-corretto-al2 AS builder

ARG VERSION
ARG MODULE

COPY target/dependencies/${MODULE}-${VERSION}.war build/ROOT.war

COPY custom build/custom

####################################################
# Build image from builder image
FROM tomcat:9.0.95-jdk8-corretto-al2

ARG VERSION

ENV APP_DIR=returnparcel
ENV APP_NAME="Zwrot Paczek Server"

COPY --from=builder /usr/local/tomcat/build/ROOT.war /usr/local/tomcat/webapps/
COPY --from=builder /usr/local/tomcat/build/custom/ /opt/app/${APP_DIR}
# Set environment variables
#ENV PATH=/opt/app/${APP_DIR}/entrypoint:$PATH

WORKDIR /opt/app/${APP_DIR}

#CMD ["prepareRun"]
