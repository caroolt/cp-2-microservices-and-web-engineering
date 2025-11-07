# 📚 Library Management System

Uma API REST completa para sistema de gestão de biblioteca, desenvolvida em Spring Boot com Java 17. O sistema permite gerenciar livros, autores e empréstimos de forma eficiente e organizada.

## 🚀 Funcionalidades

- **Gestão de Autores**: Criar, listar, atualizar e excluir autores
- **Gestão de Livros**: Cadastrar livros associados a autores
- **Sistema de Empréstimos**: Controlar empréstimos de livros com datas de devolução
- **API REST**: Endpoints bem documentados e padronizados
- **Documentação Swagger**: Interface interativa para testar a API
- **Banco de Dados MySQL**: Persistência robusta dos dados

## 👥 Integrantes da equipe

- Daniel Marin Palma - RM 551738
- Carolina Teixeira Coelho - RM 97643
- Renata Almeida Lima - RM 552588

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **MySQL 8.4.0**
- **Docker & Docker Compose**
- **Swagger/OpenAPI**
- **Maven**

## 📋 Pré-requisitos

Antes de executar a aplicação, certifique-se de ter instalado:

- [Docker](https://www.docker.com/get-started) (versão 20.10 ou superior)
- [Docker Compose](https://docs.docker.com/compose/install/) (versão 2.0 ou superior)

## 🚀 Como executar a aplicação

### Opção 1: Docker Hub (Recomendado)

Esta é a forma mais rápida de executar a aplicação usando as imagens pré-construídas do Docker Hub.

**📦 Imagem Docker Hub**: [caroolt/cp-2-microservices-and-web-engineering](https://hub.docker.com/r/caroolt/cp-2-microservices-and-web-engineering)

#### 1. Iniciar o banco de dados MySQL

```bash
docker run -d \
  --name mysql \
  --rm \
  -e MYSQL_ROOT_PASSWORD=root_pwd \
  -e MYSQL_USER=new_user \
  -e MYSQL_PASSWORD=my_pwd \
  -p 3306:3306 \
  mysql:latest
```

#### 2. Iniciar a API

```bash
docker run -d \
  --name api \
  --rm \
  --link mysql:mysql \
  -p 8080:8080 \
  -e DB_SERVER=mysql \
  -e DB_PORT=3306 \
  -e DB_DATABASE=api \
  -e DB_USER=root \
  -e DB_PASSWORD=root_pwd \
  caroolt/cp-2-microservices-and-web-engineering:latest
```

#### 3. Acessar a aplicação

A API estará disponível em: **http://localhost:8080**

### Opção 2: Docker Compose

Para uma execução mais simples e organizada, use o Docker Compose que gerencia todos os serviços automaticamente.

#### Executar todos os serviços

```bash
docker compose up
```

#### Executar serviços individualmente

```bash
# Apenas o banco de dados
docker compose up db

# Apenas a API
docker compose up api
```

#### Parar os serviços

```bash
docker compose down
```

## 📖 Documentação da API

### Swagger UI

Acesse a documentação interativa da API através do Swagger UI:

- **URL Principal**: http://localhost:8080/
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html

### Endpoints Principais

#### Autores
- `GET /authors` - Listar todos os autores
- `POST /authors` - Criar novo autor
- `GET /authors/{id}` - Buscar autor por ID
- `PUT /authors/{id}` - Atualizar autor
- `DELETE /authors/{id}` - Excluir autor

#### Livros
- `GET /books` - Listar todos os livros
- `POST /books` - Criar novo livro
- `GET /books/{id}` - Buscar livro por ID
- `PUT /books/{id}` - Atualizar livro
- `DELETE /books/{id}` - Excluir livro

#### Empréstimos
- `GET /loans` - Listar todos os empréstimos
- `POST /loans` - Criar novo empréstimo
- `GET /loans/{id}` - Buscar empréstimo por ID
- `PUT /loans/{id}` - Atualizar empréstimo
- `DELETE /loans/{id}` - Excluir empréstimo

## 🔧 Configuração do Banco de Dados

A aplicação está configurada para usar MySQL com as seguintes configurações padrão:

- **Host**: mysql (quando usando Docker) ou localhost
- **Porta**: 3306
- **Database**: api
- **Usuário**: root
- **Senha**: root_pwd

## 🐛 Solução de Problemas

### Porta já em uso
Se a porta 8080 estiver em uso, você pode alterar a porta no comando Docker:

```bash
docker run -p 8081:8080 caroolt/cp-2-microservices-and-web-engineering:latest
```

### Problemas de conexão com o banco
Certifique-se de que o container do MySQL está rodando antes de iniciar a API:

```bash
docker ps
```

### Limpar containers
Para remover todos os containers e volumes:

```bash
docker compose down -v
docker system prune -a
```

## 📝 Exemplos de Uso

### Criar um autor
```bash
curl -X POST http://localhost:8080/authors \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Machado de Assis",
    "birthDate": "1839-06-21"
  }'
```

### Criar um livro
```bash
curl -X POST http://localhost:8080/books \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Dom Casmurro",
    "description": "Romance clássico da literatura brasileira",
    "authorId": 1
  }'
```

## 🔍 Decisões Técnicas e Notas de Implementação

### Testes Integrados no Workflow CD

**Decisão:** Os testes integrados são executados dentro de um container Docker Ubuntu temporário ao invés de diretamente no runner do GitHub Actions.

**Motivo:** 
- O script de testes usa `apt` para instalar `curl` e `jq`, que requer permissões de root
- Executar diretamente no runner causava erro: `Permission denied` ao tentar usar `apt`
- Executar dentro de um container Docker garante um ambiente isolado e com as permissões necessárias

**Implementação:**
```yaml
docker run --rm \
  --network cp-2-microservices-and-web-engineering_app-networks \
  -v ${{ github.workspace }}/server:/server \
  -w /server \
  ubuntu:latest \
  bash -c "apt update && apt install -y curl jq && bash run_integrated_tests.sh"
```

### Uso do Nome do Serviço Docker ao Invés de localhost

**Decisão:** O script de testes integrados usa `http://api:8080` ao invés de `http://localhost:8080` para acessar a API.

**Motivo:**
- Quando executado dentro da rede Docker (`app-networks`), o container temporário não consegue acessar `localhost:8080`
- Dentro da mesma rede Docker, os serviços se comunicam pelo nome do serviço definido no `docker-compose.yaml`
- O serviço da API está nomeado como `api` no docker-compose, então usamos `api:8080`

**Implementação:**
```bash
# O script detecta automaticamente o ambiente
API_HOST=${API_HOST:-api}  # Usa 'api' por padrão (nome do serviço Docker)
API_URL="http://${API_HOST}:${API_PORT}"
```

## 📄 Licença
Este projeto foi desenvolvido como parte do Checkpoint 2 do curso de Microservices and Web Engineering.