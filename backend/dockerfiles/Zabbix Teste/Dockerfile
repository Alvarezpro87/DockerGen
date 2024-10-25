FROM ubuntu:20.04

# Evitar interações durante a instalação
ENV DEBIAN_FRONTEND=noninteractive

# Instalações
RUN apt-get update
RUN apt-get install -y wget gnupg
RUN wget https://repo.zabbix.com/zabbix/6.4/ubuntu/pool/main/z/zabbix-release/zabbix-release_6.4-1+ubuntu20.04_all.deb
RUN dpkg -i zabbix-release_6.4-1+ubuntu20.04_all.deb
RUN apt-get update
RUN apt-get install -y zabbix-server-mysql zabbix-frontend-php zabbix-agent


# Limpar cache do apt para reduzir o tamanho da imagem
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos
COPY . /app

# Comando de execução
CMD ["java -jar target/meu-projeto.jar"]
