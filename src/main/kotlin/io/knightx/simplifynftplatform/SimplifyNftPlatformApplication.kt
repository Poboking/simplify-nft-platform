package io.knightx.simplifynftplatform

import com.feiniaojin.gracefulresponse.EnableGracefulResponse
import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties
@EnableGracefulResponse
@MapperScan("io.knightx.simplifynftplatform.po")
class SimplifyNftPlatformApplication

fun main(args: Array<String>) {
    runApplication<SimplifyNftPlatformApplication>(*args)
    println("API文档:[http://127.0.0.1:9090/doc.html]")
    println("""
  ____  _                 _ _  __             _   _ _____ _____ 
 / ___|(_)_ __ ___  _ __ | (_)/ _|_   _      | \ | |  ___|_   _|
 \___ \| | '_ ` _ \| '_ \| | | |_| | | |_____|  \| | |_    | |  
  ___) | | | | | | | |_) | | |  _| |_| |_____| |\  |  _|   | |  
 |____/|_|_| |_| |_| .__/|_|_|_|  \__, |     |_| \_|_|     |_|  
                   |_|            |___/                         
    """.trimIndent())
}