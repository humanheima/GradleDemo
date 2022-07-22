package com.example.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager

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
        return TransformManager.PROJECT_ONLY
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs,
                   Collection<TransformInput> referencedInputs,
                   TransformOutputProvider outputProvider,
                   boolean isIncremental) throws IOException, TransformException, InterruptedException {
        println(TAG + "  transform start")
        if (inputs != null) {
            inputs.each { transformInput ->
                // directoryInputs代表着以源码方式依赖参与项目编译的所有目录结构及其目录下的源码文件
                // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
                transformInput.directoryInputs.each { directoryInput ->
                    File dir = directoryInput.file
                    if (dir != null) {
//                        dir.traverse { file ->
//                            println(TAG + "== find all file : " + file.name)
//                        }
                        dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
                            println(TAG + " find class: " + file.name)
                        }
                    }
                }
            }
        }
        println(TAG + "  transform finish")
    }

//    @Override
//    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
//        //super.transform(transformInvocation)
//        println(TAG + "  transformInvocation start")
//        Collection<TransformInput> transformInputs = transformInvocation.inputs;
//        if (transformInputs != null) {
//            transformInputs.each { transformInput ->
//                // directoryInputs代表着以源码方式依赖参与项目编译的所有目录结构及其目录下的源码文件
//                // 比如我们手写的类以及R.class、BuildConfig.class以及MainActivity.class等
//                transformInput.directoryInputs.each { directoryInput ->
//                    File dir = directoryInput.file
//                    if (dir != null) {
//                        dir.traverse(type: FileType.FILES, nameFilter: ~/.*\.class/) { File file ->
//                            println(TAG + " find class: " + file.name)
//                        }
//                    }
//                }
//            }
//        }
//        println(TAG + "  transformInvocation finish")
//
//    }
}