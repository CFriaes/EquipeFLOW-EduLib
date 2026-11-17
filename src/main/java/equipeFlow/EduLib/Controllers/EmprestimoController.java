package equipeFlow.EduLib.Controllers;

import equipeFlow.EduLib.DTOs.CriarEmprestimoDTO;
import equipeFlow.EduLib.DTOs.DevolucaoDTO;
import equipeFlow.EduLib.DTOs.EmprestimoDTO;
import equipeFlow.EduLib.Emprestimo.EmprestimoService;
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
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService) {
        this.emprestimoService = emprestimoService;
    }

    @PostMapping
    public ResponseEntity<EmprestimoDTO> criarEmprestimo(
            @Valid @RequestBody CriarEmprestimoDTO dados) {

        EmprestimoDTO emprestimo = emprestimoService.criarEmprestimo(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(emprestimo);
    }

    @GetMapping("/aluno/{matricula}")
    public ResponseEntity<List<EmprestimoDTO>> buscarEmprestimosDoAluno(
            @PathVariable("matricula") String matricula) {

        return ResponseEntity.ok(
                emprestimoService.buscarEmprestimosPorMatricula(matricula));
    }

    @PutMapping("/{id_emprestimo}/devolucao")
    public ResponseEntity<EmprestimoDTO> registrarDevolucao(
            @PathVariable("id_emprestimo") String idEmprestimo,
            @Valid @RequestBody DevolucaoDTO dados) {

        return ResponseEntity.ok(
                emprestimoService.registrarDevolucao(idEmprestimo, dados));
    }
}
