package equipeFlow.EduLib;

import equipeFlow.EduLib.DTOs.CriarEmprestimoDTO;
import equipeFlow.EduLib.DTOs.DevolucaoDTO;
import equipeFlow.EduLib.DTOs.EmprestimoDTO;
import equipeFlow.EduLib.Mappers.EmprestimoMapper;
import equipeFlow.EduLib.Mappers.LivroMapper;
import equipeFlow.EduLib.Models.AlunoModel;
import equipeFlow.EduLib.Models.EmprestimoItemModel;
import equipeFlow.EduLib.Models.EmprestimoModel;
import equipeFlow.EduLib.Models.LivroModel;
import equipeFlow.EduLib.Repositories.AlunoRepository;
import equipeFlow.EduLib.Repositories.EmprestimoRepository;
import equipeFlow.EduLib.Repositories.livroRespository;
import equipeFlow.EduLib.Service.EmprestimoService;
import equipeFlow.EduLib.Utils.IdPublico;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IdsTextuaisTest {

    @Test
    void formataEExtraiIds() {
        assertEquals("LIV-1", IdPublico.formatarLivro(1L));
        assertEquals("EMP-1", IdPublico.formatarEmprestimo(1L));
        assertEquals(1L, IdPublico.extrairLivro("LIV-1"));
        assertEquals(1L, IdPublico.extrairEmprestimo("EMP-1"));
    }

    @Test
    void livroMapperRetornaIdTextual() {
        LivroModel livro = new LivroModel();
        livro.setIdLivro(1L);
        LivroMapper mapper = new LivroMapper();
        assertEquals("LIV-1", mapper.map(livro).getIdLivro());
        assertEquals(1L, mapper.map(mapper.map(livro)).getIdLivro());
    }

    @Test
    void criarEmprestimoAceitaLivroTextualERetornaEmprestimoTextual() {
        AlunoRepository alunos = mock(AlunoRepository.class);
        livroRespository livros = mock(livroRespository.class);
        EmprestimoRepository emprestimos = mock(EmprestimoRepository.class);

        AlunoModel aluno = new AlunoModel();
        aluno.setMatricula("2026001");
        aluno.setNome_aluno("Maria Souza");
        LivroModel livro = new LivroModel();
        livro.setIdLivro(1L);
        livro.setTitulo("SOA na Prática");
        livro.setExemplaresDisponiveis(2);

        when(alunos.findByMatricula("2026001")).thenReturn(Optional.of(aluno));
        when(livros.findById(1L)).thenReturn(Optional.of(livro));
        when(emprestimos.save(any(EmprestimoModel.class))).thenAnswer(invocation -> {
            EmprestimoModel salvo = invocation.getArgument(0);
            salvo.setId_emprestimo(1L);
            return salvo;
        });

        CriarEmprestimoDTO dados = new CriarEmprestimoDTO();
        dados.setAlunoMatricula("2026001");
        dados.setAlunoNome("Maria Souza");
        dados.setLivroId("LIV-1");

        EmprestimoService service = new EmprestimoService(
                emprestimos, alunos, livros, new EmprestimoMapper()
        );
        EmprestimoDTO resultado = service.criarEmprestimo(dados);

        assertEquals("EMP-1", resultado.getIdEmprestimo());
        assertEquals(1, livro.getExemplaresDisponiveis());
        verify(livros).findById(1L);
    }

    @Test
    void devolverEmprestimoAceitaIdTextual() {
        AlunoRepository alunos = mock(AlunoRepository.class);
        livroRespository livros = mock(livroRespository.class);
        EmprestimoRepository emprestimos = mock(EmprestimoRepository.class);

        AlunoModel aluno = new AlunoModel();
        aluno.setMatricula("2026001");
        LivroModel livro = new LivroModel();
        livro.setIdLivro(1L);
        livro.setTitulo("SOA na Prática");
        livro.setExemplaresDisponiveis(1);

        EmprestimoModel emprestimo = new EmprestimoModel();
        emprestimo.setId_emprestimo(1L);
        emprestimo.setAluno(aluno);
        emprestimo.setStatus("ATIVO");
        emprestimo.setData_emprestimo(LocalDate.now().minusDays(2));
        emprestimo.setData_devolucao_prevista(LocalDate.now().plusDays(12));
        EmprestimoItemModel item = new EmprestimoItemModel();
        item.setEmprestimo(emprestimo);
        item.setLivro(livro);
        emprestimo.setItens(List.of(item));

        when(emprestimos.findById(1L)).thenReturn(Optional.of(emprestimo));
        when(emprestimos.save(emprestimo)).thenReturn(emprestimo);

        DevolucaoDTO dados = new DevolucaoDTO();
        dados.setDataDevolucaoReal(LocalDate.now());

        EmprestimoService service = new EmprestimoService(
                emprestimos, alunos, livros, new EmprestimoMapper()
        );
        EmprestimoDTO resultado = service.registrarDevolucao("EMP-1", dados);

        assertEquals("EMP-1", resultado.getIdEmprestimo());
        assertEquals("DEVOLVIDO", resultado.getStatus());
        assertEquals(2, livro.getExemplaresDisponiveis());
        verify(emprestimos).findById(1L);
    }
}
