package com.github.simohin.testbot.service.compile.client

import com.github.simohin.testbot.service.compile.dto.CompileTask
import com.github.simohin.testbot.service.compile.dto.CompileTaskResult
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "onlineCompile", url = "https://www.compilejava.net/v1/")
interface OnlineCompileClient {

    @RequestMapping(method = [RequestMethod.POST], value = ["/compile"], produces = ["application/json"])
    fun compile(@RequestBody compileTask: CompileTask): CompileTaskResult
}
