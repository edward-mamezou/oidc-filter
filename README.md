Usage
=====

```
$ mvn clean install
$ cd example
$ export OIDC_REGION=<Cognito のリージョン (例: ap-northeast-1)>
$ export OIDC_POOLID=<Cognito のプールID>
$ gradle clean bootRun 
```

```
curl -H 'Authorization: <ID Token>' http://localhost:8080/hello
```