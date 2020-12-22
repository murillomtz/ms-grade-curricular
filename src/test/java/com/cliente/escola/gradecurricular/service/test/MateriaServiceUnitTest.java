package com.cliente.escola.gradecurricular.service.test;


import com.cliente.escola.gradecurricular.constant.MensagensConstant;
import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.cliente.escola.gradecurricular.exception.MateriaException;
import com.cliente.escola.gradecurricular.repository.IMateriaRepository;
import com.cliente.escola.gradecurricular.service.MateriaService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class MateriaServiceUnitTest {

    @Mock
    private IMateriaRepository materiaRepository;

    @InjectMocks
    private MateriaService materiaService;

    private static MateriaEntity materiaEntity;

    @BeforeAll
    public static void init() {

        materiaEntity = new MateriaEntity();
        materiaEntity.setId(1L);
        materiaEntity.setCodigo("ILP");
        materiaEntity.setFrequencia(1);
        materiaEntity.setHoras(64);
        materiaEntity.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

    }

    /*
     * CENARIO DE SUCESSO
     *
     * */
    @Test
    public void testListarSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);


        Mockito.when(this.materiaRepository.findAll()).thenReturn(listMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listar();
        Long id = 1L;

        Assert.assertNotNull(listMateriaDto);
        Assert.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assert.assertEquals(id, listMateriaDto.get(0).getId());
        Assert.assertEquals("/materia/1", listMateriaDto.get(0).getLinks().getRequiredLink("self").getHref());
        Assert.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, times(1)).findAll();
    }

    @Test
    public void testListarPorHorarioMinimoSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByHoraMinima(64)).thenReturn(listMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listarPorHorarioMinimo(64);
        Long id = 1L;
        Assert.assertNotNull(listMateriaDto);
        Assert.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assert.assertEquals(id, listMateriaDto.get(0).getId());
        Assert.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, times(1)).findByHoraMinima(64);

    }

    @Test
    public void testListarPorFrequenciaSucesso() {
        List<MateriaEntity> listMateria = new ArrayList<>();
        listMateria.add(materiaEntity);

        Mockito.when(this.materiaRepository.findByFrequencia(1)).thenReturn(listMateria);

        List<MateriaDto> listMateriaDto = this.materiaService.listarPorFrequencia(1);
        Long id = 1L;
        Assert.assertNotNull(listMateriaDto);
        Assert.assertEquals("ILP", listMateriaDto.get(0).getCodigo());
        Assert.assertEquals(id, listMateriaDto.get(0).getId());
        Assert.assertEquals(1, listMateriaDto.size());

        Mockito.verify(this.materiaRepository, times(1)).findByFrequencia(1);

    }

    @Test
    public void testConsultarSucesso() {
        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        MateriaDto materiaDto = this.materiaService.consultar(1L);

        Long id = 1L;
        Assert.assertNotNull(materiaDto);
        Assert.assertEquals("ILP", materiaDto.getCodigo());
        Assert.assertEquals(id, materiaDto.getId());
        Assert.assertEquals(1, materiaDto.getFrequencia());

        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
    }

    @Test
    public void testCadastrarSucesso() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        materiaEntity.setId(null);

        Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(null);
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.cadastrar(materiaDto);

        assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);

        materiaEntity.setId(1L);

    }

    @Test
    public void testAtualizarSucesso() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenReturn(materiaEntity);

        Boolean sucesso = this.materiaService.atualizar(materiaDto);

        assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);


    }

    @Test
    public void testExcluirSucesso() {

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Boolean sucesso = this.materiaService.excluir(1L);

        Assert.assertTrue(sucesso);

        Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
        Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);
    }

    /*
     *
     * CENARIOS DE THROW-MATERIA-EXCEPTION
     *
     */


    @Test
    public void testAtualizarThrowMateriaException() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizar(materiaDto);
        });

        assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);


    }

    @Test
    public void testExcluirThrowMateriaException() {

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.empty());

        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.excluir(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_NAO_ENCONTRADA.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, times(0)).deleteById(1L);


    }

    @Test
    public void testCadastrarComIdThrowMateriaException() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrar(materiaDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_ID_INFORMADO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(0)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);


    }

    @Test
    public void testCadastrarComCodigoExistenteThrowMateriaException() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        Mockito.when(this.materiaRepository.findByCodigo("ILP")).thenReturn(materiaEntity);

        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.cadastrar(materiaDto);
        });

        assertEquals(HttpStatus.BAD_REQUEST, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_MATERIA_CADASTRADA_ANTERIORMENTE.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(1)).findByCodigo("ILP");
        Mockito.verify(this.materiaRepository, times(0)).save(materiaEntity);


    }
    /*
     *
     * CENARIOS DE THROW EXCEPTION
     *
     */

    @Test
    public void testAtualizarThrowException() {

        MateriaDto materiaDto = new MateriaDto();
        materiaDto.setId(1L);
        materiaDto.setCodigo("ILP");
        materiaDto.setFrequencia(1);
        materiaDto.setHoras(64);
        materiaDto.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");


        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        Mockito.when(this.materiaRepository.save(materiaEntity)).thenThrow(IllegalStateException.class);

        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.atualizar(materiaDto);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(1)).findById(1L);
        Mockito.verify(this.materiaRepository, times(1)).save(materiaEntity);


    }

    @Test
    public void testExcluirThrowException() {

        Mockito.when(this.materiaRepository.findById(1L)).thenReturn(Optional.of(materiaEntity));
        //Quando o metodo retorna void
        Mockito.doThrow(IllegalStateException.class).when(this.materiaRepository).deleteById(1l);

        MateriaException materiaException;


        materiaException = assertThrows(MateriaException.class, () -> {
            this.materiaService.excluir(1l);
        });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, materiaException.getHttpStatus());
        assertEquals(MensagensConstant.ERRO_GENERICO.getValor(), materiaException.getMessage());

        Mockito.verify(this.materiaRepository, times(1)).deleteById(1L);
        Mockito.verify(this.materiaRepository, times(1)).findById(1L);



    }
    /*consulta, cadastro, listagem
     *
     * menos exclusçao*/
}
