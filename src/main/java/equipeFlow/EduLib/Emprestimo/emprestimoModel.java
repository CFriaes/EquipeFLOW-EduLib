package equipeFlow.EduLib.Emprestimo;
import equipeFlow.EduLib.itemEmprestimo.EmprestimoItemModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Date data_emprestimo;



    @OneToMany(
            mappedBy = "emprestimo",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<EmprestimoItemModel> itens = new ArrayList<>();
}
