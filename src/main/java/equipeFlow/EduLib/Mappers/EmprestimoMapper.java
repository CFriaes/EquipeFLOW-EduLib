package equipeFlow.EduLib.Mappers;

import equipeFlow.EduLib.DTOs.DevolucaoDTO;
import equipeFlow.EduLib.DTOs.EmprestimoDTO;
import equipeFlow.EduLib.Models.AlunoModel;
import equipeFlow.EduLib.Models.EmprestimoItemModel;
import equipeFlow.EduLib.Models.LivroModel;
import equipeFlow.EduLib.Models.EmprestimoModel;
import equipeFlow.EduLib.Utils.IdPublico;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class EmprestimoMapper {

    public EmprestimoModel toEntity(AlunoModel aluno, LivroModel livro) {
        EmprestimoModel emprestimo = new EmprestimoModel();

        emprestimo.setAluno(aluno);

        EmprestimoItemModel item = new EmprestimoItemModel();

        item.setLivro(livro);

        item.setEmprestimo(emprestimo);

        emprestimo.setItens(new ArrayList<>());
        emprestimo.getItens().add(item);

        return emprestimo;
    }

    public EmprestimoDTO toDTO(EmprestimoModel emprestimo) {

        EmprestimoDTO dto = new EmprestimoDTO();

        dto.setIdEmprestimo(
                IdPublico.formatarEmprestimo(emprestimo.getId_emprestimo())
        );

        dto.setAlunoMatricula(emprestimo.getAluno().getMatricula());

        dto.setLivroTitulo(
                emprestimo.getItens()
                        .get(0)
                        .getLivro()
                        .getTitulo()
        );

        dto.setDataEmprestimo(
                emprestimo.getData_emprestimo()
        );

        dto.setDataDevolucaoPrevista(
                emprestimo.getData_devolucao_prevista()
        );

        dto.setDataDevolucaoReal(
                emprestimo.getData_devolucao_real()
        );

        dto.setMultaCobradaReais(
                emprestimo.getMulta_cobrada_reais()
        );

        dto.setStatus(
                emprestimo.getStatus()
        );

        return dto;
    }

    public void updateEntity(DevolucaoDTO dto, EmprestimoModel emprestimo) {

        emprestimo.setData_devolucao_real(dto.getDataDevolucaoReal());

    }

}