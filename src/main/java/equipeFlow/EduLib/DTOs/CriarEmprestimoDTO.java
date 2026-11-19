package equipeFlow.EduLib.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Dados de entrada para criar um emprestimo. */
@Schema(name = "CriarEmprestimoDTO", description = "Dados para realizar um empréstimo. Todos os campos são obrigatórios.")
@Getter
@Setter
@NoArgsConstructor
public class CriarEmprestimoDTO {

    @NotBlank
    @JsonProperty("aluno_matricula")
    @Schema(description = "Matrícula do aluno", example = "2026001234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alunoMatricula;

    @NotBlank
    @JsonProperty("aluno_nome")
    @Schema(description = "Nome do aluno", example = "Maria Souza", requiredMode = Schema.RequiredMode.REQUIRED)
    private String alunoNome;

    @NotBlank
    @JsonProperty("livro_id")
    @Schema(description = "Identificador do livro a ser emprestado", example = "LIV-101", requiredMode = Schema.RequiredMode.REQUIRED)
    private String livroId;
}
