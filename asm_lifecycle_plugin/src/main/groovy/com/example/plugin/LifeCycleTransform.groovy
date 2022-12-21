package com.example.plugin

import groovy.io.FileType
import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils

class LifeCycleTransform extends Transform {

    private String TAG = "LifeCycleTransform"
    /**
     * 设置我们自定义的 Transform 对应的 Task 名称。Gradle 在编译的时候，会将这个名称显示在控制台上。
     * 比如：Task :app:transformClassesWithXXXForDebug。
     *
     * @return
     */
    @Override
    String getName() {
        return "LifeCycleTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        Log.printlnWithTag(TAG, "transformInvocation start")
        Collection<TransformInput> inputs = transformInvocation.inputs
        inputs.each { transformInput ->
            // directoryInputs代表着以源码方式依赖参与项目编译的所有目录结构及其目录下的源码文件
            // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
            Log.printlnWithTag(TAG, "process directoryInputs start")
            transformInput.directoryInputs.each { directoryInput ->
                String path = directoryInput.file.absolutePath
                Log.printlnWithTag(TAG, "directoryInput file path" + path)
                //获取输出目录
                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name,
                        directoryInput.contentTypes,
                        directoryInput.scopes,
                        Format.DIRECTORY)

                if (dest != null) {
                    dest.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                        Log.printlnWithTag(TAG," find class: " + file.name)
                    }
                }

                Log.printlnWithTag(TAG, "dest file path" + dest.absolutePath)
                // 将input的目录复制到output指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
            Log.printlnWithTag(TAG, "process directoryInputs end")

            Log.printlnWithTag(TAG, "process jarInputs start")

            // jar文件，如第三方依赖
            transformInput.jarInputs.each { jarInput ->

                String path = jarInput.file.absolutePath
                Log.printlnWithTag(TAG, "jarInput file path" + path)

                File dest = transformInvocation.outputProvider.getContentLocation(jarInput.name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)

                Log.printlnWithTag(TAG, "dest file path" + dest.absolutePath)

                FileUtils.copyFile(jarInput.file, dest)
            }
            Log.printlnWithTag(TAG, "process jarInputs end")
        }
        Log.printlnWithTag(TAG, "  transformInvocation finish")
    }

}