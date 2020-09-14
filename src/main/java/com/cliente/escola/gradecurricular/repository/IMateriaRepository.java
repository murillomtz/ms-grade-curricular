package com.cliente.escola.gradecurricular.repository;

import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMateriaRepository extends JpaRepository<MateriaEntity, Long> {
}
