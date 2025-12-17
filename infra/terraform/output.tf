
output "cluster_name" {
  description = "O nome do cluster EKS."
  value       = var.projectName
}


output "configure_kubectl" {
  description = "Execute este comando para configurar o kubectl e se conectar ao seu cluster EKS."
  value       = "aws eks update-kubeconfig --region ${var.region} --name ${var.cluster_name}"
}
