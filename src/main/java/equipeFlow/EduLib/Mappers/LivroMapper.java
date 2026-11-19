package equipeFlow.EduLib.Mappers;

import equipeFlow.EduLib.DTOs.LivroDTO;
import equipeFlow.EduLib.Models.LivroModel;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {

    public LivroDTO map(LivroModel livroModel){
        LivroDTO livroDTO = new LivroDTO();
        livroDTO.setTitulo(livroModel.getTitulo());
        livroDTO.setAutor(livroModel.getAutor());
        livroDTO.setExemplaresDisponiveis(livroDTO.getExemplaresDisponiveis());
        livroDTO.setIsbn(livroModel.getIsbn());

        return livroDTO;
    }

    public LivroModel map(LivroDTO livroDTO){
        LivroModel livroModel = new LivroModel();
        livroModel.setTitulo(livroDTO.getTitulo());
        livroModel.setAutor(livroDTO.getAutor());
        livroModel.setExemplaresDisponiveis(livroDTO.getExemplaresDisponiveis());
        livroDTO.setIsbn(livroDTO.getIsbn());

        return livroModel;
    }

}
