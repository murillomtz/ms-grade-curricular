package com.cliente.escola.gradecurricular.service;

import com.cliente.escola.gradecurricular.dto.MateriaDto;

import java.util.List;

public interface IMateriaService {

    public Boolean atualizar(final MateriaDto materia);

    public Boolean excluir(final Long id);

    public List<MateriaDto> listar();

    public MateriaDto consultar(final Long id);

    public Boolean cadastrar(final MateriaDto materia);
}
