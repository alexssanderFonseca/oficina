data "aws_caller_identity" "current" {}

module "eks" {
  source  = "terraform-aws-modules/eks/aws"
  version = "20.8.4"
  #cluster_iam_role_arn = "arn:aws:iam::654654620628:role/c184636a4783078l13041262t1w654654-LabEksClusterRole-a7X7fEj68WuT"
  cluster_name    = var.cluster_name
  cluster_version = "1.29"

  authentication_mode = "API_AND_CONFIG_MAP"

  vpc_id     = module.vpc.vpc_id
  subnet_ids = module.vpc.private_subnets

  cluster_endpoint_public_access       = true
  cluster_endpoint_public_access_cidrs = ["0.0.0.0/0"]
  create_kms_key                       = false

  cluster_encryption_config = {
    provider_key_arn = "arn:aws:kms:${var.region}:${data.aws_caller_identity.current.account_id}:key/f00b8176-1e52-49ce-858e-5a315979c3e1"
    resources        = ["secrets"]
  }

  cluster_addons = {
    coredns = {
      most_recent = true
    }
    kube-proxy = {
      most_recent = true
    }
    vpc-cni = {
      most_recent = true
    }
    aws-ebs-csi-driver = {
      most_recent              = true
      service_account_role_arn = module.ebs_csi_driver_irsa.iam_role_arn
    }
  }

  eks_managed_node_groups = {
    academico_nodes = {
      name = "node"

      instance_types = ["t3.small"]

      min_size     = 1
      desired_size = 1
      max_size     = 1

      disk_size = 10
    }
  }

  tags = {
    "Environment" = "academico"
  }

  enable_cluster_creator_admin_permissions = true
}

module "ebs_csi_driver_irsa" {
  source  = "terraform-aws-modules/iam/aws//modules/iam-role-for-service-accounts-eks"
  version = "~> 5.0"

  role_name = "${var.cluster_name}-ebs-csi-driver"

  attach_ebs_csi_policy = true

  oidc_providers = {
    main = {
      provider_arn               = module.eks.oidc_provider_arn
      namespace_service_accounts = ["kube-system:ebs-csi-controller-sa"]
    }
  }

  tags = {
    "Environment" = "academico"
  }
}