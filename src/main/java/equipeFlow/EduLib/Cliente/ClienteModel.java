package equipeFlow.EduLib.Cliente;


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
public class ClienteModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private Long id_cliente;

    @Column(name = "nome_cliente")
    private String nome_cliente;

    @Column(name = "idade_cliente")
    private Integer idade_cliente;



}
