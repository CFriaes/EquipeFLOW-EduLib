package equipeFlow.EduLib.Repositories;

import equipeFlow.EduLib.Models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface livroRespository extends JpaRepository<LivroModel, Long> {
}
