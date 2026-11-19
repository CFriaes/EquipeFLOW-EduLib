package equipeFlow.EduLib.Repositories;

import equipeFlow.EduLib.Models.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlunoRepository extends JpaRepository <AlunoModel, Long> {

    Optional<AlunoModel> findByMatricula(String Matricula);
}
