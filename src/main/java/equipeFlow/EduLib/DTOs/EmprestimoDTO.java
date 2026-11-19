package equipeFlow.EduLib.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Contrato de saida para emprestimos (ATIVO ou DEVOLVIDO). */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmprestimoDTO {

    @JsonProperty("id_emprestimo")
    private Long idEmprestimo;

    @JsonProperty("aluno_matricula")
    private String alunoMatricula;

    @JsonProperty("livro_titulo")
    private String livroTitulo;

    @JsonProperty("data_emprestimo")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataEmprestimo;

    @JsonProperty("data_devolucao_prevista")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucaoPrevista;

    @JsonProperty("data_devolucao_real")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucaoReal;

    @JsonProperty("multa_cobrada_reais")
    private BigDecimal multaCobradaReais;

    @JsonProperty("status")
    private String status;
}
