package com.wavesenterprise.sdk.node.client.blocking.ratelimit

import com.wavesenterprise.sdk.node.client.blocking.lb.mockkNodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.lb.mockkPrivacyService
import com.wavesenterprise.sdk.node.client.blocking.lb.mockkTxService
import com.wavesenterprise.sdk.node.client.blocking.node.NodeBlockingServiceFactory
import com.wavesenterprise.sdk.node.client.blocking.ratelimit.exception.TooManyRequestsException
import com.wavesenterprise.sdk.node.domain.blocking.sendDataRequest
import io.mockk.Called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RateLimitingServiceFactoryTest {

    private lateinit var rateLimitingServiceFactory: RateLimitingServiceFactory
    private lateinit var rateLimiter: DefaultRateLimiter

    private val mockkNodeBlockingServiceFactory: NodeBlockingServiceFactory = mockkNodeBlockingServiceFactory()
    private val backOffExecution: RandomDelayRateLimitingBackOff.RandomDelayRateLimitingBackOffExecution = mockk()
    private val backOff: RandomDelayRateLimitingBackOff = mockk()
    private val strategy: RateLimitingStrategy = mockk()
    private val mockkPrivacyService = mockkPrivacyService()
    private val mockkTxService = mockkTxService()

    @BeforeEach
    fun setUp() {
        every { mockkNodeBlockingServiceFactory.txService() } returns mockkTxService
        every { mockkNodeBlockingServiceFactory.privacyService() } returns mockkPrivacyService
        every { backOff.start() } returns backOffExecution

        rateLimiter = DefaultRateLimiter(
            strategy = strategy,
            backOff = backOff,
        )
        rateLimitingServiceFactory = RateLimitingServiceFactory(
            nodeBlockingServiceFactory = mockkNodeBlockingServiceFactory,
            rateLimiter = rateLimiter,
        )
    }

    @Test
    fun `shouldn't retry when first invocation is success`() {
        every { strategy.isLimitExceeded() } returns false
        val sendDataRequest = sendDataRequest()
        rateLimitingServiceFactory.privacyService().sendData(sendDataRequest)

        verifySequence {
            strategy.isLimitExceeded()
            mockkPrivacyService.sendData(sendDataRequest)
        }
    }

    @Test
    fun `should retry when invocation is failed`() {
        every { strategy.isLimitExceeded() } returnsMany listOf(true, false)
        every { backOffExecution.nextBackOff() } returns 1

        val sendDataRequest = sendDataRequest()
        rateLimitingServiceFactory.privacyService().sendData(sendDataRequest)

        verifySequence {
            strategy.isLimitExceeded()
            backOffExecution.nextBackOff()
            strategy.isLimitExceeded()
            mockkPrivacyService.sendData(sendDataRequest)
        }
    }

    @Test
    fun `shouldn't retry when back off limit exceeded`() {
        every { strategy.isLimitExceeded() } returns true
        every { backOffExecution.nextBackOff() } returns RateLimitingBackOff.STOP

        assertThrows<TooManyRequestsException> {
            rateLimitingServiceFactory.privacyService().sendData(sendDataRequest())
        }
        verifySequence {
            strategy.isLimitExceeded()
            backOffExecution.nextBackOff()
            mockkPrivacyService wasNot Called
        }
    }
}
