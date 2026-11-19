
package equipeFlow.EduLib.Utils;

import equipeFlow.EduLib.Erro.RegraNegocioException;

public final class IdPublico {

    private IdPublico() {
    }

    public static String formatarLivro(Long id) {
        return "LIV-" + id;
    }

    public static String formatarEmprestimo(Long id) {
        return "EMP-" + id;
    }

    public static Long extrairLivro(String codigo) {
        return extrairNumero(codigo, "LIV-", "livro");
    }

    public static Long extrairEmprestimo(String codigo) {
        return extrairNumero(codigo, "EMP-", "empréstimo");
    }

    private static Long extrairNumero(
            String codigo,
            String prefixo,
            String recurso
    ) {

        if (codigo == null || !codigo.startsWith(prefixo)) {
            throw new RegraNegocioException(
                    "Identificador de " + recurso + " inválido"
            );
        }

        String numero = codigo.substring(prefixo.length());

        if (!numero.matches("[1-9][0-9]*")) {
            throw new RegraNegocioException(
                    "Identificador de " + recurso + " inválido"
            );
        }

        try {
            return Long.parseLong(numero);
        } catch (NumberFormatException ex) {
            throw new RegraNegocioException(
                    "Identificador de " + recurso + " inválido"
            );
        }
    }
}
