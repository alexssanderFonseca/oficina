# ADR-003: Escolha do PostgreSQL como SGBD Relacional

## Status

Aceito

## Decisão

Adotar o PostgreSQL como o Sistema de Gerenciamento de Banco de Dados (SGBD) relacional para a persistência dos dados da aplicação.

## Contexto

A aplicação gerencia dados de negócio críticos e inter-relacionados, como clientes, ordens de serviço, peças e orçamentos. A integridade e a consistência desses dados são fundamentais para o funcionamento correto do sistema e para a confiabilidade das operações da oficina.

## Alternativas Consideradas

*   **Outros SGBDs Relacionais (ex: MySQL):** Foram considerados viáveis, pois também atenderiam aos requisitos de um modelo relacional e suporte a transações. A escolha final não se deu por uma limitação técnica deles, mas por outros fatores.
*   **Bancos de Dados NoSQL (ex: MongoDB):** Foram conscientemente descartados. A decisão se baseou na necessidade de garantir estritamente as propriedades ACID e na natureza dos dados, que possuem relacionamentos bem definidos, se alinhando perfeitamente a um esquema relacional.

## Justificativa da Escolha

*   **Prioridade Máxima em ACID:** A principal diretriz técnica para a escolha foi a necessidade de transações atômicas, consistentes, isoladas e duráveis (ACID). Para um sistema de gestão onde operações como "fechar uma ordem de serviço" envolvem múltiplos passos (atualizar estoque de peças, faturar, registrar serviço), a garantia de que uma transação ou é concluída por inteiro ou é totalmente desfeita é inegociável. O PostgreSQL tem uma implementação robusta e comprovada do padrão ACID, o que o torna uma escolha segura.

*   **Alinhamento ao Teorema CAP (Foco em Consistência e Disponibilidade - CA):** O Teorema CAP postula que um sistema distribuído só pode garantir duas de três propriedades: Consistência (C), Disponibilidade (A) e Tolerância a Particionamento (P). Ao optar por um SGBD relacional único como o PostgreSQL, a arquitetura inerentemente não é distribuída, portanto não precisa se preocupar com "Particionamento" (P). A escolha, nesse cenário, é explicitamente por um sistema **CA (Consistência e Disponibilidade)**. Isso significa que a arquitetura prioriza que **toda leitura receba o dado mais recente (Consistência)** e que **o banco de dados esteja sempre pronto para operações (Disponibilidade)**.

*   **Familiaridade da Equipe e Ecossistema:** De forma pragmática, a equipe já possuía experiência com o PostgreSQL. Isso, somado à sua excelente compatibilidade com o ecossistema Java/Spring (especialmente com o Spring Data JPA/Hibernate) e por ser uma tecnologia open-source robusta e amplamente utilizada, consolidou a decisão como a de menor risco e maior produtividade.

## Consequências

A escolha por um SGBD relacional tradicional simplifica a arquitetura e o desenvolvimento inicial, garantindo forte consistência. Como consequência, a escalabilidade horizontal do banco de dados (distribuição de escritas em múltiplos nós) é mais complexa e não é um recurso nativo, o que é uma limitação aceitável dado o escopo do projeto.

## Modelo de Dados de Referência

O modelo de dados relacional detalhado, incluindo o diagrama ER e a explicação dos relacionamentos, é mantido como um documento vivo separado para refletir o estado atual do esquema.

Para detalhes, consulte o documento: [Modelo de Dados](./MODELO_DE_DADOS.md)
