package equipeFlow.EduLib.Repositories;

import equipeFlow.EduLib.Models.EmprestimoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmprestimoRepository extends JpaRepository<EmprestimoModel, Long> {
    List<EmprestimoModel> findByAlunoMatricula(String Matricula);
}
