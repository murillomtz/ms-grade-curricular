package com.cliente.escola.gradecurricular.controller;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.cliente.escola.gradecurricular.model.Response;
import com.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.cliente.escola.gradecurricular.service.IMateriaService;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/materia")
public class MateriaController {

    private static final String DELETE = "DELETE";
    private static final String UPDATE = "UPDATE";

    private final IMateriaService materiaService;

    public MateriaController(IMateriaService materiaService) {

        this.materiaService = materiaService;
    }

    /**
     * Metodo retorna um List de MateriaEntity
     */
    @GetMapping
    public ResponseEntity<Response<List<MateriaDto>>> listarMaterias() {
        Response<List<MateriaDto>> response = new Response<>();
        response.setData(this.materiaService.listar());
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .listarMaterias()).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Metodo retorna o objeto referente ao id passado por paramentro no link
     *
     * @param id id do objeto
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response<MateriaDto>> consultarMateria(@PathVariable Long id) {
        Response<MateriaDto> response = new Response<>();
        response.setData(this.materiaService.consultar(id));
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .consultarMateria(id)).withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .excluirMateria(id)).withRel(DELETE));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .excluirMateria(id)).withRel(UPDATE));
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    /**
     * Metodo salva um objeto, e retorna true em caso de sucesso e false em casos de falha.
     *
     * @throws Exception
     * @RequestBody - Passar um formatação(corpo) em forma de json para o servidor
     */
    @PostMapping(value = "")
    public ResponseEntity<Boolean> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {

        return ResponseEntity.status(HttpStatus.CREATED).body(this.materiaService.cadastrar(materia));
    }


    /**
     * Metodo deleta o obj com referencia ao ID
     *
     * @param id id do objeto
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> excluirMateria(@PathVariable Long id) {

        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.excluir(id));
    }


    /**
     * Metodo atualiza um novo obj(Materia) apartir de outro obj passado como referencia
     *
     * @param materia Objeto a ser alterado
     */
    @PutMapping
    public ResponseEntity<Boolean> atualizarMateria(@Valid @RequestBody MateriaDto materia) {

        return ResponseEntity.status(HttpStatus.OK).body(this.materiaService.atualizar(materia));
    }
}
