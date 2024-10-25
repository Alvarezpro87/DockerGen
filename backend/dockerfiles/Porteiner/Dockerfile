FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y curl
RUN curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
RUN echo "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" > /etc/apt/sources.list.d/docker.list
RUN apt-get update
RUN apt-get install -y docker-ce docker-ce-cli containerd.io


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 9000


CMD ["/portainer"]
