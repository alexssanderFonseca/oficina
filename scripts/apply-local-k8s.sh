#!/bin/bash
# Este script aplica os manifestos Kubernetes para o ambiente local usando o Kustomize.
# Ele foi feito para ser executado de qualquer diretório dentro do projeto.

# --- Determina o diretório raiz do projeto ---
SCRIPT_DIR=$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" &> /dev/null && pwd)
PROJECT_ROOT=$(cd -- "$SCRIPT_DIR/.." &> /dev/null && pwd)
# ---

# --- Instala o Kustomize se não estiver presente ---
if ! command -v kustomize &> /dev/null
then
    echo "Kustomize não encontrado. Instalando..."
    # O script de instalação do kustomize é executado
    curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack/install_kustomize.sh" | bash
    # Move o binário para um local no PATH
    sudo mv kustomize /usr/local/bin/
fi
# ---

echo "Aplicando a configuração local para Minikube..."
# Usamos 'kustomize build' com o load-restrictor=None para permitir o acesso ao diretório base
# e então passamos o resultado para 'kubectl apply'.
# Executamos a partir do diretório raiz para que os paths funcionem.
(cd "$PROJECT_ROOT" && kustomize build --load-restrictor=LoadRestrictionsNone infra/k8s/overlays/local | kubectl apply -f -)

# Verificação de erro do comando anterior
if [ $? -ne 0 ]; then
    echo "Erro ao aplicar a configuração do Kubernetes."
    exit 1
fi

echo ""
echo "Deploy local concluído!"
echo "Aguarde alguns instantes para os contêineres do PostgreSQL e da aplicação iniciarem."
echo ""
echo "Para acessar a aplicação, execute em um novo terminal:"
echo "minikube service oficina-app-service -n local-ns"
