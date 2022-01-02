このサンプルのコンセプト
=====

![図](overview.png)

# ビルド方法

```
$ mvn clean install
$ cd example
$ export OIDC_ISSUERURL=https://cognito-idp.<REGION>.amazonaws.com/<POOL ID>
$ export OIDC_BASEURL=<呼び出すサービスのベースのURL>
$ gradle clean bootRun 
```

# 実行

```
curl -H 'Authorization: <ID Token>' http://localhost:8080/hello
```
