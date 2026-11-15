package equipeFlow.EduLib.Livro;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="tb_livro")
@Entity
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_livro")
    private Long id_livro;

    @Column(name="título")
    private String titulo;

    @Column(name="ano_publicação")
    private Integer ano_publicacao;

    @Column(name="Quantidade_Estoque")
    private Integer disponiveis_estoque;



}
