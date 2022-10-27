package com.github.simohin.testbot.repository

import com.github.simohin.testbot.entity.GameEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface GameRepository : JpaRepository<GameEntity, Long> {

    fun findByTemplateName(templateName: String): Optional<GameEntity>
}
