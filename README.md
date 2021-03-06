### 学习gradle

## gradle命令
1. gradlew -v 查看 版本信息
2. gradlew -h 查看帮助
3. gradlew build 检查依赖并编译打包
4. gradlew assemblerelease 编译release版本
5. gradlew assembledebug 编译debug版本
6. gradlew :app:dependencies --configuration implementation 查看 app implementation  了那些依赖

## ADB 命令 参考链接 http://yuanfentiank789.github.io/2016/09/01/adb/
1. adb devices 列出所有连接设备 或者使用 adb devices -l 
2. adb install app\build\outputs\apk\debug\hm-debug-1.0-huawei.apk 安装apk
3. adb uninstall [-k] <package> 卸载包名为package的应用('-k' 意味着保存数据和缓存目录)
4. adb keygen keyname.jsk 生成adb公钥和私钥。
5. adb help 查看帮助命令
6. adb shell 运行shell 命令
7. sqlite3 进入shell 以后使用 sqlite3 操作数据库
8. adb shell df 查看手机磁盘空间
9. adb shell getprop ro.build.version.release 获取手机系统版本
10. adb shell pm list packages -f 查看所有已安装应用的包名
11. adb push README.md /sdcard/test/README.md 
12. adb tcpip PORT               restart adb server listening on TCP on PORT
13. adb connect HOST[:PORT]      connect to a device via TCP/IP [default port=5555]
使用adb命令启动应用
```xml
adb shell 
am start -n 包名/要启动的activity的全路径名 例如
am start -n com.hm.retrofitrxjavademo/com.hm.retrofitrxjavademo.ui.activity.MainActivity
```
查看手机型号
```xml
adb shell getprop ro.product.model
```
查看分辨率
```xml
adb shell wm size
```

```
Physical size: 1080x1920
Override size: 2160x2200

```
修改屏幕分辨率
```
wm size 2160x2200
```
修改完毕以后，可以使用`adb shell wm size`查看物理实际分辨率然后改回来。


查看屏幕密度
```xml
adb shell wm density
```
显示屏参数
```xml
adb shell dumpsys window displays
```
查看Android 系统版本
```xml
adb shell getprop ro.build.version.release
```
截图,可以使用 adb shell screencap -h 查看 screencap 命令的帮助信息
```xml
adb shell screencap -p /sdcard/sc.png 
```
录屏 可以使用 adb shell screenrecord --help
```xml
adb shell screenrecord /sdcard/filename.mp4
```

输出系统崩溃的log：

系统应用 ——>

```
adb shell dumpsys dropbox system_app_crash --print > crash.txt

adb shell dumpsys dropbox system_app_anr --print > anr.txt
```

第三方应用 ——>

```
adb shell dumpsys dropbox data_app_crash --print > crash.txt

adb shell dumpsys dropbox data_app_anr --print > anr.txt
```







 

