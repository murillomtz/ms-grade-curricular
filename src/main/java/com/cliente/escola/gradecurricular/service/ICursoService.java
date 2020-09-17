package com.cliente.escola.gradecurricular.service;

import com.cliente.escola.gradecurricular.entity.CursoEntity;
import com.cliente.escola.gradecurricular.model.CursoModel;

import java.util.List;

public interface ICursoService {
    Boolean cadastrar(CursoModel cursoModel);

    Boolean atualizar(CursoModel cursoModel);

    Boolean excluir(Long cursoId);

    CursoEntity consultarPorCodigo(String codCurso);

    List<CursoEntity> listar();
}
