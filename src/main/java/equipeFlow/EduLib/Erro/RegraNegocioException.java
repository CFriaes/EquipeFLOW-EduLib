package equipeFlow.EduLib.Erro;

import org.springframework.http.HttpStatus;

/**
 * 400 Bad Request por violação de regra de negócio.
 * Ex.: livro sem exemplares disponíveis, empréstimo já devolvido.
 */
public class RegraNegocioException extends ApiException {

    public static final String CODIGO_PADRAO = "REGRA_DE_NEGOCIO_VIOLADA";

    public RegraNegocioException(String mensagem) {
        super(HttpStatus.BAD_REQUEST, CODIGO_PADRAO, mensagem);
    }

    public RegraNegocioException(String codigoErro, String mensagem) {
        super(HttpStatus.BAD_REQUEST, codigoErro, mensagem);
    }
}
