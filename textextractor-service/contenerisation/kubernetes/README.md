

Local context
```bash
kubectl config use-context colima
```

AWS context
aws eks update-kubeconfig --name <cluster-name> --region <region>
```bash
aws eks --region eu-central-1 update-kubeconfig --name eks-cluster
```

GCP context
gcloud container clusters get-credentials <cluster-name> --region <region>
```bash
aws eks --region eu-central-1 update-kubeconfig --name eks-cluster
```

Azure context
az aks get-credentials --name <cluster-name> --resource-group <group>


```bash
kubectl create namespace estrix-textextractor-namespace
kubectl config set-context --current --namespace=estrix-textextractor-namespace
```


```bash
kubectl apply -f textextractor-svc.yaml
```

```bash
kubectl apply -f ingress.yaml
```

```bash
kubectl delete all --all
```


kubectl cluster-info



kubectl config get-contexts   # List all available clusters
kubectl config current-context
kubectl config use-context <context-name>

