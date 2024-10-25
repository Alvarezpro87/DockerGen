# Base image
FROM php:7.4-apache

# Baixar e configurar o PhpLiteAdmin
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://bitbucket.org/phpliteadmin/public/downloads/phpLiteAdmin_v1-9-8-2.zip && \
    unzip phpLiteAdmin_v1-9-8-2.zip -d /var/www/html/ && \
    mv /var/www/html/phpLiteAdmin_v1-9-8-2/phpliteadmin.php /var/www/html/index.php

# Expor a porta padrão do Apache
EXPOSE 89

# Comando de inicialização
CMD ["apache2-foreground"]
