package equipeFlow.EduLib.Controllers;

import equipeFlow.EduLib.DTOs.LivroDTO;
import equipeFlow.EduLib.Livro.LivroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Recebe a consulta de livros e delega a busca ao Service. */
@RestController
@RequestMapping("/api/v1/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController(LivroService livroService) {
        this.livroService = livroService;
    }

    @GetMapping
    public ResponseEntity<List<LivroDTO>> buscarLivros(
            @RequestParam(name = "busca", required = false) String busca) {
        return ResponseEntity.ok(livroService.buscarLivros(busca));
    }
}
