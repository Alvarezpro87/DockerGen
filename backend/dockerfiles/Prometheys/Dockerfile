FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN docker pull prom/prometheus
RUN docker pull prom/alertmanager


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 9090
EXPOSE 9093


CMD ["sh", "-c", "docker run -d --name prometheus -p 9090:9090 prom/prometheus & docker run -d --name alertmanager -p 9093:9093 prom/alertmanager"]
