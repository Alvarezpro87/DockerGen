package com.dockergen.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "configuracoes")
@Data
public class Configuracao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeProjeto;

    private String nomeImagem;

    private String nomeContainer;

    @ElementCollection
    @CollectionTable(name = "tecnologias_selecionadas", joinColumns = @JoinColumn(name = "configuracao_id"))
    @Column(name = "tecnologia")
    private List<String> tecnologiasSelecionadas;

}
