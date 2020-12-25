package com.ralphordanza.budgetup.core.domain.cache

import com.ralphordanza.budgetup.core.domain.DomainMapper
import com.ralphordanza.budgetup.core.domain.model.Wallet

class WalletCacheMapper : DomainMapper<WalletCache, Wallet> {
    override fun mapToDomainModel(model: WalletCache): Wallet {
        return Wallet(
            id =  model.id,
            name = model.name,
            amount = model.amount
        )
    }

    override fun mapFromEntity(domainModel: Wallet): WalletCache {
        return WalletCache(
            id =  domainModel.id,
            name = domainModel.name,
            amount = domainModel.amount
        )
    }
}