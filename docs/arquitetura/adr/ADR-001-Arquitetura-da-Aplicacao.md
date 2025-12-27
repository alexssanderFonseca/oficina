# ADR-001: Arquitetura Hexagonal em Monólito Modular com Java e Spring Boot

## Status

Aceito

## Decisão

Desenvolver a aplicação utilizando a linguagem **Java (21)**, o framework **Spring Boot**, e uma arquitetura de **Monólito Modular**. O design de cada módulo segue os princípios da **Arquitetura Hexagonal** (Portas e Adaptadores) para garantir baixo acoplamento e alta testabilidade.

## Contexto

O projeto acadêmico necessita de uma arquitetura que seja organizada e manutenível, e que sirva como uma base sólida para uma potencial evolução futura. É fundamental demonstrar a aplicação de padrões de design modernos que promovem a separação de responsabilidades.

## Alternativas Consideradas

*   **Arquitetura de Microsserviços:** Descartada no momento inicial pela alta complexidade operacional (comunicação, observabilidade, resiliência), considerada um passo futuro, não o ponto de partida.
*   **Monólito Tradicional (não-modular):** Descartado por levar a um alto acoplamento e dificultar a manutenção e a futura migração para microsserviços.

## Justificativa da Escolha

*   **Estratégia Evolutiva (Monólito Modular):** A escolha do monólito modular é uma decisão estratégica. Ela oferece a simplicidade de desenvolvimento e deploy de um monólito no presente, mas com uma visão de futuro: os módulos são desenhados para que possam ser extraídos e convertidos em microsserviços independentes com esforço reduzido.
*   **Design de Baixo Acoplamento (Arquitetura Hexagonal):** Para garantir a independência entre os módulos, foi aplicada a Arquitetura Hexagonal. A lógica de negócio de cada módulo (o "hexágono") é isolada e não depende de detalhes de infraestrutura ou de outros módulos. A comunicação é feita por "portas" (interfaces), e os detalhes externos (APIs REST, persistência com JPA) são "adaptadores". Isso reforça o baixo acoplamento e torna o sistema flexível e altamente testável.
*   **Ecossistema Padrão de Mercado (Java & Spring Boot):** A escolha da linguagem e do framework se baseia na maturidade, vasta documentação e enorme adoção no mercado. Para um projeto acadêmico, isso significa desenvolver uma experiência prática em tecnologias altamente requisitadas.
*   **Produtividade na Camada de Dados (Spring Data JPA):** O uso de Spring Data JPA acelera o desenvolvimento ao abstrair a complexidade da camada de persistência, permitindo que a equipe foque nas regras de negócio.
*   **Eficiência no Desenvolvimento (Lombok & MapStruct):** Adoção de bibliotecas que reduzem código boilerplate, como Lombok para construtores e getters, e MapStruct para mapeamento de DTOs. Isso aumenta a legibilidade e a produtividade.

## Consequências

A arquitetura resultante é altamente organizada e preparada para o futuro, mas exige da equipe uma disciplina constante para manter os limites entre os módulos e seguir os princípios da Arquitetura Hexagonal. Qualquer desvio pode levar à perda dos benefícios de baixo acoplamento.
