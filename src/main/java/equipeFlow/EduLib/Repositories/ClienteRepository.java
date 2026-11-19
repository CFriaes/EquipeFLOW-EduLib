package equipeFlow.EduLib.Repositories;

import equipeFlow.EduLib.Models.AlunoModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository <AlunoModel, Long> {
}
