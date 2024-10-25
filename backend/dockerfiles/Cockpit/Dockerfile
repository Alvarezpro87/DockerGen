# Base image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Instalação de dependências e Cockpit
RUN apt-get update && \
    apt-get install -y cockpit && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Exponha a porta padrão do Cockpit
EXPOSE 9090

# Comando para inicializar o Cockpit
CMD ["/usr/lib/cockpit/cockpit-ws", "--no-tls", "--port", "9090"]