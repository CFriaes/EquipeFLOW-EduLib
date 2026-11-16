package equipeFlow.EduLib.DTOs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Contrato de saida para livros do acervo. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    @JsonProperty("id_livro")
    private String idLivro;

    @JsonProperty("titulo")
    private String titulo;

    @JsonProperty("autor")
    private String autor;

    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("exemplares_disponiveis")
    private Integer exemplaresDisponiveis;
}
