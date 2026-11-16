package equipeFlow.EduLib.Erro;

import org.springframework.http.HttpStatus;

/**
 * Base das exceções de negócio. Os services (Cauan) lançam uma das subclasses
 * e o GlobalExceptionHandler converte em JSON padronizado com o status correto.
 */
public abstract class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String codigoErro;

    protected ApiException(HttpStatus status, String codigoErro, String mensagem) {
        super(mensagem);
        this.status = status;
        this.codigoErro = codigoErro;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCodigoErro() {
        return codigoErro;
    }
}
