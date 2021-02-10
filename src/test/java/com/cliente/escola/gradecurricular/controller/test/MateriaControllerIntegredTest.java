package com.cliente.escola.gradecurricular.controller.test;

import com.cliente.escola.gradecurricular.dto.MateriaDto;
import com.cliente.escola.gradecurricular.entity.MateriaEntity;
import com.cliente.escola.gradecurricular.model.Response;
import com.cliente.escola.gradecurricular.repository.IMateriaRepository;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(JUnitPlatform.class)
public class MateriaControllerIntegredTest {

    private final String USERNAME= "admin";
    private final String PASSWORD= "123";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private IMateriaRepository materiaRepository;

    @BeforeEach
    public void init() {
        this.montaBaseDeDados();

    }

    @AfterEach
    public void finish() {
        this.materiaRepository.deleteAll();
    }

    private void montaBaseDeDados() {
        MateriaEntity m1 = new MateriaEntity();
        m1.setCodigo("ILP");
        m1.setFrequencia(2);
        m1.setHoras(64);
        m1.setNome("INTRODUCAO A LINGUAGEM DE PROGRAMACAO");

        MateriaEntity m2 = new MateriaEntity();
        m2.setCodigo("POO");
        m2.setFrequencia(2);
        m2.setHoras(84);
        m2.setNome("PROGRAMACAO ORIENTADA A OBJETO");

        MateriaEntity m3 = new MateriaEntity();
        m3.setCodigo("APA");
        m3.setFrequencia(1);
        m3.setHoras(102);
        m3.setNome("ANALIZE DE PROJETO E ALGORISMO");

        this.materiaRepository.saveAll(Arrays.asList(m1, m2, m3));


    }


    @Test
    public void testListarMaterias() {


        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange("http://localhost:" + this.port +
                "/materia/", HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
        });


        Assert.assertNotNull(materias.getBody().getData());
        Assert.assertEquals(3, materias.getBody().getData().size());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }


    @Test
    public void testConsultarMateriasPorHoraMinima() {

        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange("http://localhost:" + this.port +
                "/materia/horario-minimo/80", HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
        });


        Assert.assertNotNull(materias.getBody().getData());
        Assert.assertEquals(2, materias.getBody().getData().size());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMateriasPorFrequencia() {
        ResponseEntity<Response<List<MateriaDto>>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange("http://localhost:" + this.port +
                "/materia/frequencia/1", HttpMethod.GET, null, new ParameterizedTypeReference<Response<List<MateriaDto>>>() {
        });


        Assert.assertNotNull(materias.getBody().getData());
        Assert.assertEquals(1, materias.getBody().getData().size());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testConsultarMateriaPorId() {
        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        Long id = materiasList.get(0).getId();

        ResponseEntity<Response<MateriaDto>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange("http://localhost:" + this.port +
                "/materia/"+id, HttpMethod.GET, null, new ParameterizedTypeReference<Response<MateriaDto>>() {
        });


        Assert.assertNotNull(materias.getBody().getData());
        Assert.assertEquals(id, materias.getBody().getData().getId());
        Assert.assertEquals(64, materias.getBody().getData().getHoras());
        Assert.assertEquals("ILP", materias.getBody().getData().getCodigo());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testAtualizarMateria() {

        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        MateriaEntity materia = materiasList.get(0);

        materia.setNome("Teste Atualiza materia");

        HttpEntity<MateriaEntity> request = new HttpEntity<>(materia);

        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.PUT, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        MateriaEntity materiaAtualizada = this.materiaRepository.findById(materia.getId()).get();

        Assert.assertTrue(materias.getBody().getData());
        Assert.assertEquals("Teste Atualiza materia", materiaAtualizada.getNome());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }

    @Test
    public void testCadastrarMateria() {

        MateriaEntity m4 = new MateriaEntity();
        m4.setCodigo("CALC1");
        m4.setFrequencia(2);
        m4.setHoras(102);
        m4.setNome("CALCULO 1");


        HttpEntity<MateriaEntity> request = new HttpEntity<>(m4);

        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange(
                "http://localhost:" + this.port + "/materia/", HttpMethod.POST, request,
                new ParameterizedTypeReference<Response<Boolean>>() {
                });
        List<MateriaEntity> listmateriaAtualizada = this.materiaRepository.findAll();

        Assert.assertTrue(materias.getBody().getData());
        Assert.assertEquals(4, listmateriaAtualizada.size());
        Assert.assertEquals(201, materias.getBody().getStatusCode());
    }

    @Test
    public void testExcluirMateriaPorId() {
        List<MateriaEntity> materiasList = this.materiaRepository.findAll();
        Long id = materiasList.get(0).getId();

        ResponseEntity<Response<Boolean>> materias = restTemplate.withBasicAuth(USERNAME,PASSWORD).exchange("http://localhost:" + this.port +
                "/materia/"+id, HttpMethod.DELETE, null, new ParameterizedTypeReference<Response<Boolean>>() {
        });

        List<MateriaEntity> listmateriaAtualizada = this.materiaRepository.findAll();

        Assert.assertTrue(materias.getBody().getData());
        Assert.assertEquals(2, listmateriaAtualizada.size());
        Assert.assertEquals(200, materias.getBody().getStatusCode());
    }

}
