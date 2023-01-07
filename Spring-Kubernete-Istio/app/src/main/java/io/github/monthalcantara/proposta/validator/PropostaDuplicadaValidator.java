package io.github.monthalcantara.proposta.validator;

import io.github.monthalcantara.proposta.exception.RegraNegocioException;
import io.github.monthalcantara.proposta.repository.PropostaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static io.github.monthalcantara.proposta.commons.ApplicationConstants.MENSAGEM_DOCUMENTO_DUCPLICADO;

@Service
public class PropostaDuplicadaValidator {
    private final Logger log = LoggerFactory.getLogger(PropostaDuplicadaValidator.class);

    public void valida(PropostaRepository repository, String documento) {
        log.info("Iniciando validação de documento repetido");

        if (repository.getByDocumento(documento).isPresent()) throw new RegraNegocioException(MENSAGEM_DOCUMENTO_DUCPLICADO);

        log.info("Validação de documento repetido realizado com sucesso");
    }
}
