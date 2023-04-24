import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("java")
    id("org.springframework.boot") version "3.0.5" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

val mapstructVersion = "1.5.3.Final"

allprojects {
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    configure<DependencyManagementExtension> {
        dependencies{
            dependency("org.mapstruct:mapstruct-processor:$mapstructVersion")
            dependency("org.mapstruct:mapstruct:$mapstructVersion")
        }
    }

    dependencies {
        implementation("org.projectlombok:lombok")
        annotationProcessor("org.projectlombok:lombok")

        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("org.springframework.boot:spring-boot-starter-webflux")
        implementation("org.springframework.boot:spring-boot-starter-validation")
    }

    if (listOf("microservices").contains(project.name)) {
        tasks["jar"].enabled = false
        tasks["bootJar"].enabled = false
    }

    if ("microservices" == project.parent?.name) {
        tasks["jar"].enabled = false
        tasks["bootJar"].enabled = true
    }
}

tasks.jar {
    enabled = false
}
