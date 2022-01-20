Envoy
===

```
docker network create envoy
```

```
docker run -it --name example --network=envoy --rm takesection/example:test
```

```
docker run -it --name envoy --network=envoy -v `pwd`/front-envoy.yaml:/etc/front-envoy.yaml --rm -p 9901:9901 -p 8080:8080 envoyproxy/envoy:v1.21-latest -c /etc/front-envoy.yaml --service-cluster front-proxy
```

```
curl -H 'Authorization: Bearer <ID Token>' http://localhost:8080/hello
```
