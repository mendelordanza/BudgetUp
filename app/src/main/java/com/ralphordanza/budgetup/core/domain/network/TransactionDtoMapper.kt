package com.ralphordanza.budgetup.core.domain.network

import com.ralphordanza.budgetup.core.domain.DomainMapper
import com.ralphordanza.budgetup.core.domain.model.Transaction
import com.ralphordanza.budgetup.core.domain.model.Wallet

class TransactionDtoMapper : DomainMapper<TransactionDto, Transaction> {

    override fun mapToDomainModel(model: TransactionDto): Transaction {
        return Transaction(
            id = model.id,
            note = model.note,
            createdAt = model.createdAt,
            amount = model.amount,
            walletId = model.walletId,
            type = model.type
        )
    }

    override fun mapFromEntity(domainModel: Transaction): TransactionDto {
        return TransactionDto(
            id = domainModel.id,
            note = domainModel.note,
            createdAt = domainModel.createdAt,
            amount = domainModel.amount,
            walletId = domainModel.walletId,
            type = domainModel.type
        )
    }

    fun fromEntityList(initial: List<TransactionDto>): List<Transaction>{
        return initial.map { mapToDomainModel(it) }
    }
}