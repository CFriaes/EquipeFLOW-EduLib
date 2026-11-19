package equipeFlow.EduLib.Models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "tb_cliente")
@Entity
public class AlunoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_aluno")
    private Long idAluno;

    @Column(name = "matricula", unique = true, nullable = false)
    private String matricula;

    @Column(name = "nome_aluno")
    private String nome_aluno;

    @Column(name = "idade_aluno")
    private Integer idade_aluno;


}
