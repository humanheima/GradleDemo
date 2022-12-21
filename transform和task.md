### Transform

以 `gradle-api4.1.3` 来进行学习。

`com.android.build.api.transform.Transform`




从 1.5.0-beta1 版本开始，Android 插件中包含了 Transform API ，它允许第三方插件在将编译后的类文件转换为 dex 文件之前对其进行操作。

android gradle plugin 提供了 transform api 用来在 .class to dex 过程中对 class 进行处理，可以理解为一种特殊的 Task，因为 transform 最终也会转化为 Task 去执行
要实现 transform 需要继承 `com.android.build.api.transform.Transform` 并实现其方法，实现了 Transform 以后，要想应用，就调用 `project.android.registerTransform()`



```
> Task :app:transformClassesWithLifeCycleTransformForDebug
LifeCycleTransform : transformInvocation start
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/8d15b5205197952adff9ec4b89ebd297/transformed/stetho-1.5.0-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/0.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/42f9c77a2e3b03a8e2230be173292399/transformed/appcompat-v7-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/1.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/modules-2/files-2.1/commons-cli/commons-cli/1.2/2bf96b7aa8b611c177d329452af1dc933e14501c/commons-cli-1.2.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/2.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/modules-2/files-2.1/com.google.code.findbugs/jsr305/2.0.1/516c03b21d50a644d538de0f0369c620989cd8f0/jsr305-2.0.1.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/3.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/11a126f3c5a4cda7d4bbc90d3572bafa/transformed/support-fragment-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/4.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/2433f726bd9c33ef99c46c045254be12/transformed/animated-vector-drawable-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/5.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/fa44ed60be1bdb3b7ffc2d56cf1153c8/transformed/support-core-ui-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/6.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/13706322de7e37a4d7d72866845b378e/transformed/support-core-utils-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/7.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/5920fc4d2dc8933590e7ba7d6e1ee70c/transformed/support-vector-drawable-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/8.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/54ed8f14468b29fadc908122f2bf0210/transformed/support-compat-27.1.1-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/9.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/modules-2/files-2.1/com.android.support/support-annotations/27.1.1/39ded76b5e1ce1c5b2688e1d25cdc20ecee32007/support-annotations-27.1.1.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/10.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/d8466e9d66ec7724b29e8636e6407645/transformed/livedata-core-1.1.0-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/11.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/530421f32013c741f468891fd63b8b59/transformed/viewmodel-1.1.0-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/12.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/f951795ec0b69a672872ed1a17de3c60/transformed/runtime-1.1.0-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/13.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/modules-2/files-2.1/android.arch.lifecycle/common/1.1.0/edf3f7bfb84a7521d0599efa3b0113a0ee90f85/common-1.1.0.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/14.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/transforms-3/046ea7ac1d00c5783534222c8f8ca8d2/transformed/runtime-1.1.0-runtime.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/15.jar
LifeCycleTransform : jarInput file path/Users/dumingwei/.gradle/caches/modules-2/files-2.1/android.arch.core/common/1.1.0/8007981f7d7540d89cd18471b8e5dcd2b4f99167/common-1.1.0.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/16.jar
LifeCycleTransform : process jarInputs end
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : process jarInputs end
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : jarInput file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/compile_and_runtime_not_namespaced_r_class_jar/debug/R.jar
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/17.jar
LifeCycleTransform : process jarInputs end
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : directoryInput file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/javac/debug/classes
LifeCycleTransform :  find class: SdkManager.class
LifeCycleTransform :  find class: MainActivity.class
LifeCycleTransform :  find class: Lg.class
LifeCycleTransform :  find class: Person.class
LifeCycleTransform :  find class: BuildConfig.class
LifeCycleTransform :  find class: App.class
LifeCycleTransform : dest file path/Users/dumingwei/OLD_AS_Project/GradleDemo/app/build/intermediates/transforms/LifeCycleTransform/debug/18
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : process jarInputs end
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : process jarInputs end
LifeCycleTransform : process directoryInputs start
LifeCycleTransform : process directoryInputs end
LifeCycleTransform : process jarInputs start
LifeCycleTransform : process jarInputs end
LifeCycleTransform :   transformInvocation finish
```