package com.cliente.escola.gradecurricular.service;

import com.cliente.escola.gradecurricular.controller.MateriaController;
import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.cliente.escola.gradecurricular.exception.MateriaException;
import com.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.cache.annotation.*;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@CacheConfig(cacheNames = "materia")
@Service
public class MateriaService implements IMateriaService {

    private static final String MENSAGEM_ERRO = "Erro interno identificado. Contate o suporte";
    private static final String MENSAGEM_NAO_ENCONTRADA = "Materia não encontrada";
    private IMateriaRepository materiaRepository;
    private ModelMapper mapper;

    public MateriaService(IMateriaRepository materiaRepository) {
        this.materiaRepository = materiaRepository;
        this.mapper = new ModelMapper();
    }


    //@CacheEvict(key = "#materia.id")
    @Override
    public Boolean atualizar(MateriaDto materia) {
        try {
            this.consultar(materia.getId());

            MateriaEntity materiaEntityAtualizada = this.mapper.map(materia, MateriaEntity.class);

            //Salvamos as alterações
            this.materiaRepository.save(materiaEntityAtualizada);
            return Boolean.TRUE;

        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Boolean excluir(Long id) {
        try {
            this.consultar(id);
            this.materiaRepository.deleteById(id);
            return Boolean.TRUE;
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw e;
        }
    }

    /*quando o tamanho da lsita for menor que 3 ele nao sobe para o cache*/
    @CachePut(unless = "#result.size()<3")
    @Override
    public List<MateriaDto> listar() {
        try {
            List<MateriaDto> materiasDto = this.mapper.map(
                    this.materiaRepository.findAll(), new TypeToken<List<MateriaDto>>() {
                    }.getType());
            materiasDto.forEach(materia -> {
                materia.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                        .consultarMateria(materia.getId())).withSelfRel());
            });


            return materiasDto;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* put executa o metodo e se tiver algo diferete ele atualiza o cahce,menos performatico
     * que Cacheble*/
    @CachePut(key = "#id")
    @Override
    public MateriaDto consultar(Long id) {
        try {

            Optional<MateriaEntity> materiaOptinal = this.materiaRepository.findById(id);
            if (materiaOptinal.isPresent()) {
                return this.mapper.map(materiaOptinal.get(), MateriaDto.class);
            }
            throw new MateriaException(MENSAGEM_NAO_ENCONTRADA, HttpStatus.NOT_FOUND);
        } catch (MateriaException m) {
            throw m;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @CacheEvict(allEntries = true) //Escluir tudo toda vez q usar o cadastrar
    @Override
    public Boolean cadastrar(MateriaDto materia) {
        try {

            //Cria uma relação de mapeamento da classe recebida por parametro pela MateriaEntity
            MateriaEntity materiaEntity = this.mapper.map(materia, MateriaEntity.class);
            this.materiaRepository.save(materiaEntity);
            return Boolean.TRUE;
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @Override
    public List<MateriaDto> listarPorHorarioMinimo(int horaMinima) {
        return this.mapper.map(this.materiaRepository.findByHoraMinima(horaMinima), new TypeToken<List<MateriaDto>>() {
        }.getType());
    }

    @Override
    public List<MateriaDto> listarPorFrequencia(int frequencia) {
        return this.mapper.map(this.materiaRepository.findByFrequencia(frequencia), new TypeToken<List<MateriaDto>>() {
        }.getType());
    }


}
