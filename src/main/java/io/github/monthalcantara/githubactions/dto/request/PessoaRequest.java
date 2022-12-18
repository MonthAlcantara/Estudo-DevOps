package io.github.monthalcantara.githubactions.dto.request;

import io.github.monthalcantara.githubactions.domain.Pessoa;
import lombok.Getter;

@Getter
public class PessoaRequest {

    private String nome;
    private String sobrenome;
    private String email;

    private PessoaRequest() {
    }

    public PessoaRequest(String nome, String sobrenome, String email) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
    }

    public Pessoa toDomain() {
        return new Pessoa(this.nome,
                this.sobrenome,
                this.email);
    }

}
