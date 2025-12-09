terraform {
  backend "s3" {
    bucket         = "tfstate-fiap-alex"
    key            = "tfstate-fiap-alex-academy"
    region         = "us-east-1"
    dynamodb_table = "terraform-lock"
    encrypt        = true
  }
}
