Para aplicar o Kustomize diretamente da sua máquina, siga estes passos:

1.  **Instale o Kustomize:**
    Se você ainda não tem o Kustomize instalado, você pode instalá-lo seguindo as instruções oficiais. Para Linux/macOS, você pode usar:
    ```bash
    curl -s "https://raw.githubusercontent.com/kubernetes-sigs/kustomize/master/hack/install_kustomize.sh" | bash
    sudo mv kustomize /usr/local/bin/kustomize
    ```
    Ou baixe o binário diretamente da página de releases do Kustomize no GitHub: `https://github.com/kubernetes-sigs/kustomize/releases` e mova-o para um diretório no seu `PATH` (e.g., `/usr/local/bin`).

2.  **Tenha acesso a um cluster Kubernetes:**
    Certifique-se de que seu `kubectl` está configurado para se conectar ao cluster Kubernetes desejado (por exemplo, Minikube, Docker Desktop Kubernetes, ou um cluster remoto). Você pode verificar isso com:
    ```bash
    kubectl config current-context
    ```

3.  **Navegue até o diretório do overlay Kustomize:**
    Vá para o diretório que contém o arquivo `kustomization.yaml` do ambiente que você deseja aplicar. Por exemplo, se você quer aplicar o overlay `local`:
    ```bash
    cd infra/k8s/overlays/local
    ```

4.  **Construa e aplique o manifesto Kustomize:**
    Use o comando `kustomize build` para gerar o manifesto final e `kubectl apply` para aplicá-lo ao seu cluster.

    *   **Para construir e visualizar o manifesto (opcional):**
        ```bash
        kustomize build .
        ```
        Isso imprimirá o YAML resultante no console.

    *   **Para construir e aplicar diretamente:**
        ```bash
        kustomize build . | kubectl apply -f -
        ```
        Este comando constrói o manifesto e o "pipa" diretamente para o `kubectl apply`, que o aplica ao seu cluster.

**Exemplo completo (para o ambiente `local`):**

```bash
# 1. Navegue para o diretório base do seu projeto
cd /home/alex/fiap/fase1/oficina

# 2. Navegue para o overlay local
cd infra/k8s/overlays/local

# 3. Construa e aplique o Kustomize
kustomize build . | kubectl apply -f -
```

**Importante:**
*   Se você tiver placeholders ou substituições de segredos como no seu pipeline de CI/CD, você precisará replicar essa lógica localmente se quiser injetar os mesmos valores. Por exemplo, se você tem `DB_PASSWORD_PLACEHOLDER`, você teria que usar `sed` ou ferramentas similares para substituí-lo antes de aplicar. No entanto, para ambientes locais de desenvolvimento, é comum que os segredos sejam gerenciados de forma diferente (e.g., via `ConfigMap`s ou segredos menos restritivos).
*   Se você usa `kustomize edit set image`, você precisaria executá-lo *antes* de `kustomize build`.

Espero que isso ajude!
