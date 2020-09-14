package com.cliente.escola.gradecurricular.service;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.cliente.escola.gradecurricular.exception.MateriaException;
import com.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<MateriaDto> listar() {
        try {
            return this.mapper.map(
                    this.materiaRepository.findAll(), new TypeToken<List<MateriaDto>>() {
                    }.getType());
        } catch (Exception e) {
            throw new MateriaException(MENSAGEM_ERRO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
}
