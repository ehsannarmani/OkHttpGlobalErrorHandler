plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
}

group = "ir.ehsannarmani"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()

}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    val retrofitVersion = "+"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
    implementation("com.squareup.retrofit2:converter-scalars:$retrofitVersion")
    implementation(project(":OkHttpGlobalErrorHandler"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}