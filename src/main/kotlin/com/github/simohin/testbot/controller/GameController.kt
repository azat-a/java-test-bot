package com.github.simohin.testbot.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/game")
class GameController {

    @GetMapping
    fun get(model: Model, @RequestParam gameShortName: String? = null): String {
        model.addAttribute("game", gameShortName)
        return "game"
    }
}
