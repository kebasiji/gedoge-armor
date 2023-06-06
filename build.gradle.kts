plugins {
    id("org.springframework.boot") version "2.7.12" apply false
    id("io.spring.dependency-management") version "1.0.15.RELEASE" apply false
    kotlin("jvm") version "1.6.21" apply false
    kotlin("plugin.spring") version "1.6.21" apply false
}

group = "com.gedoge.armor"

subprojects {

    repositories {
        maven { setUrl("https://maven.aliyun.com/repository/central") }
        maven { setUrl("https://maven.aliyun.com/repository/public/") }
        maven { setUrl("https://maven.aliyun.com/repository/spring") }
        maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
        mavenCentral()
        mavenLocal()
    }

}