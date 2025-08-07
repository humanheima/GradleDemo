


# 1. 使用 docker 环境

在 Build Steps 阶段添加一个执行脚本步骤，拉取并运行Docker镜像。

```shell
# 拉取镜像
/usr/local/bin/docker pull leilifengxingmw/jenkins_images:1.0

# 启动容器并构建 Android 项目
/usr/local/bin/docker run --rm \
  -v $WORKSPACE:/workspace \
  -v $HOME/.gradle:/root/.gradle \
  -w /workspace \
  leilifengxingmw/jenkins_images:1.0 \
  bash -c "echo 'Container started successfully'"

```

这段命令的解释

```shell
这段代码是一条 Docker 命令，用于运行一个 Docker 容器。以下是对其每一部分的详细解析：

1. **`/usr/local/bin/docker`**  
   这是 Docker 的可执行文件路径，表示通过该路径调用 Docker 命令。通常情况下，Docker 安装后可以通过全局命令 `docker` 直接调用，但这里明确指定了路径，可能是为了确保使用特定版本的 Docker 或在某些脚本环境中明确路径。

2. **`run`**  
   Docker 的子命令，用于运行一个新的容器。

3. **`--rm`**  
   这是一个选项，表示容器在退出后会自动被删除。通常用于临时运行的任务，防止容器堆积占用磁盘空间。

4. **`-v $WORKSPACE:/workspace`**  
   这是一个卷挂载（volume mount）选项：
   - `$WORKSPACE`：宿主机上的某个目录路径，`$WORKSPACE` 是一个环境变量，指向当前的工作目录（例如 Jenkins 的工作空间）。
   - `:/workspace`：容器内的目录路径。宿主机的 `$WORKSPACE` 目录会被映射到容器内的 `/workspace` 目录。
   - 作用：允许容器访问和操作宿主机上的 `$WORKSPACE` 目录，实现数据共享。

5. **`-v $HOME/.gradle:/root/.gradle`**  
   另一个卷挂载选项：
   - `$HOME/.gradle`：宿主机上用户家目录下的 `.gradle` 目录，通常用于存储 Gradle 的缓存和配置文件。
   - `:/root/.gradle`：容器内 `/root/.gradle` 目录，映射到宿主机的 `$HOME/.gradle`。
   - 作用：将宿主机的 Gradle 缓存挂载到容器中，容器内的 Gradle 构建可以复用宿主机的缓存，加快构建速度并保持配置一致性。

6. **`-w /workspace`**  
   设置容器的工作目录（working directory）为 `/workspace`。容器启动后，所有的命令都会在这个目录下执行。

7. **`leilifengxingmw/jenkins_images:1.0`**  
   这是要运行的 Docker 镜像的名称和标签：
   - `leilifengxingmw/jenkins_images`：镜像名称，可能存储在 Docker Hub 或私有仓库中，可能是为 Jenkins 构建环境定制的镜像。
   - `:1.0`：镜像的版本标签，表示使用版本 1.0 的镜像。

8. **`bash -c "echo 'Container started successfully'"`**  
   这是容器启动时执行的命令：
   - `bash`：启动一个 Bash shell。
   - `-c`：告诉 Bash 执行后面的字符串作为命令。
   - `"echo 'Container started successfully'"`：具体的命令，输出字符串 "Container started successfully" 到控制台。
   - 作用：这是一个简单的测试命令，用于验证容器是否成功启动。通常在实际使用中，这里会替换为更复杂的构建或测试命令（例如运行 Gradle 构建）。

### 总体含义
这条命令的目的是运行一个临时的 Docker 容器，基于 `leilifengxingmw/jenkins_images:1.0` 镜像，挂载宿主机的 `$WORKSPACE` 和 `$HOME/.gradle` 目录到容器中，设置工作目录为 `/workspace`，并执行一个简单的 Bash 命令输出 "Container started successfully"。  
它的典型场景可能是在 Jenkins 持续集成环境中，运行一个隔离的构建或测试任务，复用宿主机的 Gradle 缓存以提高效率。

### 可能的实际用途
- **Jenkins 构建**：这条命令可能用于 Jenkins 流水线，运行构建任务（例如编译代码、运行测试）。
- **环境隔离**：通过 Docker 容器提供一致的构建环境，避免宿主机环境差异。
- **测试容器启动**：当前命令仅输出一条消息，可能用于调试或验证容器配置是否正确。

如果有更多上下文（例如完整的 Jenkins 流水线配置），可以进一步分析其具体用途。
```

# 2. 在 Build Steps 阶段添加一个执行脚本步骤

```shell
chmod +x ci_tools/build.sh
./ci_tools/build.sh $VERSION_NAME $VERSION_CODE $BUILD_TYPE
```
执行项目下，ci_tools目录下的 build.sh 脚本。

# 3.收集打包后的产物并生成二维码

```shell
# 获取 APK 文件路径
APK_PATH="${WORKSPACE}/app/build/outputs/apk/debug/app-debug.apk"

QR_IMAGE_PATH="${WORKSPACE}/ci_tools/qrcode.png"

javac -cp ${WORKSPACE}/ci_tools/zxing-core-3.5.3.jar:${WORKSPACE}/ci_tools/zxing-javase-3.5.3.jar ${WORKSPACE}/ci_tools/GenerateQRCode.java
java -cp ${WORKSPACE}/ci_tools:${WORKSPACE}/ci_tools/zxing-core-3.5.3.jar:${WORKSPACE}/ci_tools/zxing-javase-3.5.3.jar GenerateQRCode "$APK_PATH" "$QR_IMAGE_PATH"
```

注意生成二维码，这里使用java方式，需要在ci_tools目录下引入两个jar包。`zxing-core-3.5.3.jar` 和 `zxing-javase-3.5.3.jar` 。
# 4. 打包后的操作(Post-build Actions)
添加一个 `Archive the artifacts` 步骤，收集产物。以逗号分割，支持通配符。
```shell
app/build/outputs/apk/**/*.apk,app/build/outputs/mapping/release/*.txt,ci_tools/qrcode.png
```

# 一些注意点

1. 在 Environment 设置阶段，可以使用 Inject environment variables to the build process，把一些路径传递给构建进程。

```shell
ANDROID_HOME=/Users/dumingwei/Library/Android/sdk
PATH=$PATH:/Users/dumingwei/Library/Android/sdk/tools:/Users/dumingwei/Library/Android/sdk/platform-tools
PATH=$PATH:/Library/Frameworks/Python.framework/Versions/3.10/bin/python3
```

![Environment 注入环境变量.png](Environment%20%E6%B3%A8%E5%85%A5%E7%8E%AF%E5%A2%83%E5%8F%98%E9%87%8F.png)
