package equipeFlow.EduLib.Docs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/** Metadados da documentação OpenAPI exibida em /docs. */
@Configuration
public class OpenApiConfig {

    public static final String TAG_LIVROS = "Livros";
    public static final String TAG_EMPRESTIMOS = "Empréstimos";

    @Bean
    public OpenAPI eduLibOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("EduLib API")
                        .version("v1")
                        .description("""
                                Web Service RESTful da biblioteca universitária **EduLib**: \
                                consulta do acervo, controle de empréstimos e devolução com cálculo de multa.

                                **Regras de negócio**
                                - Cada empréstimo concede **14 dias** de prazo para devolução.
                                - Devolução após o prazo gera multa de **R$ 2,50 por dia de atraso**.
                                - Não é permitido emprestar livro sem exemplares disponíveis.

                                **Erros padronizados:** toda resposta 4xx/5xx segue o formato \
                                `{"codigo_erro", "mensagem", "timestamp"}`.
                                """)
                        .contact(new Contact().name("Equipe GYMFLOW")))
                .servers(List.of(new Server().url("/").description("Servidor atual")))
                .tags(List.of(
                        new Tag().name(TAG_LIVROS).description("Consulta do acervo de livros"),
                        new Tag().name(TAG_EMPRESTIMOS).description("Realização e devolução de empréstimos")));
    }
}
