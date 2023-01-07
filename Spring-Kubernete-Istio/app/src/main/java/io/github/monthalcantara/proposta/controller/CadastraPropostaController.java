package io.github.monthalcantara.proposta.controller;

import io.github.monthalcantara.proposta.dto.NovaPropostaRequest;
import io.github.monthalcantara.proposta.dto.PropostaCriadaResponse;
import io.github.monthalcantara.proposta.repository.PropostaRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propostas")
public class CadastraPropostaController {

    private final PropostaRepository repository;

    public CadastraPropostaController(PropostaRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public ResponseEntity cadastraProposta(@Valid @RequestBody NovaPropostaRequest request) {
        var proposta = request.toModel();
        var propostaSaved = repository.save(proposta);
        var response = new PropostaCriadaResponse(propostaSaved);
        return new ResponseEntity(response, HttpStatus.CREATED);
    }
}
