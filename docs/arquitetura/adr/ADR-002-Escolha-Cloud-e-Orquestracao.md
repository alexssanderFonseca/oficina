# ADR-002: Adoção da AWS e EKS para Aplicação de Conceitos de Cloud Native

## Status

Aceito

## Decisão

Adotar a AWS (Amazon Web Services) como o provedor de nuvem primário para hospedagem da aplicação e utilizar o Amazon EKS (Elastic Kubernetes Service) como orquestrador de contêineres.

## Contexto

O projeto, desenvolvido em um contexto acadêmico, visa aplicar conceitos modernos de DevOps, infraestrutura como código e orquestração de contêineres. A escolha de uma plataforma de nuvem e de um orquestrador é um requisito fundamental para demonstrar na prática essas competências. A aplicação já é containerizada (usando Docker, como visto no `Dockerfile`), e um orquestrador é essencial para automatizar deploys, gerenciar o ciclo de vida dos contêineres e permitir escalabilidade automática.

## Alternativas Consideradas

*   **Google Cloud Platform (GCP) com GKE:** Alternativa válida para explorar um ecossistema Cloud Native concorrente.
*   **Microsoft Azure com AKS:** Outra opção relevante, demonstraria competência em um terceiro grande provedor.
*   **Ambiente On-premise com Kubernetes:** Descartado por não atender ao objetivo de aprendizado de utilizar serviços de nuvem pública.

## Justificativa da Escolha

*   **Foco no Aprendizado Dirigido:** A escolha da AWS permite que a equipe aprofunde seus conhecimentos em um provedor de nuvem líder de mercado, aproveitando uma base de conhecimento já existente para explorar conceitos mais avançados (como Kubernetes gerenciado), em vez de gastar tempo com a configuração básica de um novo provedor.
*   **Recursos via AWS Academy:** A instituição de ensino, através do programa AWS Academy, oferece créditos (U$50) para utilização dos serviços da AWS. Isso não apenas viabiliza o projeto financeiramente, mas também incentiva o uso de recursos que estariam fora do *Free Tier*, permitindo uma exploração mais aprofundada e realista da plataforma sem custos para a equipe.
*   **Aplicação de Padrões de Mercado:** O objetivo acadêmico é utilizar tecnologias que são padrão na indústria. A AWS e o Kubernetes são centrais no ecossistema de software atual. O uso prático deles no projeto proporciona uma experiência relevante e valorizada no mercado de trabalho.
*   **Demonstração de Conceitos de Orquestração:** Utilizar um serviço gerenciado como o EKS permite focar nos objetivos de aprendizado sobre "deploy, escala e gerenciamento de uma aplicação em Kubernetes", abstraindo a complexidade de instalar e manter um cluster, o que seria um desvio do foco principal do trabalho.

## Consequências da Decisão

*   **Análise Prática de Vendor Lock-in:** A decisão se torna um objeto de estudo sobre o conceito de *vendor lock-in*. Permite discutir, na prática, como o uso de uma API de orquestração padrão (Kubernetes) ajuda a mitigar esse risco, mesmo usando um serviço gerenciado. É uma lição valiosa de arquitetura.
*   **Desafio Técnico como Oportunidade:** A complexidade inerente ao Kubernetes é encarada como um desafio técnico positivo, forçando o aprendizado em tópicos avançados como redes, automação com `kustomize` (já no projeto) e monitoramento.
*   **Introdução à Gestão de Custos (FinOps):** Mesmo com os créditos da AWS Academy, o acompanhamento do uso de recursos no console da AWS introduz a disciplina de "FinOps" (Cloud Financial Management), ensinando a importância de provisionar e usar recursos de forma consciente.
