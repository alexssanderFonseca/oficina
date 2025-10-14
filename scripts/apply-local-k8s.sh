#!/bin/bash
# Este script aplica os manifestos Kubernetes necessários para rodar o ambiente localmente no Minikube.
# Ele exclui recursos específicos da nuvem, como LoadBalancer.

# Define o diretório base para os manifestos
BASE_DIR="infra/k8s"

echo "Aplicando namespace..."
kubectl apply -f "$BASE_DIR/ns.yaml"

echo "
Aplicando segredos e configurações..."
kubectl apply -f "$BASE_DIR/postgres-secret.yaml"
kubectl apply -f "$BASE_DIR/app-configmap.yaml"
kubectl apply -f "$BASE_DIR/postgres-configmap.yaml"

echo "
Aplicando recursos do PostgreSQL..."
kubectl apply -f "$BASE_DIR/postgres-pvc.yaml"
kubectl apply -f "$BASE_DIR/postgres-service.yaml"
kubectl apply -f "$BASE_DIR/postgres-deployment.yaml"

echo "
Aplicando recursos da aplicação..."
kubectl apply -f "$BASE_DIR/app-service.yaml"
kubectl apply -f "$BASE_DIR/app-deployment.yaml"
kubectl apply -f "$BASE_DIR/app-hpa.yaml"

echo "

Deploy local concluído!"
echo "Aguarde alguns instantes para os contêineres iniciarem."

echo "
Para acessar a aplicação, execute em um novo terminal:"
echo "minikube service oficina-app-service -n oficina"
