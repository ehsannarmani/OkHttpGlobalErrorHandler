plugins {
    kotlin("jvm")
    `maven-publish`
}

group = "ir.ehsannarmani"
version = "0.0.1"

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    val retrofitVersion = "+"
    implementation ("com.squareup.retrofit2:retrofit:$retrofitVersion")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}

publishing {
    publications {
        create("maven", MavenPublication::class) {
            afterEvaluate {
                from(components["java"])
                groupId = "com.github.ehsannarmani"
                artifactId = "OkHttpGlobalErrorHandler"
                version = "0.0.1"
            }
        }
    }
}