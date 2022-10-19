import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.0"
    id("org.jmailen.kotlinter") version "3.12.0"
}

group = "org.yakou.lang"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven { setUrl("https://jitpack.io") }
}

dependencies {
    implementation("commons-cli:commons-cli:1.5.0")
    implementation("org.ow2.asm:asm:9.3")

    implementation("com.github.ChAoSUnItY:Nenggao:1.2.1")

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

kotlinter {
    disabledRules = arrayOf("no-wildcard-imports", "filename")
}
