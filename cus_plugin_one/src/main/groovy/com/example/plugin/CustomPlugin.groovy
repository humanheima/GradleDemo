package com.example.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by p_dmweidu on 2022/12/20
 * Desc: 用来统计task的耗时
 */
class CustomPlugin implements Plugin<Project> {

    private Project mProject

    @Override
    void apply(Project project) {
        println("this is CusPlugin")
        mProject = project
        //注册监听，以统计任务的耗时
        project.gradle.addListener(new BuildTimeListener(project))
    }
}