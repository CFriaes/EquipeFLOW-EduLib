package equipeFlow.EduLib.Service;

import equipeFlow.EduLib.DTOs.CriarEmprestimoDTO;
import equipeFlow.EduLib.DTOs.DevolucaoDTO;
import equipeFlow.EduLib.DTOs.EmprestimoDTO;
import equipeFlow.EduLib.Erro.RecursoNaoEncontradoException;
import equipeFlow.EduLib.Erro.RegraNegocioException;
import equipeFlow.EduLib.Mappers.EmprestimoMapper;
import equipeFlow.EduLib.Models.AlunoModel;
import equipeFlow.EduLib.Models.EmprestimoItemModel;
import equipeFlow.EduLib.Models.LivroModel;
import equipeFlow.EduLib.Models.EmprestimoModel;
import equipeFlow.EduLib.Repositories.AlunoRepository;
import equipeFlow.EduLib.Repositories.EmprestimoRepository;
import equipeFlow.EduLib.Repositories.livroRespository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@AllArgsConstructor
@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final AlunoRepository alunoRepository;
    private final livroRespository livroRepository;
    private final EmprestimoMapper emprestimoMapper;

    private static final int PRAZO_EMPRESTIMO_DIAS = 14;

    private static final BigDecimal MULTA_POR_DIA =
            new BigDecimal("2.50");

    // POST - Criar empréstimo
    @Transactional
    public EmprestimoDTO criarEmprestimo(
            CriarEmprestimoDTO dados) {

        AlunoModel aluno = alunoRepository
                .findByMatricula(dados.getAlunoMatricula())
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Aluno não encontrado"
                        )
                );


        if (aluno.getNome_aluno() == null ||
                !aluno.getNome_aluno().trim()
                        .equalsIgnoreCase(
                                dados.getAlunoNome().trim()
                        )) {

            throw new RegraNegocioException(
                    "O nome informado não corresponde à matrícula"
            );
        }


        Long idLivro = converterId(
                dados.getLivroId(),
                "livro"
        );


        LivroModel livro = livroRepository
                .findById(idLivro)
                .orElseThrow(() ->
                        new RecursoNaoEncontradoException(
                                "Livro não encontrado"
                        )
                );

        if (livro.getExemplaresDisponiveis() == null ||
                livro.getExemplaresDisponiveis() <= 0) {

            throw new RegraNegocioException(
                    "Livro sem exemplares disponíveis"
            );
        }

        // Construir entidade pelo Mapper
        EmprestimoModel emprestimo =
                emprestimoMapper.toEntity(aluno, livro);

        // Definir regras do empréstimo
        LocalDate hoje = LocalDate.now();

        emprestimo.setData_emprestimo(hoje);

        emprestimo.setData_devolucao_prevista(
                hoje.plusDays(PRAZO_EMPRESTIMO_DIAS)
        );

        emprestimo.setData_devolucao_real(null);

        emprestimo.setMulta_cobrada_reais(
                BigDecimal.ZERO
        );

        emprestimo.setStatus("ATIVO");

        // Diminuir estoque
        livro.setExemplaresDisponiveis(
                livro.getExemplaresDisponiveis() - 1
        );

        livroRepository.save(livro);

        // Salvar empréstimo e seus itens
        EmprestimoModel emprestimoSalvo =
                emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDTO(emprestimoSalvo);
    }

    // GET - Histórico de empréstimos do aluno
    @Transactional(readOnly = true)
    public List<EmprestimoDTO> buscarEmprestimosPorMatricula(
            String matricula) {

        List<EmprestimoModel> emprestimos =
                emprestimoRepository.findByAlunoMatricula(matricula);

        return emprestimos.stream()
                .map(emprestimoMapper::toDTO)
                .toList();
    }

    // PUT - Registrar devolução
    @Transactional
    public EmprestimoDTO registrarDevolucao(
            String idEmprestimo,
            DevolucaoDTO dados) {

        Long id = converterId(
                idEmprestimo,
                "empréstimo"
        );

        // Buscar empréstimo
        EmprestimoModel emprestimo =
                emprestimoRepository.findById(id)
                        .orElseThrow(() ->
                                new RecursoNaoEncontradoException(
                                        "Empréstimo não encontrado"
                                )
                        );

        // Impedir devolução duplicada
        if (!"ATIVO".equals(emprestimo.getStatus())) {

            throw new RegraNegocioException(
                    "Este empréstimo já foi encerrado"
            );
        }

        LocalDate dataDevolucao =
                dados.getDataDevolucaoReal();

        // Validar data da devolução
        if (dataDevolucao.isBefore(
                emprestimo.getData_emprestimo())) {

            throw new RegraNegocioException(
                    "A devolução não pode ocorrer antes do empréstimo"
            );
        }

        if (dataDevolucao.isAfter(LocalDate.now())) {

            throw new RegraNegocioException(
                    "A data de devolução não pode estar no futuro"
            );
        }

        // Calcular dias de atraso
        long diasAtraso = ChronoUnit.DAYS.between(
                emprestimo.getData_devolucao_prevista(),
                dataDevolucao
        );

        diasAtraso = Math.max(0, diasAtraso);

        // Calcular multa
        BigDecimal multa = MULTA_POR_DIA.multiply(
                BigDecimal.valueOf(diasAtraso)
        );

        // Atualizar data pelo Mapper
        emprestimoMapper.updateEntity(
                dados,
                emprestimo
        );

        // Finalizar empréstimo
        emprestimo.setStatus("DEVOLVIDO");
        emprestimo.setMulta_cobrada_reais(multa);

        // Devolver os exemplares ao estoque
        for (EmprestimoItemModel item :
                emprestimo.getItens()) {

            LivroModel livro = item.getLivro();

            livro.setExemplaresDisponiveis(
                    livro.getExemplaresDisponiveis() + 1
            );

            livroRepository.save(livro);
        }

        // Persistir devolução
        EmprestimoModel emprestimoAtualizado =
                emprestimoRepository.save(emprestimo);

        return emprestimoMapper.toDTO(
                emprestimoAtualizado
        );
    }

    // Método auxiliar para validar IDs numéricos
    private Long converterId(String valor, String recurso) {

        try {
            Long id = Long.valueOf(valor);

            if (id <= 0) {
                throw new NumberFormatException();
            }

            return id;

        } catch (NumberFormatException ex) {

            throw new RegraNegocioException(
                    "Identificador de " + recurso + " inválido"
            );
        }
    }
}