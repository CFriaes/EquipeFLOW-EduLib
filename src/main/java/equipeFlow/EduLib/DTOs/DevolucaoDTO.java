package equipeFlow.EduLib.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** Dados de entrada para registrar a devolucao. */
@Schema(name = "DevolucaoDTO", description = "Dados para registrar a devolução de um livro")
@Getter
@Setter
@NoArgsConstructor
public class DevolucaoDTO {

    @NotNull
    @JsonProperty("data_devolucao_real")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data em que o livro foi devolvido (YYYY-MM-DD). Após o prazo de 14 dias é cobrada multa de R$ 2,50 por dia de atraso.", type = "string", format = "date", example = "2026-11-30", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDate dataDevolucaoReal;
}
