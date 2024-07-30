plugins {
    id("org.springframework.boot") version "3.3.1"
    id("io.spring.dependency-management") version "1.1.5"
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "2.0.0"
    java
}

group = "io.knightx"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    maven {
        url = uri("https://jitpack.io")
    }
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    runtimeOnly("com.mysql:mysql-connector-j")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.3")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // Mybatis Plus 依赖
    implementation("com.baomidou:mybatis-plus-boot-starter:3.5.7")
    // mybatis Plus Join
    implementation("com.github.yulichang:mybatis-plus-join-boot-starter:1.4.13")
    // Graceful Response 依赖
    implementation("com.feiniaojin:graceful-response:4.0.0-boot3")
    // Sa-Token
    implementation("cn.dev33:sa-token-spring-boot3-starter:1.37.0")
    // page-helper
    implementation("com.github.pagehelper:pagehelper-spring-boot-starter:2.1.0")
    // knife4j
    implementation("com.github.xiaoymin:knife4j-openapi3-jakarta-spring-boot-starter:4.4.0")
    // 解决factoryBeanObjectType异常:spring3.2.x系列与mybtis旧版本不兼容
    implementation("org.mybatis:mybatis-spring:3.0.3")
    // hutool
    implementation("cn.hutool:hutool-all:5.8.29")
    // fisco-bcos
    implementation("org.fisco-bcos:solcJ:0.4.25.1")
    implementation("org.fisco-bcos.java-sdk:fisco-bcos-java-sdk:2.9.0")
    implementation("io.netty:netty-all:4.1.53.Final")
    // ipfs java 
    implementation("com.github.ipfs:java-ipfs-http-client:1.4.4")
    // rabbitmq 
    implementation("com.rabbitmq:amqp-client:5.21.0")
    // 解决jackson不支持Localdatetime类型问题
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
    // jackson data bing
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.2")
    // Sa-Token 整合 Redis （使用 jackson 序列化方式）
    implementation("cn.dev33:sa-token-redis-jackson:1.38.0")
    // Sa-Token 整合 Redis （使用 jackson 序列化方式）
    implementation("org.apache.commons:commons-pool2")

}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
