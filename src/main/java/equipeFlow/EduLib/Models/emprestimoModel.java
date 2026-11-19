package equipeFlow.EduLib.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tb_emprestimo")
@Entity
public class emprestimoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_emprestimo")
    private Long id_emprestimo;

    @Column(name = "data_emprestimo")
    private LocalDate data_emprestimo;

    @Column(name = "data_devolucao_prevista")
    private LocalDate data_devolucao_prevista;

    @Column(name = "data_devolucao_real")
    private LocalDate data_devolucao_real;

    @Column(name = "multa_cobrada")
    private BigDecimal multa_cobrada_reais;

    @Column(name = "status")
    private String status;



    @OneToMany(
            mappedBy = "emprestimo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EmprestimoItemModel> itens = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idAluno")
    private AlunoModel aluno;
}
