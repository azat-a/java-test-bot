package com.github.simohin.testbot.controller

import com.github.simohin.testbot.service.compile.OnlineCompileService
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/compile")
class CompileController(
    private val onlineCompileService: OnlineCompileService
) {

    @PostMapping
    @ResponseBody
    fun compile(@RequestBody dto: CompileDto) = onlineCompileService.checkCode(dto.code!!, dto.args!!)

    data class CompileDto(
        val code: String? = null,
        val args: List<String>? = emptyList()
    )
}
