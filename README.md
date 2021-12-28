このサンプルのコンセプト
=====

![図](overview.png)

# ビルド方法

```
$ mvn clean install
$ cd example
$ export OIDC_REGION=<Cognito のリージョン (例: ap-northeast-1)>
$ export OIDC_POOLID=<Cognito のプールID>
$ export OIDC_BASEURL=<呼び出すサービスのベースのURL>
$ gradle clean bootRun 
```

# 実行

```
curl -H 'Authorization: <ID Token>' http://localhost:8080/hello
```
