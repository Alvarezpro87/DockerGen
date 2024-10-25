# Base image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Instalação de dependências e Prometheus
RUN apt-get update && \
    apt-get install -y wget tar && \
    wget https://github.com/prometheus/prometheus/releases/download/v2.37.1/prometheus-2.37.1.linux-amd64.tar.gz && \
    tar -xvf prometheus-2.37.1.linux-amd64.tar.gz && \
    mv prometheus-2.37.1.linux-amd64 /etc/prometheus && \
    chmod +x /etc/prometheus/prometheus && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Exponha a porta padrão do Prometheus
EXPOSE 9095

# Define o comando de inicialização do Prometheus
CMD ["/etc/prometheus/prometheus"]
