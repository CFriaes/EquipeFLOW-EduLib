# EduLib API

Web Service RESTful da biblioteca universitária **EduLib**: consulta do acervo de livros,
controle de empréstimos e registro de devoluções com cálculo de multa.

Projeto extraclasse da disciplina **Computação em Nuvem e Arquitetura Orientada a Serviços (SOA)**,
Bacharelado em Ciência da Computação, IFPA Campus Ananindeua.
Domínio de negócio: **Domínio 4 — Educação & Biblioteca**.

## Equipe

| Nome | GitHub |
|---|---|
| Bradsson Santos | [@brad38](https://github.com/brad38) |
| Cauan Friaes | [@CFriaes](https://github.com/CFriaes) |
| Eliel Monteiro | [@ElielDev01](https://github.com/ElielDev01) |
| João Guilherme | [@Joaogui2004](https://github.com/Joaogui2004) |

## Tecnologias

| Ferramenta | Versão |
|---|---|
| Java (JDK) | 27 |
| Spring Boot | 4.1.2 |
| Maven (via Maven Wrapper) | 3.10.0 |
| Spring Data JPA (Hibernate) | gerenciada pelo Spring Boot |
| Banco de dados H2 | gerenciada pelo Spring Boot |
| Flyway (migrations) | gerenciada pelo Spring Boot |
| springdoc-openapi (Swagger UI) | 3.2.0 |

## Requisitos

- **JDK 27** instalado (confira com `java -version`)
- **Git**

Não é preciso instalar o Maven: o projeto usa o Maven Wrapper (`mvnw` / `mvnw.cmd`),
que baixa a versão correta automaticamente.

## Como executar

```bash
git clone https://github.com/CFriaes/EquipeFLOW-EduLib.git
cd EquipeFLOW-EduLib
```

Windows (PowerShell):

```powershell
.\mvnw.cmd spring-boot:run
```

Linux / macOS:

```bash
./mvnw spring-boot:run
```

A primeira execução demora mais, porque o Maven baixa as dependências.

## Endereços

| O quê | URL |
|---|---|
| API | http://localhost:8080/api/v1 |
| Swagger UI | http://localhost:8080/docs |
| Especificação OpenAPI (JSON) | http://localhost:8080/v3/api-docs |
| Console do H2 | http://localhost:8080/h2-console |

Para entrar no console do H2, use **JDBC URL** `jdbc:h2:file:./data/edulib`,
usuário `sa` e senha em branco.

## Endpoints

| Verbo | Rota | Descrição | Sucesso |
|---|---|---|---|
| GET | `/api/v1/livros?busca=` | Busca livros no acervo (`busca` é opcional) | 200 |
| POST | `/api/v1/emprestimos` | Realiza um empréstimo | 201 |
| GET | `/api/v1/emprestimos/aluno/{matricula}` | Lista os empréstimos de um aluno | 200 |
| PUT | `/api/v1/emprestimos/{id_emprestimo}/devolucao` | Registra a devolução | 200 |

## Regras de negócio

- Cada empréstimo concede **14 dias** de prazo para devolução.
- Devolução após o prazo gera multa de **R$ 2,50 por dia de atraso**.
- Não é permitido emprestar livro sem exemplares disponíveis.

## Formato de erro

Toda resposta de erro (4xx/5xx) segue o mesmo formato:

```json
{
  "codigo_erro": "RECURSO_NAO_ENCONTRADO",
  "mensagem": "Livro não encontrado.",
  "timestamp": "2026-11-18T14:30:00Z"
}
```

## Variáveis de ambiente

Todas são opcionais. Sem elas, a API usa os valores padrão, próprios para rodar localmente.

| Variável | Para que serve | Valor padrão |
|---|---|---|
| `DB_URL` | Endereço (JDBC) do banco de dados | `jdbc:h2:file:./data/edulib` |
| `DB_USER` | Usuário do banco | `sa` |
| `DB_PASSWORD` | Senha do banco | (vazia) |
| `DDL_AUTO` | Como o Hibernate trata as tabelas | `validate` |

## Banco de dados

- Localmente, o H2 grava os dados na pasta `data/`, na raiz do projeto. Essa pasta está no
  `.gitignore`. Para começar com o banco vazio, pare a API e apague a pasta `data/`.
- As alterações de estrutura do banco são versionadas com **Flyway**, em
  `src/main/resources/db/migration`. Cada arquivo segue o padrão `V<número>__<descricao>.sql`
  (por exemplo `V2__adiciona_coluna_isbn.sql`).
- **Nunca edite uma migration que já foi aplicada.** Para mudar algo, crie uma nova, com o
  número seguinte.

## Testes

- **Postman:** importe `EduLib-API-Collection.postman_collection.json` e rode a coleção
  com o Collection Runner, na ordem.
- **Testes automatizados:** `.\mvnw.cmd test` (Windows) ou `./mvnw test` (Linux/macOS).

## Documentação complementar

- [Guia de erros padronizados e Swagger](docs/GUIA-ERROS-E-SWAGGER.md)