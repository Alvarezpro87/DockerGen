# Base image
FROM mongo:latest

# Instalação do Mongo Express
RUN apt-get update && apt-get install -y curl && \
    curl -fsSL https://deb.nodesource.com/setup_14.x | bash - && \
    apt-get install -y nodejs && \
    npm install -g mongo-express

# Expor as portas padrão do MongoDB e Mongo Express
EXPOSE 27017
EXPOSE 8081

# Variáveis de ambiente para o Mongo Express
ENV ME_CONFIG_MONGODB_ADMINUSERNAME=admin
ENV ME_CONFIG_MONGODB_ADMINPASSWORD=admin
ENV ME_CONFIG_MONGODB_SERVER=127.0.0.1

# Comando de inicialização
CMD ["mongo-express"]
