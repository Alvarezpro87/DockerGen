# Base image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Atualiza o sistema e instala dependências necessárias
RUN apt-get update && \
    apt-get install -y curl gnupg lsb-release && \
    curl -fsSL https://deb.nodesource.com/setup_18.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g mongo-express && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Cria uma pasta de trabalho
WORKDIR /app

# Exponha a porta padrão do Mongo Express
EXPOSE 8081

# Define o comando de inicialização do Mongo Express
CMD ["mongo-express"]
