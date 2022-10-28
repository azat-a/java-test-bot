package com.github.simohin.testbot.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.simohin.testbot.model.ModeInfo
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.Collections
import java.util.Locale
import java.util.logging.Logger

@Component
class MimeToLang(
    private val mapper: ObjectMapper
) {

    companion object {
        private val log = Logger.getLogger(MimeToLang::class.simpleName)
    }

    fun getLangByMime(mime: String?): String? {
        return loadModeInfo().stream()
            .filter { modeInfo ->
                if (modeInfo?.mimes != null && modeInfo.mimes.isNotEmpty()
                ) {
                    return@filter modeInfo.mimes.contains(mime)
                } else {
                    return@filter modeInfo?.mime.equals(mime)
                }
            }
            .map { modeInfo ->
                modeInfo?.name
                    ?.lowercase(Locale.getDefault())
            }
            .findFirst()
            .filter { it != null }
            .orElse("Plain Text")!!
            .lowercase(Locale.getDefault())
    }

    private fun loadModeInfo() = try {
        mapper.readValue(javaClass.classLoader
            .getResourceAsStream("mime.json"),
            object : TypeReference<List<ModeInfo?>?>() {})!!
    } catch (e: IOException) {
        log.warning(e.message)
        Collections.emptyList()
    }
}
