package io.github.monthalcantara.proposta.exception;

public class RegraNegocioException extends RuntimeException{
    public final String mensagem;

    public RegraNegocioException(String mensagem) {
        this.mensagem = mensagem;
    }
}
