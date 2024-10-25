FROM ubuntu:20.04

# Evitar interações durante a instalação
ENV DEBIAN_FRONTEND=noninteractive

# Instalações
RUN apt-get update
RUN apt-get install -y mysql-server


# Limpar cache do apt para reduzir o tamanho da imagem
RUN apt-get clean && rm -rf /var/lib/apt/lists/*

# Diretório de trabalho
WORKDIR /app

# Copiar arquivos
COPY . /app

# Comando de execução
CMD ["java -jar target/meu-projeto.jar"]
