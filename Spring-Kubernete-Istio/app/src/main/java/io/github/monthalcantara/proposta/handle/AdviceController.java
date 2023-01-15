package io.github.monthalcantara.proposta.handle;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.monthalcantara.proposta.exception.RegraNegocioException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class AdviceController {

    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<ErroDto> regraNegocio(RegraNegocioException ex) {
        final var httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
        var erroDto = new ErroDto(ex.mensagem, null, httpStatus.value());
        return new ResponseEntity<>(erroDto, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDto> methodArgumentNotValid(MethodArgumentNotValidException ex) {
        final var httpStatus = ex.getStatusCode();

        final var mensagens = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        var erroDto = new ErroDto(null, mensagens, httpStatus.value());

        return new ResponseEntity<>(erroDto, httpStatus);
    }

    private static class ErroDto {
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private String mensagem;
        @JsonInclude(JsonInclude.Include.NON_NULL)
        private final List<String> mensagens;
        private final int codigo;

        public ErroDto(String mensagem, List<String> mensagens, int codigo) {
            this.mensagem = mensagem;
            this.mensagens = mensagens;
            this.codigo = codigo;
        }

        public String getMensagem() {
            return mensagem;
        }

        public List<String> getMensagens() {
            return mensagens;
        }

        public int getCodigo() {
            return codigo;
        }
    }
}
