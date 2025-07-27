# Voll.med API

API REST para gerenciamento de consultas médicas, médicos, pacientes e autenticação de usuários.

## Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Data JPA
- Spring Security (JWT)
- Maven
- MySQL
- Swagger/OpenAPI

## Funcionalidades

- Cadastro, atualização, listagem, detalhamento e inativação de médicos
- Agendamento e cancelamento de consultas
- Autenticação de usuários via JWT
- Validações customizadas para agendamento e cancelamento
- Paginação nas listagens
- Documentação automática via Swagger

## Estrutura do Projeto

- `domain`: Entidades, DTOs e regras de negócio
- `controller`: Endpoints REST
- `infra`: Infraestrutura (segurança, exceções)
- `repository`: Interfaces de acesso ao banco de dados

## Configuração

### Banco de Dados

Configure o MySQL e crie o banco `vollmed_api`. Defina as variáveis de ambiente:

- `MYSQL_PASSWORD`: Senha do usuário root do MySQL
- `DB_PASSWORD`: Segredo para geração do token JWT

O arquivo `application.properties` já está preparado para uso local.

### Dependências

Instale as dependências com Maven:
```
mvn clean install
```

### Executando a Aplicação
```
mvn spring-boot:run
```

A API estará disponível em `http://localhost:8080`.

## Endpoints Principais

### Autenticação

- `POST /login`  
  Autentica o usuário e retorna o token JWT.

### Médicos

- `POST /medicos`  
  Cadastra um novo médico.
- `GET /medicos`  
  Lista médicos ativos (com paginação).
- `GET /medicos/{id}`  
  Detalha um médico.
- `PUT /medicos`  
  Atualiza dados de um médico.
- `DELETE /medicos/{id}`  
  Inativa um médico.

### Consultas

- `POST /consultas`  
  Agenda uma consulta.
- `DELETE /consultas`  
  Cancela uma consulta.

## Segurança

Todos os endpoints (exceto `/login`) exigem autenticação via Bearer Token (JWT).

## Documentação

Acesse a documentação Swagger em: http://localhost:8080/swagger-ui.html

## Testes

Os testes podem ser executados com:
```
mvn test
```

## Contribuição

1. Fork este repositório
2. Crie uma branch (`git checkout -b feature/nova-feature`)
3. Commit suas alterações (`git commit -am 'Adiciona nova feature'`)
4. Push para a branch (`git push origin feature/nova-feature`)
5. Abra um Pull Request

## Licença

Este projeto está sob a licença MIT.
