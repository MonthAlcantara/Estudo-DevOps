package io.github.monthalcantara.proposta.dto;

import io.github.monthalcantara.proposta.domain.Proposta;

import java.math.BigDecimal;

public class PropostaCriadaResponse {
    private Long id;
    private String documento;
    private String nome;
    private String email;
    private String endereco;
    private BigDecimal salario;

    private PropostaCriadaResponse() {
    }

    public PropostaCriadaResponse(Proposta proposta) {
        this.id = proposta.getId();
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.endereco = proposta.getEndereco();
        this.email = proposta.getEmail();
        this.salario = proposta.getSalario();
    }

    public Long getId() {
        return id;
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
