package equipeFlow.EduLib.Controllers;

import equipeFlow.EduLib.Docs.OpenApiConfig;
import equipeFlow.EduLib.Docs.Resposta400;
import equipeFlow.EduLib.Docs.Resposta404;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import equipeFlow.EduLib.DTOs.CriarEmprestimoDTO;
import equipeFlow.EduLib.DTOs.DevolucaoDTO;
import equipeFlow.EduLib.DTOs.EmprestimoDTO;
import equipeFlow.EduLib.Service.EmprestimoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Recebe operacoes de emprestimos e delega as regras ao Service. */
@RestController
@RequestMapping("/api/v1/emprestimos")
@Tag(name = OpenApiConfig.TAG_EMPRESTIMOS)
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @Operation(
            summary = "Realizar empréstimo",
            description = "Registra o empréstimo de um livro a um aluno e decrementa 1 exemplar disponível. "
                    + "O prazo de devolução é de 14 dias a partir da data do empréstimo.")
    @ApiResponse(responseCode = "201", description = "Empréstimo criado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmprestimoDTO.class)))
    @Resposta400
    @Resposta404
    @PostMapping
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(@Valid @RequestBody CriarEmprestimoDTO dados) {

        EmprestimoDTO emprestimo = emprestimoService.criarEmprestimo(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @Operation(
            summary = "Consultar empréstimos do aluno",
            description = "Lista os empréstimos (ativos e já devolvidos) de um aluno. "
                    + "Se o aluno não tiver empréstimos, retorna uma lista vazia.")
    @ApiResponse(responseCode = "200", description = "Lista de empréstimos do aluno (pode ser vazia)",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = EmprestimoDTO.class))))
    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<EmprestimoDTO>> buscarEmprestimosDoAluno(
            @Parameter(description = "Matrícula do aluno", example = "2026001234")
            @PathVariable("matricula") String matricula) {

        return ResponseEntity.ok(
                emprestimoService.buscarEmprestimosPorMatricula(matricula));
    }

    @Operation(
            summary = "Registrar devolução",
            description = "Encerra o empréstimo (status DEVOLVIDO), incrementa 1 exemplar no acervo e calcula a multa "
                    + "de R$ 2,50 por dia de atraso quando a devolução ocorre após o prazo de 14 dias.")
    @ApiResponse(responseCode = "200", description = "Devolução registrada",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmprestimoDTO.class)))
    @Resposta400
    @Resposta404
    @PutMapping("/{id_emprestimo}/devolucao")
    public ResponseEntity<EmprestimoDTO> registrarDevolucao(
            @Parameter(description = "Identificador do empréstimo", example = "EMP-5510")
            @PathVariable("id_emprestimo") String idEmprestimo,
            @Valid @RequestBody DevolucaoDTO dados) {

        return ResponseEntity.ok(
                emprestimoService.registrarDevolucao(idEmprestimo, dados));
    }
}
