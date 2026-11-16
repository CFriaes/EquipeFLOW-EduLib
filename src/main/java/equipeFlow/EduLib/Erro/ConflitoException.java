package equipeFlow.EduLib.Erro;

import org.springframework.http.HttpStatus;

/** 409 Conflict. Reservada para conflitos de estado/duplicidade. */
public class ConflitoException extends ApiException {

    public static final String CODIGO_PADRAO = "CONFLITO";

    public ConflitoException(String mensagem) {
        super(HttpStatus.CONFLICT, CODIGO_PADRAO, mensagem);
    }

    public ConflitoException(String codigoErro, String mensagem) {
        super(HttpStatus.CONFLICT, codigoErro, mensagem);
    }
}
