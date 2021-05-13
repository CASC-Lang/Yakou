import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
        maven("https://jitpack.io")
    }

    dependencies {
        classpath("com.strumenta.antlr-kotlin:antlr-kotlin-gradle-plugin:0951069063")
    }
}

plugins {
    kotlin("jvm") version "1.4.32"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(group = "com.strumenta.antlr-kotlin", name = "antlr-kotlin-runtime-jvm", version = "0951069063")
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.12.0")
}

tasks.register<com.strumenta.antlrkotlin.gradleplugin.AntlrKotlinTask>("generateKotlinGrammarSource") {
    antlrClasspath = configurations.detachedConfiguration(
        project.dependencies.create("com.strumenta.antlr-kotlin:antlr-kotlin-target:0951069063")
    )
    maxHeapSize = "64m"
    packageName = "io.github.chaosunity.casc"
    arguments = listOf("-visitor", "-no-listener")
    source = project.objects
        .sourceDirectorySet("antlr", "antlr")
        .srcDir("src/generated/antlr").apply {
            include("*.g4")
        }
    outputDirectory = File("src/generated/kotlin")
}

tasks.getByName("compileKotlin").dependsOn("generateKotlinGrammarSource")

configure<SourceSetContainer> {
    named("main") {
        withConvention(org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet::class) {
            kotlin.srcDir("src/generated/kotlin")
        }
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        languageVersion = "1.5"
        jvmTarget = "1.8"
    }
}