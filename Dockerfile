FROM debian:latest AS builder
MAINTAINER Christophe de Batz <christophe.db@gmail.com>

# update linux
RUN apt upgrade -y && apt update -y

# install usefull binaries
RUN apt install -y wget curl bash postgresql gnupg2

# install the new jdk17
RUN wget https://download.oracle.com/java/17/latest/jdk-17_linux-x64_bin.deb
RUN apt install -y ./jdk-17_linux-x64_bin.deb

# switch from jdk11 to jdk17
ENV JAVA_HOME=/usr/lib/jvm/jdk-17/
ENV PATH=$JAVA_HOME/bin:$PATH

# install maven 3.8.3
RUN wget https://dlcdn.apache.org/maven/maven-3/3.8.4/binaries/apache-maven-3.8.4-bin.tar.gz
RUN tar -zxvf apache-maven-3.8.4-bin.tar.gz
RUN mv apache-maven-3.8.4 /opt/maven

ENV M2_HOME=/opt/maven
ENV PATH=${M2_HOME}/bin:$PATH

# display versions
RUN echo "Successfully installed java version..." && java -version
RUN echo "Successfully installed maven version..." && mvn -v

WORKDIR /app

COPY src/ app/src/
COPY pom.xml app/
COPY .mvn app/
COPY mvnw app/
COPY mvnw.cmd app/

COPY ./bin /app/bin
RUN chmod +x /app/bin/entrypoint.sh
