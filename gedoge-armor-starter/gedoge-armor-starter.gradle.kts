import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("io.spring.dependency-management")
    kotlin("jvm")
    `java-library`
    `maven-publish`
    signing
}

configure<io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension> {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

dependencies {
    api(project(":gedoge-armor-autoconfigure"))
    api(project(":gedoge-armor-core"))
    api("org.jetbrains.kotlin:kotlin-reflect")
    api("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    api("org.springframework.boot:spring-boot-autoconfigure")
    api("org.springframework:spring-context")
    api("org.springframework:spring-context-support")
    api("org.springframework:spring-jdbc")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    api("com.fasterxml.jackson.module:jackson-module-kotlin")
    api("com.fasterxml.jackson.module:jackson-module-parameter-names")
    api("org.slf4j:slf4j-api")
    api("org.quartz-scheduler:quartz")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.name
            version = project.version.toString()
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("${project.group}:${project.name}")
                description.set("armor core module")
                url.set("https://github.com/kebasiji/gedoge-armor")
                packaging = "jar"
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("zhang cheng")
                        email.set("aaadot@163.com")
                    }
                }
                scm {
                    connection.set("scm:git@github.com:kebasiji/gedoge-armor.git")
                    developerConnection.set("scm:git@github.com:kebasiji/gedoge-armor.git")
                    url.set("https://github.com/kebasiji/gedoge-armor")
                }
            }
        }
    }
    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials(PasswordCredentials::class)
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications["mavenJava"])
}

tasks.javadoc {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
}