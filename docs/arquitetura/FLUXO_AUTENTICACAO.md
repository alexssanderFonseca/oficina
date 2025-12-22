# Fluxos de Autenticação

Este documento detalha os fluxos de autenticação implementados na aplicação, que se dividem em dois cenários principais: autenticação administrativa (para funcionários) e autenticação para clientes.

## 1. Autenticação Administrativa (Funcionários)

Este fluxo é destinado aos funcionários da oficina e utiliza credenciais de `username` e `password` para acesso a funcionalidades administrativas.

![Diagrama de Sequência - Autenticação Administrativa](funcionario_auth.png)

### Explicação do Fluxo:

1.  **Requisição de Login:** O Funcionário envia uma requisição `POST` para o endpoint `/auth/login` da API, contendo seu `username` e `password`.
2.  **Validação de Credenciais:** O Servidor da API encaminha as credenciais para a Camada de Segurança, que valida o `username` e `password` contra o banco de dados (na tabela `usuarios`).
3.  **Autenticação Bem-Sucedida:** Se as credenciais forem válidas, a Camada de Segurança informa à API sobre a autenticação bem-sucedida do usuário.
4.  **Geração do JWT:** A API então solicita ao Gerador de JWT (JSON Web Token) a criação de um token de acesso, passando as informações do usuário autenticado.
5.  **Retorno do JWT:** O Gerador de JWT retorna o token gerado para a API.
6.  **Resposta ao Funcionário:** A API envia o token JWT de volta ao Funcionário, geralmente no corpo da resposta JSON.
7.  **Requisições Subsequentes:** Para todas as requisições subsequentes a endpoints protegidos, o Funcionário deve incluir este `tokenJWT` no cabeçalho `Authorization` (formato `Bearer tokenJWT`). O Servidor da API validará este token para autorizar a requisição.

## 2. Autenticação do Cliente

Este fluxo permite que clientes acessem endpoints específicos de consulta, utilizando seu CPF como forma de identificação e autenticação simplificada.

![Diagrama de Sequência - Autenticação do Cliente](cliente_auth.png)

### Explicação do Fluxo:

1.  **Requisição de Autenticação:** O Cliente envia uma requisição `POST` para o endpoint `/auth/cliente` da API, fornecendo seu `cpf`.
2.  **Verificação do Cliente:** O Servidor da API encaminha o `cpf` para a Camada de Negócio (por exemplo, um `ClienteService`), que busca o cliente correspondente na tabela de `cliente` no banco de dados.
3.  **Validação do CPF:**
    *   **CPF Encontrado:** Se um cliente com o `cpf` fornecido for encontrado, a Camada de Negócio informa à API que o cliente é válido.
    *   **CPF Não Encontrado:** Se o `cpf` não corresponder a nenhum cliente, a API retorna um erro `401 Unauthorized` ao Cliente.
4.  **Geração do JWT (se CPF Válido):** Caso o cliente seja válido, a API solicita ao Gerador de JWT a criação de um token de acesso (que pode ter permissões específicas para clientes).
5.  **Retorno do JWT:** O Gerador de JWT retorna o token gerado para a API.
6.  **Resposta ao Cliente:** A API envia o `tokenJWT` de volta ao Cliente.
7.  **Requisições de Consulta:** Para acessar endpoints de consulta protegidos, o Cliente deve incluir este `tokenJWT` no cabeçalho `Authorization`. O Servidor da API validará este token para permitir o acesso.
