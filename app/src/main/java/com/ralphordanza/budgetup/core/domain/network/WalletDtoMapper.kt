package com.ralphordanza.budgetup.core.domain.network

import com.ralphordanza.budgetup.core.domain.DomainMapper
import com.ralphordanza.budgetup.core.domain.model.Wallet

class WalletDtoMapper : DomainMapper<WalletDto, Wallet> {

    override fun mapToDomainModel(model: WalletDto): Wallet {
        return Wallet(
            model.id,
            model.name,
            model.amount
        )
    }

    override fun mapFromEntity(domainModel: Wallet): WalletDto {
        return WalletDto(
            domainModel.id,
            domainModel.name,
            domainModel.amount
        )
    }

    fun fromEntityList(initial: List<WalletDto>): List<Wallet>{
        return initial.map { mapToDomainModel(it) }
    }
}