# Cadastro de Alunos — Spring Boot + PostgreSQL + Docker

Aplicação web para **cadastro de alunos** (CRUD completo) com tela de **login**
e controle de acesso por perfil. Feita com Spring Boot, Thymeleaf e PostgreSQL,
e empacotada em Docker.

---

## Alunos

- Ana Julia de Oliveira Rabelo - Matrícula: 1250207762
- Lucas Guimarães Fabrício - Matrícula: 1250111347
- Lucas Emanuel de Souza Santos - Matrícula: 1250124354

## Funcionalidades

- **Login** com dois perfis (usuários em memória, só para fins didáticos):
  - **Admin** (`admin` / `admin123`) — acesso total: inserir, editar e excluir.
  - **Colaborador** (`colab` / `colab123`) — acesso limitado: inserir e visualizar.
- **CRUD de alunos**: listar, cadastrar, editar e excluir.
- **Validação** dos campos (nome, matrícula, curso e período) com mensagens de erro.
- **Controle de sessão** via interceptor: quem não está logado é redirecionado
  para `/login`; rotas de editar/excluir são exclusivas do Admin.

---

## Tecnologias

- Java 17
- Spring Boot 4 (Spring MVC, Spring Data JPA, Thymeleaf, Validation)
- PostgreSQL 16
- Bootstrap 5 (via CDN)
- Docker

---

## Estrutura do projeto

```
src/main/java/com/example/cadastroaluno/
├── CadastroalunoApplication.java     # classe principal (Spring Boot)
├── controller/
│   ├── AlunoController.java          # rotas do CRUD de alunos
│   └── LoginController.java          # login / logout
├── config/
│   ├── AuthInterceptor.java          # "segurança": valida sessão e perfil
│   └── WebConfig.java                # registra o interceptor
├── model/
│   ├── Aluno.java                    # entidade JPA (vira tabela 'aluno')
│   └── Usuario.java                  # usuário em memória (não é tabela)
├── repository/
│   └── AlunoDAO.java                 # repositório JPA
└── exception/
    ├── AlunoNaoEncontradoException.java
    └── GlobalExceptionHandler.java
src/main/resources/
├── application.properties            # configuração (banco, JPA)
└── templates/                        # páginas Thymeleaf (login, alunos, form, erro)
```

### Campos do aluno

| Campo       | Regras de validação                                  |
|-------------|------------------------------------------------------|
| `nome`      | Obrigatório, entre 3 e 100 caracteres                |
| `matricula` | Obrigatória, apenas números                          |
| `curso`     | Obrigatório                                          |
| `periodo`   | Obrigatório, apenas números                          |

### Rotas

| Método | Rota            | Descrição                          | Acesso        |
|--------|-----------------|------------------------------------|---------------|
| GET    | `/login`        | Tela de login                      | Público       |
| POST   | `/login`        | Autentica usuário                  | Público       |
| GET    | `/logout`       | Encerra a sessão                   | Logado        |
| GET    | `/`             | Lista de alunos                    | Logado        |
| GET    | `/novo`         | Formulário de novo aluno           | Logado        |
| POST   | `/salvar`       | Salva (cria ou atualiza)           | Logado        |
| GET    | `/editar/{id}`  | Formulário de edição               | Apenas Admin  |
| GET    | `/excluir/{id}` | Exclui um aluno                    | Apenas Admin  |

---

## Como executar com Docker

### Pré-requisitos
- Docker Desktop instalado e aberto (ícone verde / "running").

### Passos

1. Criar a rede (permite que os containers conversem pelo nome):
```powershell
docker network create cadastro-net
```

2. Subir o PostgreSQL:
```powershell
docker run -d --name cadastro-postgres --network cadastro-net -e POSTGRES_DB=cadastroaluno -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v cadastro-pgdata:/var/lib/postgresql/data postgres:16
```
> Aguarde uns 10 segundos para o banco inicializar. Você pode confirmar com
> `docker logs cadastro-postgres --tail 5` — quando aparecer
> *"database system is ready to accept connections"*, pode seguir.

**3. Construir a imagem do backend** (compila o `.jar` dentro do Docker):
```powershell
docker build -t cadastroaluno:1.0 .
```

**4. Subir o backend** (conecta no Postgres pelo nome do container):
```powershell
docker run -d --name cadastro-backend --network cadastro-net -e SPRING_DATASOURCE_URL=jdbc:postgresql://cadastro-postgres:5432/cadastroaluno -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -p 8080:8080 cadastroaluno:1.0
```

**5. Acessar a aplicação:**

👉 http://localhost:8080 (redireciona para a tela de login)

### Comandos úteis
```powershell
docker ps                          # ver containers rodando
docker logs cadastro-backend -f    # acompanhar os logs da aplicação
```

### Derrubar tudo
```powershell
docker rm -f cadastro-backend cadastro-postgres
docker network rm cadastro-net
```
> Para apagar também os dados do banco: `docker volume rm cadastro-pgdata`

### Recompilar após alterar o código
Sempre que mudar o código, refaça a imagem e suba de novo o backend:
```powershell
docker rm -f cadastro-backend
docker build -t cadastroaluno:1.0 .
docker run -d --name cadastro-backend --network cadastro-net -e SPRING_DATASOURCE_URL=jdbc:postgresql://cadastro-postgres:5432/cadastroaluno -e SPRING_DATASOURCE_USERNAME=postgres -e SPRING_DATASOURCE_PASSWORD=postgres -p 8080:8080 cadastroaluno:1.0
```

---

## Como executar localmente (sem Docker)

Útil para rodar pela IDE durante o desenvolvimento.

1. Tenha um **PostgreSQL** rodando em `localhost:5432` com um banco
   `cadastroaluno` (usuário/senha `postgres`/`postgres`), conforme o
   [application.properties](src/main/resources/application.properties).
2. Rode a aplicação:
   ```powershell
   .\mvnw spring-boot:run
   ```
3. Acesse http://localhost:8080

> O Hibernate está com `ddl-auto=update`, então a tabela `aluno` é criada
> automaticamente na primeira execução.

---

## Usuários de teste

| Perfil      | Login   | Senha      | Permissões                          |
|-------------|---------|------------|-------------------------------------|
| Admin       | `admin` | `admin123` | Inserir, editar e excluir           |
| Colaborador | `colab` | `colab123` | Inserir e visualizar                |
