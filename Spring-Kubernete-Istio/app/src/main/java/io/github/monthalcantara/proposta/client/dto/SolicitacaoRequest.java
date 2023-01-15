package io.github.monthalcantara.proposta.client.dto;

public class SolicitacaoRequest {
    private String documento;
    private String nome;
    private String idProposta;

    private SolicitacaoRequest() {
    }

    public SolicitacaoRequest(String documento, String nome, String idProposta) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }
}
