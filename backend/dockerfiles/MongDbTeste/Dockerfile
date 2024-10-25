FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y mongodb mongo-express


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 27017
EXPOSE 8081


CMD ["mongod", "--bind_ip", "0.0.0.0"]
