FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update
RUN apt-get install -y wget gnupg
RUN wget https://repo.zabbix.com/zabbix/6.4/ubuntu/pool/main/z/zabbix-release/zabbix-release_6.4-1+ubuntu20.04_all.deb
RUN dpkg -i zabbix-release_6.4-1+ubuntu20.04_all.deb
RUN apt-get update
RUN apt-get install -y zabbix-server-mysql zabbix-frontend-php zabbix-apache-conf zabbix-agent
RUN sed -i 's/# php_value date.timezone Europe\/Riga/php_value date.timezone UTC/' /etc/apache2/conf-enabled/zabbix.conf
RUN service apache2 restart


RUN apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . /app

EXPOSE 80


CMD ["/usr/sbin/apache2ctl", "-D", "FOREGROUND"]
