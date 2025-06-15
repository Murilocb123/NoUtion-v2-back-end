
# Noution V2 - Back-end

Backend do projeto **Noution V2**, desenvolvido em Java com Spring Boot. Este projeto fornece uma API REST para gerenciamento de tarefas com suporte a multi-tenant, autenticação JWT e integração com AWS SQS.

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot
- Spring Security (JWT)
- JPA / Hibernate (Multi-tenant)
- AWS SQS
- Maven

## 📁 Estrutura do Projeto

```
noution-v2-backend/
├── .mvn/
├── src/
│   └── main/
│       └── java/
│           └── br/com/murilocb123/noutionbackend/
│               ├── controller/           # Endpoints REST
│               ├── converter/            # Conversores DTO <-> Entity
│               ├── dto/                  # Data Transfer Objects
│               ├── entities/             # Entidades do Banco de Dados
│               ├── enums/                # Enums
│               ├── infra/                # Configurações (Segurança, AWS, CORS, Multi-Tenant)
│               └── NoutionBackendApplication.java  # Classe principal
├── pom.xml
```

## 🚀 Como Executar Localmente

### Pré-requisitos

- Java 17+
- Maven 3.8+
- Docker (opcional, para serviços auxiliares como banco de dados)

### Passos

1. Clone o repositório:

```bash
git clone https://github.com/Murilocb123/noution-v2-backend.git
cd noution-v2-backend
```

2. Configure as variáveis de ambiente (Exemplo de `application.properties`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/noution
spring.datasource.username=root
spring.datasource.password=senha

spring.security.jwt.secret=seuSegredoAqui
aws.sqs.queue.url=suaQueueURL
```

3. Execute o projeto:

```bash
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

## 🔐 Autenticação

O projeto utiliza autenticação baseada em JWT.

### Endpoints de Autenticação:

- `POST /auth/login` — Login
- `POST /auth/register` — Registro de novo usuário

## 📦 Endpoints Principais

| Método | Endpoint       | Descrição                  |
|--------|----------------|----------------------------|
| POST   | `/auth/login`  | Autenticação de usuários   |
| POST   | `/auth/register`| Registro de novos usuários |
| GET    | `/tasks`       | Listar tarefas             |
| POST   | `/tasks`       | Criar nova tarefa          |
| PUT    | `/tasks/{id}`  | Atualizar tarefa           |
| DELETE | `/tasks/{id}`  | Deletar tarefa             |

## 🏗️ Multi-Tenancy

O projeto implementa isolamento de dados por cliente (tenant) utilizando a estratégia de `Tenant Identifier Resolver` do Hibernate.

## ☁️ Integração com AWS

- **SQS:** Consumo e envio de mensagens para filas AWS SQS.


## 🐋 Docker image
Para facilitar o deploy, uma imagem Docker está disponível.
URL: https://hub.docker.com/repository/docker/tiomuri12/noution-v2-back-end


---

Desenvolvido por [MuriloCB123](https://github.com/Murilocb123) 🚀
