package equipeFlow.EduLib.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Contrato de saida para emprestimos (ATIVO ou DEVOLVIDO). */
@Schema(name = "EmprestimoDTO", description = "Empréstimo de um livro a um aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    @JsonProperty("id_emprestimo")
    @Schema(description = "Identificador do empréstimo", example = "EMP-5510")
    private String idEmprestimo;

    @JsonProperty("aluno_matricula")
    @Schema(description = "Matrícula do aluno", example = "2026001234")
    private String alunoMatricula;

    @JsonProperty("livro_titulo")
    @Schema(description = "Título do livro emprestado", example = "SOA na Prática")
    private String livroTitulo;

    @JsonProperty("data_emprestimo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data do empréstimo", type = "string", format = "date", example = "2026-11-16")
    private LocalDate dataEmprestimo;

    @JsonProperty("data_devolucao_prevista")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data limite de devolução (data do empréstimo + 14 dias)", type = "string", format = "date", example = "2026-11-30")
    private LocalDate dataDevolucaoPrevista;

    @JsonProperty("data_devolucao_real")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Data em que o livro foi devolvido; nulo enquanto o empréstimo estiver ATIVO", type = "string", format = "date", example = "2026-12-06", nullable = true)
    private LocalDate dataDevolucaoReal;

    @JsonProperty("multa_cobrada_reais")
    @Schema(description = "Multa em reais (R$ 2,50 por dia de atraso; 0 se devolvido no prazo)", example = "15.00")
    private BigDecimal multaCobradaReais;

    @JsonProperty("status")
    @Schema(description = "Situação do empréstimo", allowableValues = {"ATIVO", "DEVOLVIDO"}, example = "ATIVO")
    private String status;
}
