package com.dockergen.backend.repository;

import com.dockergen.backend.model.Configuracao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfiguracaoRepository extends JpaRepository<Configuracao, Long> {
    // Métodos personalizados podem ser adicionados aqui
}
