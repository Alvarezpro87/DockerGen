FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y curl
RUN curl -fsSL https://get.docker.com | sh
RUN docker pull portainer/portainer-ce


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 9000


CMD ["/portainer", "--no-auth", "-H", "unix:///var/run/docker.sock"]
