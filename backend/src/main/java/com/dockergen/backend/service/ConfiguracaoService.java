package com.dockergen.backend.service;

import com.dockergen.backend.model.Configuracao;
import com.dockergen.backend.repository.ConfiguracaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfiguracaoService {

    @Autowired
    private ConfiguracaoRepository configuracaoRepository;

    public Configuracao salvarConfiguracao(Configuracao configuracao) {
        return configuracaoRepository.save(configuracao);
    }

    public List<Configuracao> listarConfiguracoes() {
        return configuracaoRepository.findAll();
    }

    // Outros métodos podem ser adicionados conforme necessário
}
