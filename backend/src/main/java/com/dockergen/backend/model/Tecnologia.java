package com.dockergen.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Tecnologia {
    private String nome;
    private List<String> comandoInstalacao;
    private String cmd;
    private List<Integer> exposedPorts;

}
