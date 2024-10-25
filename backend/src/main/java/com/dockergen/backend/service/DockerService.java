package com.dockergen.backend.service;

import com.dockergen.backend.model.ContainerDTO;
import com.dockergen.backend.model.Tecnologia;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.BuildImageResultCallback;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.Normalizer;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DockerService {

    private final DockerClient dockerClient;

    @Autowired
    private TecnologiaService tecnologiaService;

    @Autowired
    private DockerfileGenerator dockerfileGenerator;

    public DockerService(TecnologiaService tecnologiaService, DockerfileGenerator dockerfileGenerator) {
        this.tecnologiaService = tecnologiaService;
        this.dockerfileGenerator = dockerfileGenerator;

        DefaultDockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder().build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(config.getDockerHost())
                .sslConfig(config.getSSLConfig())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(90))
                .responseTimeout(Duration.ofSeconds(90))
                .build();

        dockerClient = DockerClientImpl.getInstance(config, httpClient);
    }
    // Método para listar todos os containers
    public List<ContainerDTO> listarContainers() {
        try {
            List<Container> containers = dockerClient.listContainersCmd()
                    .withShowAll(true) // Listar todos, incluindo os parados
                    .exec();


            // Converter os containers para um DTO que seja amigável ao frontend
            return containers.stream().map(container -> new ContainerDTO(
                    container.getId(),
                    container.getState(),
                    container.getPorts(),
                    container.getNames() != null && container.getNames().length > 0 ? container.getNames()[0] : "Sem Nome"
            )).collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    // Método para iniciar um container
    public String iniciarContainer(String containerId) {
        try {
            dockerClient.startContainerCmd(containerId).exec();
            return "Container iniciado com sucesso.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao iniciar o container.";
        }
    }
    // Método para parar um container
    public String pararContainer(String containerId) {
        try {
            dockerClient.stopContainerCmd(containerId).exec();
            return "Container parado com sucesso.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao parar o container.";
        }
    }
    // Método para remover um container
    public String removerContainer(String containerId) {
        try {
            dockerClient.removeContainerCmd(containerId).exec();
            return "Container removido com sucesso.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao remover o container.";
        }
    }

    public String buildAndRunContainer(String dockerfilePath, String imageName, String containerName, List<String> tecnologiasSelecionadas) {
        File baseDir = new File(dockerfilePath);

        try {
            String validImageName = normalizeImageName(imageName);

            String imageId = dockerClient.buildImageCmd(baseDir)
                    .withTags(Set.of(validImageName))
                    .exec(new BuildImageResultCallback() {
                        @Override
                        public void onNext(BuildResponseItem item) {
                            if (item.getStream() != null) {
                                System.out.print(item.getStream());
                            }
                            super.onNext(item);
                        }
                    }).awaitImageId();

            // Passar a lista de tecnologias selecionadas para o runContainer
            return runContainer(imageId, containerName, tecnologiasSelecionadas);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String runContainer(String imageId, String containerName, List<String> tecnologiasSelecionadas) {
        try {
            CreateContainerCmd containerCmd = dockerClient.createContainerCmd(imageId)
                    .withName(containerName);

            // Verificar se precisamos expor e mapear portas
            String tech = tecnologiasSelecionadas.get(0);

            Optional<Tecnologia> tecnologiaOptional = tecnologiaService.getTecnologia(tech);
            if (tecnologiaOptional.isPresent()) {
                Tecnologia tecnologia = tecnologiaOptional.get();

                if (tecnologia.getExposedPorts() != null && !tecnologia.getExposedPorts().isEmpty()) {
                    List<ExposedPort> exposedPorts = new ArrayList<>();
                    Ports portBindings = new Ports();

                    for (Integer port : tecnologia.getExposedPorts()) {
                        ExposedPort tcpPort = ExposedPort.tcp(port);
                        exposedPorts.add(tcpPort);
                        // Mapear a porta do contêiner para a mesma porta no host
                        portBindings.bind(tcpPort, Ports.Binding.bindPort(port));
                    }

                    // Aplicar as configurações de portas ao comando de criação do contêiner
                    containerCmd.withExposedPorts(exposedPorts)
                            .withPortBindings(portBindings);
                }
            }

            // Criar e iniciar o contêiner
            String containerId = containerCmd.exec().getId();
            dockerClient.startContainerCmd(containerId).exec();


            System.out.println("Contêiner iniciado com ID: " + containerId);

            return containerId;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getContainerStatus(String containerId) {
        try {
            return dockerClient.inspectContainerCmd(containerId).exec().getState().getStatus();
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao obter status";
        }
    }

    private String normalizeImageName(String imageName) throws IllegalArgumentException {
        if (imageName == null || imageName.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da imagem não pode ser vazio.");
        }

        String normalized = Normalizer.normalize(imageName, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9.-]", "-");

        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("O nome da imagem resultante não é válido.");
        }

        return normalized;
    }
}
