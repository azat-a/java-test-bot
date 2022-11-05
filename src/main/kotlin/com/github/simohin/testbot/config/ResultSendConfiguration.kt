package com.github.simohin.testbot.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalancedRetryFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.retry.backoff.BackOffPolicy
import org.springframework.retry.backoff.FixedBackOffPolicy

@Configuration
class ResultSendConfiguration(
    @Value("\${result.send.backoff.period}")
    private val backoff: Long
) {
    @Bean
    fun retryFactory(): LoadBalancedRetryFactory? {
        return object : LoadBalancedRetryFactory {
            override fun createBackOffPolicy(service: String): BackOffPolicy {
                return FixedBackOffPolicy().apply {
                    backOffPeriod = backoff
                }
            }
        }
    }
}
