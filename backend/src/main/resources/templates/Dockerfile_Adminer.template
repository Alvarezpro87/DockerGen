# Base image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Atualiza o sistema e instala dependências necessárias
RUN apt-get update && \
    apt-get install -y adminer && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Cria o diretório para o Adminer e move o arquivo correto para lá
RUN mkdir -p /var/www/html && ln -s /usr/share/adminer/adminer.php /var/www/html/index.php

# Exponha a porta padrão do Adminer
EXPOSE 8081

# Definir Adminer como o ponto de entrada
CMD ["php", "-S", "0.0.0.0:8081", "-t", "/var/www/html"]