package equipeFlow.EduLib.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/** Dados de entrada para registrar a devolucao. */
@Getter
@Setter
@NoArgsConstructor
public class DevolucaoDTO {

    @NotNull
    @JsonProperty("data_devolucao_real")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucaoReal;
}
