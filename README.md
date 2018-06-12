## android studio （windows）常用快捷键总结 ##
1. ctrl + q 查看一个方法的声明。
2. ctrl + e 查看最近打开过的文件。
3. ctrl + shift +e 查看最近修改的文件。
4. f2或者 shift + f2 在报错的地方跳转。
5. ctrl + j 调出 live template。
6. ctrl + alt + 空格 调出代码提示。
7. alt + 向上箭头或者向下箭头在方法之间快速移动。
8. ctrl+shift+j shortcut joins two lines into one and removes unnecessary space to match your code style.
9. ctrl + h 查看一个类的继承结构。
10. ctrl + 减号 把一个方法收起来
11. ctrl + 加号 把一个方法 展开
12. ctrl shift + 减号 把所有的方法收起来。
13. ctrl shift + 加号 把所有的方法展开。
14. ctrl + 鼠标左击，或者 ctrl + alt +f7 查看一个方法或者变量在什么地方被使用。
15. ctrl + shift + backspace 回到上次编辑的地方。
16. ctrl + shift +f7 查看相同的语句，比如一个 'return null'。
17. ctrl + 鼠标左击，可以打开一个文件坐在的路径。
18. ctrl + i  实现方法。
19. name.isempty(),这时候，如果要用if 把前面的代码包起来，就可以在name.isempty()输入 ‘.’然后 按 ctrl +j.
20. when choosing a live template from a suggestion list, press ctrl+q to view quick documentation - to be sure you make a right choice:
21. ctrl+n 打开任意一个class文件
22. ctrl + shift +n 打开任意一个文件。
23. you can quickly find all places where a particular class, method or variable is used in the whole project by positioning the caret at the symbol's name or at its usage in code and pressing alt+f7 (edit | find | find usages in the popup menu).
24. ctrl + i 实现抽象方法或者继承的接口的方法
25. ctrl + o 复写方法。
26. ctrl + alt + b 把鼠标放在一个抽象方法名上，可以查看什么地方实现了这个方法。
27. ctrl + alt + f 把一个局部变量变成一个成员变量.
28. ctrl + alt + v 变化如下.


    	textphone.settext(sputil.getinstance().getphone());
		//变成下面两行。
        string phone = sputil.getinstance().getphone();
        textphone.settext(phone);
29. ctrl + p ：当光标在括号里面的时候，可以查看传入的 参数类型。
30. use ctrl+shift+f7 (edit | find | highlight usages in file) to quickly highlight usages of some variable in the current file.
use f3 and shift+f3 keys to navigate through highlighted usages.
press esc to remove highlighting.
31. ctrl +shift + j ：把两行合并成一行，去掉多余的空格。
32. ctrl+h 查看一个类的继承结构。
33. ctrl + shift +alt + n 查看一个成员变量或者方法。

## gradle命令
1. gradlew -v 查看 版本信息
2. gradlew -h 查看帮助
3. gradlew build 检查依赖并编译打包
4. gradlew assemblerelease 编译release版本
5. gradlew assembledebug 编译debug版本
6. gradlew :app:dependencies --configuration implementation 查看 app implementation  了那些依赖
 

## ADB 命令
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




## APK瘦身

1. 代码混淆可以减小该文件的大小，因为混淆后的代码将较长的文件名、实例、变量、方法名等等做了简化，从而实现字节长度上的优化。
2. 删掉没有用到的代码和资源 可以借助Android Studio→Inspect Code...对工程做静态代码检查，删掉无用的代码。release 版本开启 ` minifyEnabled true shrinkResources true` 删除无用的资源
* 一个APK尽量只用一套图片，从内存占用和适配的角度考虑，这一套图建议放在xhdpi文件夹下；
* 使用tinypng等图片压缩工具对图片进行压缩；
* 使用VectorDrawable,能不用图片的就不用图片（用代码实现），比如说使用`shape`实现圆角背景色
3. 只提供对主流架构的支持，比如arm，对于mips和x86架构可以考虑不支持，这样可以大大减小APK的体积；

