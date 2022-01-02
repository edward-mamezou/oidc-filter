このサンプルのコンセプト
=====

![図](overview.png)

# ビルド方法

```
$ mvn clean install
$ cd example
$ export OIDC_ISSUERURL=https://cognito-idp.<REGION>.amazonaws.com/<POOL ID>
$ export OIDC_BASEURL=<呼び出すサービスのベースのURL>
$ gradle clean build
$ docker build . -t example
$ docker run -e OIDC_ISSUERURL=$OIDC_ISSUERURL -e OIDC_BASEURL=$OIDC_BASEURL -p 8080:8080 example
```

# 実行

```
curl -H 'Authorization: Bearer <ID Token>' http://localhost:8080/hello
```
