package io.github.monthalcantara.proposta.client;

import io.github.monthalcantara.proposta.client.dto.SolicitacaoRequest;
import io.github.monthalcantara.proposta.client.dto.SolicitacaoResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "propostas",url = "${servico.proposta.url}")
public interface PropostaClient {

    @PostMapping("/api/solicitacao")
    SolicitacaoResponse buscaRestricao(@RequestBody SolicitacaoRequest request);
}
