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
@Table(name="tb_livro")
@Entity
public class LivroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_livro")
    private String idLivro;

    @Column(name="titulo")
    private String Titulo;

    @Column(name = "autor")
    private String autor;

    @Column(name="quantidade_estoque")
    private Integer exemplaresDisponiveis;

    @Column(name = "isbn")
    private String isbn;
}
