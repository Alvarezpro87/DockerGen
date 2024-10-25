package com.dockergen.backend.model;

import com.github.dockerjava.api.model.ContainerPort;

import lombok.Data;

@Data

public class ContainerDTO {
    private String id;
    private String status;
    private ContainerPort[] ports;
    private String name;

    public ContainerDTO(String id, String status, ContainerPort[] ports, String name) {
        this.id = id;
        this.status = status;
        this.ports = ports;
        this.name = name;
    }


}