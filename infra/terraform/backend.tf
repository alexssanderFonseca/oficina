terraform {
  backend "s3" {
    bucket         = "tfstate-fiap-alex"
    key            = "oficina/terraform.tfstate"
    region         = "us-east-1"
    dynamodb_table = "terraform-lock"
    encrypt        = true
  }
}