## 性能优化
1. 内存优化，使用Android Monitor；第三方内存泄露分析工具Leakcanary
 * 加载图片进行尺寸压缩
 * 使用Android提供的集合 SparseArray
 * 避免内存泄漏
2. 性能问题 ，可以启用严格模式，使用HierarchyViewer,还有开发者选项里的过渡绘制检测工具等
 * fragment 可以考虑使用懒加载
 * 减少布局层级
 * 使用 include 和 merge 标签
 * ViewStub的使用
 * 去掉没用的背景色，去掉Window背景
 * 使用Space：过渡绘制问题是因为绘制引起的，space标签可以只在布局文件中占位，不绘制，Space标签有对应的java类Space.java，通过阅读源码可以发现，它继承至View.java，并且复写了draw方法，该方法为空，既没有调用父类的draw方法，也没有执行自己的代码，表示该类是没有绘制操作的，但onMeasure方法正常调用，说明是有宽高的。
 
 3. 冷启动优化 优化方案 [Android 你应该知道的的应用冷启动过程分析和优化方案](http://yifeng.studio/2016/11/15/android-optimize-for-cold-start/)
    1. 开发人员唯一能做的就是在 Application 和 第一个 Activity 中，减少 onCreate() 方法的工作量，从而缩短冷启动的时间。像应用中嵌入的一些第三方 SDK，都建议在 Application 中做一些初始化工作，开发人员不妨采取懒加载的形式移除这部分代码，而在真正需要用到第三方 SDK 时再进行初始化。
    2. 通过主题设置，不显示启动时的白屏背景。新建一个主题样式，并添加如下属性：
    
    ```
    <style name="LaunchStyle" parent="AppTheme">
    <item name="android:windowIsTranslucent">true</item>
    <item name="android:windowNoTitle">true</item>
    </style>
    ```
    然后将这个主题样式设置给第一个启动的 Activity
    
    ``` 
      <activity android:name=".MainActivity"
                android:theme="@style/LaunchStyle">
                <intent-filter>
                    <action android:name="android.intent.action.MAIN"/>
    
                    <category android:name="android.intent.category.LAUNCHER"/>
                </intent-filter>
            </activity>
    ```
    再修改该 Activity 类的代码，在加载布局视图前，将主题修改回来
    
    ```
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setTheme(R.style.AppTheme);
            setContentView(R.layout.activity_main);
        }

    ```
    这种方式下：用户点击桌面图标，没有任何反应，过一段时间应用才打开。其实这里只是将白屏背景透明化或者隐藏起来而已。我们可以通过主题中的 windowBackground 属性，自定义应用启动时的窗口背景。
    
    新建一个名为 shape_launch.xml 的 drawable 文件
    ```
    <?xml version="1.0" encoding="utf-8"?>
    <layer-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:opacity="opaque">
    <item android:drawable="@color/colorPrimary"/>
    <item >
        <bitmap
            android:src="@mipmap/ic_launcher"
            android:gravity="center" />
    </item>
    </layer-list>

    ```
    然后修改 styles.xml 文件中的主题样式
    ```
    <style name="LaunchStyle" parent="AppTheme">
    	<item name="android:windowBackground">@drawable/shape_launch</item>
    </style>
    ```
    最后将这个主题设置给启动的 Activity，设置过程和上面隐藏启动窗口时的设置一样
    
    第二种，使用与主界面 UI 框架一致的 placeholder 内容，这种情况下需要计算诸如 Statusbar、Toolbar 控件的高度，shape_launch.xml 内容如下：
```java
<?xml version="1.0" encoding="utf-8"?>
<layer-list xmlns:android="http://schemas.android.com/apk/res/android"
    android:opacity="opaque">
    <item android:drawable="@color/colorPrimaryDark"/>
    <item
        android:drawable="@color/colorPrimary"
        android:top="25dp"/>
    <item
        android:top="81dp"
        android:drawable="@android:color/white">
    </item>
</layer-list>

```
这里模拟了一个高度为25dp的状态栏和一个高度为56dp的标题栏，给用户一种错觉：点击桌面图标，应用立即启动并进入主界面。
    
 









 

