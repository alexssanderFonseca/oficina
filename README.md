# 🚗 Oficina

Aplicação para simular o fluxo de execução de serviços em uma oficina mecânica, cobrindo desde o orçamento até a conclusão da ordem de serviço.

---

## 📋 Descrição

O **Oficina** é um sistema monolítico construído em **Java 21** com **Spring Boot** e **PostgreSQL**. A sua arquitetura é baseada no padrão **Ports and Adapters (Arquitetura Hexagonal)**, que isola a lógica de negócio de detalhes de infraestrutura.

O sistema foi projetado para simular processos típicos de uma oficina, como:

-   Registro e gerenciamento de clientes
-   Criação e aprovação de orçamentos
-   Emissão e acompanhamento de ordens de serviço
-   Controle de peças, insumos e serviços executados
-   Segurança e controle de acesso

A aplicação foi desenvolvida com foco em **boas práticas**, **DDD**, e uma arquitetura limpa para garantir **separação de responsabilidades**, **testabilidade** e **manutenibilidade**.

---

## 🛠️ Tecnologias Utilizadas

-   **Java 21**
-   **Spring Boot**
-   **PostgreSQL**
-   **Gradle** (gerenciamento de dependências)
-   **JUnit 5** (testes automatizados)

---

## Documentação da API

A documentação da API está disponível via Swagger UI. Para acessá-la, inicie a aplicação e entre em:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🚀 Como Executar

1.  Para executar a aplicação e o banco de dados, rode o comando na raiz do projeto:

    ```bash
    docker-compose up
    ```

---

## 📂 Estrutura do Projeto

O projeto é modularizado por contexto de negócio. A partir do diretório `src/main/java/br/com/alexsdm/postech/oficina/module`, cada módulo representa um domínio específico:

```text
cliente/
orcamento/
ordem_servico/
peca_insumo/
servico/
# ... e outros
```

### Estrutura Interna de cada Módulo (Ports and Adapters)

Cada módulo segue a arquitetura hexagonal, organizada da seguinte forma:

```text
├── core/                     # O núcleo do domínio (coração da aplicação)
│   ├── domain/               # Contém as entidades, VOs e regras de negócio puras
│   ├── port/                 # Define as "portas" (interfaces) de comunicação
│   │   ├── in/               # Portas de entrada (o que a aplicação oferece, ex: IAtualizarClienteUseCase)
│   │   └── out/              # Portas de saída (o que a aplicação precisa, ex: IClienteRepository)
│   └── usecase/              # Implementação das portas de entrada, orquestrando a lógica
│
└── adapters/                 # Implementações concretas das portas
    ├── in/                   # Adaptadores de entrada (driving adapters)
    │   └── controller/       # Ex: Controladores REST que recebem requisições HTTP
    └── out/                  # Adaptadores de saída (driven adapters)
        └── persistence/      # Ex: Implementação do repositório usando Spring Data JPA
```

-   **Core**: É o centro da aplicação, livre de dependências externas (frameworks, bancos de dados). Contém a lógica de negócio pura.
-   **Ports**: São as interfaces que definem os contratos de comunicação. As portas de entrada (`in`) são implementadas pelos `usecases`, enquanto as portas de saída (`out`) são implementadas pelos adaptadores de persistência ou clientes de outras APIs.
-   **Adapters**: São a "ponte" entre o núcleo e o mundo exterior. Eles adaptam as tecnologias específicas (como HTTP, JPA, etc.) para as interfaces definidas nas portas.

Essa estrutura garante que o núcleo da aplicação permaneça isolado e testável, independentemente das tecnologias utilizadas na camada de infraestrutura.
---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.