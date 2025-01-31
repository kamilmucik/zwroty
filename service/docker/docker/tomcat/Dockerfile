FROM --platform=linux/amd64 ubuntu:18.04

RUN mkdir -p /opt/tomcat/

WORKDIR /opt/tomcat

#RUN  sed -i.bak -r 's!(deb|deb-src) \S+!\1 mirror://mirrors.ubuntu.com/mirrors.txt!' /etc/apt/sources.list \
RUN apt-get update  -y \
  && apt-get install -y software-properties-common \
  # && add-apt-repository ppa:openjdk-r/ppa \
  && apt-get install -y openjdk-11-jre-headless \
  && apt-get install -y curl \
  && apt-get install -y vim \
  && apt-get install -y iputils-ping \
  && apt-get install -y less \
  && apt-get install -y tree \
  && apt-get install -y tesseract-ocr \
  && apt-get install -y libtesseract-dev \
  && rm -rf /var/lib/apt/lists/*

# RUN curl -O https://dlcdn.apache.org/tomcat/tomcat-9/v9.0.67/bin/apache-tomcat-9.0.67.tar.gz
RUN curl -O https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.67/bin/apache-tomcat-9.0.67.tar.gz
RUN tar zxvf apache*.tar.gz
RUN mv apache-tomcat-9.0.67/* /opt/tomcat/.
RUN rm -rf /opt/tomcat/webapps/*

# ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64
ENV CATALINA_HOME /opt/tomcat
ENV PATH $PATH:$JAVA_HOME/bin

COPY ./artifacts /opt/artifacts
COPY ./target/ROOT.war /opt/ROOT.war
COPY entrypoint.sh /opt/entrypoint.sh

EXPOSE 8080 8000 9100
ENTRYPOINT ["/bin/bash", "/opt/entrypoint.sh"]
