package com.yif.gradle

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.gradle.internal.pipeline.TransformManager
import groovy.io.FileType

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

/**
 * Transform 可以看成Gradle 编译时候的task 在.class转换成.dex文件中，会执行这些task，对所有.class文件进行抓换，
 * 转换逻辑定义在transform 中，常用的混淆，分包，.jar包合并都是transform实现的
 */
public class PluginImplTransform extends Transform {


    /**
     * 设置自定义的transform名称
     * @return
     */
    @Override
    public String getName() {
        return "PluginImplTransform";
    }

    /**
     * 项目中会有各种格式文件，这里接收文件类型，返回Set<QualifiedContent.ContentType>集合
     * ContentType 有两种值，1.CLASSES 代表只检索class文件 2.resources 代表检索Java资源文件
     * @return
     */
    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    /**
     * 规定transform自定义检索范围
     * enum Scope implements ScopeType {* PROJECT(0x01), //只是当前工程的代码
     PROJECT_LOCAL_DEPS(0x02), // 工程的本地jar
     * SUB_PROJECTS(0x04),  // 只包含子工工程
     * SUB_PROJECTS_LOCAL_DEPS(0x08), 只有子项目的本地依赖项
     EXTERNAL_LIBRARIES(0x10), 只有外部库
     TESTED_CODE(0x20), 由当前代码包含依赖项，测试代码
     PROVIDED_ONLY(0x40); 只提供本地或者远程依赖项}*
     * @return
     */
    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.PROJECT_ONLY;
    }

    /**
     * 返回值表示是否支持增量编译
     * @return
     */
    @Override
    public boolean isIncremental() {
        return false;
    }

    /**
     * 关键重要方法 获取两个数据流向
     * inputs 传过来的输入流
     * outputProvider 获取输出目录 最后将修改文件复制到输出目录，必须做，否则报错
     * @param transformInvocation
     * @throws TransformException* @throws InterruptedException* @throws IOException
     */
    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);
        //拿到所有class 文件
        Collection<TransformInput> transformInputs = transformInvocation.inputs();
        transformInputs.each { TransformInput input ->
            //directoryInputs 表示以源码的方式参与项目编译所有的目录结构及其目录下的源码格式
            //比如 手写的类，R.class buildConfig.class 以及MainActivity.class
            input.directoryInputs.each {
                DirectoryInput directoryInput ->
                    File dir = directoryInput.file
                    if (dir) {
                        //设置过滤文件为.class 文件（去除文件类型），并打印名称
                        dir.traverse(type: FileType.FILES, nameFilter: -/.*\.class/) {
                            File file ->
                                println "Find class: " + file.name
                        }
                    }
            }
        }

    }
}
