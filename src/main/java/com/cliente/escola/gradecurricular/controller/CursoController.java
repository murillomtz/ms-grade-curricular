package com.cliente.escola.gradecurricular.controller;

import com.cliente.escola.gradecurricular.entity.CursoEntity;
import com.cliente.escola.gradecurricular.model.CursoModel;
import com.cliente.escola.gradecurricular.model.Response;
import com.cliente.escola.gradecurricular.service.ICursoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/curso")
public class CursoController {


    private final ICursoService cursoService;

    public CursoController(ICursoService cursoService) {
        this.cursoService = cursoService;
    }


    @PostMapping
    public ResponseEntity<Response<Boolean>> cadastrarCurso(@Valid @RequestBody CursoModel curso) {

        Response<Boolean> response = new Response<>();

        response.setData(cursoService.cadastrar(curso));
        response.setStatusCode(HttpStatus.CREATED.value());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    /*
     * Listar cursos
     */

    @GetMapping
    public ResponseEntity<Response<List<CursoEntity>>> listarCurso() {
        Response<List<CursoEntity>> response = new Response<>();
        response.setData(this.cursoService.listar());
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Consultar curso por código do curso
     */

    @GetMapping("/{codCurso}")
    public ResponseEntity<Response<CursoEntity>> consultarCursoPorMateria(@PathVariable String codCurso) {
        Response<CursoEntity> response = new Response<>();
        response.setData(this.cursoService.consultarPorCodigo(codCurso));
        response.setStatusCode(HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Atualizar curso
     */
    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarCurso(@Valid @RequestBody CursoModel curso) {
        Response<Boolean> response = new Response<>();
        response.setData(cursoService.atualizar(curso));
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /*
     * Excluir curso
     */
    @DeleteMapping("/{cursoId}")
    public ResponseEntity<Response<Boolean>> excluirCurso(@PathVariable Long cursoId) {
        Response<Boolean> response = new Response<>();
        response.setData(cursoService.excluir(cursoId));
        response.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


}
