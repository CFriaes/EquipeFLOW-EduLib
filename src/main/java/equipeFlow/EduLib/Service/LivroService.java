package equipeFlow.EduLib.Service;

import equipeFlow.EduLib.DTOs.LivroDTO;
import equipeFlow.EduLib.Mappers.LivroMapper;
import equipeFlow.EduLib.Models.LivroModel;
import equipeFlow.EduLib.Repositories.livroRespository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class LivroService {

    private final livroRespository livroRepository;
    private final LivroMapper livroMapper;

    @Transactional(readOnly = true)
    public List<LivroDTO> buscarLivros(String busca) {

        List<LivroModel> livros;

        if (busca == null || busca.isBlank()) {

            livros = livroRepository.findAll();

        } else {

            livros = livroRepository
                    .findByTitulo(busca.trim());
        }

        return livros.stream()
                .map(livroMapper::map)
                .toList();
    }
}
