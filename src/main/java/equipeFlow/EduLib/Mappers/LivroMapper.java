
package equipeFlow.EduLib.Mappers;

import equipeFlow.EduLib.DTOs.LivroDTO;
import equipeFlow.EduLib.Models.LivroModel;
import equipeFlow.EduLib.Utils.IdPublico;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {

    public LivroDTO map(LivroModel livroModel) {

        LivroDTO livroDTO = new LivroDTO();

        livroDTO.setIdLivro(IdPublico.formatarLivro(livroModel.getIdLivro()));

        livroDTO.setTitulo(livroModel.getTitulo());
        livroDTO.setAutor(livroModel.getAutor());
        livroDTO.setExemplaresDisponiveis(
                livroModel.getExemplaresDisponiveis()
        );
        livroDTO.setIsbn(livroModel.getIsbn());

        return livroDTO;
    }

    public LivroModel map(LivroDTO livroDTO) {

        LivroModel livroModel = new LivroModel();

        if (livroDTO.getIdLivro() != null) {
            livroModel.setIdLivro(IdPublico.extrairLivro(livroDTO.getIdLivro()));
        }

        livroModel.setTitulo(livroDTO.getTitulo());
        livroModel.setAutor(livroDTO.getAutor());
        livroModel.setExemplaresDisponiveis(
                livroDTO.getExemplaresDisponiveis()
        );
        livroModel.setIsbn(livroDTO.getIsbn());

        return livroModel;
    }
}
