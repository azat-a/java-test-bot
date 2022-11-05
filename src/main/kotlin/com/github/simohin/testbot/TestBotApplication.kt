package com.github.simohin.testbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.retry.annotation.EnableRetry
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableFeignClients
@EnableRetry
@EnableScheduling
class TestBotApplication

fun main(args: Array<String>) {
    runApplication<TestBotApplication>(*args)
}
