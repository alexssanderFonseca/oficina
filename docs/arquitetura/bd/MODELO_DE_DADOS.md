# Modelo de Dados da Aplicação

Este documento descreve o modelo de entidade-relacionamento da aplicação, servindo como uma referência viva que evolui junto com o sistema.

## Diagrama Entidade-Relacionamento (ER)

A imagem abaixo representa o estado atual do esquema do banco de dados.

![Diagrama Entidade-Relacionamento](er.png)

## Explicação dos Relacionamentos

*   **Relacionamentos Fortes (Acoplamento dentro do mesmo módulo):**
    *   Um `CLIENTE` possui um `ENDERECO` (relação 1-para-1) e múltiplos `VEICULO`s (relação 1-para-N). Essas entidades pertencem ao mesmo módulo (`cliente`) e são fortemente acopladas através de `JoinColumn` do JPA.
    *   `ORCAMENTO` e `ORDEM_SERVICO` contêm múltiplos itens de peças e serviços (relação 1-para-N). São relacionamentos de composição, onde os itens não existem de forma independente do seu "pai".

*   **Relacionamentos "Soft" / Desacoplados (Acoplamento entre módulos):**
    *   Este é um pilar da Arquitetura Hexagonal aplicada. Entidades como `ORCAMENTO` e `ORDEM_SERVICO` **não têm um relacionamento direto via JPA** com `CLIENTE` ou `VEICULO`. Elas apenas armazenam o `Id` (FK soft).
    *   Isso significa que o módulo `orcamento`, por exemplo, não depende diretamente do código do módulo `cliente`. Ele apenas sabe que existe um "cliente com um certo ID". A busca das informações completas do cliente é responsabilidade da camada de aplicação/serviço, que orquestra a chamada entre os módulos.
    *   O mesmo padrão ocorre entre os itens (de orçamento ou OS) e as entidades mestras `PECA_INSUMO` e `SERVICO`, garantindo o baixo acoplamento entre os diferentes domínios da aplicação.
