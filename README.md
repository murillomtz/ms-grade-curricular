# Microserviço de Grade Curricular

Serviço REST para manter cursos e matérias de uma escola. O projeto permite consultar a grade e filtrar matérias por carga horária mínima ou frequência, com modelos de entrada, validação e respostas padronizadas.

**Categoria:** Backend · Microserviço · Gestão acadêmica.

## Tecnologias principais

- Java 8
- Spring Boot 2.3.0
- Spring Data JPA
- PostgreSQL
- Swagger

## Funcionalidades

- Cadastrar, listar, atualizar e excluir cursos.
- Consultar um curso pelo seu código.
- Cadastrar, listar, atualizar e excluir matérias.
- Consultar uma matéria pelo identificador.
- Filtrar matérias por horário mínimo e frequência.
- Validar modelos de entrada e devolver resultados em um objeto de resposta comum.

## API versionada

| Rota | Uso |
| --- | --- |
| `/v1/curso` | Listar, criar e atualizar cursos. |
| `/v1/curso/{codCurso}` | Consultar um curso por código. |
| `/v1/curso/{cursoId}` | Excluir um curso por identificador. |
| `/v1/materia` | Listar, criar e atualizar matérias. |
| `/v1/materia/{id}` | Consultar ou excluir uma matéria. |
| `/v1/materia/horario-minimo/{horaMinima}` | Filtrar pela carga horária mínima. |
| `/v1/materia/frequencia/{frequencia}` | Filtrar pela frequência. |

## Organização e recursos técnicos

Os controladores e serviços da versão 1 ficam em `v1/`. DTOs e modelos separam as representações da API das entidades persistidas. ModelMapper auxilia as conversões, Spring Cache é utilizado no serviço de matérias e a configuração Swagger documenta a API. O projeto também declara Spring Actuator.

## Executar

Requisitos: JDK compatível com Java 8, Maven e PostgreSQL. A configuração original contém uma conexão remota antiga; substitua-a por uma conexão própria antes de iniciar o serviço. As propriedades ficam em `src/main/resources/application.properties`.

```bash
mvn spring-boot:run
```

A porta configurada é 8080. O projeto inclui a dependência H2 e exemplos comentados de configuração, mas o ambiente principal utiliza PostgreSQL. Para usar H2, é necessário alinhar URL, driver e dialeto na configuração.

## Relação com outros projetos

Nenhum frontend correspondente foi identificado na lista desta coleção. O projeto foi classificado e documentado pela leitura do código; ele não recebeu capturas de interface nem foi executado nesta etapa voltada aos frontends.


## Histórico

Repositório criado no GitHub em **2020-09-09**. Essa data identifica a criação do repositório; não comprova o início nem a conclusão do desenvolvimento.
