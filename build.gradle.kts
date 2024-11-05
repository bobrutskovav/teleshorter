import org.springframework.boot.gradle.tasks.bundling.BootBuildImage
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("org.graalvm.buildtools.native") version "0.10.3"
}

group = "com.aleksx"
version = "1.0.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.telegram:telegrambots:6.9.7.1")
    implementation("org.apache.commons:commons-lang3:3.13.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    compileOnly("org.projectlombok:lombok")

    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
tasks.named<BootJar>("bootJar") {
    exclude("application.yaml")
}

tasks.named<BootBuildImage>("bootBuildImage") {
    imageName.set("alekssh1fter/${project.name}:${project.version}-arm")
    builder = "dashaun/builder-arm:20240403"
    environment = mapOf(
        "BP_NATIVE_IMAGE" to "true",
        "BP_EMBED_CERTS" to "true",
        "BP_NATIVE_IMAGE_BUILD_ARGUMENTS" to "--initialize-at-build-time=org.apache.commons.logging.LogFactory"
    )
    docker {
        publishRegistry {
            username = project.extra["dockerHubUser"] as String
            password = project.extra["dockerHubPass"] as String
            //token = dockerHubToken
        }
    }
}