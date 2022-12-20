### 



### 获取 gradle 启动参数

在 项目根目录的 gradle 文件中，打印下面的内容。

```groovy
//获取启动参数
String tskReqStr = project.gradle.getStartParameter().getTaskRequests().toString()
println(tskReqStr)
```

输出结果

```
app: build.gradle : [DefaultTaskExecutionRequest{args=[:app:assembleDebug],projectPath='null'}]
```

可以看到我们点击run 按钮之后，就是请求执行 `:app:assembleDebug` 这个task。




./gradlew app:replaceTask -Dorg.gradle.debug=true