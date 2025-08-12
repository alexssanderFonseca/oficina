# 🚗 Oficina

Aplicação para simular o fluxo de execução de serviços em uma oficina mecânica, cobrindo desde o orçamento até a conclusão da ordem de serviço.

---

## 📋 Descrição

O **Oficina** é um sistema monolítico no modelo **MVC**, desenvolvido em **Java 21** com **Spring Boot** e **PostgreSQL**, projetado para demonstrar e simular processos típicos de uma oficina, como:

- Registro e gerenciamento de clientes
- Criação e aprovação de orçamentos
- Emissão e acompanhamento de ordens de serviço
- Controle de peças, insumos e serviços executados
- Segurança e controle de acesso

A aplicação foi desenvolvida com foco em **boas práticas**, **DDD** (quando aplicável) e **separação clara de responsabilidades**.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot**
- **PostgreSQL**
- **Gradle** (gerenciamento de dependências)
- **JUnit 5** (testes automatizados)


---

## Documentação da API

A documentação da API está disponível via Swagger UI, que é gerada automaticamente com base nas anotações OpenAPI presentes nos controllers.

### Como acessar
## 🚀 Como Executar

1. Para executar a aplicação roda o comando na raiz do projeto:

   ```bash
    docker compose up
   ```


## 📂 Estrutura do Projeto

A partir do diretório `src/main/java`, o projeto está organizado por módulos de domínio:

```text
br.com.alexsdm.oficina/
 ├──              # Funcionalidades administrativas
 ├── clientes/         # Gestão de clientes
 ├── pecasinsumos/     # Gestão de peças e insumos
 ├── monitoramento/    # Gestão da ofina
 ├── veiculos/         # Cadastro e gerenciamento de veículos
 ├── servicos/         # Catálogo e execução de serviços
 ├── security/         # Segurança e autenticação
 ├── orcamento/        # Criação e aprovação de orçamentos
 └── ordemservico/     # Acompanhamento e execução de ordens de serviço
 ```



### Estrutura interna de cada módulo

Sempre que aplicável, cada módulo segue a seguinte organização:
```text
controller/       # Camada de entrada (API/HTTP)
service/
├── application  # Application Services (coordenação de casos de uso)
└── domain       # Domain Services (lógica de domínio)
repository/       # Interfaces de persistência
exceptions/       # Exceções específicas do módulo
entity/            # Entidades e Value Objects

```



---
## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.