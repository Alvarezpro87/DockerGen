FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 3306


CMD ["mysqld_safe"]
