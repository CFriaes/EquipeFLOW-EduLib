package equipeFlow.EduLib.Controllers;

import equipeFlow.EduLib.Docs.OpenApiConfig;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import equipeFlow.EduLib.DTOs.LivroDTO;
import equipeFlow.EduLib.Service.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Recebe a consulta de livros e delega a busca ao Service. */
@RestController
@RequestMapping("/api/v1/livros")
@Tag(name = OpenApiConfig.TAG_LIVROS)
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @Operation(
            summary = "Buscar livros no acervo",
            description = "Lista os livros do acervo. Se o parâmetro `busca` for informado, filtra pelos livros "
                    + "que correspondem ao termo; sem ele, retorna todo o acervo. "
                    + "Quando nada é encontrado, retorna uma lista vazia.")
    @ApiResponse(responseCode = "200", description = "Lista de livros (pode ser vazia)",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = LivroDTO.class))))
    @GetMapping
    public ResponseEntity<List<LivroDTO>> buscarLivros(
            @Parameter(description = "Termo de busca (opcional)", example = "Sistemas Distribuídos")
            @RequestParam(name = "busca", required = false) String busca) {
        return ResponseEntity.ok(livroService.buscarLivros(busca));
    }
}
