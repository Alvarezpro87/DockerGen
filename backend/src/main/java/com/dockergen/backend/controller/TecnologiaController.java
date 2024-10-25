package com.dockergen.backend.controller;

import com.dockergen.backend.model.Tecnologia;
import com.dockergen.backend.service.TecnologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping("/api/tecnologias")
@CrossOrigin(origins = "http://localhost:5173")
public class TecnologiaController {

    @Autowired
    private TecnologiaService tecnologiaService;

    // Listar todas as tecnologias
    @GetMapping
    public ResponseEntity<Collection<Tecnologia>> listarTecnologias() {
        return ResponseEntity.ok(tecnologiaService.listarTecnologias().values());
    }


    // Remover uma tecnologia existente
    @DeleteMapping("/remover/{nome}")
    public ResponseEntity<String> removerTecnologia(@PathVariable String nome) {
        if (!tecnologiaService.getTecnologia(nome).isPresent()) {
            return ResponseEntity.badRequest().body("Tecnologia não encontrada: " + nome);
        }
        tecnologiaService.removerTecnologia(nome);
        return ResponseEntity.ok("Tecnologia removida: " + nome);
    }
}
