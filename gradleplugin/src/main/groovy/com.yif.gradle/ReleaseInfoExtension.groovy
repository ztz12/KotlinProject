package com.yif.gradle

/**
 * 与自定义插件进行参数传递
 */
class ReleaseInfoExtension{
    String versionName
    String versionCode
    String versionInfo
    String fileName

    ReleaseInfoExtension(){

    }


    @Override
    String toString() {
        """| versionName = ${versionName}
           | versionCode = ${versionCode}
           | versionInfo = ${versionInfo}
           | fileName = ${fileName}
        """.stripMargin()
    }
}