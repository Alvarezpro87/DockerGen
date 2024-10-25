package com.dockergen.backend.service;

import com.dockergen.backend.model.Tecnologia;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class DockerfileGenerator {

    private final TecnologiaService tecnologiaService;
    private final ResourceLoader resourceLoader;

    public DockerfileGenerator(TecnologiaService tecnologiaService, ResourceLoader resourceLoader) {
        this.tecnologiaService = tecnologiaService;
        this.resourceLoader = resourceLoader;
    }

    public String gerarDockerfile(List<String> tecnologiasSelecionadas) throws IOException {
        // Verificar se apenas uma tecnologia foi selecionada
        if (tecnologiasSelecionadas.size() != 1) {
            throw new IllegalArgumentException("Por favor, selecione apenas uma tecnologia.");
        }

        // Obter a tecnologia selecionada
        String tech = tecnologiasSelecionadas.get(0);

        // Determinar o template a ser usado
        String templatePath = "classpath:templates/Dockerfile.template";

        if ("Adminer".equalsIgnoreCase(tech)) {
            templatePath = "classpath:templates/Dockerfile_Adminer.template";  // Usar o template específico do Adminer
        } else if ("Cockpit".equalsIgnoreCase(tech)) {
            templatePath = "classpath:templates/Dockerfile_Cockpit.template";  // Usar o template específico do Cockpit
        } else if ("PhpLiteAdmin".equalsIgnoreCase(tech)) {
            templatePath = "classpath:templates/Dockerfile_Phpliteadmin.template";  // Usar o template específico do Phplite
        }

        // Carregar o template usando ResourceLoader
        Resource resource = resourceLoader.getResource(templatePath);
        InputStream inputStream = resource.getInputStream();
        String template = new Scanner(inputStream, StandardCharsets.UTF_8).useDelimiter("\\A").next();

        // Construir os comandos de instalação, o comando final e as portas a serem expostas
        StringBuilder installations = new StringBuilder();
        String finalCmd = "";
        String exposePorts = "";

        Optional<Tecnologia> tecnologiaOptional = tecnologiaService.getTecnologia(tech);
        if (tecnologiaOptional.isPresent()) {
            Tecnologia tecnologia = tecnologiaOptional.get();

            // Comandos de instalação
            List<String> comandos = tecnologia.getComandoInstalacao();
            for (String comando : comandos) {
                installations.append("RUN ").append(comando).append("\n");
            }

            // Comando CMD
            finalCmd = tecnologia.getCmd();

            // Portas a serem expostas
            if (tecnologia.getExposedPorts() != null && !tecnologia.getExposedPorts().isEmpty()) {
                StringBuilder exposeBuilder = new StringBuilder();
                for (Integer port : tecnologia.getExposedPorts()) {
                    exposeBuilder.append("EXPOSE ").append(port).append("\n");
                }
                exposePorts = exposeBuilder.toString();
            }


        } else {
            System.out.println("Tecnologia não encontrada: " + tech);
        }

        // Substituir os placeholders no template
        template = template.replace("{{INSTALLATIONS}}", installations.toString());
        template = template.replace("{{EXPOSE_PORTS}}", exposePorts);
        template = template.replace("{{CMD}}", finalCmd);

        return template;
    }
}
