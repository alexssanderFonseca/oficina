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
- **Maven** (gerenciamento de dependências)
- **JUnit 5** (testes automatizados)


---

## 📂 Estrutura do Projeto

A partir do diretório `src/main/java`, o projeto está organizado por módulos de domínio:

```text
br.com.seuusuario.oficina/
 ├── admin/                # Funcionalidades administrativas
 │   ├── clientes/         # Gestão de clientes
 │   ├── pecasinsumos/     # Gestão de peças e insumos
 │   ├── veiculos/         # Cadastro e gerenciamento de veículos
 │   ├── servicos/         # Catálogo e execução de serviços
 │   └── security/         # Segurança e autenticação
 │
 ├── orcamento/            # Criação e aprovação de orçamentos
 │
 └── ordemservico/         # Acompanhamento e execução de ordens de serviço
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
model/            # Entidades e Value Objects

```

---

## 🚀 Como Executar

> **Em breve:** instruções detalhadas para instalação e execução local.

---

## 📜 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.