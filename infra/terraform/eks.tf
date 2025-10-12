
module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "20.8.4"

  cluster_name    = var.cluster_name
  cluster_version = "1.29"

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets


  eks_managed_node_groups = {
    academico_nodes = {
      name           = "node"

      instance_types = ["t3.micro"]


      min_size     = 1
      desired_size = 1
      max_size     = 1

      disk_size = 10
    }
  }

  tags = {
    "Environment" = "academico"
  }
}
