package equipeFlow.EduLib.Erro;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/** Testa o handler de forma isolada (sem banco), com um controller falso. */
class GlobalExceptionHandlerTest {

    record EntradaFalsa(@NotBlank String nome) {}

    @RestController
    @RequestMapping("/teste")
    static class ControllerFalso {
        @GetMapping("/nao-encontrado") String naoEncontrado() { throw new RecursoNaoEncontradoException("Livro não encontrado."); }
        @GetMapping("/regra") String regra() { throw new RegraNegocioException("Sem exemplares."); }
        @GetMapping("/conflito") String conflito() { throw new ConflitoException("Conflito."); }
        @GetMapping("/boom") String boom() { throw new IllegalStateException("segredo interno"); }
        @GetMapping("/numero/{n}") String numero(@PathVariable int n) { return "ok"; }
        @PostMapping("/valida") String valida(@Valid @RequestBody EntradaFalsa e) { return "ok"; }
    }

    private MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new ControllerFalso())
                .setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Test void retorna404() throws Exception {
        mvc.perform(get("/teste/nao-encontrado"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.codigo_erro").value("RECURSO_NAO_ENCONTRADO"))
                .andExpect(jsonPath("$.mensagem").value("Livro não encontrado."))
                .andExpect(jsonPath("$.timestamp").isString());
    }

    @Test void retorna400DeRegraDeNegocio() throws Exception {
        mvc.perform(get("/teste/regra"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo_erro").value("REGRA_DE_NEGOCIO_VIOLADA"));
    }

    @Test void retorna409() throws Exception {
        mvc.perform(get("/teste/conflito"))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.codigo_erro").value("CONFLITO"));
    }

    @Test void retorna400DeValidacao() throws Exception {
        mvc.perform(post("/teste/valida").contentType(MediaType.APPLICATION_JSON).content("{\"nome\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo_erro").value("VALIDACAO_INVALIDA"))
                .andExpect(jsonPath("$.mensagem").value(org.hamcrest.Matchers.containsString("nome")));
    }

    @Test void retorna400DeJsonMalformado() throws Exception {
        mvc.perform(post("/teste/valida").contentType(MediaType.APPLICATION_JSON).content("{nome:"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo_erro").value("CORPO_INVALIDO"));
    }

    @Test void retorna400DeTipoInvalido() throws Exception {
        mvc.perform(get("/teste/numero/abc"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.codigo_erro").value("PARAMETRO_INVALIDO"));
    }

    @Test void retorna405() throws Exception {
        mvc.perform(delete("/teste/regra"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.codigo_erro").value("METODO_NAO_PERMITIDO"));
    }

    @Test void erroInesperadoNaoVazaDetalhes() throws Exception {
        mvc.perform(get("/teste/boom"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.codigo_erro").value("ERRO_INTERNO"))
                .andExpect(content().string(org.hamcrest.Matchers.not(org.hamcrest.Matchers.containsString("segredo"))));
    }
}
