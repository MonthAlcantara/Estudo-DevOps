package io.github.monthalcantara.proposta.service;

import io.github.monthalcantara.proposta.domain.Proposta;
import io.github.monthalcantara.proposta.repository.PropostaRepository;
import io.github.monthalcantara.proposta.validator.PropostaDuplicadaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CadastraPropostaService {

    private final PropostaRepository repository;
    private final PropostaDuplicadaValidator validator;
    private final Logger log = LoggerFactory.getLogger(CadastraPropostaService.class);

    public CadastraPropostaService(PropostaRepository repository, PropostaDuplicadaValidator validator) {
        this.repository = repository;
        this.validator = validator;
    }

    public Proposta executa(Proposta proposta) {

        var propostaDocumento = proposta.getDocumento();

        log.info("Iniciando fluxo de cadastro de proposta para o documento {}", propostaDocumento);

        validator.valida(repository, propostaDocumento);

        log.info("Salvando proposta do documento {}", propostaDocumento);

        var propostaSalva = repository.save(proposta);

        log.info("Proposta do documento {} salva com sucesso", propostaDocumento);

        return propostaSalva;
    }
}
