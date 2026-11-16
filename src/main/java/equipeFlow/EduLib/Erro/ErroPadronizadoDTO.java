package equipeFlow.EduLib.Erro;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

/**
 * DTO de saída padronizado para qualquer erro 4xx/5xx da API.
 * Formato exigido pela Ficha de Domínio: codigo_erro, mensagem e timestamp.
 */
@Schema(name = "ErroPadronizadoDTO", description = "Resposta de erro padronizada da API")
public record ErroPadronizadoDTO(

        @JsonProperty("codigo_erro")
        @Schema(description = "Código estável do erro, em MAIÚSCULAS, para uso por clientes da API",
                example = "RECURSO_NAO_ENCONTRADO")
        String codigoErro,

        @JsonProperty("mensagem")
        @Schema(description = "Descrição clara do erro ocorrido",
                example = "Livro não encontrado.")
        String mensagem,

        @JsonProperty("timestamp")
        @Schema(description = "Momento do erro (ISO-8601, UTC)", example = "2026-11-18T14:30:00Z")
        Instant timestamp
) {
    public static ErroPadronizadoDTO de(String codigoErro, String mensagem) {
        return new ErroPadronizadoDTO(codigoErro, mensagem, Instant.now());
    }
}
