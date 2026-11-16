# Guia rápido: erros padronizados e Swagger (EduLib)

## Para o Cauan (Services)
Lance estas exceções (pacote `equipeFlow.EduLib.Erro`). O `GlobalExceptionHandler` converte em JSON padronizado.

| Situação | Exceção | Status |
|---|---|---|
| `livro_id` / `id_emprestimo` inexistente | `new RecursoNaoEncontradoException("Livro não encontrado.")` | 404 |
| Livro sem exemplares | `new RegraNegocioException("Não há exemplares disponíveis para este livro.")` | 400 |
| Empréstimo já devolvido | `new RegraNegocioException("Este empréstimo já foi encerrado.")` | 400 |
| Conflito de estado (se precisar) | `new ConflitoException("...")` | 409 |

Também é possível passar um código próprio: `new RegraNegocioException("LIVRO_SEM_EXEMPLARES", "mensagem")`.

## Para o Brad (Controllers/DTOs)
- Use `@Valid` nos `@RequestBody` e Bean Validation nos DTOs de entrada (`@NotBlank`, `@NotNull`); o handler devolve 400 `VALIDACAO_INVALIDA`.
- Não trate exceções no controller: deixe subir.
- As anotações do Swagger nos controllers/DTOs serão adicionadas pela Revemar depois do merge. Pode usar `@Tag`, `@Operation` etc. se quiser adiantar.

## Formato de erro (todas as respostas 4xx/5xx)
```json
{"codigo_erro": "RECURSO_NAO_ENCONTRADO", "mensagem": "Livro não encontrado.", "timestamp": "2026-11-18T14:30:00Z"}
```

## Swagger
- UI: `http://localhost:8080/docs`  |  JSON OpenAPI: `http://localhost:8080/v3/api-docs`
- Anotações reutilizáveis para os métodos dos controllers: `@Resposta400`, `@Resposta404`, `@Resposta409` (pacote `equipeFlow.EduLib.Docs`).

## Postman
Importe `EduLib-API-Collection.postman_collection.json`, ajuste as variáveis da coleção conforme o seed do H2 e rode com o Collection Runner, na ordem.
