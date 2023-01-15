package io.github.monthalcantara.proposta.service;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.github.monthalcantara.proposta.client.PropostaClient;
import io.github.monthalcantara.proposta.client.dto.SolicitacaoRequest;
import io.github.monthalcantara.proposta.client.dto.SolicitacaoResponse;
import io.github.monthalcantara.proposta.client.dto.TipoSolicitacao;
import io.github.monthalcantara.proposta.domain.Proposta;
import io.github.monthalcantara.proposta.exception.RegraNegocioException;
import io.github.monthalcantara.proposta.repository.PropostaRepository;
import io.github.monthalcantara.proposta.validator.PropostaDuplicadaValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CadastraPropostaService {

    private final PropostaRepository repository;
    private final ConsultaRestricaoService consultaRestricaoService;
    private final PropostaDuplicadaValidator validator;
    private final Logger log = LoggerFactory.getLogger(CadastraPropostaService.class);

    public CadastraPropostaService(PropostaRepository repository, ConsultaRestricaoService consultaRestricaoService, PropostaDuplicadaValidator validator) {
        this.repository = repository;
        this.consultaRestricaoService = consultaRestricaoService;
        this.validator = validator;
    }

    public Proposta executa(Proposta proposta) {

        var propostaDocumento = proposta.getDocumento();

        log.info("Iniciando fluxo de cadastro de proposta para o documento {}", propostaDocumento);

        validator.valida(repository, propostaDocumento);

        var solicitacaoResponse = consultaRestricaoService.executa(new SolicitacaoRequest(propostaDocumento, proposta.getNome(), "1"));
        if(TipoSolicitacao.COM_RESTRICAO.getValor().equals(solicitacaoResponse.getResultadoSolicitacao())){
            throw new RegraNegocioException("Não rola pra vc amigão");
        }
        log.info("Salvando proposta do documento {}", propostaDocumento);

        var propostaSalva = repository.save(proposta);

        log.info("Proposta do documento {} salva com sucesso", propostaDocumento);

        return propostaSalva;
    }
}
