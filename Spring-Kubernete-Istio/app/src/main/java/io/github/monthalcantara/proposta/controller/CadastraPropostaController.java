package io.github.monthalcantara.proposta.controller;

import io.github.monthalcantara.proposta.dto.NovaPropostaRequest;
import io.github.monthalcantara.proposta.dto.PropostaCriadaResponse;
import io.github.monthalcantara.proposta.service.CadastraPropostaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/propostas")
public class CadastraPropostaController {

    private final CadastraPropostaService cadastraPropostaService;
    private final Logger log = LoggerFactory.getLogger(CadastraPropostaController.class);

    public CadastraPropostaController(CadastraPropostaService cadastraPropostaService) {
        this.cadastraPropostaService = cadastraPropostaService;
    }

    @PostMapping
    public ResponseEntity<PropostaCriadaResponse> cadastraProposta(@Valid @RequestBody NovaPropostaRequest request) {
        final var requestDocumento = request.getDocumento();

        log.info("Recebida requisição para cadastro de proposta para o documento {}", requestDocumento);

        final var proposta = request.toModel();
        final var propostaSaved = cadastraPropostaService.executa(proposta);

        log.info("Finalizado fluxo de cadastro de proposta para o documento {}", requestDocumento);

        final var response = new PropostaCriadaResponse(propostaSaved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
