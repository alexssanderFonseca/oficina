variable "region" {
  description = "Região da AWS para implantar os recursos. Escolha uma que tenha o Free Tier disponível."
  type        = string
  default     = "us-east-1"
}

variable "cluster_name" {
  description = "Nome do cluster EKS para o projeto acadêmico."
  type        = string
  default     = "projeto-academico-cluster"
}
