package com.xx.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import com.xx.plugin.*

/**
 * Created by p_dmweidu on 2022/11/30
 * Desc:用来在构建过程中自定义任务 copyProcessedResources 替换夜间模式的皮肤包darkmode.7z
 * 把 copyProcessedResources 这个任务插入到 processDebugResources 之后 和 mergeDebugAssets 之前。
 */
class ReplaceAssetsPlugin implements Plugin<Project> {

    private String TAG = "ReplaceAssetsPlugin"

    Extension mExtension

    @Override
    void apply(Project project) {
        Log.printlnWithTag(TAG, "apply plugin")
        project.extensions.create("ReplaceAssetsExt", Extension.class)
//        mExtension = project.property("ReplaceAssetsExt") as Extension
//
//        if (mExtension == null) {
//            Log.printlnWithTag(TAG, "mExtension == null , return")
//            return
//        }

        /**
         * Project 的 afterEvaluate 方法 先于 Grade.taskGraph.whenReady执行
         */
        project.afterEvaluate {
            Log.printlnWithTag(TAG, "project afterEvaluate")

            mExtension = project.property("ReplaceAssetsExt") as Extension

            if (!mExtension.enable) {
                Log.printlnWithTag(TAG, "mExtension.enable = false , return")
                return
            }



            Set<Task> processDebugResourcesTask = project.getTasksByName("processDebugResources",false)

            if (processDebugResourcesTask.isEmpty()) {
                Log.printlnWithTag(TAG, "afterEvaluate 不存在任务  processDebugResources 返回")
                return
            }

            Set<Task> mergeDebugAssetsTasks = project.getTasksByName("mergeDebugAssets",false)

            if (mergeDebugAssetsTasks.isEmpty()) {
                Log.printlnWithTag(TAG, "afterEvaluate 不存在任务  mergeDebugAssets 返回")
                return
            }
            //创建任务
            def replaceTask = project.task("replaceTask", type: ReplaceTask)

            if (!processDebugResourcesTask.isEmpty() && !mergeDebugAssetsTasks.isEmpty()) {
                processDebugResourcesTask.forEach {
                    Log.printlnWithTag(TAG,"projectsEvaluated processDebugResourcesTask 任务名称：${it.name} , 路径：${it.path} ")
                }
                replaceTask.dependsOn(processDebugResourcesTask)
                mergeDebugAssetsTasks.forEach {
                    //if (it.path == ":app:mergeDebugAssets") {
                    it.dependsOn(processDebugResourcesTask, replaceTask)
                    Log.printlnWithTag(TAG, "afterEvaluate 获取 mergeDebugAssets 任务名称：${it.name} , 路径：${it.path} ")
                    // }
                }
            }
        }

        project.gradle.taskGraph.whenReady {
            Log.printlnWithTag(TAG, "all task ready")

            List<Task> allTasks = project.gradle.taskGraph.getAllTasks()

            allTasks.forEach {
                Log.printlnWithTag(TAG, "whenReady name = $it.name , path = $it.path")
            }
        }
    }


}