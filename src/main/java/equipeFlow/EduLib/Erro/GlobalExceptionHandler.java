package equipeFlow.EduLib.Erro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

/**
 * Tratamento centralizado de exceções. Nunca devolve stack trace nem 500 genérico
 * com detalhes internos (Regra de Ouro 3 do Roteiro).
 * Aplica o mesmo formato de erro a toda a API, inclusive quando o verbo HTTP é inválido.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static ResponseEntity<ErroPadronizadoDTO> resposta(HttpStatus status, String codigo, String mensagem) {
        return ResponseEntity.status(status).body(ErroPadronizadoDTO.de(codigo, mensagem));
    }

    /** 404 / 400 / 409 lançados pelos services. */
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarApiException(ApiException ex) {
        return resposta(ex.getStatus(), ex.getCodigoErro(), ex.getMessage());
    }

    /** 400: Bean Validation (@Valid) no corpo da requisição. */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarValidacao(MethodArgumentNotValidException ex) {
        String detalhes = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining("; "));
        if (detalhes.isBlank()) {
            detalhes = "Dados da requisição inválidos.";
        }
        return resposta(HttpStatus.BAD_REQUEST, "VALIDACAO_INVALIDA", detalhes);
    }

    /** 400: JSON malformado, tipo errado ou data em formato inválido. */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarCorpoIlegivel(HttpMessageNotReadableException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "CORPO_INVALIDO",
                "Corpo da requisição ausente, malformado ou com valores em formato inválido.");
    }

    /** 400: parâmetro de rota/query com tipo inválido. */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarTipoParametro(MethodArgumentTypeMismatchException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "PARAMETRO_INVALIDO",
                "O parâmetro '" + ex.getName() + "' possui um valor inválido.");
    }

    /** 400: parâmetro obrigatório ausente. */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarParametroAusente(MissingServletRequestParameterException ex) {
        return resposta(HttpStatus.BAD_REQUEST, "PARAMETRO_OBRIGATORIO",
                "O parâmetro obrigatório '" + ex.getParameterName() + "' não foi informado.");
    }

    /** 404: rota inexistente. */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarRotaInexistente(NoResourceFoundException ex) {
        return resposta(HttpStatus.NOT_FOUND, "ROTA_NAO_ENCONTRADA", "A rota solicitada não existe.");
    }

    /** 405: verbo HTTP não permitido na rota. */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarMetodoNaoPermitido(HttpRequestMethodNotSupportedException ex) {
        return resposta(HttpStatus.METHOD_NOT_ALLOWED, "METODO_NAO_PERMITIDO",
                "O verbo HTTP '" + ex.getMethod() + "' não é permitido nesta rota.");
    }

    /** 415: Content-Type diferente de JSON. */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarMediaType(HttpMediaTypeNotSupportedException ex) {
        return resposta(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "TIPO_DE_MIDIA_NAO_SUPORTADO",
                "Use Content-Type: application/json.");
    }

    /** Último recurso: registra no log e devolve mensagem genérica, sem stack trace. */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroPadronizadoDTO> tratarInesperado(Exception ex) {
        log.error("Erro inesperado", ex);
        return resposta(HttpStatus.INTERNAL_SERVER_ERROR, "ERRO_INTERNO",
                "Ocorreu um erro interno. Tente novamente mais tarde.");
    }
}
