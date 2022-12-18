package io.github.monthalcantara.githubactions.controller;

import io.github.monthalcantara.githubactions.domain.Pessoa;
import io.github.monthalcantara.githubactions.dto.request.PessoaRequest;
import io.github.monthalcantara.githubactions.repository.PessoaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CadastraPessoaControllerTest {

    private PessoaRepository repository = Mockito.mock(PessoaRepository.class);
    private CadastraPessoaController controller;

    @BeforeEach
    void init() {
        controller = new CadastraPessoaController(repository);
    }

    @Test
    void cadastra() {
        //Given
        final var pessoaRequest = new PessoaRequest("nome", "sobrenome", "email@email.com.br");
        final var pessoa = new Pessoa("nome", "sobrenome", "email@email.com.br");

        //When
        Mockito.when(repository.save(pessoa)).thenReturn(pessoa);
        final var result = controller.cadastra(pessoaRequest);

        //Then
        Assertions.assertEquals(pessoa.getNome(), pessoaRequest.getNome());
        Assertions.assertEquals(pessoa.getSobrenome(), pessoaRequest.getSobrenome());
        Assertions.assertEquals(pessoa.getEmail(), pessoaRequest.getEmail());

    }
}