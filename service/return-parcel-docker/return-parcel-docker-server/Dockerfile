FROM tomcat:8.5.100-jdk8-temurin-jammy AS builder

ARG VERSION
ARG MODULE

COPY target/dependencies/${MODULE}-${VERSION}.war build/ROOT.war

# Copy custom files for docker
COPY custom build/

####################################################
# Build image from builder image
FROM tomcat:8.5.100-jdk8-temurin-jammy

ARG VERSION

ENV APP_DIR=returnparcel
ENV APP_NAME="Zwrot Paczek Server"

COPY --from=builder /usr/local/tomcat/build/ROOT.war /usr/local/tomcat/webapps/
#COPY --from=builder /usr/local/tomcat/build/ /opt/app/${APP_DIR}
# Map writable temp folders to /tmp
RUN umask 0002 &&  \
    cd /opt/app/${APP_DIR} && mkdir /tmp/${APP_DIR} && ln -s /tmp/${APP_DIR} tmp

ENV CATALINA_HOME /usr/local/tomcat
#ENV PATH $PATH:$JAVA_HOME/bin
# Set environment variables
ENV PATH=$PATH:/opt/app/${APP_DIR}/entrypoint:$JAVA_HOME/bin

WORKDIR /usr/local/tomcat
#WORKDIR /opt/app/${APP_DIR}

#CMD ["prepareRun"]
