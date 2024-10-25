FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y rabbitmq-server
RUN rabbitmq-plugins enable rabbitmq_management


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 5672
EXPOSE 15672


CMD ["/usr/sbin/rabbitmq-server"]
