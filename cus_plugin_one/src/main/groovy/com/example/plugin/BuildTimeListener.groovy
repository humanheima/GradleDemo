package com.example.plugin

import org.gradle.BuildListener
import org.gradle.BuildResult
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.execution.TaskExecutionListener
import org.gradle.api.initialization.Settings
import org.gradle.api.invocation.Gradle
import org.gradle.api.tasks.TaskState

/**
 * Created by p_dmweidu on 2022/12/20
 * Desc:
 */
public class BuildTimeListener implements TaskExecutionListener, BuildListener {


    private final String TAG = "BuildTimeListener"

    Project project
    //用来记录 task 的执行时长信息
    Map<String, TaskTimeInfo> taskTimeMap = new LinkedHashMap<>()

    BuildTimeListener(Project project) {
        this.project = project
    }


    @Override
    void buildStarted(Gradle gradle) {

    }

    @Override
    void settingsEvaluated(Settings settings) {

    }

    @Override
    void projectsLoaded(Gradle gradle) {

    }

    @Override
    void projectsEvaluated(Gradle gradle) {

    }

    @Override
    void beforeExecute(Task task) {
        //task开始执行之前搜集task的信息
        TaskTimeInfo timeInfo = new TaskTimeInfo()
        timeInfo.start = System.currentTimeMillis()
        timeInfo.path = task.getPath()
        taskTimeMap.put(task.getPath(), timeInfo)
    }

    @Override
    void afterExecute(Task task, TaskState taskState) {
        //task执行完之后，记录结束时的时间
        TaskTimeInfo timeInfo = taskTimeMap.get(task.getPath())
        timeInfo.end = System.currentTimeMillis()
        //计算该 task 的执行时长
        timeInfo.total = timeInfo.end - timeInfo.start
    }

    @Override
    void buildFinished(BuildResult buildResult) {
        Log.printlnWithTag(TAG, "-----------------打印所有的任务耗时开始----------------------")
        Log.printlnWithTag(TAG, "---------------------------------------")
        Log.printlnWithTag(TAG, "build finished, now println all task execution time:")
        taskTimeMap.each { k, v -> Log.printlnWithTag(TAG, "${k}:[${v.total}ms]") }
        Log.printlnWithTag(TAG, "---------------------------------------")
        Log.printlnWithTag(TAG, "---------------------------------------")
        Log.printlnWithTag(TAG, "-----------------打印所有的任务耗时结束----------------------")
    }

    class TaskTimeInfo {
        //task执行总时长
        long total
        String path
        long start
        long end
    }

}
