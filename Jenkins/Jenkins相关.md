
### Jenkins 安装
```shell script
brew install jenkins-lts
```

* 启动服务：brew services start jenkins-lts 启动服务后，在浏览器输入 http://localhost:8080/ 即可访问 Jenkins
* 重启服务：brew services restart jenkins-lts
* 停止服务：brew services stop jenkins-lts

### 使用的时候遇到的问题

```text
SDK location not found. Define location with an ANDROID_SDK_ROOT environment variable or by setting the sdk.dir path in your project's local properties file at '/Users/dumingwei/.jenkins/workspace/GradleDemo/local.properties'.
```

解决方法，需要设置 ANDROID_SDK_ROOT 这个全局变量 。

![配置全局变量](配置全局变量.png)

注意，还要设置 ：打开你的 FreeStyle 项目配置页面，
在 Build Environment 部分，勾选 Inject environment variables to the build process。如果没有这个选项，可能是 EnvInject 插件未安装。

### 传递参数

![Jenkins参数化构建传递参数](Jenkins参数化构建传递参数.png)
![传递构建参数](传递构建参数.png)

传递的参数，可以通过在gradle.properties里面对应的名称来接收。


### 其他

* [macOS Installers for Jenkins LTS Homebrew Installer ](https://www.jenkins.io/download/lts/macos/)
* [Android Jenkins+Git+Gradle 持续集成 -- 实在太详细](https://juejin.cn/post/6844903457833353229)
* [Mac的Jenkins持续集成环境搭建](https://www.jianshu.com/p/96fa461c543b)
* [Mac下获取Homebrew安装的软件路径](https://juejin.cn/post/6844903561705291789)


## TODO 

查看已安装的 Python 包 qrcode 的版本
```
pip3 show qrcode
```

export PATH=$PATH:/Library/Frameworks/Python.framework/Versions/3.10/bin/python3



APK_URL="http://192.168.0.102:8080/apk/${BUILD_ID}/app-release.apk"
QR_IMAGE_PATH="${WORKSPACE}/qrcode.png"
java -cp ${WORKSPACE}/ci_tools:${WORKSPACE}/ci_tools/zxing-core-3.5.3.jar GenerateQRCode "$APK_URL" "$QR_IMAGE_PATH"