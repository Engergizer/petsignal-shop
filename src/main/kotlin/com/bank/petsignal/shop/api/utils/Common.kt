package com.bank.petsignal.shop.api.utils

import com.bank.petsignal.shop.api.common.exception.CustomException
import mu.KLogger
import mu.KLogging
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.support.DefaultTransactionDefinition
import java.sql.SQLException

object Common {

    private var logger: KLogger = KLogging().logger()

    fun <T, R> T?.runCatch(block: () -> R?) : R? {
        var status: TransactionStatus? = null
        var transactionManager: PlatformTransactionManager? = null

        this?.let {
            when(it) {
                is PlatformTransactionManager -> {
                    transactionManager = it!! as PlatformTransactionManager
                    status = transactionManager!!.getTransaction(DefaultTransactionDefinition())
                }
                else -> it
            }
        }

        return runCatching {
            block()
        }.onSuccess {
            logger.debug("Common.runCatch => onSuccess => $it")
            transactionManager?.commit(status!!)
            it
        }.onFailure {
            logger.debug("Common.runCatch => onFailure => $it")
            transactionManager?.rollback(status!!)
            it
        }.getOrElse {
            when(it) {
                is EmptyResultDataAccessException -> null
                else -> throw it
            }
        }
    }
}