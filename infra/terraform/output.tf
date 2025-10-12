
output "cluster_name" {
  description = "O nome do cluster EKS."
  value       = module.eks.cluster_name
}

output "cluster_endpoint" {
  description = "O endpoint do servidor da API Kubernetes do cluster."
  value       = module.eks.cluster_endpoint
}

output "configure_kubectl" {
  description = "Execute este comando para configurar o kubectl e se conectar ao seu cluster EKS."
  value       = "aws eks update-kubeconfig --region ${var.region} --name ${var.cluster_name}"
}
