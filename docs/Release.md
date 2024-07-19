# Release

发版规则：

master 分支，tag 自动发版



发版流程

SNAPSHOT：发布 master 分支

RELEASE：从 master checkout 一个发版分支，从发版分支创建 tag，推送 tag 触发发版



版本号调整：

```shell
mvn versions:set -DnewVersion=0.0.1-SNAPSHOT 

git tag -a v0.0.1 -m "0.0.1 release"
```

发版时自动将版本切换为正式版本，然后发版



在 github 的 `Settings` -> `Secrets and variables` -> `Actions` -> `Repository secrets` 添加 maven 发布密钥：

* SONATYPE_USER。查看[sonatype](https://s01.oss.sonatype.org/#welcome) 登陆用户名，参考：[sonatype](https://issues.sonatype.org/secure/Signup!default.jspa)
* SONATYPE_PASSWORD。查看[sonatype](https://s01.oss.sonatype.org/#welcome) 登陆密码，参考：[sonatype](https://issues.sonatype.org/secure/Signup!default.jspa)
* GPG_PASSWORD。gpg 密码。创建 gpg 时需设置用户名、密码和邮箱。
* GPG_SECRET。使用 `gpg --list-secret-keys` 查看 gpg 私钥，在用 `gpg -a --export-secret-keys KEY_ID` 导出密钥，添加至 github。

参考：

* [maven发布jar 到中央仓库](https://juejin.cn/post/7089402732649381896)
* [发布Jar包到Maven中央仓库](https://github.com/xuxueli/xuxueli.github.io/blob/master/blog/notebook/9-%E5%85%B6%E4%BB%96/%E5%8F%91%E5%B8%83Jar%E5%8C%85%E5%88%B0Maven%E4%B8%AD%E5%A4%AE%E4%BB%93%E5%BA%93.md)
* [Deploy to Maven Central with Github Actions: Step-by-step guide](https://www.bitshifted.co/blog/deploy-maven-central-github-actions-step-by-step-guide/)。使用 user token

```shell
# 查看公钥
gpg --list-keys
# 查看私钥
gpg --list-secret-keys

gpg --delete-key [your key]
gpg --delete-secret-keys [your key]

gpg --keyserver hkp://keyserver.ubuntu.com --send-keys [your key]
gpg --keyserver hkp://keyserver.ubuntu.com --recv-keys [your key]
```





gpg --keyserver hkp://keyserver.ubuntu.com --recv-keys A6289E39FDCBC181ADD14994076C30ADAC85D591