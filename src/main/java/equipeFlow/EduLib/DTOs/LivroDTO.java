package equipeFlow.EduLib.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/** Contrato de saida para livros do acervo. */
@Schema(name = "LivroDTO", description = "Livro do acervo da biblioteca")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LivroDTO {

    @JsonProperty("id_livro")
    @Schema(description = "Identificador do livro", example = "LIV-101")
    private String idLivro;

    @JsonProperty("titulo")
    @Schema(description = "Título do livro", example = "SOA na Prática")
    private String titulo;

    @JsonProperty("autor")
    @Schema(description = "Autor do livro", example = "Nicolai Josuttis")
    private String autor;

    @JsonProperty("isbn")
    @Schema(description = "ISBN do livro", example = "978-85-7608-251-4")
    private String isbn;

    @JsonProperty("exemplares_disponiveis")
    @Schema(description = "Quantidade de exemplares disponíveis para empréstimo (0 = indisponível)", example = "3")
    private Integer exemplaresDisponiveis;
}
