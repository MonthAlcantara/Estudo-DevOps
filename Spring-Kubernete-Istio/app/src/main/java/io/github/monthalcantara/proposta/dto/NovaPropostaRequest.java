package io.github.monthalcantara.proposta.dto;

import io.github.monthalcantara.proposta.domain.Proposta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class NovaPropostaRequest {
    @NotBlank
    private String documento;
    @NotBlank
    private String nome;
    @NotBlank
    private String email;
    @NotBlank
    private String endereco;
    @Positive
    @NotNull
    private BigDecimal salario;

    public Proposta toModel() {
        return new Proposta(this.documento, this.nome, this.email, this.endereco, this.salario);
    }

    private NovaPropostaRequest() {
    }

    public NovaPropostaRequest(String documento, String nome, String email, String endereco, BigDecimal salario) {
        this.documento = documento;
        this.nome = nome;
        this.email = email;
        this.endereco = endereco;
        this.salario = salario;
    }

    public String getEmail() {
        return email;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }
}
