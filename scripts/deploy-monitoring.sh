#!/bin/bash

# Exit immediately if a command exits with a non-zero status.
set -e

# --- Configuration ---
# The name for the Helm release
HELM_RELEASE_NAME="datadog-agent"
# The namespace for the installation (optional, default is 'default')
NAMESPACE="default"
# Default path to the values file
DEFAULT_VALUES_FILE="datadog-values.yaml"
# The name of the Kubernetes secret for the Datadog API key
K8S_SECRET_NAME="datadog-secret"

# --- Argument Parsing ---
# Initialize VALUES_FILE with the default value
VALUES_FILE=$DEFAULT_VALUES_FILE

# Parse command-line arguments
while [[ "$#" -gt 0 ]]; do
    case $1 in
        -f|--file) VALUES_FILE="$2"; shift ;;
        *) echo "Unknown parameter passed: $1"; exit 1 ;;
    esac
    shift
done

# --- Pre-flight Checks ---
# Check if DD_API_KEY is set
if [ -z "$DD_API_KEY" ]; then
  echo "Error: DD_API_KEY environment variable is not set."
  echo "Usage: DD_API_KEY=<your_api_key> ./scripts/deploy-monitoring.sh [-f <path_to_values_file>]"
  exit 1
fi

# Check for required tools
for tool in helm kubectl; do
  if ! command -v $tool &> /dev/null; then
    echo "Error: $tool is not installed. Please install it first."
    exit 1
  fi
done

# --- Deployment Steps ---

echo "1. Adding Datadog Helm repository..."
helm repo add datadog https://helm.datadoghq.com > /dev/null
helm repo update > /dev/null

echo "2. Creating Kubernetes secret for Datadog API key..."
# Delete the secret if it already exists to ensure idempotency
kubectl delete secret $K8S_SECRET_NAME --ignore-not-found=true 2>/dev/null || true # Suppress error if secret doesn't exist
kubectl create secret generic $K8S_SECRET_NAME --from-literal=api-key=$DD_API_KEY

# Uninstall any previous failed installation
echo "3. Cleaning up any previous failed installation..."
helm uninstall $HELM_RELEASE_NAME --namespace $NAMESPACE 2>/dev/null || true # Suppress error if release doesn't exist

echo "4. Deploying the Datadog Agent via Helm..."
helm install $HELM_RELEASE_NAME datadog/datadog \
  -f $VALUES_FILE \
  --namespace $NAMESPACE

echo "✅ Datadog Agent deployment initiated successfully."
echo "   Run 'kubectl get pods -n $NAMESPACE' to check the status."
