// https://asciidoctor.github.io/asciidoctor-gradle-plugin/development-3.x/user-guide/
// doc: https://asciidoctor.github.io/asciidoctor-gradle-plugin/development-3.x/
// src: https://github.com/asciidoctor/asciidoctor-gradle-plugin
// project: https://asciidoctor.org/docs/asciidoctor-gradle-plugin/

repositories {
//    jcenter() // don't delete, otherwise fails
    mavenCentral()
    mavenLocal()
}

plugins {
    id("org.asciidoctor.jvm.convert") version "3.1.0"
    id("com.github.christophpickl.link-checker") version "1.0"
}

//buildscript {
//    repositories {
//        gradlePluginPortal()
//        mavenLocal()
//        dependencies {
//            classpath "com.github.christophpickl:linkChecker:1.0"
//        }
//    }
//}

tasks {
    "asciidoctor"(org.asciidoctor.gradle.jvm.AsciidoctorTask::class) {
        doFirst {
            println("asciidoctor: source=${Locations.sourceDirectory} output=${Locations.htmlBuildOutput}")
        }
        // options(mapOf("doctype" to "book", "ruby" to "erubis"))
        attributes = mapOf(
            // https://docs.asciidoctor.org/asciidoctor/latest/html-backend/default-stylesheet/#customize-docinfo
//            "docinfo" to "shared"
            "stylesheet" to "custom.css",
        )
        sourceDir(Locations.sourceDirectory)
        setOutputDir(Locations.htmlBuildOutput)
        // https://asciidoctor.github.io/asciidoctor-gradle-plugin/master/user-guide/
        // copyAllResources
        // resources
    }
}

val copyResourcesTask = tasks.register<Copy>("copyResources") {
    doFirst {
        println("Copying resources from ${Locations.sourceDirectory} to ${Locations.htmlBuildOutput}")
    }
    from(Locations.sourceDirectory)
    include(*Constants.includedNonHtmlFileExtensions.map { "**/*.$it" }.toList().toTypedArray())
    into(Locations.htmlBuildOutput)
}

tasks.build {
    dependsOn(copyResourcesTask)
    dependsOn("asciidoctor")
}

tasks.register<GradleBuild>("deploy") {
    tasks = listOf("copyLocalDocs", "copyLocalSite")
}

tasks.register<Copy>("copyLocalDocs") {
    val targetDir = File(project.projectDir, "docs")
    doFirst {
        println("Local copy to: $targetDir")
    }
    from(Locations.htmlBuildOutput)
    into(targetDir)
}

tasks.register<Copy>("copyLocalSite") {
    doFirst {
        println("Local deploy to: ${Constants.localWebRoot}")
    }
    from(Locations.htmlBuildOutput)
    //include("**/*.jpg")
    into(Constants.localWebRoot)
}


// ./gradlew -q linkChecker
//tasks.create<LinkCheckerTask>("linkChecker") {
//    localBuildDirAbsPath = Locations.htmlBuildOutput.absolutePath
//    websiteHomePagePath = "/index.html"
//    linkCheckIgnore = setOf("https://quizlet.com/nl/603903561/psychology-flash-cards/")
//}
