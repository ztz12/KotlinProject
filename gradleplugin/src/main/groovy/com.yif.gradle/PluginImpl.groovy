package com.yif.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project

class PluginImpl implements Plugin<Project> {
    /**
     * 插件被引用要执行的方法
     * @param project 引入当前插件的project
     */
    void apply(Project project) {
        project.task('testTask')  {
            doLast {
                println "Hello gradle plugin $project.name"
                //创建plugin ，在外部通过releaseInfo来完成ReleaseInfoExtension这个类的初始化
                //创建扩展属性
                project.extensions.create('releaseInfo',ReleaseInfoExtension)
                //创建task
                project.tasks.create('releaseInfoTask',ReleaseInfoTask)
            }
        }
    }
}