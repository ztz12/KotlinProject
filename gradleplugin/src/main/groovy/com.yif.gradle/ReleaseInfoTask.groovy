package com.yif.gradle

import groovy.xml.MarkupBuilder
import org.gradle.api.tasks.TaskAction

/**
 * 自定义task 实现维护版本信息功能
 */
class ReleaseInfoTask{
    ReleaseInfoTask(){
        group = 'yif'
        description = 'update the release info'
    }

    /**
     * 执行于gradle执行阶段代码
     */
    @TaskAction
    void doAction(){
        updateInfo()
    }

    //真正将指定的Extension 类信息写入到指定文件中
    private void updateInfo(){
        //获取将要写入的信息
        String versionName = project.extensions.releaseInfo.versionName
        String versionCode = project.extensions.releaseInfo.versionCode
        String versionInfo = project.extensions.releaseInfo.versionInfo
        String fileName = project.extensions.releaseInfo.fileName
        def file = project.file(fileName)
        def sw = new StringWriter()
        def xmlBuilder = new MarkupBuilder(sw)
        if (file != null && file.size() <= 0) {
            xmlBuilder.releases {
                release ->
                    versionName(versionMsg.versionName)
                    versionCode(versionMsg.versionCode)
                    versionInfo(versionMsg.versionInfo)
            }
            file.withWriter { writer -> writer.append(sw.toString()) }
        } else {
            release {
                versionName(versionMsg.versionName)
                versionCode(versionMsg.versionCode)
                versionInfo(versionMsg.versionInfo)
            }
            def lines = file.readLines()
            def lengths = lines.size() - 1
            file.withWriter { writer ->
                lines.eachWithIndex { String line, int index ->
                    if (index != lenghts) {
                        writer.append(line + '\r\n')
                    } else if (index == lenghts) {
                        writer.append('\r\r\n' + sw.toString() + '\r\n')
                        writer.append(lines.get(lengths))
                    }
                }
            }
        }
    }
}