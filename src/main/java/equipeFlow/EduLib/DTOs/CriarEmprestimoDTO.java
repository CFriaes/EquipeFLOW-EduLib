package equipeFlow.EduLib.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Dados de entrada para criar um emprestimo. */
@Getter
@Setter
@NoArgsConstructor
public class CriarEmprestimoDTO {

    @NotBlank
    @JsonProperty("aluno_matricula")
    private String alunoMatricula;

    @NotBlank
    @JsonProperty("aluno_nome")
    private String alunoNome;

    @NotBlank
    @JsonProperty("livro_id")
    private String livroId;
}
