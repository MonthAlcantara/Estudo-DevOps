package io.github.monthalcantara.proposta.client.dto;

public enum TipoSolicitacao {
    COM_RESTRICAO("COM_RESTRICAO"),
    SEM_RESTRICAO("SEM_RESTRICAO");
    private String valor;

    TipoSolicitacao(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
