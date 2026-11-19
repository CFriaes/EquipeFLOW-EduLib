package equipeFlow.EduLib.Models;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_emprestimoitemmodel")
public class EmprestimoItemModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_emprestimo")
    private EmprestimoModel emprestimo;

    @ManyToOne
    @JoinColumn(name = "id_livro")
    private LivroModel livro;
}
