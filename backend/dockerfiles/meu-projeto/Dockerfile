FROM ubuntu:20.04

# Evitar interações durante a instalação
ENV DEBIAN_FRONTEND=noninteractive

# Instalações
RUN apt-get update
RUN apt-get install -y openjdk-21-jdk
RUN apt-get update
RUN apt-get install -y nodejs


# Limpar cache do apt para reduzir o tamanho da imagem
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos (se necessário)
COPY . /app

# Comando de execução, baseado na tecnologia escolhida
CMD ["node /app/index.js"]
