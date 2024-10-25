# Base image
FROM ubuntu:20.04

ENV DEBIAN_FRONTEND=noninteractive

# Atualiza o sistema e instala dependências necessárias
RUN apt-get update && \
    apt-get install -y wget gnupg lsb-release curl && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Instalação do PostgreSQL
RUN apt-get update && \
    apt-get install -y postgresql postgresql-contrib && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Configuração do PostgreSQL
USER postgres
RUN /etc/init.d/postgresql start && \
    psql --command "CREATE USER admin WITH SUPERUSER PASSWORD 'admin';" && \
    createdb -O admin mydatabase && \
    echo "listen_addresses='*'" >> /etc/postgresql/12/main/postgresql.conf && \
    echo "host all all all md5" >> /etc/postgresql/12/main/pg_hba.conf

USER root
EXPOSE 5435

# Instalação do PgAdmin
RUN curl -fsSLo /usr/share/keyrings/pgadmin.asc https://www.pgadmin.org/static/packages_pgadmin_org.pub && \
    echo "deb [signed-by=/usr/share/keyrings/pgadmin.asc] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" | tee /etc/apt/sources.list.d/pgadmin4.list && \
    apt-get update && \
    apt-get install -y pgadmin4-web && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# Configuração do PgAdmin
RUN /usr/pgadmin4/bin/setup-web.sh --yes && \
    mkdir -p /var/lib/pgadmin && \
    mkdir -p /var/log/pgadmin && \
    chown -R www-data:www-data /var/lib/pgadmin && \
    chown -R www-data:www-data /var/log/pgadmin

# Expondo a porta para PgAdmin
EXPOSE 5050

# Comando final para inicializar o PostgreSQL e o PgAdmin
CMD service postgresql start && /usr/pgadmin4/bin/pgadmin4
