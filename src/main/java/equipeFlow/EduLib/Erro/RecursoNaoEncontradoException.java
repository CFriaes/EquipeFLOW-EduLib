package equipeFlow.EduLib.Erro;

import org.springframework.http.HttpStatus;

/** 404 Not Found. Ex.: livro_id inexistente, id_emprestimo inexistente. */
public class RecursoNaoEncontradoException extends ApiException {

    public static final String CODIGO_PADRAO = "RECURSO_NAO_ENCONTRADO";

    public RecursoNaoEncontradoException(String mensagem) {
        super(HttpStatus.NOT_FOUND, CODIGO_PADRAO, mensagem);
    }

    public RecursoNaoEncontradoException(String codigoErro, String mensagem) {
        super(HttpStatus.NOT_FOUND, codigoErro, mensagem);
    }
}
