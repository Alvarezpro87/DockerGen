package com.dockergen.backend.service;

import com.dockergen.backend.model.Tecnologia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TecnologiaService {

    private static final Logger logger = LoggerFactory.getLogger(TecnologiaService.class);

    private final Map<String, Tecnologia> tecnologias = new HashMap<>();

    public TecnologiaService() {

        // Zabbix
        tecnologias.put("Zabbix", new Tecnologia("Zabbix", Arrays.asList(
                "apt-get update",
                "apt-get install -y wget gnupg",
                "wget https://repo.zabbix.com/zabbix/6.4/ubuntu/pool/main/z/zabbix-release/zabbix-release_6.4-1+ubuntu20.04_all.deb",
                "dpkg -i zabbix-release_6.4-1+ubuntu20.04_all.deb",
                "apt-get update",
                "apt-get install -y zabbix-server-mysql zabbix-frontend-php zabbix-apache-conf zabbix-agent",
                // Configurar o Zabbix Frontend
                "sed -i 's/# php_value date.timezone Europe\\/Riga/php_value date.timezone UTC/' /etc/apache2/conf-enabled/zabbix.conf",
                "service apache2 restart"
        ), "CMD [\"/usr/sbin/apache2ctl\", \"-D\", \"FOREGROUND\"]", Arrays.asList(80)));

        // RabbitMQ
        tecnologias.put("RabbitMQ", new Tecnologia("RabbitMQ", Arrays.asList(
                "apt-get update",
                "apt-get install -y rabbitmq-server",
                "rabbitmq-plugins enable rabbitmq_management"
        ), "CMD [\"/usr/sbin/rabbitmq-server\"]", Arrays.asList(5672, 15672)));


        // MySQL
        tecnologias.put("MySQL", new Tecnologia("MySQL", Arrays.asList(
                "apt-get update",
                "DEBIAN_FRONTEND=noninteractive apt-get install -y mysql-server phpmyadmin"
        ), "CMD [\"mysqld_safe\"]", Arrays.asList(3306, 8081)));

        // Adminer
        tecnologias.put("Adminer", new Tecnologia("Adminer", Arrays.asList(
                "apt-get update",
                "apt-get install -y adminer"
        ), "CMD [\"php\", \"-S\", \"0.0.0.0:8080\", \"-t\", \"/usr/share/adminer\"]", Arrays.asList(8081)));

        // Cockpit
        tecnologias.put("Cockpit", new Tecnologia("Cockpit", Arrays.asList(
                "apt-get update",
                "apt-get install -y cockpit"
        ), "CMD [\"/usr/sbin/cockpit-ws\"]", Arrays.asList(9090)));


        // PhpLiteAdmin para PHP com Apache
        tecnologias.put("PhpLiteAdmin", new Tecnologia("PhpLiteAdmin", Arrays.asList(
                "apt-get update",
                "apt-get install -y wget unzip php",
                "wget https://bitbucket.org/phpliteadmin/public/downloads/phpLiteAdmin_v1-9-8-2.zip",
                "unzip phpLiteAdmin_v1-9-8-2.zip -d /var/www/html/",
                "mv /var/www/html/phpLiteadmin.php /var/www/html/index.php",
                "rm phpLiteAdmin_v1-9-8-2.zip"
        ), "apache2-foreground", Arrays.asList(89)));



        // Adicione outras tecnologias conforme necessário
    }

    // Buscar uma tecnologia pelo nome
    public Optional<Tecnologia> getTecnologia(String nome) {
        Optional<Tecnologia> tecnologia = Optional.ofNullable(tecnologias.get(nome));

        if (tecnologia.isEmpty()) {
            logger.warn("Tecnologia não encontrada: {}", nome);
        }

        return tecnologia;
    }

    // Listar todas as tecnologias disponíveis
    public Map<String, Tecnologia> listarTecnologias() {
        return tecnologias;
    }

    // Adicionar uma nova tecnologia dinamicamente
    public void adicionarTecnologia(String nome, List<String> comandosInstalacao, String cmd, List<Integer> exposedPorts) {
        if (tecnologias.containsKey(nome)) {
            logger.warn("Tentativa de adicionar uma tecnologia já existente: {}", nome);
        } else {
            tecnologias.put(nome, new Tecnologia(nome, comandosInstalacao, cmd, exposedPorts));
            logger.info("Tecnologia adicionada: {}", nome);
        }
    }

    // Remover uma tecnologia existente
    public void removerTecnologia(String nome) {
        if (tecnologias.containsKey(nome)) {
            tecnologias.remove(nome);
            logger.info("Tecnologia removida: {}", nome);
        } else {
            logger.warn("Tentativa de remover uma tecnologia não existente: {}", nome);
        }
    }
}
