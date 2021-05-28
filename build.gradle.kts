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
    application
    kotlin("jvm") version "1.4.32"
}

group = "org.casclang.casc"
version = "0.1"

application.mainClass.set("org.casclang.casc.MainKt")

repositories {
    mavenCentral()
    maven("https://jitpack.io")
}

dependencies {
    compile(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation(group = "com.strumenta.antlr-kotlin", name = "antlr-kotlin-runtime-jvm", version = "0951069063")
    implementation(group = "org.apache.commons", name = "commons-lang3", version = "3.12.0")
}

tasks.register<com.strumenta.antlrkotlin.gradleplugin.AntlrKotlinTask>("generateKotlinGrammarSource") {
    antlrClasspath = configurations.detachedConfiguration(
        project.dependencies.create("com.strumenta.antlr-kotlin:antlr-kotlin-target:0951069063")
    )
    maxHeapSize = "64m"
    packageName = "org.casclang.casc"
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

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "org.casclang.casc.MainKt"
    }

    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    exclude("META-INF/versions/9/*.class")
}