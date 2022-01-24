Envoy
===

![図](envoy.png)

2022年1月21日現在、opa のイメージに linux/arm64 がないため、別のサーバで動かす必要がある。

```
docker run -it --rm -p 9191:9191 -p 8181:8181 -v `pwd`:/work openpolicyagent/opa:latest-envoy run --server --log-level debug -c /work/config.yaml /work/opa.rego
```

```
docker network create envoy
```

```
docker run -it --name example --network=envoy --rm takesection/example:test
```

front-envoy.yaml の、`<REGION>`、`<POOL ID>`、`<CLIENT ID>` は使用する環境に合わせて修正してください。

```
docker run -it --name envoy --network=envoy -v `pwd`/front-envoy.yaml:/etc/front-envoy.yaml --rm -p 9901:9901 -p 8080:8080 envoyproxy/envoy:v1.21-latest -c /etc/front-envoy.yaml --service-cluster front-proxy
```

```
curl -H 'Authorization: Bearer <ID Token>' http://localhost:8080/hello
```

```
curl -v -H 'Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c' http://localhost:8080/hello
```
