package com.cliente.escola.gradecurricular.controller;

import com.cliente.escola.gradecurricular.config.SwaggerConfig;
import com.cliente.escola.gradecurricular.constant.HyperLinkConstant;
import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.model.Response;
import com.cliente.escola.gradecurricular.service.IMateriaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(tags = SwaggerConfig.MATERIA)
@RestController
@RequestMapping("/materia")
public class MateriaController {

    private static final String DELETE = "DELETE";
    private static final String UPDATE = "UPDATE";
    private static final String LIST = "GET_ALL";


    private final IMateriaService materiaService;

    public MateriaController(IMateriaService materiaService) {

        this.materiaService = materiaService;
    }

    /**
     * Metodo retorna um List de MateriaEntity
     */
    @ApiOperation(value = "Listar todas as matérias cadastradas")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Lista de matérias exibida com sucesso"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
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
    @ApiOperation(value = "Consultar matéria por ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Matéria encontrada com sucesso"),
            @ApiResponse(code = 404, message = "Matéria não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/{id}")
    public ResponseEntity<Response<MateriaDto>> consultarMateria(@PathVariable Long id) {

        Response<MateriaDto> response = new Response<>();
        MateriaDto materia = this.materiaService.consultar(id);

        response.setData(this.materiaService.consultar(id));
        response.setStatusCode(HttpStatus.OK.value());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .consultarMateria(id)).withSelfRel());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .excluirMateria(id)).withRel(HyperLinkConstant.EXCLUIR.getValor()));

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .atualizarMateria(materia)).withRel(HyperLinkConstant.ATUALIZAR.getValor()));

        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


    /**
     * Metodo salva um objeto, e retorna true em caso de sucesso e false em casos de falha.
     *
     * @throws Exception
     * @RequestBody - Passar um formatação(corpo) em forma de json para o servidor
     */
    @ApiOperation(value = "Cadastrar uma nova Matéria")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Matéria criada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @PostMapping(value = "")
    public ResponseEntity<Response<Boolean>> cadastrarMateria(@Valid @RequestBody MateriaDto materia) {
        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.cadastrar(materia));
        response.setStatusCode(HttpStatus.CREATED.value());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).
                cadastrarMateria(materia))
                .withSelfRel());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .atualizarMateria(materia)).withRel(HyperLinkConstant.ATUALIZAR.getValor()));
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .listarMaterias()).withRel(HyperLinkConstant.LISTAR.getValor()));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Metodo deleta o obj com referencia ao ID
     *
     * @param id id do objeto
     */
    @ApiOperation(value = "Excluir matéria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Matéria excluída com sucesso"),
            @ApiResponse(code = 404, message = "Matéria não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> excluirMateria(@PathVariable Long id) {

        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.excluir(id));
        response.setStatusCode(HttpStatus.OK.value());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).
                excluirMateria(id))
                .withSelfRel());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class)
                .listarMaterias()).withRel(HyperLinkConstant.LISTAR.getValor()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    /**
     * Metodo atualiza um novo obj(Materia) apartir de outro obj passado como referencia
     *
     * @param materia Objeto a ser alterado
     */
    @ApiOperation(value = "Atualizar matéria")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Matéria atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Erro na requisição enviada pelo cliente"),
            @ApiResponse(code = 404, message = "Matéria não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @PutMapping
    public ResponseEntity<Response<Boolean>> atualizarMateria(@Valid @RequestBody MateriaDto materia) {
        Response<Boolean> response = new Response<>();
        response.setData(this.materiaService.atualizar(materia));
        response.setStatusCode(HttpStatus.OK.value());

        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).atualizarMateria(materia))
                .withSelfRel());
        response.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).listarMaterias())
                .withRel(HyperLinkConstant.LISTAR.getValor()));

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Consultar matéria por hora mínima")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
            @ApiResponse(code = 404, message = "Matéria não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/horario-minimo/{horaMinima}")
    public ResponseEntity<Response<List<MateriaDto>>> consultaMateriaPorHoraMinima(@PathVariable int horaMinima) {
        Response<List<MateriaDto>> response = new Response<>();
        List<MateriaDto> materia = this.materiaService.listarPorHorarioMinimo(horaMinima);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateriaPorHoraMinima(horaMinima))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @ApiOperation(value = "Consultar matéria por frequencia")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Consulta realizada com sucesso"),
            @ApiResponse(code = 404, message = "Matéria não encontrada"),
            @ApiResponse(code = 500, message = "Erro interno no serviço"),
    })
    @GetMapping("/frequencia/{frequencia}")
    public ResponseEntity<Response<List<MateriaDto>>> consultaMateriaPorFrequencia(@PathVariable int frequencia) {
        Response<List<MateriaDto>> response = new Response<>();
        List<MateriaDto> materia = this.materiaService.listarPorFrequencia(frequencia);
        response.setData(materia);
        response.setStatusCode(HttpStatus.OK.value());
        response.add(WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder.methodOn(MateriaController.class).consultaMateriaPorFrequencia(frequencia))
                .withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
