package io.github.monthalcantara.githubactions.controller;

import io.github.monthalcantara.githubactions.dto.request.PessoaRequest;
import io.github.monthalcantara.githubactions.repository.PessoaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
public class CadastraPessoaController {

    private final PessoaRepository cadastraPessoaRepository;

    public CadastraPessoaController(PessoaRepository cadastraPessoaRepository) {
        this.cadastraPessoaRepository = cadastraPessoaRepository;
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody PessoaRequest novaPessoa) {
        cadastraPessoaRepository.save(novaPessoa.toDomain());
        return ResponseEntity.ok("Chegou aqui");
    }
}
