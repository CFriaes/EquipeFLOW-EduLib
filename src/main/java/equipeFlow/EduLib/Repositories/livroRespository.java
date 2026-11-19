package equipeFlow.EduLib.Repositories;

import equipeFlow.EduLib.Models.LivroModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface livroRespository extends JpaRepository<LivroModel, String> {
    List<LivroModel> findByTituloContainingIgnoreCase(String Titulo);
}
