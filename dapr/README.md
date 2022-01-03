Dapr Middleware
===

# Dapr enabled Kubernetes cluster

See: [Dapr enabled Kubernetes cluster](https://docs.dapr.io/operations/hosting/kubernetes/kubernetes-deploy/)

```
dapr init -k
```

Helm チャートを使って、Nginx の Ingress Controller をインストールします。

```
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm install ingress-nginx ingress-nginx/ingress-nginx
```

Amazon Cognito Userpool の ISSUER URL、Client ID で `deploy/oidc.yaml` を編集します。

```
kubectl apply -f deploy/oidc.yaml
kubectl apply -f deploy/pipeline.yaml
```

Amazon Cognito Userpool の ISSUER URL で `deploy/exampleapp.yaml` を編集します。

```
kubectl apply -f deploy/exampleapp.yaml
kubectl apply -f deploy/ingress.yaml
```

# Test

```
curl -v -H 'Authorization: Bearer <ID TOKEN> http://<EXTERNAL IP>/v1.0/invoke/exampleapp/method/hello
```
