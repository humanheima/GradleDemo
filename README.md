## Android studio （windows）常用快捷键总结 ##
1. ctrl + Q 查看一个方法的声明。
2. ctrl + E 查看最近打开过的文件。
3. ctrl + shift +E 查看最近修改的文件。
4. F2或者 shift + F2 在报错的地方跳转。
5. ctrl + J 调出 Live Template。
6. ctrl + alt + 空格 调出代码提示。
7. alt + 向上箭头或者向下箭头在方法之间快速移动。
8. Ctrl+Shift+J shortcut joins two lines into one and removes unnecessary space to match your code style.
9. ctrl + H 查看一个类的继承结构。
10. ctrl + 减号 把一个方法收起来
11. ctrl + 加号 把一个方法 展开
12. ctrl shift + 减号 把所有的方法收起来。
13. ctrl shift + 加号 把所有的方法展开。
14. ctrl + 鼠标左击，或者 ctrl + alt +F7 查看一个方法或者变量在什么地方被使用。
15. ctrl + shift + backspace 回到上次编辑的地方。
16. ctrl + shift +F7 查看相同的语句，比如一个 'return null'。
17. ctrl + 鼠标左击，可以打开一个文件坐在的路径。![](C:\Users\Administrator\Desktop\img_first.png)
18. ctrl + I  实现方法。
19. name.isEmpty(),这时候，如果要用if 把前面的代码包起来，就可以在name.isEmpty()输入 ‘.’然后 按 ctrl +j.
20. When choosing a live template from a suggestion list, press Ctrl+Q to view quick documentation - to be sure you make a right choice:
21. ctrl+n 打开任意一个class文件
22. ctrl + shift +n 打开任意一个文件。
23. You can quickly find all places where a particular class, method or variable is used in the whole project by positioning the caret at the symbol's name or at its usage in code and pressing Alt+F7 (Edit | Find | Find Usages in the popup menu).
24. ctrl + I 实现抽象方法或者继承的接口的方法
25. ctrl + o 复写方法。
26. ctrl + alt + b 把鼠标放在一个抽象方法名上，可以查看什么地方实现了这个方法。
27. ctrl + alt + F 把一个局部变量变成一个成员变量.
28. ctrl + alt + v 变化如下.


    	textPhone.setText(SpUtil.getInstance().getPhone());
		//变成下面两行。
        String phone = SpUtil.getInstance().getPhone();
        textPhone.setText(phone);

29.ctrl + p ：当光标在括号里面的时候，可以查看传入的 参数类型。
30.Use Ctrl+Shift+F7 (Edit | Find | Highlight Usages in File) to quickly highlight usages of some variable in the current file.
Use F3 and Shift+F3 keys to navigate through highlighted usages.
Press Esc to remove highlighting.
31.ctrl +shift + j ：把两行合并成一行，去掉多余的空格。
32.ctrl+h 查看一个类的继承结构。
33.ctrl + shift +alt + n 查看一个成员变量或者方法。

###Gradle命令
1. gradlew -v 查看 版本信息
2. gradLew -h 查看帮助
3. gradlew build 检查依赖并编译打包
4. gradlew assembleRelease 编译release版本
5. gradlew assembleDebug 编译debug版本


 

