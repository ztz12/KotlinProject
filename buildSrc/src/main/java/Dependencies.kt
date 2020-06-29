//统一管理项目中的版本信息
object Versions {
    //build config
    const val minSDK = 19
    const val compileSDK = 28
    const val targetSDK = 28
    const val buildToolsVersion = "28.0.3"

    //App Version
    const val appVersionCode = 1
    const val appVersionName = "1.0.0"

    //Plugins
    const val androidGradlePlugin = "3.1.3"

    //Kotlin Version
    const val kotlin = "1.3.31"

    const val appcompat = "1.1.0"

    const val constraintLayout = "1.1.3"

    const val junit = "4.12"

    const val espresso = "3.2.0"

    const val runner = "1.2.0"
}

//统一管理项目中的使用依赖库
object Deps {

    //kotlin
    const val KotlinLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlin}"
    const val KotlinGradle = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"

    //Support Library
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"

    //test
    const val junit = "junit:junit:${Versions.junit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espresso}"
}