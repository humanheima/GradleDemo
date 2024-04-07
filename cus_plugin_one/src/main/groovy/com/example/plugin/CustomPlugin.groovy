package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by p_dmweidu on 2022/12/20
 * Desc: 用来统计task的耗时
 */
class CustomPlugin implements Plugin<Project> {

    private Project mProject

    private Extension mExtension


    private String TAG = "CustomPlugin"

    @Override
    void apply(Project project) {
        project.extensions.create("CustomPluginExt", Extension.class)
        project.afterEvaluate {
            mExtension = project.property("CustomPluginExt") as Extension
            if (!mExtension.enable) {
                Log.printlnWithTag(TAG, "mExtension.enable = false , return")
                return
            }
            Log.printlnWithTag(TAG, "this is CusPlugin")
            mProject = project
            //注册监听，以统计任务的耗时
            //这里支持的Listener有很多，可以根据需求自行选择
            /**
             * Adds the given listener to this build. The listener may implement any of the given listener interfaces:
             *
             * <ul>
             * <li>{@link org.gradle.BuildListener}
             * <li>{@link org.gradle.api.execution.TaskExecutionGraphListener}
             * <li>{@link org.gradle.api.ProjectEvaluationListener}
             * <li>{@link org.gradle.api.execution.TaskExecutionListener}
             * <li>{@link org.gradle.api.execution.TaskActionListener}
             * <li>{@link org.gradle.api.logging.StandardOutputListener}
             * <li>{@link org.gradle.api.tasks.testing.TestListener}
             * <li>{@link org.gradle.api.tasks.testing.TestOutputListener}
             * <li>{@link org.gradle.api.artifacts.DependencyResolutionListener}
             * </ul>
             *
             * @param listener The listener to add. Does nothing if this listener has already been added.
             */
            //注意，这里的project.gradle是全局的gradle对象，而不是project内的对象
            project.gradle.addListener(new BuildTimeListener(project))
        }
    }
}