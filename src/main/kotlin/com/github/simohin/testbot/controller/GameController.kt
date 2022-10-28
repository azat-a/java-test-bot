package com.github.simohin.testbot.controller

import com.github.simohin.testbot.model.Snippet
import com.github.simohin.testbot.service.GameService
import com.github.simohin.testbot.service.MimeToLang
import com.github.simohin.testbot.service.compile.OnlineCompileService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/game")
class GameController(
    private val mimeToLang: MimeToLang,
    private val onlineCompileService: OnlineCompileService,
    private val gameService: GameService,
) {

    @GetMapping
    fun get(
        model: Model,
        @RequestParam userId: Long,
        @RequestParam userName: String,
        @RequestParam taskTemplate: String
    ): String {
        val game = gameService.find(userId, taskTemplate)
        model.addAttribute("snippet", Snippet(code = game.codeTemplate))
        return "game"
    }

    @PostMapping
    fun save(
        model: Model,
        snippet: Snippet,
        @RequestParam userId: Long,
        @RequestParam userName: String,
        @RequestParam taskTemplate: String
    ): String {
        snippet.lang = "language-" + mimeToLang.getLangByMime(snippet.mime)
        model.addAttribute("snippet", snippet)
        val result = onlineCompileService.checkCode(snippet.code!!)

        if (result.isCompileSuccess() && result.isExecutionSuccess()) {
            gameService.saveResult(userId, userName, taskTemplate, snippet)
            return "game-saved"
        }
        model.addAttribute("result", result)
        return "game-failed"
    }
}
