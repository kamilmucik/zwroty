FROM ubuntu:18.04

MAINTAINER k.mucik@e-strix.pl

RUN mkdir -p /opt/tomcat/

WORKDIR /opt/tomcat


RUN  sed -i.bak -r 's!(deb|deb-src) \S+!\1 mirror://mirrors.ubuntu.com/mirrors.txt!' /etc/apt/sources.list \
  && apt-get update  -y \
  && apt-get install -y software-properties-common \
  && add-apt-repository ppa:openjdk-r/ppa \
  && apt-get install -y openjdk-11-jdk \
  && apt-get install -y curl \
  && apt-get install -y vim \
  && apt-get install -y less \
  && rm -rf /var/lib/apt/lists/*

RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.65/bin/apache-tomcat-9.0.65.tar.gz
RUN tar zxvf apache*.tar.gz
RUN mv apache-tomcat-9.0.65/* /opt/tomcat/.

ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$JAVA_HOME/bin

COPY node_exporter /opt/node_exporter
COPY setenv.sh /opt/tomcat/bin/setenv.sh
COPY entrypoint.sh /opt/entrypoint.sh

EXPOSE 8080 8000 9100
ENTRYPOINT ["/bin/bash", "/opt/entrypoint.sh"]

