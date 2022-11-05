package com.github.simohin.testbot.repository

import com.github.simohin.testbot.entity.UserResultEntity
import com.github.simohin.testbot.entity.UserResultId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserResultRepository : JpaRepository<UserResultEntity, UserResultId> {

    fun getByUserId(userId: Long): List<UserResultEntity>
    fun getBySent(sent: Boolean): List<UserResultEntity>
}
