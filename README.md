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
-   **Terraform**
-   **AWS** (deploy na cloud)


---

## Documentação da API

A documentação da API está disponível via Swagger UI. Para acessá-la, inicie a aplicação e entre em:
```
http://localhost:8080/swagger-ui/index.html
```

---

## 🧪 Testando a API com Insomnia

Para facilitar os testes e o consumo da API, uma collection do Insomnia está disponível no projeto. Você pode importá-la diretamente no seu Insomnia a partir do seguinte arquivo:

- [**Collection Insomnia**](docs/collections/insomnia-collection.yaml)

---

## 🚀 Como Executar Localmente

Existem duas formas de executar o projeto localmente, dependendo do seu objetivo.

### Opção 1: Apenas a Aplicação (com Docker Compose)

Esta é a forma mais rápida e simples de subir a aplicação e o banco de dados, ideal para desenvolvimento focado na API.

1.  Para executar, rode o comando na raiz do projeto:

    ```bash
    docker-compose up
    ```
2.  A aplicação estará disponível em `http://localhost:8080`.

3.  Para acessar a documentação interativa e testar os endpoints, abra o Swagger UI no seu navegador:
    ```
    http://localhost:8080/swagger-ui/index.html
    ```

### Opção 2: Ambiente Kubernetes Completo (com Minikube)

Esta abordagem simula um ambiente Kubernetes real na sua máquina, sendo ideal para testar os manifestos da pasta `infra/k8s` e a interação completa dos serviços.

#### 1. Pré-requisitos

- **Docker:** [Instruções de instalação](https://docs.docker.com/engine/install/)
- **kubectl:** [Instruções de instalação](https://kubernetes.io/docs/tasks/tools/install-kubectl/)
- **Minikube:** Siga o guia de instalação oficial para o seu sistema operacional.
  - **Link Oficial:** https://minikube.sigs.k8s.io/docs/start/

#### 2. Inicie o Cluster Minikube

Abra seu terminal e execute o comando para iniciar o cluster:
```bash
minikube start
```

#### 3. Configure o Ambiente Docker

Este é o passo mais importante. Você precisa configurar seu terminal para usar o ambiente Docker de dentro do Minikube. Isso garante que a imagem que você construir estará visível para o Kubernetes.
```bash
eval $(minikube -p minikube docker-env)
```
**Dica:** Você precisará rodar este comando em cada novo terminal que abrir para interagir com o Minikube.

#### 4. Construa a Imagem da Aplicação

Com o ambiente configurado, navegue até a raiz do projeto e construa a imagem Docker.
```bash
docker build -t alexmarquesfa/oficina:latest .
```
**Importante:** O nome da imagem (`alexmarquesfa/oficina`) deve ser o mesmo que está definido no arquivo `app-deployment.yaml`. O uso da tag `:latest` faz com que o Kubernetes não tente buscar a imagem de um repositório remoto (`imagePullPolicy: IfNotPresent`).

#### 5. Aplique os Manifestos Kubernetes

Execute o script que aplica os manifestos na ordem correta, excluindo recursos específicos da nuvem:
```bash
bash scripts/apply-local-k8s.sh
```
Após alguns instantes, todos os recursos (Pods, Services, Deployments, etc.) estarão sendo criados.

#### 6. Acesse a Aplicação

Para acessar o serviço, que foi exposto como `NodePort`, use o seguinte comando do Minikube. Ele abrirá a URL diretamente no seu navegador:
```bash
minikube service oficina-app-service -n oficina
```

#### 7. (Opcional) Monitoramento e Limpeza

- **Verificar status dos Pods:** `kubectl get pods -n oficina`
- **Parar o cluster:** `minikube stop`
- **Deletar o cluster:** `minikube delete`

---

## 🏗️ Infraestrutura como Código (Terraform)

![Desenho da Arquitetura EKS](docs/eks.png)

Toda a infraestrutura na AWS necessária para rodar esta aplicação é gerenciada como código usando o Terraform. Os arquivos de configuração se encontram no diretório `infra/terraform`.

A arquitetura provisionada inclui os seguintes recursos principais:

-   **VPC:** Uma Virtual Private Cloud é criada usando o módulo `terraform-aws-modules/vpc/aws` para isolar os recursos da aplicação. Ela é configurada com sub-redes públicas e privadas.
-   **NAT Gateway:** Um NAT Gateway é habilitado para permitir que os recursos nas sub-redes privadas (como os nós do EKS) tenham acesso à internet para baixar imagens e atualizações, sem serem expostos diretamente.
-   **Cluster EKS:** O "cérebro" do Kubernetes é provisionado usando o módulo `terraform-aws-modules/eks/aws`. A configuração inclui:
    -   Acesso público ao API Server para permitir o deploy via GitHub Actions.
    -   Criptografia de segredos usando uma chave KMS existente.
    -   Autorização de acesso para o usuário do pipeline via EKS Access Entries.
-   **Node Group:** Um grupo de instâncias EC2 (`t3.micro`) é criado para servir como os nós de trabalho (workers) do cluster, onde os contêineres da aplicação efetivamente rodam.
-   **Backend Remoto (S3 + DynamoDB):** O estado do Terraform é gerenciado remotamente em um bucket S3, com travamento (locking) de estado via DynamoDB. Isso garante a segurança e consistência ao trabalhar em equipe ou com pipelines de CI/CD.

---

## 🔄 Fluxo de CI/CD (GitHub Actions)

O projeto utiliza GitHub Actions para automação de integração e deploy contínuo. Os workflows estão definidos em `.github/workflows`.

### CI - Integração Contínua (`ci-pipeline.yml`)

Este pipeline é acionado a cada `push` em uma branch com o padrão `feature/*`. Seu objetivo é garantir a qualidade e a integridade do código antes que ele seja mesclado à `main`.

-   **Etapas:**
    1.  **Build e Testes:** Compila o código Java da aplicação e executa todos os testes unitários.
    2.  **Validação Terraform:** Roda `terraform plan` para garantir que o código de infraestrutura está sintaticamente correto e é aplicável.
    3.  **Validação Docker:** Constrói a imagem Docker para garantir que o `Dockerfile` está funcionando.
    4.  **Criar Pull Request:** Se todas as etapas anteriores passarem, um Pull Request é criado automaticamente para a branch `main`, sinalizando que a feature está pronta para revisão.

### CD - Deploy Contínuo (`cd-pipeline.yml`)

Este pipeline é acionado automaticamente após um `merge` na branch `main`. Seu objetivo é colocar a nova versão da aplicação em produção no ambiente da AWS.

-   **Etapas:**
    1.  **Publicar Imagem no Docker Hub:** Constrói a imagem Docker da aplicação, a identifica com uma tag única (o hash do commit) e a envia para o Docker Hub.
    2.  **Deploy da Infraestrutura:** Roda `terraform apply` para aplicar qualquer mudança pendente na infraestrutura do EKS ou da VPC.
    3.  **Deploy da Aplicação:** Executa os seguintes passos:
        -   Usa `kustomize` para atualizar o manifesto do `Deployment` com a tag da nova imagem Docker.
        -   Usa `kubectl apply` para aplicar os manifestos Kubernetes no cluster EKS.
        -   Verifica o status do rollout para garantir que a nova versão subiu com sucesso.

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