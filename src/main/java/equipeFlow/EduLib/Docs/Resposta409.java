package equipeFlow.EduLib.Docs;

import equipeFlow.EduLib.Erro.ErroPadronizadoDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Documenta a resposta 409 no Swagger com o JSON de erro padronizado.
 * Uso: coloque @Resposta409 no método do controller.
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
        responseCode = "409",
        description = "Conflito de estado",
        content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ErroPadronizadoDTO.class),
                examples = @ExampleObject(value = """
                        {
                          "codigo_erro": "CONFLITO",
                          "mensagem": "O recurso está em um estado que não permite a operação.",
                          "timestamp": "2026-11-18T14:30:00Z"
                        }
                        """)))
public @interface Resposta409 {
}
