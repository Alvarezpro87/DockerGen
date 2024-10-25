# Base image
FROM php:7.4-apache

ENV DEBIAN_FRONTEND=noninteractive

# Instalação de dependências
RUN apt-get update && \
    apt-get install -y wget unzip && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Baixar e extrair o phpLiteAdmin
RUN wget https://bitbucket.org/phpliteadmin/public/downloads/phpLiteAdmin_v1-9-8-2.zip && \
    unzip phpLiteAdmin_v1-9-8-2.zip -d /var/www/html/ && \
    mv /var/www/html/phpliteadmin.php /var/www/html/index.php && \
    rm phpLiteAdmin_v1-9-8-2.zip

# Expor a porta 80 para o Apache servir o phpLiteAdmin
EXPOSE 89

# Comando para iniciar o Apache (já configurado por padrão na imagem php:7.4-apache)
CMD ["apache2-foreground"]
