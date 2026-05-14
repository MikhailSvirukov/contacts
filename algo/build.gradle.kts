plugins {
    id("org.jetbrains.kotlin.jvm")
    application
    id("org.jlleitschuh.gradle.ktlint")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("com.example.algo.MainKt")
}

dependencies {
    implementation(kotlin("stdlib"))
}
