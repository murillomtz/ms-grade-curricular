package com.cliente.escola.gradecurricular.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "tb_curso")
@Data
@NoArgsConstructor
public class CursoEntity implements Serializable{

    private static final long serialVersionUID = -5115709874529054925L;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Id
    @GeneratedValue(generator = "incrementar")
    @GenericGenerator(name = "incrementar", strategy = "increment")
    @Column(name = "id")
    private Long id;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "nome")
    private String nome;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @Column(name = "cod")
    private String codigo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="materia_id")
    private List<MateriaEntity> materias;
}
