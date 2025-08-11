# Dicionário de Dados - Sistema de Oficina Mecânica

| Termo | Definição | Domínio | Sinônimos / Observações |
|-------|-----------|---------|-------------------------|
| **Cliente** | Pessoa física ou jurídica proprietária de veículos que utiliza os serviços da oficina | Cliente | Proprietário / Identificado por CPF/CNPJ único |
| **Veiculo** | Automóvel de propriedade do cliente que receberá manutenção ou reparo | Cliente | Automóvel, Carro / Identificado por placa única |
| **VeiculoModelo** | Especificação técnica de marca, modelo e período de fabricação de veículos | Veiculo | Modelo / Define compatibilidade com peças |
| **Endereco** | Localização física completa do cliente | Cliente | Endereço / Rua, número, bairro, CEP, cidade, UF |
| **PecaInsumo** | Componente automotivo ou material consumível utilizado nos serviços | PecaInsumo | Peça, Insumo, Item / Controla quantidade em estoque |
| **Servico** | Atividade técnica executada pela oficina com preço pré-definido | Servico | Mão de obra / Ex: troca de óleo, alinhamento |
| **Orcamento** | Proposta formal de serviços com preços e prazos para aprovação do cliente | Orcamento | Proposta, Cotação / Deve ser aceito para gerar OS |
| **OrdemServico** | Documento de trabalho que autoriza e controla a execução dos serviços | OrdemServico | OS, Ordem de Trabalho / Controla todo o fluxo de execução |
| **ItemPecaOrcamento** | Peça específica com quantidade solicitada dentro de um orçamento | Orcamento | Item do Orçamento / Quantidade × preço de venda da peça |
| **ItemPecaOrdemServico** | Peça efetivamente utilizada na execução com preço praticado no momento | OrdemServico | Item da OS / Registra consumo real do estoque |
| **Status** | Estado atual da OS no fluxo de trabalho da oficina | OrdemServico | Estado da OS / RECEBIDA, EM_DIAGNOSTICO, AGUARDANDO_APROVACAO, EM_EXECUCAO, FINALIZADA, ENTREGUE |
| **OrcamentoStatus** | Estado atual do orçamento no processo comercial | Orcamento | Estado do Orçamento / CRIADO, ENVIADO, ACEITO, RECUSADO |
| **Diagnostico** | Análise técnica do veículo para identificar problemas e necessidades de reparo | OrdemServico | Análise / Gera lista de peças e serviços necessários |
| **Aceitar Orcamento** | Aprovação formal do cliente autorizando execução dos serviços propostos | Orcamento | Aprovar / Pré-requisito obrigatório para criar OS |
| **Executar Servico** | Realização efetiva dos trabalhos mecânicos no veículo | OrdemServico | Execução / Consome peças do estoque automaticamente |
| **Retirar do Estoque** | Operação de consumo de peças durante a execução da ordem de serviço | PecaInsumo | Baixa de Estoque / Reduz quantidade disponível da peça |
| **Disponibilidade de Peca** | Condição da peça ter estoque suficiente para uso | PecaInsumo | Peca Disponível / Quantidade > 0 e status ativo |
| **Finalizar OS** | Conclusão de todos os serviços programados na ordem de serviço | OrdemServico | Finalização / Permite entrega do veículo ao cliente |
| **Entregar Veiculo** | Devolução do veículo reparado ao cliente | OrdemServico | Entrega / Estado final da ordem de serviço |
| **Tempo Medio de Execucao** | Indicador de performance calculando duração média das ordens de serviço | OrdemServico | KPI / Medido em segundos entre início e finalização |
| **Valor Total** | Somatório de custos de peças e serviços em orçamentos ou ordens de serviço | Orcamento | Total Geral / Calculado automaticamente |
| **Modelo Compativel** | Relacionamento entre peça e modelos de veículos onde pode ser utilizada | PecaInsumo | Compatibilidade / Evita uso de peças incorretas |
| **Codigo Fabricante** | Identificação única da peça conforme especificação do fabricante | PecaInsumo | Part Number, Código Original / Identificação técnica |
| **Preco Custo** | Valor pago pela oficina ao adquirir a peça | PecaInsumo | Custo / Base para cálculo de margem |
| **Preco Venda** | Valor cobrado do cliente pela peça | PecaInsumo | Preço de Tabela / Usado em orçamentos e OS |