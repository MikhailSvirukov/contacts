plugins {
    id("org.jetbrains.kotlin.jvm")
    application
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
