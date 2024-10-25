package com.dockergen.backend.controller;

import com.dockergen.backend.model.Configuracao;
import com.dockergen.backend.model.ContainerDTO;
import com.dockergen.backend.service.ConfiguracaoService;
import com.dockergen.backend.service.DockerService;
import com.dockergen.backend.service.DockerfileGenerator;
import com.dockergen.backend.service.TecnologiaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/configuracoes")

public class ConfiguracaoController {

    @Autowired
    private ConfiguracaoService configuracaoService;

    @Autowired
    private DockerService dockerService;

    @Autowired
    private DockerfileGenerator dockerfileGenerator;

    @Autowired
    private TecnologiaService tecnologiaService;

    @PostMapping
    public ResponseEntity<String> criarConfiguracao(@RequestBody Configuracao configuracao) {
        try {
            // Verificar campos obrigatórios
            if (configuracao.getNomeContainer() == null || configuracao.getNomeContainer().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome do contêiner é obrigatório.");
            }
            if (configuracao.getNomeProjeto() == null || configuracao.getNomeProjeto().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Nome do projeto é obrigatório.");
            }
            if (configuracao.getTecnologiasSelecionadas() == null || configuracao.getTecnologiasSelecionadas().isEmpty()) {
                return ResponseEntity.badRequest().body("Por favor, selecione uma tecnologia.");
            }

            // Validação das tecnologias selecionadas
            for (String tech : configuracao.getTecnologiasSelecionadas()) {
                if (!tecnologiaService.getTecnologia(tech).isPresent()) {
                    return ResponseEntity.badRequest().body("Tecnologia não suportada: " + tech);
                }
            }

            // Salvar a configuração no banco de dados
            configuracaoService.salvarConfiguracao(configuracao);

            // Gerar o Dockerfile dinamicamente
            String dockerfileContent = dockerfileGenerator.gerarDockerfile(configuracao.getTecnologiasSelecionadas());

            // Caminho para salvar o Dockerfile
            Path path = Paths.get("dockerfiles", configuracao.getNomeProjeto(), "Dockerfile");
            Files.createDirectories(path.getParent());
            Files.writeString(path, dockerfileContent);

            // Construir a imagem Docker e iniciar o contêiner
            String containerId = dockerService.buildAndRunContainer(
                    "dockerfiles/" + configuracao.getNomeProjeto(),
                    configuracao.getNomeProjeto(),
                    configuracao.getNomeContainer(),
                    configuracao.getTecnologiasSelecionadas()

            );

            if (containerId != null) {
                return ResponseEntity.ok("Contêiner iniciado com sucesso. ID: " + containerId);
            } else {
                return ResponseEntity.status(500).body("Erro ao criar e iniciar o ambiente Docker.");
            }

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro ao manipular arquivos: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erro desconhecido: " + e.getMessage());
        }

    }
    // Listar todos os containers
    @GetMapping("/listar")
    public ResponseEntity<List<ContainerDTO>> listarContainers() {
        return ResponseEntity.ok(dockerService.listarContainers());
    }

    // Iniciar um container
    @PostMapping("/iniciar/{containerId}")
    public ResponseEntity<String> iniciarContainer(@PathVariable String containerId) {
        return ResponseEntity.ok(dockerService.iniciarContainer(containerId));
    }

    // Parar um container
    @PostMapping("/parar/{containerId}")
    public ResponseEntity<String> pararContainer(@PathVariable String containerId) {
        return ResponseEntity.ok(dockerService.pararContainer(containerId));
    }

    // Remover um container
    @DeleteMapping("/remover/{containerId}")
    public ResponseEntity<String> removerContainer(@PathVariable String containerId) {
        return ResponseEntity.ok(dockerService.removerContainer(containerId));
    }
    // Pegar o Status do conteiner
    @GetMapping("/status/{containerId}")
    public ResponseEntity<String> getContainerStatust(@PathVariable String containerId) {
        return ResponseEntity.ok(dockerService.getContainerStatus(containerId));
    }

}
