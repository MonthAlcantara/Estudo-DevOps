package io.github.monthalcantara.proposta.service;

import io.github.monthalcantara.proposta.client.PropostaClient;
import io.github.monthalcantara.proposta.client.dto.SolicitacaoRequest;
import io.github.monthalcantara.proposta.client.dto.SolicitacaoResponse;
import org.springframework.stereotype.Service;

@Service
public class ConsultaRestricaoService {
    private final PropostaClient client;

    public ConsultaRestricaoService(PropostaClient client) {
        this.client = client;
    }
    public SolicitacaoResponse executa(SolicitacaoRequest request){
        return client.buscaRestricao(request);
    }
}
