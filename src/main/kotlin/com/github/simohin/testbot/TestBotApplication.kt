package com.github.simohin.testbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class TestBotApplication

fun main(args: Array<String>) {
    runApplication<TestBotApplication>(*args)
}
