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









 

