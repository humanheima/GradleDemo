package com.example.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

public class LifeCyclePlugin implements Plugin<Project> {

    private String TAG = "LifeCyclePlugin "

    @Override
    void apply(Project target) {
        println("==LifeCyclePlugin apply== start ")

        def android = target.extensions.getByType(AppExtension)
        println(TAG + "register LifeCycleTransform")
        LifeCycleTransform transform = new LifeCycleTransform()
        android.registerTransform(transform)
    }

}