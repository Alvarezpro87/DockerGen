FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y php phpmyadmin
RUN ln -s /usr/share/phpmyadmin /var/www/html/phpmyadmin


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 8085


CMD ["php", "-S", "0.0.0.0:8081", "-t", "/usr/share/phpmyadmin"]
